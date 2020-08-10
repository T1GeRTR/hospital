package net.thumbtack.school.hospital.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class GetByPatientComissionDtoResponse extends GetByPatientTicketDtoResponse {
    private List<DoctorDto> doctors;

    public GetByPatientComissionDtoResponse(String ticket, String room, LocalDate date, LocalTime time, List<DoctorDto> doctors) {
        super(ticket, room, date, time);
        this.doctors = doctors;
    }

    public List<DoctorDto> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<DoctorDto> doctors) {
        this.doctors = doctors;
    }
}
