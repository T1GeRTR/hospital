package net.thumbtack.school.hospital.response;

public class GetAdminDtoResponse extends GetUserDtoResponse {
    private String position;

    public GetAdminDtoResponse(int id, String firstName, String lastName, String patronymic, String position) {
        super(id, firstName, lastName, patronymic);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
