package github.com.tiagoribeine.data.dto.security;

//DTO de credenciais do usuários

import java.io.Serializable;
import java.util.Objects;

public class AccountCredentialsDTO implements Serializable {
    //Será recebido como requisição do usuário

    private String username;
    private String password;
    private String fullname;

    public AccountCredentialsDTO() {
    }

    public AccountCredentialsDTO(String username, String password, String fullname) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public boolean equals(Object o) {

        if (o == null || getClass() != o.getClass()) return false;
        AccountCredentialsDTO that = (AccountCredentialsDTO) o;
        return Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getPassword(), that.getPassword()) && Objects.equals(getFullname(), that.getFullname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), getFullname());
    }
}
