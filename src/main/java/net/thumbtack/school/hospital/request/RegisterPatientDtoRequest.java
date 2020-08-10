package net.thumbtack.school.hospital.request;

import net.thumbtack.school.hospital.validators.annotations.MaxNameLength;
import net.thumbtack.school.hospital.validators.annotations.MinPasswordLength;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class RegisterPatientDtoRequest {
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
    @MaxNameLength
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z0-9]+$", message = "Only russian and english char and digit")
    private String login;
    @NotNull
    @MaxNameLength
    @MinPasswordLength
    private String password;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String address;
    @NotNull
    private String phone;

    public RegisterPatientDtoRequest(String firstName, String lastName, String patronymic, String login, String password, String email, String address, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.login = login;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phone = phone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
