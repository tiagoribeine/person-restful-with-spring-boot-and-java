package github.com.tiagoribeine.integrationtests.testcontainers;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class) //Configura o Spring para usar o Initializer como inicializador do contexto de aplicação durante os testes.
public class AbstractIntegrationTest { //Classe abstrata base para testes de integração que usará containers Docker.



    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        //Classe deve ser estática pois só pode ter um container do test containers durante a execução

        // Container MySQL que será usado em todos os testes (estático para ser único)
        static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:9.1.0"); // Imagem Docker MySQL versão 9.5.0

        // Metodo que inicia o container Docker
        private static void startContainers() {
            // Usa o TestContainers para inicializar uma instância do MySQL container, esse container só existirá um durante a execução dos testes
            // Para todos os testes será utilizado o mesmo banco
            Startables.deepStart(Stream.of(mysql)).join();
        }

        // Metodo chamado pelo Spring para inicializar o contexto de aplicação
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            // 1. Inicia o container MySQL
            startContainers();

            // 2. Obtém o ambiente do contexto Spring (onde ficam as propriedades/configurações)
            ConfigurableEnvironment environment = applicationContext.getEnvironment(); //Variáveis de ambiente do application context

            // 3. Cria uma fonte de propriedades com as configurações de conexão do MySQL
            MapPropertySource testcontainers = new MapPropertySource("testcontainers",
                    (Map) createConnectionConfiguration());
            environment.getPropertySources().addFirst(testcontainers);
        }

        // Cria as configurações de conexão com o banco de dados
        private static Map<String, String> createConnectionConfiguration() {
            // Retorna um mapa com URL, usuário e senha dinâmicos gerados pelo Testcontainers
            //Banco de dados será criado dinamicamente, portanto a cada execução a URL, usuário e senha vão mudar
            return Map.of(
                "spring.datasource.url", mysql.getJdbcUrl(), // URL JDBC gerada automaticamente
                "spring.datasource.username", mysql.getUsername(), // Usuário padrão do container
                "spring.datasource.password", mysql.getPassword() // Senha padrão do container
            );
        }
    }
}
