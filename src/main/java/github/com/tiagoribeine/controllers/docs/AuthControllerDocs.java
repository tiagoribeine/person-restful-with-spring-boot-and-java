package github.com.tiagoribeine.controllers.docs;

import github.com.tiagoribeine.data.dto.security.AccountCredentialsDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthControllerDocs {

    @Operation(summary = "Authenticates an user and returns a token")
    ResponseEntity<?> signin(AccountCredentialsDTO credentials);

    @Operation(summary = "Refresh token for authenticated user and returns a token")
    ResponseEntity<?> refreshToken(
        String username,
        String refreshToken
    );

    AccountCredentialsDTO create(@RequestBody AccountCredentialsDTO credentials);
}
