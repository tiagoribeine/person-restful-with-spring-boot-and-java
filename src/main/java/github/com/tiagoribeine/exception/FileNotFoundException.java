package github.com.tiagoribeine.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) //Define que quando esta exceção for lançada, o HTTP status será 400 (Bad Request)
public class FileNotFoundException extends RuntimeException {

    public FileNotFoundException(String message) {
                super(message); //Passado como parâmetro quando se lança a Exception
    }
    public FileNotFoundException(String message, Throwable cause) {
                super(message, cause);
    }
}
