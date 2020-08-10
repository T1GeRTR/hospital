package net.thumbtack.school.hospital.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class LoginUserDtoResponse {
    @JsonIgnore
    private String sessionId;
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;

    public LoginUserDtoResponse(String sessionId, int id, String firstName, String lastName, String patronymic) {
        this.sessionId = sessionId;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
