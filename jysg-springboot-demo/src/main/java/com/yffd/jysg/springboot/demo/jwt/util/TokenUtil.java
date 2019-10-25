package com.yffd.jysg.springboot.demo.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

/**
 * Token生成的工具类
 */
public class TokenUtil {
    private static final String APP_KEY = "why_key:why_key:why_key:why_key:why_key:why_key:why_key:why_key:"; //进行数字签名的私钥，一定要保管好

    private TokenUtil(){

    }

    /**
     * 一个JWT实际上就是一个字符串，它由三部分组成，头部(Header)、载荷(Payload)与签名(Signature)
     * @param id 当前用户ID
     * @param issuer 该JWT的签发者，是否使用是可选的
     * @param subject 该JWT所面向的用户，是否使用是可选的
     * @param ttlMillis 什么时候过期，这里是一个Unix时间戳，是否使用是可选的
     * @param audience 接收该JWT的一方，是否使用是可选的
     * @return
     */
    public static String createJWT(String id, String issuer, String subject, long ttlMillis, String audience) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

//        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(APP_KEY);
//        System.out.println(apiKeySecretBytes.length);
//        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        Key signingKey = Keys.hmacShaKeyFor(APP_KEY.getBytes());

        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(UUID.randomUUID().toString())//jwt的唯一身份标识，主要用来作为一次性token，从而回避重放攻击
                .setSubject(subject)
                .setIssuedAt(now)
                .setIssuer(issuer)
                .setAudience(audience)
                .signWith(signingKey, signatureAlgorithm);
        //设置Token的过期时间
        if(ttlMillis >=0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            jwtBuilder.setExpiration(exp);
        }
        return jwtBuilder.compact();
    }

    //私钥解密token信息
    public static Claims getClaims(String jwt) {
        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(APP_KEY.getBytes()))
                .parseClaimsJws(jwt)
                .getBody();
    }


}
