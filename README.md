# 物流订单跟踪系统

## 项目简介

物流订单跟踪系统是一个基于 Spring Boot 的现代化物流管理平台，提供实时订单跟踪、配送员管理、路线规划等功能。系统集成了高德地图服务，为用户提供直观的物流跟踪体验。

## 功能特性

### 订单管理
- 订单创建与管理
- 实时订单状态跟踪
- 订单历史记录查询
- 多维度订单搜索
- 订单状态自动更新

### 配送员管理
- 配送员信息管理
- 实时在线状态监控
- 工作区域分配
- 配送员评分系统
- 绩效统计分析

### 物流跟踪
- 实时位置追踪
- 配送路线规划
- 预计送达时间计算
- 轨迹回放功能
- 异常情况预警

### 地图服务
- 高德地图集成
- 智能路线规划
- 地理编码服务
- 距离计算
- 实时路况信息

### 数据统计
- 配送员绩效报告
- 订单完成率统计
- 配送时效分析
- 区域订单分布
- 自定义时间段统计

### 消息通知
- 订单状态变更通知
- 异常情况提醒
- 邮件通知服务
- 实时消息推送

## 技术栈

### 后端技术
- **核心框架**: Spring Boot
- **数据库**: MongoDB
- **缓存**: Redis
- **消息队列**: RabbitMQ
- **API文档**: Swagger
- **日志框架**: Logback

### 前端技术
- **框架**: Vue.js
- **UI组件**: Element Plus
- **地图服务**: 高德地图 JavaScript API
- **HTTP客户端**: Axios
- **状态管理**: Vuex

### 第三方服务
- **地图服务**: 高德地图 API
- **邮件服务**: Spring Mail
- **对象存储**: 阿里云 OSS（可选）

## 系统要求

- JDK 17 或更高版本
- Maven 3.6 或更高版本
- MongoDB 4.4 或更高版本
- Redis 6.0 或更高版本
- RabbitMQ 3.8 或更高版本
- Node.js 14 或更高版本

## 快速开始

### 环境准备
1. 安装必要的开发工具：
   ```bash
   # 安装 JDK
   brew install openjdk@17

   # 安装 Maven
   brew install maven

   # 安装 MongoDB
   brew install mongodb-community

   # 安装 Redis
   brew install redis

   # 安装 RabbitMQ
   brew install rabbitmq
   ```

2. 启动必要的服务：
   ```bash
   # 启动 MongoDB
   brew services start mongodb-community

   # 启动 Redis
   brew services start redis

   # 启动 RabbitMQ
   brew services start rabbitmq
   ```

### 配置修改

1. 修改 `application.yml` 配置文件：
   ```yaml
   spring:
     data:
       mongodb:
         uri: mongodb://localhost:27017/logistics
     redis:
       host: localhost
       port: 6379
     rabbitmq:
       host: localhost
       port: 5672
       username: guest
       password: guest
   
   amap:
     key: 您的高德地图API密钥
   ```

2. 配置邮件服务（可选）：
   ```yaml
   spring:
     mail:
       host: smtp.example.com
       port: 587
       username: your-email@example.com
       password: your-password
       properties:
         mail.smtp.auth: true
         mail.smtp.starttls.enable: true
   ```

### 项目构建与运行

1. 克隆项目：
   ```bash
   git clone [项目地址]
   cd logistics-tracking-system
   ```

2. 构建后端服务：
   ```bash
   mvn clean install
   ```

3. 运行后端服务：
   ```bash
   java -jar target/logistics-tracking-system.jar
   ```

4. 构建前端项目：
   ```bash
   cd frontend
   npm install
   npm run build
   ```

5. 访问系统：
   - 打开浏览器访问: `http://localhost:8080`

## 项目结构

```
src/
├── main/
│   ├── java/
│   │   └── com/logistics/tracking/
│   │       ├── config/        # 配置类
│   │       ├── controller/    # 控制器
│   │       ├── model/        # 数据模型
│   │       ├── repository/   # 数据访问层
│   │       ├── service/      # 业务逻辑层
│   │       └── task/         # 定时任务
│   └── resources/
│       ├── static/          # 静态资源
│       ├── templates/       # 页面模板
│       └── application.yml  # 配置文件
```

```
[前端页面(Vue)] <--HTTP--> [Spring Boot Controller]
                                  |
                                  v
                       [Service业务逻辑层]
                                  |
        ------------------------------------------------
        |              |               |               |
    [MongoDB]       [Redis]       [RabbitMQ]       [AOP日志]
  (物流轨迹存储)   (地址缓存)    (消息通知异步)   (敏感操作监控)
                                  |
                                  v
                        [邮件发送服务(SMTP)]
                                  |
                                  v
                        [高德地图 API 服务]

```

## API 文档

详细的 API 文档请参考 [API.md](API.md)

## 使用指南

### 订单跟踪
1. 在首页输入订单号
2. 点击"查询"按钮
3. 查看订单实时位置和状态
4. 可查看历史轨迹信息

### 配送员管理
1. 登录管理后台
2. 进入"配送员管理"模块
3. 可进行添加、编辑、删除配送员
4. 查看配送员实时状态和绩效

### 统计分析
1. 进入"统计报表"模块
2. 选择统计时间范围
3. 查看各类统计数据
4. 导出报表（可选）

## 常见问题

1. **系统无法启动**
   - 检查必要服务是否启动（MongoDB、Redis、RabbitMQ）
   - 检查配置文件中的连接信息是否正确
   - 查看日志文件了解具体错误信息

2. **地图无法显示**
   - 检查高德地图 API 密钥是否正确
   - 确认是否有正确的网络连接
   - 检查浏览器控制台是否有错误信息

3. **消息通知失败**
   - 检查 RabbitMQ 连接状态
   - 验证邮件服务器配置
   - 查看日志中的具体错误信息

## 开发指南

### 代码规范
- 遵循 Java 代码规范
- 使用 4 空格缩进
- 类名使用 PascalCase
- 方法名使用 camelCase
- 常量使用全大写下划线分隔

### 提交规范
- feat: 新功能
- fix: 修复问题
- docs: 文档修改
- style: 代码格式修改
- refactor: 代码重构
- test: 测试用例修改
- chore: 其他修改

### 分支管理
- master: 主分支，用于生产环境
- develop: 开发分支，用于功能集成
- feature/*: 功能分支
- hotfix/*: 紧急修复分支

## 部署指南

### 开发环境
1. 按照系统要求安装必要组件
2. 修改配置文件为开发环境配置
3. 使用开发工具运行项目

### 测试环境
1. 准备测试服务器
2. 配置测试环境数据库和中间件
3. 使用 Jenkins 等工具自动部署

### 生产环境
1. 准备生产服务器
2. 配置生产环境数据库和中间件
3. 使用 Docker 容器化部署
4. 配置负载均衡和高可用

## 贡献指南

1. Fork 项目
2. 创建功能分支
3. 提交变更
4. 推送到远程分支
5. 创建 Pull Request

## 版本历史

- v1.0.0 (2024-01-01)
  - 初始版本发布
  - 基础功能实现

## 许可证

本项目采用 MIT 许可证，详情请参见 [LICENSE](LICENSE) 文件。

## 数据库与中间件部署说明

### 1. MongoDB
- 配置文件位置：`src/main/resources/application.yml`
- 连接配置示例：
  ```yaml
  spring:
    data:
      mongodb:
        host: localhost
        port: 27017
        database: logistics_tracking
  ```
- 部署建议：
  1. 安装 MongoDB（推荐4.x及以上版本）。
  2. 启动服务：`mongod --dbpath <你的数据目录>`
  3. 可用 Navicat、Compass 等工具管理数据。
  4. 如需远程访问，需配置 bindIp、用户权限等。

### 2. Redis
- 配置文件位置：`src/main/resources/application.yml`
- 连接配置示例：
  ```yaml
  spring:
    redis:
      host: localhost
      port: 6379
      database: 0
  ```
- 部署建议：
  1. 安装 Redis（推荐5.x及以上版本）。
  2. 启动服务：`redis-server`
  3. 如需远程访问，需配置 `bind 0.0.0.0` 和 `requirepass`。

### 3. RabbitMQ
- 配置文件位置：`src/main/resources/application.yml`
- 连接配置示例：
  ```yaml
  spring:
    rabbitmq:
      host: localhost
      port: 5672
      username: guest
      password: guest
      virtual-host: /
  ```
- 部署建议：
  1. 安装 RabbitMQ（推荐3.x及以上版本）。
  2. 启动服务：`rabbitmq-server`
  3. 管理后台默认端口为15672，访问 http://localhost:15672
  4. 如需远程访问，需开放端口并配置用户权限。

## 容器化部署说明

推荐使用 Docker 容器快速部署 MongoDB、Redis、RabbitMQ 等依赖服务。

### 1. docker-compose.yml 示例

```yaml
version: '3.8'
services:
  mongodb:
    image: mongo:4.4
    container_name: logistics_mongodb
    restart: always
    ports:
      - "27017:27017"
    volumes:
      - ./data/mongodb:/data/db

  redis:
    image: redis:6.2
    container_name: logistics_redis
    restart: always
    ports:
      - "6379:6379"
    volumes:
      - ./data/redis:/data

  rabbitmq:
    image: rabbitmq:3.13.7-management-alpine
    container_name: logistics_rabbitmq
    restart: always
    ports:
      - "5672:5672"
      - "15672:15672"
    environment:
      RABBITMQ_DEFAULT_USER: guest
      RABBITMQ_DEFAULT_PASS: guest
```

### 2. 启动命令

```bash
docker-compose up -d
```

### 3. 端口说明

| 服务      | 容器端口 | 主机端口 | 说明           |
|-----------|----------|----------|----------------|
| MongoDB   | 27017    | 27017    | 数据库服务     |
| Redis     | 6379     | 6379     | 缓存服务       |
| RabbitMQ  | 5672     | 5672     | 消息队列服务   |
| RabbitMQ  | 15672    | 15672    | 管理后台Web    |

### 4. 连接配置

- application.yml 保持默认即可（host 填 localhost，端口如上）。
- RabbitMQ 管理后台访问：http://localhost:15672  默认账号 guest/guest

### 5. 其他建议

- 如需持久化数据，可加挂载参数（如上 volumes）。
- 如需远程访问，注意防火墙和端口开放。

---

## 主要数据库集合结构

### 1. 快递员集合（couriers）
| 字段名             | 类型        | 说明             |
|--------------------|------------|------------------|
| _id                | ObjectId   | 主键ID           |
| name               | String     | 快递员姓名       |
| phone              | String     | 电话号码         |
| idCard             | String     | 身份证号         |
| workArea           | String     | 工作区域         |
| online             | Boolean    | 是否在线         |
| active             | Boolean    | 是否在职         |
| completedOrders    | Int        | 完成订单数       |
| complaintsCount    | Int        | 投诉次数         |
| averageRating      | Double/Int | 平均评分         |
| totalDistance      | Double     | 总配送距离       |
| dailyDeliveries    | Int        | 当日配送单数     |
| dailyDistance      | Double     | 当日配送距离     |
| dailyRating        | Double/Int | 当日评分         |
| monthlyDeliveries  | Int        | 当月配送单数     |
| monthlyDistance    | Double     | 当月配送距离     |
| monthlyRating      | Double/Int | 当月评分         |
| totalDeliveries    | Int        | 总配送单数       |
| createTime         | Date       | 创建时间         |
| updateTime         | Date       | 更新时间         |
| lastOnlineTime     | Date       | 最后在线时间     |
| _class             | String     | Java实体类标识   |

### 2. 操作日志集合（operation_logs）
| 字段名               | 类型      | 说明               |
|----------------------|-----------|--------------------|
| _id                  | ObjectId  | 主键ID             |
| operationType        | String    | 操作类型           |
| operationDescription | String    | 操作描述           |
| operationTime        | Date      | 操作时间           |
| operatorId           | String    | 操作人ID           |
| operatorName         | String    | 操作人姓名         |
| targetId             | String    | 目标对象ID         |
| targetType           | String    | 目标对象类型       |
| details              | String    | 详细信息           |
| success              | Boolean   | 是否成功           |
| ip                   | String    | 操作IP             |
| _class               | String    | Java实体类标识     |

### 3. 订单集合（orders）
| 字段名           | 类型      | 说明             |
|------------------|-----------|------------------|
| _id              | ObjectId  | 主键ID           |
| courierId        | String    | 快递员ID         |
| courierName      | String    | 快递员姓名       |
| status           | String    | 订单状态         |
| senderName       | String    | 发件人姓名       |
| senderAddress    | String    | 发件人地址       |
| senderEmail      | String    | 发件人邮箱       |
| receiverName     | String    | 收件人姓名       |
| receiverAddress  | String    | 收件人地址       |
| receiverEmail    | String    | 收件人邮箱       |
| currentLat       | Double/Int| 当前纬度         |
| currentLng       | Double/Int| 当前经度         |
| weight           | Int       | 重量             |
| volume           | Int/Double| 体积             |
| amount           | Double    | 金额             |
| isPaid           | Boolean   | 是否已支付       |
| createTime       | Date      | 创建时间         |
| updateTime       | Date      | 更新时间         |
| pickupTime       | Date      | 揽件时间         |
| deliveryTime     | Date      | 派送完成时间     |
| dispatchTime     | Date      | 派单时间         |
| transitTime      | Date      | 在途时间         |
| remarks          | String    | 备注             |
| _class           | String    | Java实体类标识   |

### 4. 轨迹集合（logistics_track）
| 字段名         | 类型      | 说明           |
|----------------|-----------|----------------|
| _id            | ObjectId  | 主键ID         |
| orderId        | String    | 订单ID         |
| courierId      | String    | 快递员ID       |
| location       | String    | 位置描述       |
| latitude       | Double    | 纬度           |
| longitude      | Double    | 经度           |
| status         | String    | 订单状态       |
| operatorId     | String    | 操作人ID       |
| operatorName   | String    | 操作人姓名     |
| operatorType   | String    | 操作人类型     |
| description    | String    | 备注           |
| time           | Date      | 轨迹时间       |
| _class         | String    | Java实体类标识 |

### 5. 评价集合（courier_ratings）
| 字段名         | 类型      | 说明           |
|----------------|-----------|----------------|
| _id            | ObjectId  | 主键ID         |
| courierId      | String    | 快递员ID       |
| rating         | Int       | 评分           |
| comment        | String    | 评价内容       |
| createTime     | Date      | 评价时间       |
| _class         | String    | Java实体类标识 |

---

如需更多数据库结构或部署细节，请参考 `src/main/resources/application.yml` 配置及 `数据库及队列/logistics_tracking.js` 脚本。

