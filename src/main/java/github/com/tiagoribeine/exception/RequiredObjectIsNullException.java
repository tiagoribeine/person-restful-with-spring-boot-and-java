package github.com.tiagoribeine.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) //Define que quando esta exceção for lançada, o HTTP status será 400 (Bad Request)
public class RequiredObjectIsNullException extends RuntimeException {

    public RequiredObjectIsNullException() { //Metodo Construtor
                super("It is not allowed to persist a null object!"); //Passado como parâmetro quando se lança a Exception
    }

    public RequiredObjectIsNullException(String message) { //Metodo Construtor
                super(message); //Passado como parâmetro quando se lança a Exception
    }
}
