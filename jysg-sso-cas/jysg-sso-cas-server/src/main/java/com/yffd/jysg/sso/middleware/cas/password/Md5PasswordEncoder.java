package com.yffd.jysg.sso.middleware.cas.password;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.AbstractPasswordEncoder;
import org.springframework.security.crypto.util.EncodingUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 自定义密码验证
 * 配置：cas.authn.jdbc.query[0].passwordEncoder.type=com.yffd.bcap.sso.middleware.cas.password.Md5PasswordEncoder
 */
public class Md5PasswordEncoder extends AbstractPasswordEncoder {
    private final Logger LOG = LoggerFactory.getLogger(Md5PasswordEncoder.class);

    @Override
    protected byte[] encode(CharSequence rawPassword, byte[] salt) {
        try {
            byte[] input = EncodingUtils.concatenate(salt, rawPassword.toString().getBytes());
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
