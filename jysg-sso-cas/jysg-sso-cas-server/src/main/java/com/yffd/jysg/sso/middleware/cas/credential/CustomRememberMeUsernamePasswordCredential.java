package com.yffd.jysg.sso.middleware.cas.credential;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apereo.cas.authentication.RememberMeUsernamePasswordCredential;

import javax.validation.constraints.Size;

/**
 * 自定义凭证credential，添加验证码属性
 */
public class CustomRememberMeUsernamePasswordCredential extends RememberMeUsernamePasswordCredential {
    @Size(min = 5,max = 5, message = "require captcha")
    private String captcha;

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(this.captcha)
                .toHashCode();
    }

}
