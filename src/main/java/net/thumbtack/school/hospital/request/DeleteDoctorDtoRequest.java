package net.thumbtack.school.hospital.request;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class DeleteDoctorDtoRequest {
    @NotNull
    private LocalDate date;

    public DeleteDoctorDtoRequest() {
    }

    public DeleteDoctorDtoRequest(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
