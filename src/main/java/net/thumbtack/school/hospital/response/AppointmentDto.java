package net.thumbtack.school.hospital.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import net.thumbtack.school.hospital.model.AppointmentState;

import java.time.LocalTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentDto {
    @JsonIgnore
    private int id;
    @JsonIgnore
    private String ticket;
    private LocalTime time;
    @JsonIgnore
    private LocalTime timeEnd;
    private PatientDto patient;
    @JsonIgnore
    private AppointmentState appointmentState;

    public AppointmentDto(String ticket, LocalTime time, LocalTime timeEnd, PatientDto patient, AppointmentState appointmentState) {
        this(0, ticket, time, timeEnd, patient, appointmentState);
    }

    public AppointmentDto(int id, String ticket, LocalTime time, LocalTime timeEnd, PatientDto patient, AppointmentState appointmentState) {
        this.id = id;
        this.ticket = ticket;
        this.time = time;
        this.timeEnd = timeEnd;
        this.patient = patient;
        this.appointmentState = appointmentState;
    }

    public AppointmentDto() {
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public PatientDto getPatient() {
        return patient;
    }

    public void setPatient(PatientDto patient) {
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

}
