package net.thumbtack.school.hospital.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class Comission {
    private int id;
    private String ticket;
    private LocalDate date;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private Patient patient;
    private List<Doctor> doctors;
    private String room;
    private List<Appointment> appointments;

    public Comission(int id, LocalDate date, LocalTime timeStart, LocalTime timeEnd, Patient patient, List<Doctor> doctors, String room) {
        this.id = id;
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.patient = patient;
        this.doctors = doctors;
        this.room = room;
        setTicket();
    }

    public Comission(LocalDate date, LocalTime timeStart, LocalTime timeEnd, Patient patient, List<Doctor> doctors, String room) {
        this(0, date, timeStart, timeEnd, patient, doctors, room);
    }

    public Comission(LocalDate date, LocalTime timeStart, LocalTime timeEnd, Patient patient, List<Doctor> doctors, String room, List<Appointment> appointments) {
        this(0, date, timeStart, timeEnd, patient, doctors, room);
        setAppointments(appointments);
    }

    public Comission() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket() {
        StringBuilder sb = new StringBuilder();
        sb.append("C");
        for (Doctor doctor : doctors) {
            sb.append("D<").append(doctor.getId()).append(">");
        }
        String dayDate = date.getDayOfMonth() < 10 ? "0" + date.getDayOfMonth() : String.valueOf(date.getDayOfMonth());
        String mothDate = date.getMonthValue() < 10 ? "0" + date.getMonthValue() : String.valueOf(date.getMonthValue());
        String hourTime = timeStart.getHour() < 10 ? "0" + timeStart.getHour() : String.valueOf(timeStart.getHour());
        String minuteTime = timeStart.getMinute() < 10 ? "0" + timeStart.getMinute() : String.valueOf(timeStart.getMinute());
        sb.append(dayDate).append(mothDate).append(date.getYear()).append(hourTime).append(minuteTime);
        ticket = sb.toString();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comission)) return false;
        Comission comission = (Comission) o;
        return getId() == comission.getId() &&
                Objects.equals(getTicket(), comission.getTicket()) &&
                Objects.equals(getDate(), comission.getDate()) &&
                Objects.equals(getTimeStart(), comission.getTimeStart()) &&
                Objects.equals(getTimeEnd(), comission.getTimeEnd()) &&
                Objects.equals(getPatient(), comission.getPatient()) &&
                Objects.equals(getDoctors(), comission.getDoctors()) &&
                Objects.equals(getRoom(), comission.getRoom());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTicket(), getDate(), getTimeStart(), getTimeEnd(), getPatient(), getDoctors(), getRoom());
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
