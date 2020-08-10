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
public class TestSettingController extends TestControllerBaseCreateHistory {

    private static final String url = "/api/settings";

    @Test
    public void testGetSettings() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", "JAVASESSIONID=12345Admin");
        HttpEntity<HttpHeaders> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<SettingsDtoResponse> response = restTemplate.exchange(url, HttpMethod.GET, request, SettingsDtoResponse.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(response.getBody());
    }
}
