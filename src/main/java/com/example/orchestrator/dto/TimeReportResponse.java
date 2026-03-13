package com.example.orchestrator.dto;

import java.time.LocalDate;

public class TimeReportResponse {
    private String solarDate;
    private String lunarDate;
    private String weekday;
    private String solarTerm;
    private String zodiac;

    public TimeReportResponse() {
    }

    public TimeReportResponse(String solarDate, String lunarDate, String weekday, String solarTerm, String zodiac) {
        this.solarDate = solarDate;
        this.lunarDate = lunarDate;
        this.weekday = weekday;
        this.solarTerm = solarTerm;
        this.zodiac = zodiac;
    }

    public String getSolarDate() {
        return solarDate;
    }

    public void setSolarDate(String solarDate) {
        this.solarDate = solarDate;
    }

    public String getLunarDate() {
        return lunarDate;
    }

    public void setLunarDate(String lunarDate) {
        this.lunarDate = lunarDate;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getSolarTerm() {
        return solarTerm;
    }

    public void setSolarTerm(String solarTerm) {
        this.solarTerm = solarTerm;
    }

    public String getZodiac() {
        return zodiac;
    }

    public void setZodiac(String zodiac) {
        this.zodiac = zodiac;
    }
}
