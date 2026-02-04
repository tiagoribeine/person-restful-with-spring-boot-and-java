package github.com.tiagoribeine.exception.handler;
import github.com.tiagoribeine.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController //Indica que esta classe também é um controller e retorna respostas HTTP (JSON)
@ControllerAdvice //Componente central - Cria um interceptador GLOBAL e permite tratamento global de exceções em todos os controllers do projeto Spring
/*
@ControlerAdvice Deverá ser utilizada sempre que precisamor concentrar algum tratamento que seria espalhado em todos os controllers, assim quando controller lançar uma exceção,
caso ninguém forneça um tratamento mais adequado, ela cairá no tratamento global do controller advice e assim formatará de uma forma mais elegante as exceções.
*/

public class CustomEntityResponseHandler extends ResponseEntityExceptionHandler { //Herda comportamentos padrão do Spring

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request){ // Tratamento genérico: Captura também qualquer exceção não mapeada
        ExceptionResponse response = new ExceptionResponse(
                new Date(), // Data/hora do erro
                ex.getMessage(), //Pega a mensagem da exceção original
                request.getDescription( false)); //Detalhes da requisição
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); //Internal_Server_Error -> Erro 500;
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleNotFoundExceptions(Exception ex, WebRequest request){
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                ex.getMessage(), //Pega a mensagem da exceção
                request.getDescription( false));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); //Internal_Server_Error -> Erro 500;
    }

    @ExceptionHandler(RequiredObjectIsNullException.class)
    public final ResponseEntity<ExceptionResponse> handleRequiredObjectExceptions(Exception ex, WebRequest request){
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                ex.getMessage(), //Pega a mensagem da exceção
                request.getDescription( false));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); //Internal_Server_Error -> Erro 500;
    }
    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ExceptionResponse> handleBadRequestExceptions(Exception ex, WebRequest request){
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                ex.getMessage(), //Pega a mensagem da exceção
                request.getDescription( false));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); //Internal_Server_Error -> Erro 500;
    }

    @ExceptionHandler(FileNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleFileNotFoundExceptions(Exception ex, WebRequest request){
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                ex.getMessage(), //Pega a mensagem da exceção
                request.getDescription( false));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); //Internal_Server_Error -> Erro 500;
    }
    @ExceptionHandler(FileStorageException.class)
    public final ResponseEntity<ExceptionResponse> handleFileStorageExceptions(Exception ex, WebRequest request){
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                ex.getMessage(), //Pega a mensagem da exceção
                request.getDescription( false));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); //Internal_Server_Error -> Erro 500;
    }
    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    public final ResponseEntity<ExceptionResponse> handleInvalidJwtAuthenticationExceptions(Exception ex, WebRequest request){
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                ex.getMessage(), //Pega a mensagem da exceção
                request.getDescription( false));
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN); //Internal_Server_Error -> Erro 500;
    }
}
