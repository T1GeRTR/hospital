package net.thumbtack.school.hospital.request;

import net.thumbtack.school.hospital.validators.annotations.MaxNameLength;
import net.thumbtack.school.hospital.validators.annotations.MinPasswordLength;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UpdatePatientDtoRequest {
    @NotNull
    @MaxNameLength
    @Pattern(regexp = "^[а-яА-ЯёЁa0-9- ]+$", message = "Only russian and english char, digits, minus and spaces")
    private String firstName;
    @NotNull
    @MaxNameLength
    @Pattern(regexp = "^[а-яА-ЯёЁa0-9- ]+$", message = "Only russian and english char, digits, minus and spaces")
    private String lastName;
    @MaxNameLength
    @Pattern(regexp = "^[а-яА-ЯёЁa0-9- ]+$", message = "Only russian and english char, digits, minus and spaces")
    private String patronymic;
    @NotNull
    @MaxNameLength
    @MinPasswordLength
    private String oldPassword;
    @NotNull
    @MaxNameLength
    @MinPasswordLength
    private String newPassword;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String address;
    @NotNull
    private String phone;

    public UpdatePatientDtoRequest(String firstName, String lastName, String patronymic, String oldPassword, String newPassword, String email, String address, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
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

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
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
