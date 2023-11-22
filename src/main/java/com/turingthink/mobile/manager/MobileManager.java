package com.turingthink.mobile.manager;

import com.aliyun.dypnsapi20170525.models.*;
import com.aliyun.tea.TeaException;
import com.turingthink.mobile.common.sms.SmsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author GongJie Sheng
 * @date 2022/11/4 15:36
 */
@Component
@EnableConfigurationProperties(SmsProperties.class)
public class MobileManager {
    @Autowired
    private SmsProperties smsProperties;

    private String getMobileByToken(String token) throws Exception {
        // 请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_ID 和 ALIBABA_CLOUD_ACCESS_KEY_SECRET。
        // 工程代码泄露可能会导致 AccessKey 泄露，并威胁账号下所有资源的安全性。以下代码示例使用环境变量获取 AccessKey 的方式进行调用，仅供参考，建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html
        String accessKeyId = smsProperties.getAliyun().getAccessKeyId();
        String accessKeySecret = smsProperties.getAliyun().getAccessKeySecret();
        com.aliyun.dypnsapi20170525.Client client = MobileManager.createClient(accessKeyId, accessKeySecret);
        com.aliyun.dypnsapi20170525.models.GetMobileRequest getMobileRequest = new com.aliyun.dypnsapi20170525.models.GetMobileRequest()
                .setAccessToken(token);
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            GetMobileResponse mobileWithOptions = client.getMobileWithOptions(getMobileRequest, runtime);
            if (Objects.nonNull(mobileWithOptions.getBody())
                    && Objects.nonNull(mobileWithOptions.getBody().getGetMobileResultDTO())) {
                return mobileWithOptions.getBody().getGetMobileResultDTO().getMobile();
            }
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception error) {
            TeaException teaError = new TeaException(error.getMessage(), error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(teaError.message);
        }
        return null;
    }

    public GetAuthTokenResponseBody.GetAuthTokenResponseBodyTokenInfo getH5Token(String url, String origin) throws Exception {
        // 请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_ID 和 ALIBABA_CLOUD_ACCESS_KEY_SECRET。
        // 工程代码泄露可能会导致 AccessKey 泄露，并威胁账号下所有资源的安全性。以下代码示例使用环境变量获取 AccessKey 的方式进行调用，仅供参考，建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html
        String accessKeyId = smsProperties.getAliyun().getAccessKeyId();
        String accessKeySecret = smsProperties.getAliyun().getAccessKeySecret();
        com.aliyun.dypnsapi20170525.Client client = MobileManager.createClient(accessKeyId, accessKeySecret);
        com.aliyun.dypnsapi20170525.models.GetAuthTokenRequest getAuthTokenRequest = new com.aliyun.dypnsapi20170525.models.GetAuthTokenRequest()
                .setUrl(url)
                .setOrigin(origin);
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            GetAuthTokenResponse authTokenWithOptions = client.getAuthTokenWithOptions(getAuthTokenRequest, runtime);
            if (Objects.nonNull(authTokenWithOptions.getBody())
                    && Objects.nonNull(authTokenWithOptions.getBody().getTokenInfo())) {
                return authTokenWithOptions.getBody().getTokenInfo();
            }
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception error) {
            TeaException teaError = new TeaException(error.getMessage(), error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(teaError.message);
        }
        return null;
    }

    public String getH5Mobile(String spToken) throws Exception {
        String accessKeyId = smsProperties.getAliyun().getAccessKeyId();
        String accessKeySecret = smsProperties.getAliyun().getAccessKeySecret();
        com.aliyun.dypnsapi20170525.Client client = MobileManager.createClient(accessKeyId, accessKeySecret);
        com.aliyun.dypnsapi20170525.models.GetPhoneWithTokenRequest getPhoneWithTokenRequest = new com.aliyun.dypnsapi20170525.models.GetPhoneWithTokenRequest()
                .setSpToken(spToken);
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            GetPhoneWithTokenResponse phoneWithTokenWithOptions = client.getPhoneWithTokenWithOptions(getPhoneWithTokenRequest, runtime);
            if (Objects.nonNull(phoneWithTokenWithOptions.getBody())
                    && Objects.nonNull(phoneWithTokenWithOptions.getBody().getData())) {
                return phoneWithTokenWithOptions.getBody().getData().getMobile();
            }
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception error) {
            TeaException teaError = new TeaException(error.getMessage(), error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(teaError.message);
        }
        return null;
    }


    public VerifyPhoneWithTokenResponseBody.VerifyPhoneWithTokenResponseBodyGateVerify h5VerifyPhoneWithToken(String phone, String spToken) throws Exception {
        String accessKeyId = smsProperties.getAliyun().getAccessKeyId();
        String accessKeySecret = smsProperties.getAliyun().getAccessKeySecret();
        com.aliyun.dypnsapi20170525.Client client = MobileManager.createClient(accessKeyId, accessKeySecret);
        com.aliyun.dypnsapi20170525.models.VerifyPhoneWithTokenRequest verifyPhoneWithTokenRequest = new com.aliyun.dypnsapi20170525.models.VerifyPhoneWithTokenRequest()
                .setPhoneNumber(phone)
                .setSpToken(spToken);
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            VerifyPhoneWithTokenResponse verifyPhoneWithTokenResponse = client.verifyPhoneWithTokenWithOptions(verifyPhoneWithTokenRequest, runtime);
            if (Objects.nonNull(verifyPhoneWithTokenResponse.getBody())
                    && Objects.nonNull(verifyPhoneWithTokenResponse.getBody().getGateVerify())) {
                return verifyPhoneWithTokenResponse.getBody().getGateVerify();
            }
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception error) {
            TeaException teaError = new TeaException(error.getMessage(), error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(teaError.message);
        }
        return null;
    }

    /**
     * 使用AK&SK初始化账号Client
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.dypnsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // Endpoint 请参考 https://api.aliyun.com/product/Dypnsapi
        config.endpoint = "dypnsapi.aliyuncs.com";
        return new com.aliyun.dypnsapi20170525.Client(config);
    }

    /**
     * 通过token获取手机号码
     *
     * @param token
     * @return
     */
    public String getMobile(String token) throws Exception {
        //通过token获取手机号码
        return getMobileByToken(token);
    }
}
