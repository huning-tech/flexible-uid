# flexible-uid



## 功能介绍

分布式id生成器，功能类似UUID，底层基于雪花算法。雪花算法在分布式环境中生成全局唯一id需要合理配置datacenterId和workerId，flexible-uid便是完成此部分功能的一个探索。目前支持基于xml配置及zookeeper动态注册两种方式。



## 使用说明



### 准备工作



#### 1. 新建springboot工程[springboot-demo]
如果已有springboot工程,可以跳过此步骤。



##### 1.1 依赖配置 [springboot-demo\pom.xml]

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>org.example</groupId>
    <artifactId>springboot-demo</artifactId>
    <version>1.0-SNAPSHOT</version>
    
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <maven.compiler.encoding>UTF-8</maven.compiler.encoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
        <java.version>1.8</java.version>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-logging</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-simple</artifactId>
            <version>1.7.25</version>
        </dependency>
    </dependencies>
    
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>2.2.2.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
</project>
```

##### 1.2 应用配置 [springboot-demo\src\main\resources\application.properties]

```properties
server.port=8080
server.servlet.context-path=/
server.tomcat.uri-encoding=UTF-8
```

##### 1.3 日志配置 [springboot-demo\src\main\resources\simplelogger.properties]

```properties
org.slf4j.simpleLogger.defaultLogLevel=debug
org.slf4j.simpleLogger.showDateTime=true
org.slf4j.simpleLogger.dateTimeFormat=yyyy-MM-dd HH:mm:ss.SSS
org.slf4j.simpleLogger.showThreadName=true
org.slf4j.simpleLogger.showLogName=true
org.slf4j.simpleLogger.showShortLogName=true
```

##### 2.4 添加启动类 com.springboot.demo.SpringBootDemo

```java
package com.springboot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootDemo {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemo.class, args);
    }

}
```



### 基于zookeeper方式



#### 1. 启动zookeeper

#### 2. 添加依赖配置 [springboot-demo\pom.xml]

```xml
<dependency>
    <groupId>com.langdashu</groupId>
    <artifactId>flexible-uid-zookeeper</artifactId>
    <version>1.1</version>
</dependency>
<dependency>
    <groupId>com.langdashu</groupId>
    <artifactId>flexible-uid-spring-boot-starter</artifactId>
    <version>1.1</version>
</dependency>
```

#### 3. 添加配置文件 [springboot-demo\src\main\resources\flexible-uid.properties]

```properties
snowflake.startTimeMillis=1420041699999
## zk地址
zookeeper.address=127.0.0.1:2181
## 数据中心配置键
env.variable.datacenter=dc
## 预留
startup.arg.datacenter=dc
```

#### 4. 配置环境变量(非必选),不配置默认数据中心为"china"

```
dc=shenzhen
```

#### 5. 添加接口类com.springboot.demo.controller.UidController

```java
package com.springboot.demo.controller;

import com.langdashu.flexible.uid.core.exception.UidException;
import com.langdashu.flexible.uid.core.factory.UidFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UidController {

    @GetMapping("uid")
    @ResponseBody
    public Long generate() throws UidException {
        return UidFactory.getInstance().uid().getLong();
    }

}
```

#### 6. 启动springboot工程,启动成功日志如下

```
2021-10-04 17:39:17.172 [main] DEBUG UidAutoConfiguration - uid auto configure, ip:192.168.40.1, port:8080
2021-10-04 17:39:22.051 [main] DEBUG ZookeeperRegistry - dataCenterId: 0
2021-10-04 17:39:22.051 [main] DEBUG ZookeeperRegistry - workerId: 0
2021-10-04 17:39:22.051 [main] DEBUG ZookeeperRegistry - refreshWorker: Registration{dataCenter='shenzhen', worker='192.168.40.1:8080', workerId=0, dataCenterId=0, createdBy=1633340361842, updatedBy=1633340362051, version=2, path='/flexible-uid/workers/worker0000000029'}
```

#### 7. 浏览器访问接口

```
http://localhost:8080/uid
```



### 基于xml配置方式



#### 1. 添加依赖配置 [springboot-demo\pom.xml]

```xml
<dependency>
    <groupId>com.langdashu</groupId>
    <artifactId>flexible-uid-xml</artifactId>
    <version>1.1</version>
</dependency>

<dependency>
    <groupId>com.langdashu</groupId>
    <artifactId>flexible-uid-spring-boot-starter</artifactId>
    <version>1.1</version>
</dependency>
```

#### 2. 添加配置文件 [springboot-demo\src\main\resources\flexible-uid.properties]

```properties
snowflake.startTimeMillis=1420041699999
```

#### 3. 添加配置文件 [springboot-demo\src\main\resources\flexible-uid.xml]

**注意: 需要把自己本机ip配置到此文件中**

```xml
<?xml version="1.0" encoding="utf-8"?>
<uid>
    <datacenter code="beijing">
        <worker ip="192.168.40.1" port="8080"/>
        <worker ip="192.168.40.1" port="8081"/>
    </datacenter>
    <datacenter code="shanghai">
        <worker ip="192.168.40.1" port="8080"/>
        <worker ip="192.168.40.1" port="8081"/>
    </datacenter>
    <datacenter code="guangzhou">
        <worker ip="192.168.40.1" port="8080"/>
        <worker ip="192.168.40.1" port="8081"/>
    </datacenter>
    <datacenter code="shenzhen">
        <worker ip="192.168.40.1" port="8080"/>
        <worker ip="192.168.40.1" port="8081"/>
    </datacenter>
</uid>
```

#### 4. 添加接口类com.springboot.demo.controller.UidController

```java
package com.springboot.demo.controller;

import com.langdashu.flexible.uid.core.exception.UidException;
import com.langdashu.flexible.uid.core.factory.UidFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UidController {

    @GetMapping("uid")
    @ResponseBody
    public Long generate() throws UidException {
        return UidFactory.getInstance().uid().getLong();
    }

}
```

#### 5. 启动springboot工程,启动成功日志如下

```
2021-10-04 16:55:14.834 [main] DEBUG UidAutoConfiguration - uid auto configure, ip:192.168.40.1, port:8080
2021-10-04 16:55:14.838 [main] DEBUG XmlRegistry - init-filename:flexible-uid.xml
2021-10-04 16:55:14.866 [main] DEBUG XmlRegistry - init-locationMap:{192.168.40.1:8080=UidLocation{workerId=0, dataCenterId=3}, 192.168.40.1:8081=UidLocation{workerId=1, dataCenterId=3}}
```

#### 6. 浏览器访问接口

```
http://localhost:8080/uid
```



## 注意事项

本项目只是对uid的一个小小探索实现，目前还处于襁褓之中，暂不支持大规模投产使用。得益于代码上使用SPI做了可拓展设计，后续将逐步优化。