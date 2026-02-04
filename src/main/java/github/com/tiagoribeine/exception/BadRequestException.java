package github.com.tiagoribeine.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) //Define que quando esta exceção for lançada, o HTTP status será 400 (Bad Request)
public class BadRequestException extends RuntimeException {

    public BadRequestException() { //Metodo Construtor
                super("Unsupported file extension"); //Passado como parâmetro quando se lança a Exception
    }

    public BadRequestException(String message) { //Metodo Construtor
                super(message); //Passado como parâmetro quando se lança a Exception
    }
}
