package net.thumbtack.school.hospital.request;

import net.thumbtack.school.hospital.validators.annotations.MaxNameLength;
import net.thumbtack.school.hospital.validators.annotations.MinPasswordLength;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class LoginUserDtoRequest {
    @NotNull
    @MaxNameLength
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z0-9]+$", message = "Only russian and english char and digits")
    private String login;
    @NotNull
    @MaxNameLength
    @MinPasswordLength
    private String password;

    public LoginUserDtoRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
