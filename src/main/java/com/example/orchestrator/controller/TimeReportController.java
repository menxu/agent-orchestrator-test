package com.example.orchestrator.controller;

import com.example.orchestrator.dto.TimeReportResponse;
import com.example.orchestrator.util.LunarCalendar;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class TimeReportController {

    @GetMapping("/time-report")
    public TimeReportResponse getTimeReport() {
        LocalDate now = LocalDate.now();

        String solarDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LunarCalendar.LunarDate lunar = LunarCalendar.solarToLunar(now);
        String weekday = LunarCalendar.getWeekday(now);
        String solarTerm = LunarCalendar.getSolarTerm(now);
        String zodiac = lunar.zodiac;

        TimeReportResponse response = new TimeReportResponse();
        response.setSolarDate(solarDate);
        response.setLunarDate(lunar.toString());
        response.setWeekday(weekday);
        response.setSolarTerm(solarTerm.isEmpty() ? "无" : solarTerm);
        response.setZodiac(zodiac);

        return response;
    }
}
