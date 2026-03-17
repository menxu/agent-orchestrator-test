# Agent Orchestrator

Agent Orchestrator 是一个智能体编排框架，用于协调和管理多个 AI 智能体的协作工作流。

本项目初衷是为了验证 https://github.com/menxu/agent-orchestrator

## 安装

### 初始化

```bash
ao init
```

### 安装 GitHub CLI

```bash
# 安装 GitHub CLI
brew install gh

# 验证安装
gh --version

# 登录 GitHub 账号
gh auth login
```

## Agent Orchestrator 使用

### 认证与状态

```bash
# 查看 GitHub 认证状态
gh auth status

# 查看所有会话状态
ao status
```

### 启动与停止

```bash
# 停止当前运行的编排器
ao stop

# 重新启动
ao start

# 查看状态确认运行正常
ao status
```

### 会话管理

```bash
# 查看所有会话，找到你的 SESSION ID
ao session ls

# 应该会看到类似这样的输出：
# SESSION ID          PROJECT               BRANCH           STATUS      AGE
# ao-1                agent-orchestrator    feat/issue-1     IN_PROGRESS 5m

# 发送消息给会话
ao send ao-1 "请确保添加错误处理逻辑"

# 启动一个独立的智能体任务
ao spawn agent-orchestrator 7
```

## tmux 常用命令

### 基本操作

```bash
# 查看 tmux 会话 ID 列表
tmux ls

# 重新连接到会话
tmux attach -t 6ccf271c548a-ao-1
```

### 常用 tmux 操作备忘

| 操作 | 命令/快捷键 |
|------|-------------|
| 列出所有会话 | `tmux ls` |
| 连接到会话 | `tmux attach -t 会话 ID` |
| 从会话中分离（不终止） | `Ctrl+b` 然后 `d` |
| 在会话内查看会话列表 | `Ctrl+b` 然后 `s` |
| 终止会话 | `tmux kill-session -t 会话 ID` |

## GitHub Issue 自动轮询

使用 Polling 轮询让系统每隔一段时间自动检查新的 Issue。

### 创建轮询脚本

```bash
cat > scripts/github-tracker.mjs << 'EOF'
import { execSync } from 'child_process';
import fs from 'fs';
import path from 'path';
import { fileURLToPath } from 'url';

const __dirname = path.dirname(fileURLToPath(import.meta.url));

// 配置
const config = {
  repo: 'menxu/agent-orchestrator-test',
  pollInterval: 30000, // 30 秒
  projectPath: '/Users/edy/Workspace/AI/github/agent-orchestrator',
  aoPath: 'node /Users/edy/Workspace/AI/github/agent-orchestrator/packages/cli/dist/index.js'
};

console.log('使用命令:', config.aoPath);

// 记录已处理的 issues
const processedFile = path.join(process.env.HOME, '.agent-orchestrator', 'processed-issues.json');
let processedIssues = new Set();

// 确保目录存在
fs.mkdirSync(path.dirname(processedFile), { recursive: true });

// 加载已处理的 issues
try {
  if (fs.existsSync(processedFile)) {
    const data = JSON.parse(fs.readFileSync(processedFile, 'utf8'));
    processedIssues = new Set(data);
    console.log(`已加载 ${processedIssues.size} 个已处理的 issues`);
  } else {
    console.log('创建新的 processed issues 记录');
  }
} catch (err) {
  console.log('创建新的 processed issues 记录');
}

function saveProcessed() {
  try {
    fs.writeFileSync(processedFile, JSON.stringify([...processedIssues]));
  } catch (err) {
    console.error('保存 processed issues 失败:', err.message);
  }
}

async function spawnIssue(issue) {
  console.log(`发现新 issue #${issue.number}: ${issue.title}`);

  try {
    const cmd = `${config.aoPath} spawn agent-orchestrator ${issue.number}`;
    console.log(`执行：${cmd}`);

    const output = execSync(cmd, {
      cwd: config.projectPath,
      encoding: 'utf8',
      shell: '/bin/bash',
      env: {
        ...process.env,
        PATH: process.env.PATH
      }
    });

    console.log(`Issue #${issue.number} 已触发`);
    console.log(output);

    processedIssues.add(issue.number.toString());
    saveProcessed();

  } catch (err) {
    console.error(`Issue #${issue.number} 触发失败:`, err.message);
    if (err.stdout) console.log('stdout:', err.stdout);
    if (err.stderr) console.log('stderr:', err.stderr);
  }
}

async function poll() {
  console.log(`\n轮询中... ${new Date().toLocaleString()}`);

  try {
    const output = execSync(
      `gh issue list --repo ${config.repo} --state open --json number,title,body,url,createdAt --limit 100`,
      { encoding: 'utf8' }
    );

    const issues = JSON.parse(output);
    console.log(`找到 ${issues.length} 个 open issues`);

    issues.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));

    if (issues.length > 0) {
      console.log('最新 issue:', issues.slice(0, 3).map(i => `#${i.number}: ${i.title}`).join(', '));
    }

    let triggered = false;
    for (const issue of issues) {
      const issueNum = issue.number.toString();

      if (!processedIssues.has(issueNum)) {
        console.log(`发现未处理 issue #${issueNum}`);
        await spawnIssue(issue);
        triggered = true;
        await new Promise(resolve => setTimeout(resolve, 2000));
      }
    }

    if (!triggered) {
      console.log('没有新的 issues');
    }

  } catch (err) {
    console.error('轮询失败:', err.message);
  }
}

console.log('开始轮询 GitHub issues...');
poll().catch(console.error);
setInterval(() => poll().catch(console.error), config.pollInterval);
EOF
```

### 配置 package.json

```json
{
  "scripts": {
    "tracker": "node scripts/github-tracker.mjs",
    "tracker:pm2": "pm2 start scripts/github-tracker.mjs --name github-tracker",
    "tracker:stop": "pm2 stop github-tracker",
    "tracker:logs": "pm2 logs github-tracker"
  }
}
```

### 运行轮询

```bash
npm run tracker
```
