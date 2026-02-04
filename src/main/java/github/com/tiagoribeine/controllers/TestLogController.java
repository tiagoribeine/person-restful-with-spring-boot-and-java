package github.com.tiagoribeine.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Marca como Controller REST - expõe endpoints HTTP
@RestController
@RequestMapping("/api/test/v1")
public class TestLogController {

    // Cria um logger específico para esta classe usando SLF4J + Logback. SLF4J é a interface, Logback é a implementação (padrão do Spring Boot)
    private Logger logger = LoggerFactory.getLogger(TestLogController.class.getName());

    //Endpoint para testar diferentes níveis de log
    @GetMapping("/test") //Mapeia para HTTP GET na URL /test
    public String testLog(){

        //Testando diferentes níveis de LOG:

        // DEBUG: Informações detalhadas para desenvolvedores. Normalmente desabilitado em produção
        logger.debug("This is a DEBUG log");

        //INFO: Informações gerais sobre o funcionamento da aplicação. Útil para monitorar fluxo normal
        logger.info("This is an INFO log");

        // WARN: Situações incomuns que não quebram a aplicação. Alerta sobre possíveis problemas
        logger.warn("This is a WARN log");

        // ERROR: Erros graves que precisam de atenção. Problemas que afetam funcionalidade
        logger.error("This is an ERROR log");
        return "Logs generated successfully!";
    }
}
