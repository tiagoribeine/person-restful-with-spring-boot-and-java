package github.com.tiagoribeine.integrationtests.controllers.withjson;

import github.com.tiagoribeine.config.TestConfigs;
import github.com.tiagoribeine.integrationtests.dto.AccountCredentialsDTO;
import github.com.tiagoribeine.integrationtests.dto.TokenDTO;
import github.com.tiagoribeine.integrationtests.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthControllerJsonTest extends AbstractIntegrationTest {

    @LocalServerPort
    private int port;

    private static TokenDTO tokenDto;

    @BeforeAll
    static void setUp() {
        tokenDto = new TokenDTO();
    }

    @Test
    @Order(1)
    void signin() {
        AccountCredentialsDTO credentials =
                new AccountCredentialsDTO("leandro", "admin123");

        tokenDto = given()
                .basePath("/auth/signin")
                .port(port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(credentials)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenDTO.class);

        assertNotNull(tokenDto.getAccessToken());
        assertNotNull(tokenDto.getRefreshToken());
    }

    @Test
    @Order(2)
    void refreshToken() {

        tokenDto = given()
                .basePath("/auth/refresh")
                .port(port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .pathParam("username", tokenDto.getUsername())
                    .header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenDto.getRefreshToken())
                .when()
                    .put("{username}")
                    .then()
                    .statusCode(200)
                        .extract()
                        .body()
                        .as(TokenDTO.class);

        assertNotNull(tokenDto.getAccessToken());
        assertNotNull(tokenDto.getRefreshToken());
    }
}