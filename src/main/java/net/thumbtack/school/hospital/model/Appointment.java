package net.thumbtack.school.hospital.model;

import java.time.LocalTime;
import java.util.Objects;

public class Appointment {
    private int id;
    private String ticket;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private Patient patient;
    private AppointmentState appointmentState;
    private DaySchedule daySchedule;

    public Appointment(String ticket, LocalTime timeStart, LocalTime timeEnd, Patient patient, AppointmentState appointmentState, DaySchedule daySchedule) {
        this(0, ticket, timeStart, timeEnd, patient, appointmentState, daySchedule);
    }

    public Appointment(int id, String ticket, LocalTime timeStart, LocalTime timeEnd, Patient patient, AppointmentState appointmentState, DaySchedule daySchedule) {
        this.id = id;
        this.ticket = ticket;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.patient = patient;
        this.appointmentState = appointmentState;
        this.daySchedule = daySchedule;
    }

    public Appointment() {
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
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

    public AppointmentState getAppointmentState() {
        return appointmentState;
    }

    public void setAppointmentState(AppointmentState appointmentState) {
        this.appointmentState = appointmentState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return id == that.id &&
                Objects.equals(ticket, that.ticket) &&
                Objects.equals(timeStart, that.timeStart) &&
                Objects.equals(timeEnd, that.timeEnd) &&
                Objects.equals(patient, that.patient) &&
                appointmentState == that.appointmentState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ticket, timeStart, timeEnd, patient, appointmentState);
    }

    public DaySchedule getDaySchedule() {
        return daySchedule;
    }

    public void setDaySchedule(DaySchedule daySchedule) {
        this.daySchedule = daySchedule;
    }
}
