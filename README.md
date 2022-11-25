#mobile-start

# 手机号码一键登录Start

> Author：盛攻杰
>
> E-Mail：fierceplatypus@qq.com

## 前言

为了方便用户快捷登录，主动获取用户当前正在使用的网络所用的手机号码，通过这个手机号码后端完成登录，用户只需要点击一下按钮就可以完成一键登录。

## 思路

使用阿里云提供的一键登录SDK，为APP端提供一个SDK，APP端可以获取当前手机的号码所对应的token，上传至后端接口，后端拿到token请求阿里云接口，获取手机号码保存至redis一个token不能请求两次且每次都需要收费，返回手机号码给前端展示，用户点击一键登录时，再次上传token，拿到token后从redis找到手机号码后完成对这个号码的一键登录或者注册并登录。

## maven坐标

```xml
<dependency>
    <groupId>com.github.186-MysteryMan</groupId>
    <artifactId>mobile-start</artifactId>
    <version>1.0</version>
</dependency>
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

## 必要yml参数

```
spring:
  redis:
    database: 
    host: 
    password: 
    port: 
    timeout: 

sms:
  aliyun:
    accessKeyId: 
    accessKeySecret: 
```

## 后端接口

### 获取手机号码

通过阿里云token获取手机号码，并且将手机号码存入redis中，然后返回手机号码。

#### 使用方法：

先注入MobileManager类，调用getMobile

```java
@Autowired
private MobileManager mobileManager;

//获取手机号码通过token
String mobile = mobileManager.getMobile(token);
```

#### 返回参数

| 参数名称 | 参数说明 | 是否必须 | 数据类型 |
| -------- | -------- | -------- | -------- |
| mobile   | 手机号码 | true     | String   |

### 一键登录和限制登录

通过阿里云的token从redis获取手机号码，校验是否为该设备绑定的账号，然后登录或注册。

#### 使用方法：

先注入MobileManager类，调用getMobile方法获取手机号码，然后使用token加

```java
@Autowired
private MobileManager mobileManager;

//获取手机号码通过token
String mobile = mobileManager.getMobile(token);
//限制登录
mobileManager.limitLogin(mobile, equipmentNo);
//todo 通过限制登录后，进行登录或者注册操作
```

#### 返回参数

无

## 前端使用

### 获取手机号码

使用阿里云提供的SDK生成token上传。

### 一键登录和限制登录

使用刚才制作的token和自制的设备号再次上传，完成一键登录。

