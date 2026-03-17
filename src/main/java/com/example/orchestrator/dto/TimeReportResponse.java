package com.example.orchestrator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for time report endpoint.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeReportResponse {
    private String solarDate;
    private String lunarDate;
    private String weekday;
    private String solarTerm;
    private String zodiac;
}
