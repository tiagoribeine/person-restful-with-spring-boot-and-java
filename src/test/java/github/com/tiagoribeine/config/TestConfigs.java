package github.com.tiagoribeine.config;

public interface TestConfigs {
    int SERVER_PORT = 80; //Apontando os testes de integração para o Container utilizando a mesma porta

    String HEADER_PARAM_AUTHORIZATION = "Authorization";
    String HEADER_PARAM_ORIGIN = "Origin";

    String ORIGIN_ERUDIO = "https://www.erudio.com.br";
    String ORIGIN_SEMERU = "https://www.semeru.com.br";
    String ORIGIN_LOCAL = "http://localhost:8080";
}