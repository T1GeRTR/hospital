package net.thumbtack.school.hospital.response;

public class LoginPatientDtoResponse extends LoginUserDtoResponse {
    private String email;
    private String address;
    private String phone;

    public LoginPatientDtoResponse(String sessionId, int id, String firstName, String lastName, String patronymic, String email, String address, String phone) {
        super(sessionId, id, firstName, lastName, patronymic);
        this.email = email;
        this.address = address;
        this.phone = phone;
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
