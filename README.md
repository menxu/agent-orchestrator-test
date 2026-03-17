# Agent Orchestrator

Agent Orchestrator 是一个基于 Spring Boot 3.2 的智能体编排框架，用于协调和管理多个 AI 智能体的协作工作流。

## 项目简介

本项目旨在提供一个统一的平台，用于编排、调度和管理 AI 智能体（如 Claude Code 等）的执行流程。通过定义清晰的任务分配和协作机制，实现复杂任务的自动化处理。

## 技术栈

- **Java 17** - 基础编程语言
- **Spring Boot 3.2.0** - 应用框架
- **Lombok** - 代码简化库
- **Maven** - 构建工具

## API 接口

### 1. Hello World

```bash
GET /hello
```

**响应示例：**
```
Hello World
```

### 2. 系统信息查询

```bash
GET /system-info
```

**响应示例：**
```json
{
  "hostname": "localhost",
  "osName": "Mac OS X",
  "osVersion": "14.5",
  "architecture": "aarch64",
  "cpu": {
    "name": "Apple M2",
    "physicalCores": 8,
    "logicalProcessors": 8
  },
  "memory": {
    "totalBytes": 8589934592,
    "usedBytes": 1073741824,
    "usagePercent": 12.5
  },
  "load": {
    "cpuUsagePercent": 5.2
  }
}
```

### 3. 时间报告（含农历）

```bash
GET /time-report
```

**响应示例：**
```json
{
  "solarDate": "2026-03-17",
  "lunarDate": "二月十八",
  "weekday": "星期二",
  "solarTerm": "惊蛰",
  "zodiac": "蛇"
}
```

## 快速开始

### 前置条件

- JDK 17+
- Maven 3.6+

### 构建项目

```bash
mvn clean install
```

### 运行应用

```bash
mvn spring-boot:run
```

应用启动后，访问 `http://localhost:8080` 查看服务状态。

## 项目结构

```
agent-orchestrator/
├── src/main/java/com/example/orchestrator/
│   ├── AgentOrchestratorApplication.java  # 主启动类
│   ├── controller/                        # REST 控制器
│   │   ├── HelloController.java
│   │   ├── SystemInfoController.java
│   │   └── TimeReportController.java
│   ├── dto/                               # 数据传输对象
│   │   ├── SystemInfoDto.java
│   │   └── TimeReportResponse.java
│   └── util/                              # 工具类
│       └── LunarCalendar.java             # 农历转换工具
└── pom.xml
```

## 开发指南

### 添加新的 API 端点

1. 在 `controller` 包下创建新的 Controller 类
2. 使用 `@RestController` 注解
3. 使用 `@GetMapping` / `@PostMapping` 等定义路由

### 添加新的依赖

在 `pom.xml` 的 `<dependencies>` 块中添加 Maven 依赖。

## License

MIT License
