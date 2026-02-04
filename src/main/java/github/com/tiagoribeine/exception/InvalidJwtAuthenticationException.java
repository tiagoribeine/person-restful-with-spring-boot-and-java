package github.com.tiagoribeine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN) //Define que quando esta exceção for lançada, o HTTP status será 400 (Bad Request)
public class InvalidJwtAuthenticationException extends AuthenticationException {

    public InvalidJwtAuthenticationException(String message) { //Metodo Construtor
                super(message); //Passado como parâmetro quando se lança a Exception
    }

}
