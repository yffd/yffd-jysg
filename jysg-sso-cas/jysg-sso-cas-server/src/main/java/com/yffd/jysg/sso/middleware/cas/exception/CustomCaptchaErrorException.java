package com.yffd.jysg.sso.middleware.cas.exception;

import javax.security.auth.login.AccountException;

/**
 * 验证码错误异常类
 */
public class CustomCaptchaErrorException extends AccountException {

    public CustomCaptchaErrorException() {
    }

    public CustomCaptchaErrorException(String msg) {
        super(msg);
    }
}
