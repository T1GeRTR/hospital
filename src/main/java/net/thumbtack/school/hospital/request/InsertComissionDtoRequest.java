package net.thumbtack.school.hospital.request;

import net.thumbtack.school.hospital.validators.annotations.NotDuplicates;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public class InsertComissionDtoRequest {
    @NotNull
    private int patientId;
    @NotDuplicates
    private List<Integer> doctorIds;
    @NotNull
    private String room;
    @NotNull
    private LocalDate date;
    @NotNull
    private LocalTime time;
    @NotNull
    @Min(1)
    private int duration;

    public InsertComissionDtoRequest(int patientId, List<Integer> doctorIds, String room, LocalDate date, LocalTime time, int duration) {
        this.patientId = patientId;
        this.doctorIds = doctorIds;
        this.room = room;
        this.date = date;
        this.time = time;
        this.duration = duration;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public List<Integer> getDoctorIds() {
        return doctorIds;
    }

    public void setDoctorIds(List<Integer> doctorIds) {
        this.doctorIds = doctorIds;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
