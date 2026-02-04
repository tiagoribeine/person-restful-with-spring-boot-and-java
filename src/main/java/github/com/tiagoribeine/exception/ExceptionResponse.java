package github.com.tiagoribeine.exception;
import java.util.Date;

// Substitui a resposta de erro padrão do Spring

public record ExceptionResponse(
        Date timestamp, //Quando o erro ocorreu
        String message, // Mensagem amigável para o usuário
        String details //Detalhes técnicos para desenvolvedores
) {} //Retorna um Json ao invés de retornar a exceção padrão
