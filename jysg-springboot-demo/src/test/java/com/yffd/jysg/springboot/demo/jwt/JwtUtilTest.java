package com.yffd.jysg.springboot.demo.jwt;

import com.alibaba.fastjson.JSON;
import com.yffd.jysg.springboot.demo.jwt.util.JwtUtil;
import com.yffd.jysg.springboot.demo.jwt.util.TokenUtil;
import io.jsonwebtoken.Claims;
import org.junit.Test;
import springfox.documentation.spring.web.json.Json;

import java.util.HashMap;
import java.util.Map;

public class JwtUtilTest {
    String secretKey = "abcdefghiz10abcdefghizabcdefghiz";

    @Test
    public void createJWTTest() {
//        String secretKey = "abcdefghiz10abcdefghizabcdefghiz";
        //创建payload的私有声明
        Map<String, Object> customClaims = new HashMap<String, Object>();
        customClaims.put("roles", "admin,superadmin");

        String subject = "lisi";
        long ttlMillis = 1000 * 600;
        String issuer = "http://jysg.yffd.com";
        String audience = "auth system";

        String token = JwtUtil.createJWS(secretKey, customClaims, ttlMillis, subject, issuer, audience);
        //打印出token信息
        System.out.println(token);

        System.out.println("-----------解密token信息------------------");
        //解密token信息
        System.out.println(JwtUtil.parseJWS(secretKey, token));
        System.out.println(JSON.toJSONString(JwtUtil.parseJWS(secretKey, token)));

        System.out.println("parseSignature::" + JwtUtil.parseSignature(secretKey, token));
        System.out.println("getSubject::" + JwtUtil.getSubject(secretKey, token));
        System.out.println("getVal::" + JwtUtil.getVal(secretKey, token, "roles"));


        System.out.println("parseHeader::" + JwtUtil.parseHeader(secretKey, token));
        System.out.println(JSON.toJSONString(JwtUtil.parseHeader(secretKey, token)));

        System.out.println("parseBody::" + JwtUtil.parseBody(secretKey, token));
        System.out.println(JSON.toJSONString(JwtUtil.parseBody(secretKey, token)));

    }

    @Test
    public void isExpirationTest() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6ImFkbWluLHN1cGVyYWRtaW4iLCJpc3MiOiJodHRwOi8vanlzZy55ZmZkLmNvbSIsImlhdCI6MTU3MTkwODk4NCwic3ViIjoibGlzaSIsImF1ZCI6ImF1dGggc3lzdGVtIiwiZXhwIjoxNTcxOTA5MDQ0fQ.wGgut71pNgwIf2ZA9-rSDYbgdkG8_xJ3PaThQwt_gCU";
        System.out.println(JwtUtil.isExpiration(secretKey, token));
    }

    @Test
    public void getSubjectTest() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6ImFkbWluLHN1cGVyYWRtaW4iLCJpc3MiOiJodHRwOi8vanlzZy55ZmZkLmNvbSIsImlhdCI6MTU3MTk4NDk1OSwic3ViIjoibGlzaSIsImF1ZCI6ImF1dGggc3lzdGVtIiwiZXhwIjoxNTcxOTg1NTU5fQ.v24cY02yaxahNJVZBiovdQcC3EsVrNUpayUZzHFMz-8";
        token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6ImFkbWluLHN1cGVyYWRtaW4iLCJpc3MiOiJodHRwOi8vanlzZy55ZmZkLmNvbSIsImlhdCI6MTU3MTkwODk4NCwic3ViIjoibGlzaSIsImF1ZCI6ImF1dGggc3lzdGVtIiwiZXhwIjoxNTcxOTA5MDQ0fQ.wGgut71pNgwIf2ZA9-rSDYbgdkG8_xJ3PaThQwt_gCU";
        System.out.println(JwtUtil.getSubject(secretKey, token));
    }

    @Test
    public void getValTest() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6ImFkbWluLHN1cGVyYWRtaW4iLCJpc3MiOiJodHRwOi8vanlzZy55ZmZkLmNvbSIsImlhdCI6MTU3MTk4NDk1OSwic3ViIjoibGlzaSIsImF1ZCI6ImF1dGggc3lzdGVtIiwiZXhwIjoxNTcxOTg1NTU5fQ.v24cY02yaxahNJVZBiovdQcC3EsVrNUpayUZzHFMz-8";
//        token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6ImFkbWluLHN1cGVyYWRtaW4iLCJpc3MiOiJodHRwOi8vanlzZy55ZmZkLmNvbSIsImlhdCI6MTU3MTkwODk4NCwic3ViIjoibGlzaSIsImF1ZCI6ImF1dGggc3lzdGVtIiwiZXhwIjoxNTcxOTA5MDQ0fQ.wGgut71pNgwIf2ZA9-rSDYbgdkG8_xJ3PaThQwt_gCU";
        System.out.println(JwtUtil.getVal(secretKey, token, "roles"));
    }

    @Test
    public void parseBodyTest() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6ImFkbWluLHN1cGVyYWRtaW4iLCJpc3MiOiJodHRwOi8vanlzZy55ZmZkLmNvbSIsImlhdCI6MTU3MTk4NDk1OSwic3ViIjoibGlzaSIsImF1ZCI6ImF1dGggc3lzdGVtIiwiZXhwIjoxNTcxOTg1NTU5fQ.v24cY02yaxahNJVZBiovdQcC3EsVrNUpayUZzHFMz-8";
        System.out.println(JwtUtil.parseBody(secretKey, token));
    }

    @Test
    public void parseHeaderTest() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6ImFkbWluLHN1cGVyYWRtaW4iLCJpc3MiOiJodHRwOi8vanlzZy55ZmZkLmNvbSIsImlhdCI6MTU3MTkwODk4NCwic3ViIjoibGlzaSIsImF1ZCI6ImF1dGggc3lzdGVtIiwiZXhwIjoxNTcxOTA5MDQ0fQ.wGgut71pNgwIf2ZA9-rSDYbgdkG8_xJ3PaThQwt_gCU";
        System.out.println(JwtUtil.parseHeader(secretKey, token));
    }

    @Test
    public void parseSignatureTest() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6ImFkbWluLHN1cGVyYWRtaW4iLCJpc3MiOiJodHRwOi8vanlzZy55ZmZkLmNvbSIsImlhdCI6MTU3MTk4NDA1NSwic3ViIjoibGlzaSIsImF1ZCI6ImF1dGggc3lzdGVtIiwiZXhwIjoxNTcxOTg0MTE1fQ.I6a5ofrnKBiQQf7YNss1SQVDzHPwmbFJ-0oeqzvRwcU";
        token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6ImFkbWluLHN1cGVyYWRtaW4iLCJpc3MiOiJodHRwOi8vanlzZy55ZmZkLmNvbSIsImlhdCI6MTU3MTkwODk4NCwic3ViIjoibGlzaSIsImF1ZCI6ImF1dGggc3lzdGVtIiwiZXhwIjoxNTcxOTA5MDQ0fQ.wGgut71pNgwIf2ZA9-rSDYbgdkG8_xJ3PaThQwt_gCU";
        System.out.println(JwtUtil.parseSignature(secretKey, token));

    }

    @Test
    public void parseJWSTest() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6ImFkbWluLHN1cGVyYWRtaW4iLCJpc3MiOiJodHRwOi8vanlzZy55ZmZkLmNvbSIsImlhdCI6MTU3MTkwODk4NCwic3ViIjoibGlzaSIsImF1ZCI6ImF1dGggc3lzdGVtIiwiZXhwIjoxNTcxOTA5MDQ0fQ.wGgut71pNgwIf2ZA9-rSDYbgdkG8_xJ3PaThQwt_gCU";
        System.out.println(JwtUtil.parseJWS(secretKey, token));
    }
}
