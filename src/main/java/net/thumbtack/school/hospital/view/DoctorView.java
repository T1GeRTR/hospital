package net.thumbtack.school.hospital.view;

import net.thumbtack.school.hospital.model.DaySchedule;
import net.thumbtack.school.hospital.model.Doctor;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class DoctorView extends Doctor {

    private LocalDate dateStart;
    private LocalDate dateEnd;

    public DoctorView(String firstName, String lastName, String patronymic, String speciality, String room, String login, String password, List<DaySchedule> schedule) {
        this(0, firstName, lastName, patronymic, speciality, room, login, password, schedule);
    }

    public DoctorView(int id, String firstName, String lastName, String patronymic, String speciality, String room, String login, String password, List<DaySchedule> schedule) {
        super(id, firstName, lastName, patronymic, speciality, room, login, password, schedule);
    }

    public DoctorView() {
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DoctorView that = (DoctorView) o;
        return Objects.equals(dateStart, that.dateStart) &&
                Objects.equals(dateEnd, that.dateEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dateStart, dateEnd);
    }
}
