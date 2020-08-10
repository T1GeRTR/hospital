package net.thumbtack.school.hospital.controllers;

import net.thumbtack.school.hospital.request.*;
import net.thumbtack.school.hospital.response.EditScheduleDoctorDtoResponse;
import net.thumbtack.school.hospital.response.EmptyResponse;
import net.thumbtack.school.hospital.response.GetByIdDoctorDtoResponse;
import net.thumbtack.school.hospital.response.RegisterDoctorDtoResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestDoctorController extends TestControllerBaseCreateHistory {
    private static final String url = "/api/doctors";

    private void testRegister() {
        List<DayScheduleDtoRequest> dayScheduleDtoRequest = new ArrayList<>();
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Mon", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Tue", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Wed", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Thu", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Fri", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        RegisterDoctorDtoRequest doctor = new RegisterDoctorDtoRequest("Талгат", "Ракишев", "Адылханович", "dentist", "46", "rakishev", "passDoctor", LocalDate.of(2020, 4, 22), LocalDate.of(2020, 4, 30), dayScheduleDtoRequest, null, 15);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JAVASESSIONID=12345Admin");
        HttpEntity<RegisterDoctorDtoRequest> request = new HttpEntity<>(doctor, httpHeaders);
        ResponseEntity<RegisterDoctorDtoResponse> response = restTemplate.exchange(url, HttpMethod.POST, request, RegisterDoctorDtoResponse.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotEquals(0, Objects.requireNonNull(response.getBody()).getId());
        Assert.assertEquals("Талгат", response.getBody().getFirstName());
    }

    @Test
    public void testRegisterFailRoom() {
        List<DayScheduleDtoRequest> dayScheduleDtoRequest = new ArrayList<>();
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Mon", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Tue", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Wed", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Thu", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Fri", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        RegisterDoctorDtoRequest doctor = new RegisterDoctorDtoRequest("Талгат", "Ракишев", "Адылханович", "dentist", "100", "doctor99", "passDoctor", LocalDate.of(2020, 4, 22), LocalDate.of(2020, 4, 30), dayScheduleDtoRequest, null, 15);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JAVASESSIONID=12345Admin");
        HttpEntity<RegisterDoctorDtoRequest> request = new HttpEntity<>(doctor, httpHeaders);
        ResponseEntity<RegisterDoctorDtoResponse> response = restTemplate.exchange(url, HttpMethod.POST, request, RegisterDoctorDtoResponse.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testRegisterFailSpeciality() {
        List<DayScheduleDtoRequest> dayScheduleDtoRequest = new ArrayList<>();
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Mon", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Tue", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Wed", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Thu", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Fri", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        RegisterDoctorDtoRequest doctor = new RegisterDoctorDtoRequest("Талгат", "Ракишев", "Адылханович", "doctor", "1", "doctor99", "passDoctor", LocalDate.of(2020, 4, 22), LocalDate.of(2020, 4, 30), dayScheduleDtoRequest, null, 15);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JAVASESSIONID=12345Admin");
        HttpEntity<RegisterDoctorDtoRequest> request = new HttpEntity<>(doctor, httpHeaders);
        ResponseEntity<RegisterDoctorDtoResponse> response = restTemplate.exchange(url, HttpMethod.POST, request, RegisterDoctorDtoResponse.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testGetById() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JAVASESSIONID=12345Admin");
        HttpEntity<HttpHeaders> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<GetByIdDoctorDtoResponse> response = restTemplate.exchange(url + "/{id}?schedule=yes&startDate=2020-04-15&endDate=2020-04-20", HttpMethod.GET, request, GetByIdDoctorDtoResponse.class, 18);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(18, Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    public void testGetByIdFail() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JAVASESSIONID=12345Patient");
        HttpEntity<HttpHeaders> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<GetByIdDoctorDtoResponse> response = restTemplate.exchange(url + "/{id}?schedule=yes&startDate=2020-04-20&endDate=2020-04-15", HttpMethod.GET, request, GetByIdDoctorDtoResponse.class, 7);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testGetAllWithParams() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JAVASESSIONID=12345Admin");
        HttpEntity<HttpHeaders> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<List> response = restTemplate.exchange(url + "?schedule=no&startDate=2020-04-15&endDate=2020-04-20&speciality=dentist", HttpMethod.GET, request, List.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotEquals(0, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    public void testEditSchedule() {
        List<DayScheduleDtoRequest> dayScheduleDtoRequest = new ArrayList<>();
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Mon", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Tue", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Thu", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Fri", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        HttpHeaders httpHeaders = new HttpHeaders();
        EditScheduleDoctorDtoRequest schedule = new EditScheduleDoctorDtoRequest(LocalDate.of(2020, 4, 22), LocalDate.of(2020, 4, 30), dayScheduleDtoRequest, null, 17);
        httpHeaders.add("Cookie", "JAVASESSIONID=12345Admin");
        HttpEntity<EditScheduleDoctorDtoRequest> request = new HttpEntity<>(schedule, httpHeaders);
        ResponseEntity<EditScheduleDoctorDtoResponse> response = restTemplate.exchange(url + "/{id}", HttpMethod.PUT, request, EditScheduleDoctorDtoResponse.class, 18);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(Objects.requireNonNull(response.getBody()).getSchedule());
        Assert.assertEquals("Талгат", response.getBody().getFirstName());
    }

    @Test
    public void testEditScheduleFail() {
        List<DayScheduleDtoRequest> dayScheduleDtoRequest = new ArrayList<>();
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Mon", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Tue", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Thu", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        dayScheduleDtoRequest.add(new DayScheduleDtoRequest("Fri", LocalTime.of(8, 0), LocalTime.of(16, 0)));
        HttpHeaders httpHeaders = new HttpHeaders();
        EditScheduleDoctorDtoRequest schedule = new EditScheduleDoctorDtoRequest(LocalDate.of(2020, 4, 22), LocalDate.of(2020, 4, 30), dayScheduleDtoRequest, new WeekDaysScheduleDto(LocalTime.of(8, 0), LocalTime.of(16, 0), new ArrayList<String>()), 17);
        httpHeaders.add("Cookie", "JAVASESSIONID=12345Admin");
        HttpEntity<EditScheduleDoctorDtoRequest> request = new HttpEntity<>(schedule, httpHeaders);
        ResponseEntity<EditScheduleDoctorDtoResponse> response = restTemplate.exchange(url + "/{id}", HttpMethod.PUT, request, EditScheduleDoctorDtoResponse.class, 1);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testDelete() {
        testRegister();
        DeleteDoctorDtoRequest deleteDate = new DeleteDoctorDtoRequest(LocalDate.of(2020, 4, 30));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JAVASESSIONID=12345Admin");
        HttpEntity<DeleteDoctorDtoRequest> request = new HttpEntity<>(deleteDate, httpHeaders);
        ResponseEntity<EmptyResponse> response = restTemplate.exchange(url + "/{id}", HttpMethod.DELETE, request, EmptyResponse.class, 19);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(response.getBody());
    }

    @Test
    public void testDeleteFail() {
        DeleteDoctorDtoRequest deleteDate = new DeleteDoctorDtoRequest(LocalDate.of(2020, 4, 30));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JAVASESSIONID=12345Patient");
        HttpEntity<DeleteDoctorDtoRequest> request = new HttpEntity<>(deleteDate, httpHeaders);
        ResponseEntity<EmptyResponse> response = restTemplate.exchange(url + "/{id}", HttpMethod.DELETE, request, EmptyResponse.class, 8);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
