package github.com.tiagoribeine.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) //Define que quando esta exceção for lançada, o HTTP status será 400 (Bad Request)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) { //Metodo Construtor
                super(message); //Passado como parâmetro quando se lança a Exception
    }
}
