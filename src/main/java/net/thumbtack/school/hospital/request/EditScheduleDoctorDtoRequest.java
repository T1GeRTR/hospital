package net.thumbtack.school.hospital.request;

import net.thumbtack.school.hospital.validators.annotations.EditScheduleValid;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@EditScheduleValid
public class EditScheduleDoctorDtoRequest {
    @NotNull
    private LocalDate dateStart;
    @NotNull
    private LocalDate dateEnd;
    private List<DayScheduleDtoRequest> weekDaysSchedule;
    private WeekDaysScheduleDto weekSchedule;
    @Min(1)
    private int duration;

    public EditScheduleDoctorDtoRequest(LocalDate dateStart, LocalDate dateEnd, List<DayScheduleDtoRequest> weekDaysSchedule, WeekDaysScheduleDto weekSchedule, int duration) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.weekDaysSchedule = weekDaysSchedule;
        this.weekSchedule = weekSchedule;
        this.duration = duration;
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

    public List<DayScheduleDtoRequest> getWeekDaysSchedule() {
        return weekDaysSchedule;
    }

    public void setWeekSchedule(List<DayScheduleDtoRequest> weekDaysSchedule) {
        this.weekDaysSchedule = weekDaysSchedule;
    }

    public WeekDaysScheduleDto getWeekSchedule() {
        return weekSchedule;
    }

    public void setWeekDaysSchedule(WeekDaysScheduleDto weekSchedule) {
        this.weekSchedule = weekSchedule;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
