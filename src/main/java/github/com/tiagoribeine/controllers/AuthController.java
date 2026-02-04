package github.com.tiagoribeine.controllers;

import github.com.tiagoribeine.controllers.docs.AuthControllerDocs;
import github.com.tiagoribeine.data.dto.security.AccountCredentialsDTO;
import github.com.tiagoribeine.services.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication Endpoint!") //swagger
@RestController
@RequestMapping("/auth")
public class AuthController implements AuthControllerDocs {

    @Autowired
    AuthService service;

    //Montando a ResponseEntity e devolver a Response

    @PostMapping("/signin")
    @Override
    public ResponseEntity<?> signin(@RequestBody AccountCredentialsDTO credentials){
        if(credentialsIsInvalid(credentials)) return ResponseEntity.status((HttpStatus.FORBIDDEN)).body("Invalid client request!");
        var token = service.signIn(credentials);

        if (token == null) ResponseEntity.status((HttpStatus.FORBIDDEN)).body("Invalid client request!");
        return ResponseEntity.ok().body(token);
    }

    @PutMapping("/refresh/{username}")
    @Override
    public ResponseEntity<?> refreshToken(
            @PathVariable("username") String username,
            @RequestHeader("Authorization") String refreshToken
    ){
        if(parametersAreInvalid(username, refreshToken)) return ResponseEntity.status((HttpStatus.FORBIDDEN)).body("Invalid client request!");
        var token = service.refreshToken(username, refreshToken);
        if (token == null) ResponseEntity.status((HttpStatus.FORBIDDEN)).body("Invalid client request!");
        return ResponseEntity.ok().body(token);
    }

    //Create
//@CrossOrigin(origins = {"http://localhost:8080", "https://www.erudio.com.br"})
    @PostMapping(value = "/createUser",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}, //Consome Application Json
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE} // Produz Application Json Value
    )
    @Override
    public AccountCredentialsDTO create(@RequestBody AccountCredentialsDTO credentials){ //Cria pessoa(instancia de Person) no Service
        return service.create(credentials);
    }

    private boolean parametersAreInvalid(String username, String refreshToken) {
        return StringUtils.isBlank(username) || StringUtils.isBlank(refreshToken);
    }

    private static boolean credentialsIsInvalid(AccountCredentialsDTO credentials) {
        return credentials == null ||
                StringUtils.isBlank(credentials.getUsername()) ||
                StringUtils.isBlank(credentials.getPassword());
    }



}
