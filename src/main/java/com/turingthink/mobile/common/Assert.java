package com.turingthink.mobile.common;

import com.turingthink.mobile.common.execption.CustomException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author GongJie Sheng
 * @version v1.0.0
 * @Date 2022-05-20 17:50
 * @description:
 */
public class Assert {

    public static void notNull(Object obj, String message) {
        if (Objects.isNull(obj)) {
            throw new CustomException(message);
        }
    }
    public static void notBlank(String obj, String message) {
        if (Strings.isBlank(obj)) {
            CustomException.customError(message);
        }
    }
    public static void isTrue(Boolean result, String message) {
        if (!result) {
            CustomException.customError(message);
        }
    }
}
