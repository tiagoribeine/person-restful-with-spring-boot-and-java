package github.com.tiagoribeine.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //Define que quando esta exceção for lançada, o HTTP status será 400 (Bad Request)

public class FileStorageException extends RuntimeException {
    public FileStorageException(String message) { //Metodo Construtor
                super(message); //Passado como parâmetro quando se lança a Exception
    }

    public FileStorageException(String message, Throwable cause) { //Metodo Construtor
                super(message, cause); //Passado como parâmetro quando se lança a Exception
    }
}
