package github.com.tiagoribeine.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //Carrega como configurações
public class OpenApiConfig {

    @Bean //Bean: Objeto instanciado que é montado e gerenciado por um Spring
    OpenAPI customOpenAPI(){
        return new OpenAPI()
            .info(new Info()
                    .title("REST API's RESTful")
                        .version("v1")
                        .description("REST API's RESTful")
                       .termsOfService("https://github.com/tiagoribeine")
                        .license(new License()
                            .name("Apache 2.0")
                            .url("https://github.com/tiagoribeine"))
            );
    }
}
