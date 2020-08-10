package net.thumbtack.school.hospital.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;

public class DayScheduleDtoRequest {
    @NotNull
    @Size(min = 3, max = 3)
    private String weekDay;
    @NotNull
    private LocalTime timeStart;
    @NotNull
    private LocalTime timeEnd;

    public DayScheduleDtoRequest(String weekDay, LocalTime timeStart, LocalTime timeEnd) {
        this.weekDay = weekDay;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
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
}
