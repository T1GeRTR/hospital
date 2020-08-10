package net.thumbtack.school.hospital.request;

import net.thumbtack.school.hospital.validators.annotations.InsertAppointmentValid;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@InsertAppointmentValid()
public class InsertAppointmentDtoRequest {
    private Integer doctorId;
    private String speciality;
    @NotNull
    private LocalDate date;
    @NotNull
    private LocalTime time;

    public InsertAppointmentDtoRequest(Integer doctorId, String speciality, LocalDate date, LocalTime time) {
        this.doctorId = doctorId;
        this.speciality = speciality;
        this.date = date;
        this.time = time;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
