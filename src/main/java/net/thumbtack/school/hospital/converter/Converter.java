package net.thumbtack.school.hospital.converter;

import net.thumbtack.school.hospital.exception.ErrorCode;
import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.model.*;
import net.thumbtack.school.hospital.dao.DoctorDao;
import net.thumbtack.school.hospital.request.DayScheduleDtoRequest;
import net.thumbtack.school.hospital.response.*;
import net.thumbtack.school.hospital.request.WeekDaysScheduleDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Converter {

    public static List<DaySchedule> convertSchedule(LocalDate dateStart, LocalDate dateEnd, List<DayScheduleDtoRequest> weekDaysSchedule, int duration) {
        LocalDate currentDate = dateStart;
        List<DaySchedule> schedule = new ArrayList<>();
        // REVU лучше for(LocalDate currentDatedateStart;...)
        while (!currentDate.isAfter(dateEnd)) {
            for (DayScheduleDtoRequest day : weekDaysSchedule) {
                if (currentDate.getDayOfWeek().toString().substring(0, 3).equalsIgnoreCase(day.getWeekDay())) {
                    LocalTime currentTime = day.getTimeStart();
                    List<Appointment> daySchedule = new ArrayList<>();
                    while (!currentTime.plusMinutes(duration).isAfter(day.getTimeEnd())) {
                        String dayDate = currentDate.getDayOfMonth() < 10 ? "0" + currentDate.getDayOfMonth() : String.valueOf(currentDate.getDayOfMonth());
                        String mothDate = currentDate.getMonthValue() < 10 ? "0" + currentDate.getMonthValue() : String.valueOf(currentDate.getMonthValue());
                        String hourTime = currentTime.getHour() < 10 ? "0" + currentTime.getHour() : String.valueOf(currentTime.getHour());
                        String minuteTime = currentTime.getMinute() < 10 ? "0" + currentTime.getMinute() : String.valueOf(currentTime.getMinute());
                        String ticket = "D<" + "XX" + ">" + dayDate + mothDate + currentDate.getYear()
                                + hourTime + minuteTime;
                        daySchedule.add(new Appointment(ticket, currentTime, currentTime.plusMinutes(duration), null, AppointmentState.FREE, new DaySchedule(currentDate, new ArrayList<>())));
                        currentTime = currentTime.plusMinutes(duration);
                    }
                    schedule.add(new DaySchedule(currentDate, daySchedule));
                }
            }
            currentDate = currentDate.plusDays(1);
        }
        return schedule;
    }

    public static List<DayScheduleDtoRequest> convertWeekDaysToListSchedule(WeekDaysScheduleDto weekDaysScheduleDto) {
        List<DayScheduleDtoRequest> dayScheduleDtoRequest = new ArrayList<>();
        List<String> weekDays = weekDaysScheduleDto.getWeekDays();
        if (weekDays.size() == 0) {
            weekDays.add("Mon");
            weekDays.add("Tue");
            weekDays.add("Wed");
            weekDays.add("Thu");
            weekDays.add("Fri");
        }
        for (String weekDay : weekDays) {
        	 dayScheduleDtoRequest.add(new DayScheduleDtoRequest(weekDay, weekDaysScheduleDto.getTimeStart(), weekDaysScheduleDto.getTimeEnd()));
        }
        return dayScheduleDtoRequest;
    }

    public static List<DayScheduleDtoResponse> convertScheduleModelToDto(List<DaySchedule> listSchedule) {
        List<DayScheduleDtoResponse> listDayScheduleDtoResponse = new ArrayList<>();
        for (DaySchedule daySchedule : listSchedule) {
            listDayScheduleDtoResponse.add(new DayScheduleDtoResponse(daySchedule.getDate(), convertAppointmentModelToDto(daySchedule.getAppointments())));
        }
        return listDayScheduleDtoResponse;
    }

    public static List<AppointmentDto> convertAppointmentModelToDto(List<Appointment> listAppointment) {
        List<AppointmentDto> listAppointmentDto = new ArrayList<>();
        for (Appointment appointment : listAppointment) {
            listAppointmentDto.add(new AppointmentDto(appointment.getId(), appointment.getTicket(), appointment.getTimeStart(), appointment.getTimeEnd(), convertPatientModelToDto(appointment.getPatient()), appointment.getAppointmentState()));
        }
        return listAppointmentDto;
    }

    public static PatientDto convertPatientModelToDto(Patient patient) {
        if (patient != null) {
            return new PatientDto(patient.getId(), patient.getFirstName(), patient.getLastName(), patient.getPatronymic(), patient.getEmail(), patient.getAddress(), patient.getPhone(), patient.getLogin(), patient.getPassword());
        }
        return null;
    }

    public static List<DoctorDto> convertDoctorModelToDto(List<Doctor> doctors) {
        if (doctors != null) {
            List<DoctorDto> list = new ArrayList<>();
            for (Doctor doctor : doctors) {
                list.add(new DoctorDto(doctor.getId(), doctor.getFirstName(), doctor.getLastName(), doctor.getPatronymic(), doctor.getSpeciality()));
            }
            return list;
        }
        return null;
    }

    public static List<GetByPatientAppointmentDtoResponse> convertAppointmentToAppointmentDto(List<Appointment> appointments, DoctorDao doctorDao) throws ServerException {
        List<GetByPatientAppointmentDtoResponse> list = new ArrayList<>();
        if (appointments.size() != 0) {
            for (Appointment appointment: appointments){
                Doctor doctor = doctorDao.getByAppointmentId(appointment.getId());
                for (DaySchedule daySchedule: doctor.getSchedule()){
                    if (daySchedule.getAppointments().contains(appointment)){
                        GetByPatientAppointmentDtoResponse appointmentDto = new GetByPatientAppointmentDtoResponse(appointment.getTicket(), doctor.getRoom(), daySchedule.getDate(), appointment.getTimeStart(), doctor.getId(),doctor.getFirstName(), doctor.getLastName(), doctor.getPatronymic(), doctor.getSpeciality());
                    }
                }
            }
        }
        return list;
    }

    public static List<GetByPatientComissionDtoResponse> convertComissionModelToDto(List<Comission> comissions) {
        List<GetByPatientComissionDtoResponse> list = new ArrayList<>();
        for (Comission comission : comissions) {
            list.add(new GetByPatientComissionDtoResponse(comission.getTicket(), comission.getRoom(), comission.getDate(), comission.getTimeStart(), convertDoctorModelToDto(comission.getDoctors())));
        }
        return list;
    }

    public static String convertPhone(String phone) throws ServerException {
        boolean hasPlus = phone.charAt(0) == "+".charAt(0);
        phone = phone.replaceAll("[-+]", "");
        boolean wrongNumber = !((phone.charAt(0) == "8".charAt(0) && !hasPlus) || (phone.charAt(0) == "7".charAt(0) && hasPlus) && phone.charAt(1) == "9".charAt(0) && phone.length() == 11 && phone.matches("\\d+"));
        if (wrongNumber) {
            throw new ServerException(ErrorCode.WRONG_PHONE);
        }
        return hasPlus ? "+" + phone : phone;
    }
}
