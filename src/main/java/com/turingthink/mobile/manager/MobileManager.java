package com.turingthink.mobile.manager;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dypnsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dypnsapi20170525.models.GetMobileRequest;
import com.aliyun.sdk.service.dypnsapi20170525.models.GetMobileResponse;
import com.aliyun.sdk.service.dypnsapi20170525.models.GetMobileResponseBody;
import com.turingthink.mobile.common.Assert;
import com.turingthink.mobile.common.execption.CustomException;
import com.turingthink.mobile.common.sms.SmsProperties;
import darabonba.core.client.ClientOverrideConfiguration;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author GongJie Sheng
 * @date 2022/11/4 15:36
 */
@Component
@EnableConfigurationProperties(SmsProperties.class)
public class MobileManager {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private SmsProperties smsProperties;
    private static final String LOGIN_EQUIPMENT_NO_REDIS_KEY = "mobile:equipment:";

    private String getMobileByToken(String token) throws CustomException {
        // Configure Credentials authentication information, including ak, secret, token
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(smsProperties.getAliyun().getAccessKeyId())
                .accessKeySecret(smsProperties.getAliyun().getAccessKeySecret())
                .build());

        // Configure the Client
        AsyncClient client = AsyncClient.builder()
                // Region ID
                .region("cn-shenzhen")
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride("dypnsapi.aliyuncs.com")
                )
                .build();
        // Parameter settings for API request
        GetMobileRequest getMobileRequest = GetMobileRequest.builder()
                .accessToken(token)
                .build();

        // Asynchronously get the return value of the API request
        CompletableFuture<GetMobileResponse> response = client.getMobile(getMobileRequest);
        // Synchronously get the return value of the API request
        GetMobileResponse resp = null;
        try {
            resp = response.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        client.close();
        GetMobileResponseBody.GetMobileResultDTO getMobileResultDTO = resp.getBody().getGetMobileResultDTO();
        Assert.notNull(getMobileResultDTO, "?????????token?????????" + resp.getBody().getMessage());
        return getMobileResultDTO.getMobile();
    }

    /**
     * ??????token??????????????????????????????redis
     *
     * @param token
     * @return
     */
    public String getMobile(String token) throws Exception {
        //??????token??????????????????
        String mobile = redisTemplate.opsForValue().get(token);
        if (Strings.isBlank(mobile)) {
            mobile = getMobileByToken(token);
            Assert.notBlank(mobile, "?????????token????????????????????????");
            redisTemplate.opsForValue().set(token, mobile, 1, TimeUnit.HOURS);
        } else {
            redisTemplate.delete(token);
        }
        return mobile;
    }

    /**
     * ???????????????????????????????????????????????????15???
     *
     * @param mobile
     * @param equipmentNo
     * @return ??????????????????
     */
    public void limitLogin(String mobile, String equipmentNo) throws Exception {
        //????????????????????????????????????????????????????????????????????????????????????????????????
        String redisKey = LOGIN_EQUIPMENT_NO_REDIS_KEY + equipmentNo;
        String redisMobile = redisTemplate.opsForValue().get(redisKey);
        Assert.isTrue(Strings.isBlank(redisMobile) || redisMobile.equals(mobile), "?????????????????????????????????????????????????????????" + mobile);
        //?????????????????????????????????????????????redis????????????????????????????????????
        redisTemplate.opsForValue().set(redisKey, mobile, 15, TimeUnit.DAYS);
    }
}
