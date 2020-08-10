package net.thumbtack.school.hospital.response.statictics;

public class StatisticsDoctorDto {
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String speciality;
    private String room;
    private int appointments;
    private int comissions;

    public StatisticsDoctorDto(int id, String firstName, String lastName, String patronymic, String speciality, String room, int appointments, int comissions) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.speciality = speciality;
        this.room = room;
        this.appointments = appointments;
        this.comissions = comissions;
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

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getAppointments() {
        return appointments;
    }

    public void setAppointments(int appointments) {
        this.appointments = appointments;
    }

    public int getComissions() {
        return comissions;
    }

    public void setComissions(int comissions) {
        this.comissions = comissions;
    }
}
