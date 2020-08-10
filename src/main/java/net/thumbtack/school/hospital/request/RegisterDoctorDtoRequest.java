package net.thumbtack.school.hospital.request;

import net.thumbtack.school.hospital.validators.annotations.MaxNameLength;
import net.thumbtack.school.hospital.validators.annotations.MinPasswordLength;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public class RegisterDoctorDtoRequest {
    @NotNull
    @MaxNameLength
    @Pattern(regexp="^[а-яА-ЯёЁa0-9- ]+$",message="Only russian and english char, digits, minus and spaces")
    private String firstName;
    @NotNull
    @MaxNameLength
    @Pattern(regexp="^[а-яА-ЯёЁa0-9- ]+$",message="Only russian and english char, digits, minus and spaces")
    private String lastName;
    @Size(max=50)
    @Pattern(regexp="^[а-яА-ЯёЁa0-9- ]+$",message="Only russian and english char, digits, minus and spaces")
    private String patronymic;
    @NotNull
    @MaxNameLength
    private String speciality;
    @NotNull
    private String room;
    @NotNull
    @MaxNameLength
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z0-9]+$", message = "Only russian and english char and digit")
    private String login;
    @NotNull
    @MaxNameLength
    @MinPasswordLength
    private String password;
    @NotNull
    private LocalDate dateStart;
    @NotNull
    private LocalDate dateEnd;
    private List<DayScheduleDtoRequest> weekDaysSchedule;
    private WeekDaysScheduleDto weekSchedule;
    @NotNull
    @Min(1)
    private int duration;

    public RegisterDoctorDtoRequest(String firstName, String lastName, String patronymic, String speciality, String room, String login, String password, LocalDate dateStart, LocalDate dateEnd, List<DayScheduleDtoRequest> weekDaysSchedule, WeekDaysScheduleDto weekSchedule, int duration) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.speciality = speciality;
        this.room = room;
        this.login = login;
        this.password = password;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.weekDaysSchedule = weekDaysSchedule;
        this.weekSchedule = weekSchedule;
        this.duration = duration;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<DayScheduleDtoRequest> getWeekDaysSchedule() {
        return weekDaysSchedule;
    }

    public void setWeekDaysSchedule(List<DayScheduleDtoRequest> weekDaysSchedule) {
        this.weekDaysSchedule = weekDaysSchedule;
    }

    public WeekDaysScheduleDto getWeekSchedule() {
        return weekSchedule;
    }

    public void setWeekSchedule(WeekDaysScheduleDto weekSchedule) {
        this.weekSchedule = weekSchedule;
    }
}
