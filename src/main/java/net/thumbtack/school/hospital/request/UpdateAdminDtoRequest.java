package net.thumbtack.school.hospital.request;

import net.thumbtack.school.hospital.validators.annotations.MaxNameLength;
import net.thumbtack.school.hospital.validators.annotations.MinPasswordLength;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UpdateAdminDtoRequest {
    @NotNull
    @MaxNameLength
    @Pattern(regexp = "^[а-яА-ЯёЁa0-9- ]+$", message = "Only russian and english char, digits, minus and spaces")
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
    @MinPasswordLength
    private String oldPassword;
    @NotNull
    @Size(max = 50, min = 8)
    private String newPassword;

    public UpdateAdminDtoRequest(String firstName, String lastName, String patronymic, String position, String oldPassword, String newPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.position = position;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
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
}
