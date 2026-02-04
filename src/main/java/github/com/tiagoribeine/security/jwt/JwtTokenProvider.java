package github.com.tiagoribeine.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import github.com.tiagoribeine.data.dto.security.TokenDTO;
import github.com.tiagoribeine.exception.InvalidJwtAuthenticationException;
import jakarta.annotation.PostConstruct;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key:secret}") //: valor default
    private String secretKey = "secret"; //Valor default

    @Value("${security.jwt.token.expire-length:3600000}")//Tempo de duração do token
    private long validityInMilliseconds = 3600000; //Tempo default -> 1h

    @Autowired
    private UserDetailsService userDetailsService;

    Algorithm algorithm = null;

    @PostConstruct //Permite que executemos uma ação logo após iniciar o Spring
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    //Metodo responsável por gerar o Token
    public TokenDTO createAccessToken(String username, List<String> roles) {

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds); //Incrementando a data de agora com a validade
        String accessToken = getAccessToken(username, roles, now, validity); //Username, Permissões e Validade
        String refreshToken = getRefreshToken(username, roles, now, validity);

        return new TokenDTO(username, true, now, validity, accessToken, refreshToken); //Username, autenticado = true, now
    }

    public TokenDTO refreshToken(String refreshToken) {
        var token = "";
        if(refreshTokenContainsBearer(refreshToken)) {
            token = refreshToken.substring("Bearer ".length());
        }

        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        String username = decodedJWT.getSubject();
        List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
        return createAccessToken(username, roles);
    }

    private String getRefreshToken(String username, List<String> roles, Date now, Date validity) {

    //Definindo a validade
    Date refreshTokenValidity = new Date(now.getTime() + (validityInMilliseconds *3));

    //3 horas de validade -> Tempo extra para usar o refresh e renovar. Dessa forma evita-se trafegar essas informações pela rede

    return JWT.create()
            .withClaim("roles", roles)
            .withIssuedAt(now)
            .withExpiresAt(refreshTokenValidity)
            .withSubject(username)
            .sign(algorithm); //Assinando o Token
    }

    private String getAccessToken(String username, List<String> roles, Date now, Date validity) {
        String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString(); //Definindo a URL do Servidor

        return JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withSubject(username)
                .withIssuer(issuerUrl)
                .sign(algorithm); //Assinando o Token
    }

    public Authentication getAuthentication(String token) {
        DecodedJWT decodedJWT = decodedToken(token); //decodificando o Token
        UserDetails userDetails = this.userDetailsService
                .loadUserByUsername(decodedJWT.getSubject()); //Obtém a autenticação e retorna se está autenticado ou não
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private DecodedJWT decodedToken(String token) {
        //Implementando a lógica para decodificar o Token

        Algorithm alg = Algorithm.HMAC256(secretKey.getBytes());
        JWTVerifier verifier = JWT.require(alg).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT;
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization"); //Header padrão - Quando se trabalha com  autenticação, o Token sempre vem no header Authorization

        //Beaer Codigo-Do-Token
        if (refreshTokenContainsBearer(bearerToken)) { //Caso comece com "Bearer significa que é válido
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }

    private static boolean refreshTokenContainsBearer(String refreshToken) {
        return StringUtils.isNotBlank(refreshToken) && refreshToken.startsWith("Bearer ");
    }

    public boolean validateToken(String token){
        DecodedJWT decodedJWT = decodedToken(token);
        try {
            if(decodedJWT.getExpiresAt().before(new Date())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            throw new InvalidJwtAuthenticationException("Expired or Invalid JWT Token");
        }
    }
}

