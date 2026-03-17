package com.example.orchestrator.dto;

/**
 * DTO for system information endpoint.
 * Note: Lombok annotations are configured in pom.xml but require Java 17-21 to process.
 */
public class SystemInfoDto {
    private String hostname;
    private String osName;
    private String osVersion;
    private String architecture;
    private CpuInfo cpu;
    private MemoryInfo memory;
    private DiskInfo disk;
    private LoadInfo load;

    public SystemInfoDto() {
    }

    public SystemInfoDto(String hostname, String osName, String osVersion, String architecture,
                         CpuInfo cpu, MemoryInfo memory, DiskInfo disk, LoadInfo load) {
        this.hostname = hostname;
        this.osName = osName;
        this.osVersion = osVersion;
        this.architecture = architecture;
        this.cpu = cpu;
        this.memory = memory;
        this.disk = disk;
        this.load = load;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getArchitecture() {
        return architecture;
    }

    public void setArchitecture(String architecture) {
        this.architecture = architecture;
    }

    public CpuInfo getCpu() {
        return cpu;
    }

    public void setCpu(CpuInfo cpu) {
        this.cpu = cpu;
    }

    public MemoryInfo getMemory() {
        return memory;
    }

    public void setMemory(MemoryInfo memory) {
        this.memory = memory;
    }

    public DiskInfo getDisk() {
        return disk;
    }

    public void setDisk(DiskInfo disk) {
        this.disk = disk;
    }

    public LoadInfo getLoad() {
        return load;
    }

    public void setLoad(LoadInfo load) {
        this.load = load;
    }

    public static class CpuInfo {
        private String name;
        private int physicalCores;
        private int logicalProcessors;
        private long frequencyHz;

        public CpuInfo() {
        }

        public CpuInfo(String name, int physicalCores, int logicalProcessors, long frequencyHz) {
            this.name = name;
            this.physicalCores = physicalCores;
            this.logicalProcessors = logicalProcessors;
            this.frequencyHz = frequencyHz;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPhysicalCores() {
            return physicalCores;
        }

        public void setPhysicalCores(int physicalCores) {
            this.physicalCores = physicalCores;
        }

        public int getLogicalProcessors() {
            return logicalProcessors;
        }

        public void setLogicalProcessors(int logicalProcessors) {
            this.logicalProcessors = logicalProcessors;
        }

        public long getFrequencyHz() {
            return frequencyHz;
        }

        public void setFrequencyHz(long frequencyHz) {
            this.frequencyHz = frequencyHz;
        }
    }

    public static class MemoryInfo {
        private long totalBytes;
        private long availableBytes;
        private long usedBytes;
        private double usagePercent;

        public MemoryInfo() {
        }

        public MemoryInfo(long totalBytes, long availableBytes, long usedBytes, double usagePercent) {
            this.totalBytes = totalBytes;
            this.availableBytes = availableBytes;
            this.usedBytes = usedBytes;
            this.usagePercent = usagePercent;
        }

        public long getTotalBytes() {
            return totalBytes;
        }

        public void setTotalBytes(long totalBytes) {
            this.totalBytes = totalBytes;
        }

        public long getAvailableBytes() {
            return availableBytes;
        }

        public void setAvailableBytes(long availableBytes) {
            this.availableBytes = availableBytes;
        }

        public long getUsedBytes() {
            return usedBytes;
        }

        public void setUsedBytes(long usedBytes) {
            this.usedBytes = usedBytes;
        }

        public double getUsagePercent() {
            return usagePercent;
        }

        public void setUsagePercent(double usagePercent) {
            this.usagePercent = usagePercent;
        }
    }

    public static class DiskInfo {
        private long totalBytes;
        private long freeBytes;
        private long usedBytes;
        private double usagePercent;

        public DiskInfo() {
        }

        public DiskInfo(long totalBytes, long freeBytes, long usedBytes, double usagePercent) {
            this.totalBytes = totalBytes;
            this.freeBytes = freeBytes;
            this.usedBytes = usedBytes;
            this.usagePercent = usagePercent;
        }

        public long getTotalBytes() {
            return totalBytes;
        }

        public void setTotalBytes(long totalBytes) {
            this.totalBytes = totalBytes;
        }

        public long getFreeBytes() {
            return freeBytes;
        }

        public void setFreeBytes(long freeBytes) {
            this.freeBytes = freeBytes;
        }

        public long getUsedBytes() {
            return usedBytes;
        }

        public void setUsedBytes(long usedBytes) {
            this.usedBytes = usedBytes;
        }

        public double getUsagePercent() {
            return usagePercent;
        }

        public void setUsagePercent(double usagePercent) {
            this.usagePercent = usagePercent;
        }
    }

    public static class LoadInfo {
        private double systemLoadAverage1Min;
        private double systemLoadAverage5Min;
        private double systemLoadAverage15Min;
        private double cpuUsagePercent;

        public LoadInfo() {
        }

        public LoadInfo(double systemLoadAverage1Min, double systemLoadAverage5Min,
                       double systemLoadAverage15Min, double cpuUsagePercent) {
            this.systemLoadAverage1Min = systemLoadAverage1Min;
            this.systemLoadAverage5Min = systemLoadAverage5Min;
            this.systemLoadAverage15Min = systemLoadAverage15Min;
            this.cpuUsagePercent = cpuUsagePercent;
        }

        public double getSystemLoadAverage1Min() {
            return systemLoadAverage1Min;
        }

        public void setSystemLoadAverage1Min(double systemLoadAverage1Min) {
            this.systemLoadAverage1Min = systemLoadAverage1Min;
        }

        public double getSystemLoadAverage5Min() {
            return systemLoadAverage5Min;
        }

        public void setSystemLoadAverage5Min(double systemLoadAverage5Min) {
            this.systemLoadAverage5Min = systemLoadAverage5Min;
        }

        public double getSystemLoadAverage15Min() {
            return systemLoadAverage15Min;
        }

        public void setSystemLoadAverage15Min(double systemLoadAverage15Min) {
            this.systemLoadAverage15Min = systemLoadAverage15Min;
        }

        public double getCpuUsagePercent() {
            return cpuUsagePercent;
        }

        public void setCpuUsagePercent(double cpuUsagePercent) {
            this.cpuUsagePercent = cpuUsagePercent;
        }
    }
}
