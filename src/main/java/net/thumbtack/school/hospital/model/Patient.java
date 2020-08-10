package net.thumbtack.school.hospital.model;

import java.util.List;
import java.util.Objects;

public class Patient extends User {
    private String email;
    private String address;
    private String phone;
    private List<Appointment> appointments;
    private List<Comission> comissions;

    public Patient(String firstName, String lastName, String patronymic, String email, String address, String phone, String login, String password, List<Appointment> appointments, List<Comission> comissions) {
        this(0, firstName, lastName, patronymic, email, address, phone, login, password, appointments, comissions);
    }

    public Patient(int id, String firstName, String lastName, String patronymic, String email, String address, String phone, String login, String password, String type, List<Appointment> appointments, List<Comission> comissions) {
        this(id, firstName, lastName, patronymic, email, address, phone, login, password, appointments, comissions);
        setType(type);
    }

    public Patient(int id, String firstName, String lastName, String patronymic, String email, String address, String phone, String login, String password, List<Appointment> appointments, List<Comission> comissions) {
        super(id, firstName, lastName, patronymic, login, password, "PATIENT");
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.appointments = appointments;
        this.comissions = comissions;
    }

    public Patient() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        if (!super.equals(o)) return false;
        Patient patient = (Patient) o;
        return Objects.equals(getEmail(), patient.getEmail()) &&
                Objects.equals(getAddress(), patient.getAddress()) &&
                Objects.equals(getPhone(), patient.getPhone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getEmail(), getAddress(), getPhone());
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<Comission> getComissions() {
        return comissions;
    }

    public void setComissions(List<Comission> comissions) {
        this.comissions = comissions;
    }
}
