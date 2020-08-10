package net.thumbtack.school.hospital.response.statictics;

import java.util.List;

public class StatisticsDtoResponse {
    private List<StatisticsDoctorDto> doctors;
    private List<StatisticsPatientDto> patients;
    private StatisticsAppointments appointments;
    private int comissions;

    public StatisticsDtoResponse(List<StatisticsDoctorDto> doctors, List<StatisticsPatientDto> patients, StatisticsAppointments appointments, int comissions) {
        this.doctors = doctors;
        this.patients = patients;
        this.appointments = appointments;
        this.comissions = comissions;
    }

    public List<StatisticsDoctorDto> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<StatisticsDoctorDto> doctors) {
        this.doctors = doctors;
    }

    public List<StatisticsPatientDto> getPatients() {
        return patients;
    }

    public void setPatients(List<StatisticsPatientDto> patients) {
        this.patients = patients;
    }

    public StatisticsAppointments getAppointments() {
        return appointments;
    }

    public void setAppointments(StatisticsAppointments appointments) {
        this.appointments = appointments;
    }

    public int getComissions() {
        return comissions;
    }

    public void setComissions(int comissions) {
        this.comissions = comissions;
    }
}
