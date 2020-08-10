package net.thumbtack.school.hospital.controllers;

import net.thumbtack.school.hospital.request.*;
import net.thumbtack.school.hospital.response.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestController extends TestControllerBaseCreateHistory {

    @Test
    public void testServer() {
        //Clear db
        Assert.assertEquals(HttpStatus.OK, restTemplate.exchange("/api/debug/clear", HttpMethod.POST, null, EmptyResponse.class).getStatusCode());
//Login
        //wrong login
        LoginUserDtoRequest user = new LoginUserDtoRequest("wrong", "passAdmin");
        ResponseEntity<LoginUserDtoResponse> responseLogin = restTemplate.postForEntity("/api/session", user, LoginUserDtoResponse.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseLogin.getStatusCode());
        //OK
        user = new LoginUserDtoRequest("admin", "passAdmin");
        responseLogin = restTemplate.postForEntity("/api/session", user, LoginUserDtoResponse.class);
        Assert.assertEquals(HttpStatus.OK, responseLogin.getStatusCode());
        String adminSession = Objects.requireNonNull(responseLogin.getHeaders().get("Set-Cookie")).get(0);

//Register Admin
        //login exist
        RegisterAdminDtoRequest admin = new RegisterAdminDtoRequest("Максим", "Ковальчук", "Александрович", "admin", "admin", "password");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", adminSession);
        HttpEntity<RegisterAdminDtoRequest> requestRegisterAdmin = new HttpEntity<>(admin, httpHeaders);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, restTemplate.exchange("/api/admins", HttpMethod.POST, requestRegisterAdmin, RegisterAdminDtoResponse.class).getStatusCode());
        //wrong password
        admin = new RegisterAdminDtoRequest("Максим", "Ковальчук", "Александрович", "admin", "MaxAdmin", "qwe");
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", adminSession);
        requestRegisterAdmin = new HttpEntity<>(admin, httpHeaders);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, restTemplate.exchange("/api/admins", HttpMethod.POST, requestRegisterAdmin, RegisterAdminDtoResponse.class).getStatusCode());
        //OK
        admin = new RegisterAdminDtoRequest("Максим", "Ковальчук", "Александрович", "admin", "MaxAdmin94", "password123456789");
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", adminSession);
        requestRegisterAdmin = new HttpEntity<>(admin, httpHeaders);
        Assert.assertEquals(HttpStatus.OK, restTemplate.exchange("/api/admins", HttpMethod.POST, requestRegisterAdmin, RegisterAdminDtoResponse.class).getStatusCode());

//UpdateAdmin
        //login
        user = new LoginUserDtoRequest("maxadmin94", "password123456789");
        responseLogin = restTemplate.postForEntity("/api/session", user, LoginUserDtoResponse.class);
        Assert.assertEquals(HttpStatus.OK, responseLogin.getStatusCode());
        adminSession = Objects.requireNonNull(responseLogin.getHeaders().get("Set-Cookie")).get(0);
        //wrongPassword
        UpdateAdminDtoRequest adminUpdate = new UpdateAdminDtoRequest("Максим", "Ковальчук", "Александрович", "admin", "wrongPassword", "password123456789");
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", adminSession);
        HttpEntity<UpdateAdminDtoRequest> requestUpdateAdmin = new HttpEntity<>(adminUpdate, httpHeaders);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, restTemplate.exchange("/api/admins", HttpMethod.PUT, requestUpdateAdmin, RegisterAdminDtoResponse.class).getStatusCode());
        //wrongName
        adminUpdate = new UpdateAdminDtoRequest("Maxim", "Ковальчук", "Александрович", "admin2", "password123456789", "password123456789");
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", adminSession);
        requestUpdateAdmin = new HttpEntity<>(adminUpdate, httpHeaders);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, restTemplate.exchange("/api/admins", HttpMethod.PUT, requestUpdateAdmin, RegisterAdminDtoResponse.class).getStatusCode());
        //OK
        adminUpdate = new UpdateAdminDtoRequest("Максим", "Ковальчук", "Александрович", "admin2", "password123456789", "password123456789");
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", adminSession);
        requestUpdateAdmin = new HttpEntity<>(adminUpdate, httpHeaders);
        Assert.assertEquals(HttpStatus.OK, restTemplate.exchange("/api/admins", HttpMethod.PUT, requestUpdateAdmin, RegisterAdminDtoResponse.class).getStatusCode());
//RegisterDoctor
        //weeksSchedule and weekDaysSchedule not null
        List<DayScheduleDtoRequest> dayScheduleDtoRequest = new ArrayList<>();
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Mon", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Tue", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Wed", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Thu", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Fri", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        RegisterDoctorDtoRequest registerDoctor = new RegisterDoctorDtoRequest("Талгат", "Ракишев", "Адылханович", "dentist", "11", "doctor1", "passDoctor", LocalDate.of(2020, 4, 22), LocalDate.of(2020, 4, 30), dayScheduleDtoRequest, new WeekDaysScheduleDto(LocalTime.of(8, 0), LocalTime.of(16, 0), new ArrayList<>()), 15);
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", adminSession);
        HttpEntity<RegisterDoctorDtoRequest> requestRegisterDoctor = new HttpEntity<>(registerDoctor, httpHeaders);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, restTemplate.exchange("/api/doctors", HttpMethod.POST, requestRegisterDoctor, RegisterDoctorDtoResponse.class).getStatusCode());
        //wrong speciality
        registerDoctor = new RegisterDoctorDtoRequest("Талгат", "Ракишев", "Адылханович", "wrongSpeciality", "15", "doctor1", "passDoctor", LocalDate.of(2020, 4, 22), LocalDate.of(2020, 4, 30), dayScheduleDtoRequest, null, 15);
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", adminSession);
        requestRegisterDoctor = new HttpEntity<>(registerDoctor, httpHeaders);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, restTemplate.exchange("/api/doctors", HttpMethod.POST, requestRegisterDoctor, RegisterDoctorDtoResponse.class).getStatusCode());
        //wrong room
        registerDoctor = new RegisterDoctorDtoRequest("Талгат", "Ракишев", "Адылханович", "dentist", "1000", "doctor1", "passDoctor", LocalDate.of(2020, 4, 22), LocalDate.of(2020, 4, 30), dayScheduleDtoRequest, null, 15);
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", adminSession);
        requestRegisterDoctor = new HttpEntity<>(registerDoctor, httpHeaders);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, restTemplate.exchange("/api/doctors", HttpMethod.POST, requestRegisterDoctor, RegisterDoctorDtoResponse.class).getStatusCode());
        //OK doctor1
        List<Integer> testDoctors = new ArrayList<>();
        registerDoctor = new RegisterDoctorDtoRequest("Талгат", "Ракишев", "Адылханович", "dermatologist", "11", "testDoctor1", "passDoctor", LocalDate.of(2020, 4, 14), LocalDate.of(2020, 4, 30), dayScheduleDtoRequest, null, 15);
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", adminSession);
        requestRegisterDoctor = new HttpEntity<>(registerDoctor, httpHeaders);
        ResponseEntity<RegisterDoctorDtoResponse> responseRegisterDoctor1 = restTemplate.exchange("/api/doctors", HttpMethod.POST, requestRegisterDoctor, RegisterDoctorDtoResponse.class);
        Assert.assertEquals(HttpStatus.OK, responseRegisterDoctor1.getStatusCode());
        testDoctors.add(Objects.requireNonNull(responseRegisterDoctor1.getBody()).getId());
        //loginDoctor1
        user = new LoginUserDtoRequest("testDoctor1", "passDoctor");
        responseLogin = restTemplate.postForEntity("/api/session", user, LoginUserDtoResponse.class);
        Assert.assertEquals(HttpStatus.OK, responseLogin.getStatusCode());
        String doctor1Session = Objects.requireNonNull(responseLogin.getHeaders().get("Set-Cookie")).get(0);
        //room busy
        registerDoctor = new RegisterDoctorDtoRequest("Талгат", "Ракишев", "Адылханович", "dentist", "11", "doctor2", "passDoctor", LocalDate.of(2020, 4, 22), LocalDate.of(2020, 4, 30), dayScheduleDtoRequest, null, 15);
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", adminSession);
        requestRegisterDoctor = new HttpEntity<>(registerDoctor, httpHeaders);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, restTemplate.exchange("/api/doctors", HttpMethod.POST, requestRegisterDoctor, RegisterDoctorDtoResponse.class).getStatusCode());
        //OK doctor2
        registerDoctor = new RegisterDoctorDtoRequest("Талгат", "Ракишев", "Адылханович", "dermatologist", "12", "testDoctor2", "passDoctor", LocalDate.of(2020, 4, 14), LocalDate.of(2020, 4, 30), dayScheduleDtoRequest, null, 15);
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", adminSession);
        requestRegisterDoctor = new HttpEntity<>(registerDoctor, httpHeaders);
        ResponseEntity<RegisterDoctorDtoResponse> responseRegisterDoctor2 = restTemplate.exchange("/api/doctors", HttpMethod.POST, requestRegisterDoctor, RegisterDoctorDtoResponse.class);
        Assert.assertEquals(HttpStatus.OK, responseRegisterDoctor2.getStatusCode());
        testDoctors.add(Objects.requireNonNull(responseRegisterDoctor2.getBody()).getId());
//RegisterPatient
        //wrong email
        RegisterPatientDtoRequest registerPatient = new RegisterPatientDtoRequest("Петр", "Петров", "Петрович", "TestPatient", "passPatient", "wrongEmail", "Omsk", "+7-999-455-76-09");
        Assert.assertEquals(HttpStatus.BAD_REQUEST, restTemplate.postForEntity("/api/patients", registerPatient, RegisterPatientDtoResponse.class).getStatusCode());
        //wrong phone
        registerPatient = new RegisterPatientDtoRequest("Петр", "Петров", "Петрович", "TestPatient", "passPatient", "petr@gmail.com", "Omsk", "+8-999-455-76-09");
        Assert.assertEquals(HttpStatus.BAD_REQUEST, restTemplate.postForEntity("/api/patients", registerPatient, RegisterPatientDtoResponse.class).getStatusCode());
        //OK
        registerPatient = new RegisterPatientDtoRequest("Петр", "Петров", "Петрович", "TestPatient", "passPatient", "petr@gmail.com", "Omsk", "+7-999-455-76-09");
        ResponseEntity<RegisterPatientDtoResponse> responseRegisterPatient = restTemplate.postForEntity("/api/patients", registerPatient, RegisterPatientDtoResponse.class);
        Assert.assertEquals(HttpStatus.OK, responseRegisterPatient.getStatusCode());
        Integer patientId = responseRegisterPatient.getBody().getId();
        //loginPatient
        user = new LoginUserDtoRequest("testPatient", "passPatient");
        responseLogin = restTemplate.postForEntity("/api/session", user, LoginUserDtoResponse.class);
        Assert.assertEquals(HttpStatus.OK, responseLogin.getStatusCode());
        String patientSession = Objects.requireNonNull(responseLogin.getHeaders().get("Set-Cookie")).get(0);
//InsertAppointment
        //wrongSpeciality
        InsertAppointmentDtoRequest appointmentInsert = new InsertAppointmentDtoRequest(null, "wrongSpeciality", LocalDate.of(2020, 4, 15), LocalTime.of(8, 0));
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", patientSession);
        HttpEntity<InsertAppointmentDtoRequest> requestInsertAppointment = new HttpEntity<>(appointmentInsert, httpHeaders);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, restTemplate.exchange("/api/tickets", HttpMethod.POST, requestInsertAppointment, InsertAppointmentDtoResponse.class).getStatusCode());
        //wrongDoctorId
        appointmentInsert = new InsertAppointmentDtoRequest(999, null, LocalDate.of(2020, 4, 15), LocalTime.of(8, 0));
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", patientSession);
        requestInsertAppointment = new HttpEntity<>(appointmentInsert, httpHeaders);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, restTemplate.exchange("/api/tickets", HttpMethod.POST, requestInsertAppointment, InsertAppointmentDtoResponse.class).getStatusCode());
        //wrongParams
        appointmentInsert = new InsertAppointmentDtoRequest((Integer) testDoctors.toArray()[0], "dermatologist", LocalDate.of(2020, 4, 15), LocalTime.of(8, 0));
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", patientSession);
        requestInsertAppointment = new HttpEntity<>(appointmentInsert, httpHeaders);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, restTemplate.exchange("/api/tickets", HttpMethod.POST, requestInsertAppointment, InsertAppointmentDtoResponse.class).getStatusCode());
        //OK doctor1
        appointmentInsert = new InsertAppointmentDtoRequest(null, "dermatologist", LocalDate.of(2020, 4, 15), LocalTime.of(8, 0));
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", patientSession);
        requestInsertAppointment = new HttpEntity<>(appointmentInsert, httpHeaders);
        ResponseEntity<InsertAppointmentDtoResponse> responseInsertAppointment = restTemplate.exchange("/api/tickets", HttpMethod.POST, requestInsertAppointment, InsertAppointmentDtoResponse.class);
        Assert.assertEquals(HttpStatus.OK, responseInsertAppointment.getStatusCode());
        //appointmentBusy
        appointmentInsert = new InsertAppointmentDtoRequest((Integer) testDoctors.toArray()[0], null, LocalDate.of(2020, 4, 15), LocalTime.of(8, 0));
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", patientSession);
        requestInsertAppointment = new HttpEntity<>(appointmentInsert, httpHeaders);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, restTemplate.exchange("/api/tickets", HttpMethod.POST, requestInsertAppointment, InsertAppointmentDtoResponse.class).getStatusCode());
        //OK doctor2
        appointmentInsert = new InsertAppointmentDtoRequest(null, "dermatologist", LocalDate.of(2020, 4, 15), LocalTime.of(8, 0));
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", patientSession);
        requestInsertAppointment = new HttpEntity<>(appointmentInsert, httpHeaders);
        Assert.assertEquals(HttpStatus.OK, restTemplate.exchange("/api/tickets", HttpMethod.POST, requestInsertAppointment, InsertAppointmentDtoResponse.class).getStatusCode());
//DeleteAppointment
        //wrong userType
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", doctor1Session);
        HttpEntity<HttpHeaders> requestDeleteAppointment = new HttpEntity<>(httpHeaders);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, restTemplate.exchange("/api/tickets/{id}", HttpMethod.DELETE, requestDeleteAppointment, EmptyResponse.class, 2).getStatusCode());
        //wrong sessionId
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", doctor1Session);
        requestDeleteAppointment = new HttpEntity<>(httpHeaders);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, restTemplate.exchange("/api/tickets/{id}", HttpMethod.DELETE, requestDeleteAppointment, EmptyResponse.class, 999999).getStatusCode());
        //wrong id
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", patientSession);
        requestDeleteAppointment = new HttpEntity<>(httpHeaders);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, restTemplate.exchange("/api/tickets/{id}", HttpMethod.DELETE, requestDeleteAppointment, EmptyResponse.class, 999).getStatusCode());
        //OK
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", patientSession);
        requestDeleteAppointment = new HttpEntity<>(httpHeaders);
        Assert.assertEquals(HttpStatus.OK, restTemplate.exchange("/api/tickets/{id}", HttpMethod.DELETE, requestDeleteAppointment, EmptyResponse.class, 1215).getStatusCode());
        //insert appointment again doctor2
        appointmentInsert = new InsertAppointmentDtoRequest(null, "dermatologist", LocalDate.of(2020, 4, 15), LocalTime.of(8, 0));
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", patientSession);
        requestInsertAppointment = new HttpEntity<>(appointmentInsert, httpHeaders);
        Assert.assertEquals(HttpStatus.OK, restTemplate.exchange("/api/tickets", HttpMethod.POST, requestInsertAppointment, InsertAppointmentDtoResponse.class).getStatusCode());
        //delete appointment
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", patientSession);
        requestDeleteAppointment = new HttpEntity<>(httpHeaders);
        Assert.assertEquals(HttpStatus.OK, restTemplate.exchange("/api/tickets/{id}", HttpMethod.DELETE, requestDeleteAppointment, EmptyResponse.class, 1215).getStatusCode());
//InsertComission
        //busy appointment doctor1
        InsertComissionDtoRequest requestInsertComission = new InsertComissionDtoRequest(patientId, testDoctors, "11", LocalDate.of(2020, 4, 15), LocalTime.of(8, 0), 60);
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", doctor1Session);
        HttpEntity<InsertComissionDtoRequest> request = new HttpEntity<>(requestInsertComission, httpHeaders);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, restTemplate.exchange("/api/comissions", HttpMethod.POST, request, InsertComissionDtoResponse.class).getStatusCode());
        //delete busy appointment
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", patientSession);
        requestDeleteAppointment = new HttpEntity<>(httpHeaders);
        Assert.assertEquals(HttpStatus.OK, restTemplate.exchange("/api/tickets/{id}", HttpMethod.DELETE, requestDeleteAppointment, EmptyResponse.class, 799).getStatusCode());
        //wrong room
        requestInsertComission = new InsertComissionDtoRequest(patientId, testDoctors, "23", LocalDate.of(2020, 4, 15), LocalTime.of(8, 0), 60);
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", doctor1Session);
        request = new HttpEntity<>(requestInsertComission, httpHeaders);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, restTemplate.exchange("/api/comissions", HttpMethod.POST, request, InsertComissionDtoResponse.class).getStatusCode());
        //doctorIds duplicates
        List<Integer> doctorIdsDuplicates = new ArrayList<>();
        doctorIdsDuplicates.add(testDoctors.get(0));
        doctorIdsDuplicates.add(testDoctors.get(0));
        requestInsertComission = new InsertComissionDtoRequest(patientId, doctorIdsDuplicates, "11", LocalDate.of(2020, 4, 15), LocalTime.of(8, 0), 60);
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", doctor1Session);
        request = new HttpEntity<>(requestInsertComission, httpHeaders);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, restTemplate.exchange("/api/comissions", HttpMethod.POST, request, InsertComissionDtoResponse.class).getStatusCode());
        //OK
        requestInsertComission = new InsertComissionDtoRequest(patientId, testDoctors, "11", LocalDate.of(2020, 4, 15), LocalTime.of(8, 0), 60);
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", doctor1Session);
        request = new HttpEntity<>(requestInsertComission, httpHeaders);
        Assert.assertEquals(HttpStatus.OK, restTemplate.exchange("/api/comissions", HttpMethod.POST, request, InsertComissionDtoResponse.class).getStatusCode());
//Delete Comission
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", patientSession);
        request = new HttpEntity<>(httpHeaders);
        ResponseEntity<EmptyResponse> response = restTemplate.exchange("/api/comissions" + "/{id}", HttpMethod.DELETE, request, EmptyResponse.class, 5);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
//Insert comission Again
        requestInsertComission = new InsertComissionDtoRequest(patientId, testDoctors, "11", LocalDate.of(2020, 4, 15), LocalTime.of(8, 0), 60);
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", doctor1Session);
        request = new HttpEntity<>(requestInsertComission, httpHeaders);
        Assert.assertEquals(HttpStatus.OK, restTemplate.exchange("/api/comissions", HttpMethod.POST, request, InsertComissionDtoResponse.class).getStatusCode());
//Insert comission patient time crosses
        requestInsertComission = new InsertComissionDtoRequest(patientId, testDoctors, "11", LocalDate.of(2020, 4, 15), LocalTime.of(8, 30), 60);
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", doctor1Session);
        request = new HttpEntity<>(requestInsertComission, httpHeaders);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, restTemplate.exchange("/api/comissions", HttpMethod.POST, request, InsertComissionDtoResponse.class).getStatusCode());
//Clear database
        Assert.assertEquals(HttpStatus.OK, restTemplate.exchange("/api/debug/clear", HttpMethod.POST, null, EmptyResponse.class).getStatusCode());
        setUpIsDone = false;
    }
}
