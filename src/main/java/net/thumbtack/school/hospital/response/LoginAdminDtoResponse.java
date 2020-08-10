package net.thumbtack.school.hospital.response;

public class LoginAdminDtoResponse extends LoginUserDtoResponse {
    private String position;

    public LoginAdminDtoResponse(String sessionId, int id, String firstName, String lastName, String patronymic, String position) {
        super(sessionId, id, firstName, lastName, patronymic);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
