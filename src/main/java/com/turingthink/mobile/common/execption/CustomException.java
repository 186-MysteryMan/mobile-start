package com.turingthink.mobile.common.execption;

/**
 * @author GongJie Sheng
 * @Date 2022-05-20 17:58
 * @version v1.0.0
 * @description:
 */
public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }

    public static void customError(String message) {
        throw new CustomException(message);
    }
}
