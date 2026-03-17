package com.example.orchestrator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for system information endpoint.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemInfoDto {
    private String hostname;
    private String osName;
    private String osVersion;
    private String architecture;
    private CpuInfo cpu;
    private MemoryInfo memory;
    private DiskInfo disk;
    private LoadInfo load;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CpuInfo {
        private String name;
        private int physicalCores;
        private int logicalProcessors;
        private long frequencyHz;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemoryInfo {
        private long totalBytes;
        private long availableBytes;
        private long usedBytes;
        private double usagePercent;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DiskInfo {
        private long totalBytes;
        private long freeBytes;
        private long usedBytes;
        private double usagePercent;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoadInfo {
        private double systemLoadAverage1Min;
        private double systemLoadAverage5Min;
        private double systemLoadAverage15Min;
        private double cpuUsagePercent;
    }
}
