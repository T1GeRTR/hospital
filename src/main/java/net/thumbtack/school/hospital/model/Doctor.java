package net.thumbtack.school.hospital.model;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Doctor extends User {
    private String speciality;
    private String room;
    private List<DaySchedule> schedule;

    public Doctor(String firstName, String lastName, String patronymic, String speciality, String room, String login, String password, List<DaySchedule> schedule) {
        this(0, firstName, lastName, patronymic, speciality, room, login, password, schedule);
        schedule.sort(Comparator.comparing(DaySchedule::getDate));
    }

    public Doctor(int id, String firstName, String lastName, String patronymic, String speciality, String room, String login, String password, List<DaySchedule> schedule) {
        super(id, firstName, lastName, patronymic, login, password, "DOCTOR");
        this.speciality = speciality;
        this.room = room;
        this.schedule = schedule;
    }

    public Doctor(int id, List<DaySchedule> schedule) {
        setId(id);
        this.schedule = schedule;
    }

    public Doctor() {
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

    public List<DaySchedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<DaySchedule> schedule) {
        this.schedule = schedule;
    }

    public void setTickets() {
        for (DaySchedule daySchedule : getSchedule()) {
            for (Appointment appointment : daySchedule.getAppointments()) {
                appointment.setTicket(appointment.getTicket().replace("<XX>", "<" + String.valueOf(getId()) + ">"));
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(speciality, doctor.speciality) &&
                Objects.equals(room, doctor.room) &&
                Objects.equals(schedule, doctor.schedule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), speciality, room, schedule);
    }
}
