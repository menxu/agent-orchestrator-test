package com.example.orchestrator.controller;

import com.example.orchestrator.dto.SystemInfoDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.Locale;

@RestController
public class SystemInfoController {

    @GetMapping("/system-info")
    public SystemInfoDto getSystemInfo() {
        SystemInfoDto dto = new SystemInfoDto();

        // OS Information
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String arch = System.getProperty("os.arch");

        dto.setHostname(getHostname());
        dto.setOsName(osName);
        dto.setOsVersion(osVersion);
        dto.setArchitecture(arch);

        // CPU Information
        SystemInfoDto.CpuInfo cpuInfo = new SystemInfoDto.CpuInfo();
        cpuInfo.setName(getProcessorName());
        cpuInfo.setPhysicalCores(Runtime.getRuntime().availableProcessors());
        cpuInfo.setLogicalProcessors(Runtime.getRuntime().availableProcessors());
        cpuInfo.setFrequencyHz(0); // Not easily available in pure Java
        dto.setCpu(cpuInfo);

        // Memory Information
        Runtime runtime = Runtime.getRuntime();
        SystemInfoDto.MemoryInfo memoryInfo = new SystemInfoDto.MemoryInfo();
        memoryInfo.setTotalBytes(runtime.totalMemory());
        memoryInfo.setAvailableBytes(runtime.freeMemory());
        memoryInfo.setUsedBytes(runtime.totalMemory() - runtime.freeMemory());
        memoryInfo.setUsagePercent((double) (runtime.totalMemory() - runtime.freeMemory()) / runtime.totalMemory() * 100);
        dto.setMemory(memoryInfo);

        // Disk Information
        SystemInfoDto.DiskInfo diskInfo = new SystemInfoDto.DiskInfo();
        diskInfo.setTotalBytes(java.io.File.listRoots().length > 0 ?
            java.io.File.listRoots()[0].getTotalSpace() : 0);
        diskInfo.setFreeBytes(java.io.File.listRoots().length > 0 ?
            java.io.File.listRoots()[0].getFreeSpace() : 0);
        diskInfo.setUsedBytes(java.io.File.listRoots().length > 0 ?
            java.io.File.listRoots()[0].getUsableSpace() : 0);
        diskInfo.setUsagePercent(diskInfo.getTotalBytes() > 0 ?
            (double) (diskInfo.getTotalBytes() - diskInfo.getFreeBytes()) / diskInfo.getTotalBytes() * 100 : 0);
        dto.setDisk(diskInfo);

        // Load Information
        SystemInfoDto.LoadInfo loadInfo = new SystemInfoDto.LoadInfo();
        double loadAverage = osBean.getSystemLoadAverage();
        loadInfo.setSystemLoadAverage1Min(loadAverage);
        loadInfo.setSystemLoadAverage5Min(loadAverage);
        loadInfo.setSystemLoadAverage15Min(loadAverage);

        // Try to get CPU usage via OS command
        double cpuUsage = getCpuUsage();
        loadInfo.setCpuUsagePercent(cpuUsage);
        dto.setLoad(loadInfo);

        return dto;
    }

    private String getHostname() {
        try {
            String os = System.getProperty("os.name").toLowerCase(Locale.ROOT);
            ProcessBuilder pb;
            if (os.contains("win")) {
                pb = new ProcessBuilder("hostname");
            } else {
                pb = new ProcessBuilder("hostname");
            }
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            return line != null ? line.trim() : "unknown";
        } catch (Exception e) {
            return "unknown";
        }
    }

    private String getProcessorName() {
        try {
            String os = System.getProperty("os.name").toLowerCase(Locale.ROOT);
            ProcessBuilder pb;
            if (os.contains("mac")) {
                pb = new ProcessBuilder("sysctl", "-n", "machdep.cpu.brand_string");
            } else if (os.contains("linux")) {
                pb = new ProcessBuilder("sh", "-c", "cat /proc/cpuinfo | grep 'model name' | head -1 | cut -d':' -f2 | xargs");
            } else if (os.contains("win")) {
                pb = new ProcessBuilder("wmic", "cpu", "get", "name");
            } else {
                return "Unknown";
            }
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(" ");
            }
            String result = sb.toString().trim();
            // Remove header line for Windows
            if (os.contains("win") && result.contains("\n")) {
                result = result.split("\n")[1].trim();
            }
            return result.isEmpty() ? "Unknown" : result;
        } catch (Exception e) {
            return "Unknown";
        }
    }

    private double getCpuUsage() {
        try {
            com.sun.management.OperatingSystemMXBean sunOsBean =
                (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
            return sunOsBean.getCpuLoad() * 100;
        } catch (Exception e) {
            return 0;
        }
    }
}
