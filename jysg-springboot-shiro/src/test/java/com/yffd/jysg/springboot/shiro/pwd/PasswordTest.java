package com.yffd.jysg.springboot.shiro.pwd;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;

public class PasswordTest {

    @Test
    public void test() throws Exception {
        System.out.println(md5("123456","admin"));
    }

    public static final String md5(String password, String salt){
        //加密方式
        String hashAlgorithmName = "MD5";
        //盐：为了即使相同的密码不同的盐加密后的结果也不同
        ByteSource byteSalt = ByteSource.Util.bytes(salt);
        //密码
        Object source = password;
        //加密次数
        int hashIterations = 2;
        SimpleHash result = new SimpleHash(hashAlgorithmName, source, byteSalt, hashIterations);
        return result.toString();
    }
}
