package github.com.tiagoribeine.services;

import github.com.tiagoribeine.data.dto.PersonDTO;
import github.com.tiagoribeine.data.dto.security.AccountCredentialsDTO;
import github.com.tiagoribeine.data.dto.security.TokenDTO;
import github.com.tiagoribeine.exception.RequiredObjectIsNullException;
import github.com.tiagoribeine.model.Person;
import github.com.tiagoribeine.model.User;
import github.com.tiagoribeine.repository.UserRepository;
import github.com.tiagoribeine.security.jwt.JwtTokenProvider;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.RowSet;
import java.util.HashMap;
import java.util.Map;

import static github.com.tiagoribeine.mapper.ObjectMapper.parseObject;

@Service
public class AuthService {

    Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository repository;

    //Implementando o metodo signin
    public ResponseEntity<TokenDTO> signIn(AccountCredentialsDTO credentials) {
        //Autenticando
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getUsername(),
                        credentials.getPassword()
                )
        );

        //Recuperando o usuário do banco de dados

        var user = repository.findByUsername(credentials.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException("Username " + credentials.getUsername() + " not found!");
        }

        var token = tokenProvider.createAccessToken(
                credentials.getUsername(),
                user.getRoles()
        );
        return ResponseEntity.ok(token);
    }

    public ResponseEntity<TokenDTO> refreshToken(String username, String refreshToken) {
        var user = repository.findByUsername(username);
        TokenDTO token;

        if (user != null) {
            token = tokenProvider.refreshToken(refreshToken);
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }
        return ResponseEntity.ok(token);
    }

    private String generateHashedPassword(String password /*senha não encriptada */) {
        PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder(
                "", //Vazio -> Será gerado automaticamente
                8, // -> Comprimento da chave
                185000, // -> Número de vezes que o algoritmo será aplicado
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256 // -> Especifica o algoritmo de REST
        );

        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("pbkdf2", pbkdf2Encoder);
        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders); // args: nome do algoritmo utilizado,

        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);
        return passwordEncoder.encode(password);
    }

    public AccountCredentialsDTO create(AccountCredentialsDTO user){

        if (user == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one new user!"); //Registra uma operação normal do sistema a nivel informativo.
        var entity = new User();

        //Settando os atributos
        entity.setFullname(user.getFullname());
        entity.setUserName(user.getUsername());
        entity.setPassword(generateHashedPassword(user.getPassword()) /*grava a senha encriptada pelo metodo generateHashedPassowrd*/);
        entity.setAccountNonExpired(true);
        entity.setAccountNonLocked(true);
        entity.setCredentialsNonExpired(true);
        entity.setEnabled(true);

        // Implementando o HATEOAS - create
        var dto = repository.save(entity);
        return new AccountCredentialsDTO(dto.getUsername(), dto.getPassword(), dto.getFullname()); //Salva e ja retorna ao controller
    }
}
