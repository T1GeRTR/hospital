package net.thumbtack.school.hospital.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.thumbtack.school.hospital.response.AppointmentDto;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class DaySchedule {
    @JsonIgnore
    private int id;
    private LocalDate date;
    private List<Appointment> appointments;

    public DaySchedule(LocalDate date, List<Appointment> appointments) {
        this.date = date;
        this.appointments = appointments;
        appointments.sort(Comparator.comparing(Appointment::getTimeStart));
    }

    public DaySchedule(int id, LocalDate date, List<Appointment> appointments) {
        this.id = id;
        this.date = date;
        this.appointments = appointments;
    }

    public DaySchedule() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
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
        DaySchedule that = (DaySchedule) o;
        return id == that.id &&
                Objects.equals(date, that.date) &&
                Objects.equals(appointments, that.appointments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, appointments);
    }
}
