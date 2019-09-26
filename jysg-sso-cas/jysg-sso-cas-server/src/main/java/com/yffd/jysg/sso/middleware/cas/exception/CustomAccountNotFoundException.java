package com.yffd.jysg.sso.middleware.cas.exception;

import javax.security.auth.login.AccountException;

/**
 * 用户没找到异常
 */
public class CustomAccountNotFoundException extends AccountException {

    public CustomAccountNotFoundException() {
    }

    public CustomAccountNotFoundException(String msg) {
        super(msg);
    }
}
