package net.thumbtack.school.hospital.request;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;

public class WeekDaysScheduleDto {
    @NotNull
    private LocalTime timeStart;
    @NotNull
    private LocalTime timeEnd;
    private List<String> weekDays;

    public WeekDaysScheduleDto(LocalTime timeStart, LocalTime timeEnd, List<String> weekDays) {
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.weekDays = weekDays;
    }

    public WeekDaysScheduleDto() {

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

    public List<String> getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(List<String> weekDays) {
        this.weekDays = weekDays;
    }
}
