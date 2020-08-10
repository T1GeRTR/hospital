package net.thumbtack.school.hospital.request;

import net.thumbtack.school.hospital.validators.annotations.InsertScheduleValid;
import net.thumbtack.school.hospital.validators.annotations.MaxNameLength;
import net.thumbtack.school.hospital.validators.annotations.MinPasswordLength;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class RegisterAdminDtoRequest {
    @NotNull
    @MaxNameLength
    @Pattern(regexp="^[а-яА-ЯёЁa0-9- ]+$",message="Only russian and english char, digits, minus and spaces")
    private String firstName;
    @NotNull
    @MaxNameLength
    @Pattern(regexp="^[а-яА-ЯёЁa0-9- ]+$",message="Only russian and english char, digits, minus and spaces")
    private String lastName;
    @MaxNameLength
    @Pattern(regexp="^[а-яА-ЯёЁa0-9- ]+$",message="Only russian and english char, digits, minus and spaces")
    private String patronymic;
    @NotNull
    private String position;
    @NotNull
    @MaxNameLength
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z0-9]+$", message = "Only russian and english char and digit")
    private String login;
    @NotNull
    @MaxNameLength
    @MinPasswordLength
    private String password;

    public RegisterAdminDtoRequest(String firstName, String lastName, String patronymic, String position, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.position = position;
        this.login = login;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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
