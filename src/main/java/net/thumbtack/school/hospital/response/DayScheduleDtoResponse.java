package net.thumbtack.school.hospital.response;

import java.time.LocalDate;
import java.util.List;

public class DayScheduleDtoResponse {
    private LocalDate date;
    private List<AppointmentDto> daySchedule;

    public DayScheduleDtoResponse(LocalDate date, List<AppointmentDto> daySchedule) {
        this.date = date;
        this.daySchedule = daySchedule;
    }

    public DayScheduleDtoResponse() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<AppointmentDto> getDaySchedule() {
        return daySchedule;
    }

    public void setDaySchedule(List<AppointmentDto> daySchedule) {
        this.daySchedule = daySchedule;
    }

}
