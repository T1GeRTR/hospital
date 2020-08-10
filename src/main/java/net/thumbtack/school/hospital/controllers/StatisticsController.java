package net.thumbtack.school.hospital.controllers;

import net.thumbtack.school.hospital.exception.ServerException;
import net.thumbtack.school.hospital.response.statictics.StatisticsDtoResponse;
import net.thumbtack.school.hospital.services.StatisticsService;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class StatisticsController {
    private StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping(path = "/api/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    public StatisticsDtoResponse getStatistics(@Param("startDate") String startDate, @Param("endDate") String endDate) throws ServerException {
        if (startDate == null && endDate == null) {
            return statisticsService.getStatistics(null, null);
        }
        if (startDate == null) {
            return statisticsService.getStatistics(null, LocalDate.parse(endDate));
        }
        if (endDate == null) {
            return statisticsService.getStatistics(LocalDate.parse(startDate), null);
        }
        return statisticsService.getStatistics(LocalDate.parse(startDate), LocalDate.parse(endDate));
    }
}
