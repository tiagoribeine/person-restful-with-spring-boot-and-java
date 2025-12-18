package github.com.tiagoribeine.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //Sinaliza ao Spring que essa classe tem configurações e pode ter declaração de Beans(precisa carregar as configurações da classe ao carregar o contexto)
public class WebConfig implements WebMvcConfigurer {

    //Adicionando o CORS Globalmente
    @Value("${cors.originPatterns:default}")
    private String corsOriginPatterns = "";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        var allowedOrigins = corsOriginPatterns.split(","); //Será splitado na vírgula - isso irá gerar uma lista com as origens permitidas
        registry.addMapping("/**") //Regras de mapeamento para CORS - Nesse caso aplicaremos para toda a aplicação - Todas as requisições serão filtradas por CORS
                .allowedOrigins(allowedOrigins)
                //.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") //Filtra quais metodos HTTP serão permitidos - Options é para Angular ou React - Options é para React e Angular
                .allowedMethods("*") //Permitindo todos os metodos
                .allowCredentials(true);
    }

    //Configurando o Content Negotiation
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

        // Via HEADER PARAM http://localhost:8080/api/person/v1/2
        configurer.favorParameter(false)
                .ignoreAcceptHeader(false)
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("yaml", MediaType.APPLICATION_YAML)
        ;
    }
}
