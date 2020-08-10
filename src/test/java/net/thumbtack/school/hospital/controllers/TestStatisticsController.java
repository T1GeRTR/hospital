package net.thumbtack.school.hospital.controllers;

import net.thumbtack.school.hospital.response.SettingsDtoResponse;
import net.thumbtack.school.hospital.response.statictics.StatisticsDtoResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestStatisticsController extends TestControllerBaseCreateHistory {

    private static final String url = "/api/statistics";

    @Test
    public void testGetStatistics() {
        ResponseEntity<StatisticsDtoResponse> response = restTemplate.getForEntity(url, StatisticsDtoResponse.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(Objects.requireNonNull(response.getBody()));
        Assert.assertNotEquals(0, Objects.requireNonNull(response.getBody()).getDoctors().size());
        Assert.assertNotEquals(0, Objects.requireNonNull(response.getBody()).getPatients().size());
        Assert.assertNotNull(response.getBody().getAppointments());
    }

    @Test
    public void testGetStatisticsFail() {
        ResponseEntity<StatisticsDtoResponse> response = restTemplate.getForEntity(url + "?startDate=2020-04-30&endDate=2020-01-01", StatisticsDtoResponse.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
