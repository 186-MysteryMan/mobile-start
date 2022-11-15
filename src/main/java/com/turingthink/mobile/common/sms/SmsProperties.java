package com.turingthink.mobile.common.sms;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author: fight2048
 * @e-mail: fight2048@outlook.com
 * @blog: https://github.com/fight2048
 * @time: 2021-05-22 0022 下午 9:44
 * @version v0.0.0
 * @description: 阿里云 短信SDK 参考文档：https://help.aliyun.com/document_detail/215759.html
 */
@ConfigurationProperties(prefix = SmsProperties.EMAIL_PREFIX)
public class SmsProperties {
    public static final String EMAIL_PREFIX = "sms";

    /**
     * 阿里云 SMS
     */
    private AliyunSmsProperties aliyun = new AliyunSmsProperties();

    public AliyunSmsProperties getAliyun() {
        return aliyun;
    }

    public void setAliyun(AliyunSmsProperties aliyun) {
        this.aliyun = aliyun;
    }

    /**
     * 阿里云 SMS 配置
     */
    @Data
    public static class AliyunSmsProperties {
        private String accessKeyId;
        private String accessKeySecret;
    }
}
