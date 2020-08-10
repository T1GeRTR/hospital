package net.thumbtack.school.hospital.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetDoctorDtoResponse extends GetUserDtoResponse {
    private String speciality;
    private String room;
    private List<DayScheduleDtoResponse> schedule;

    public GetDoctorDtoResponse(int id, String firstName, String lastName, String patronymic, String speciality, String room, List<DayScheduleDtoResponse> schedule) {
        super(id, firstName, lastName, patronymic);
        this.speciality = speciality;
        this.room = room;
        this.schedule = schedule;
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

    public List<DayScheduleDtoResponse> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<DayScheduleDtoResponse> schedule) {
        this.schedule = schedule;
    }

}
