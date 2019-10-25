package com.yffd.jysg.springboot.demo.jwt.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

public class JwtUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

    private JwtUtil() {
    }

    public static String createJWS(String secretKey, Map<String, Object> bodyMap, long ttlMillis, String subject) {
        return createJWS(secretKey, null, bodyMap, ttlMillis, subject, null, null);
    }

    public static String createJWS(String secretKey, Map<String, Object> bodyMap, long ttlMillis, String subject, String issuer, String audience) {
        return createJWS(secretKey, null, bodyMap, ttlMillis, subject, issuer, audience);
    }

    /**
     * 生成token：一个JWT实际上就是一个字符串，它由三部分组成，头部(Header)、载荷(Payload)与签名(Signature)
     * @param secretKey 签名时使用秘钥，该字符串长度必须大于等于32
     * @param headerMap 头部信息
     * @param bodyMap   载荷中的claim（私有）
     * @param ttlMillis 过期时间，单位毫秒，是否使用是可选的
     * @param subject   该JWT所面向的用户（一般指userid），是否使用是可选的
     * @param issuer    该JWT的签发者，是否使用是可选的
     * @param audience  该JWT的接收用户，是否使用是可选的
     * @return
     */
    private static String createJWS(String secretKey, Map<String, Object> headerMap, Map<String, Object> bodyMap,
                                   long ttlMillis, String subject, String issuer, String audience) {
        //生成JWT的时间
        long nowMillis = System.currentTimeMillis();

        //为payload添加标准声明和私有声明
        JwtBuilder builder = Jwts.builder()
                //设置头信息
                .setHeaderParams(headerMap)
                //设置payload的私有声明信息
                .addClaims(bodyMap)
                //以下设置payload的标准声明
                //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击
                //.setId(UUID.randomUUID().toString())
                .setIssuer(issuer)
                .setIssuedAt(new Date(nowMillis))
                .setSubject(subject)
                .setAudience(audience)
                //数据压缩方式
                //.compressWith(CompressionCodecs.GZIP)
                //设置签名使用的签名算法和签名使用的秘钥
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            //设置过期时间
            builder.setExpiration(exp);
        }
        return builder.compact();
    }


    /**
     * 解析token
     * @param secretKey 签名时使用秘钥，该字符串长度必须大于等于32
     * @param token 加密后的token
     * @return
     */
    public static Jws<Claims> parseJWS(String secretKey, String token) {
        return Jwts.parser()
                //设置签名的秘钥
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                //设置需要解析的jwt
                .parseClaimsJws(token);
    }

    /**
     * 解析token中的signature
     * @param secretKey
     * @param token
     * @return
     */
    public static String parseSignature(String secretKey, String token) {
        try {
            return parseJWS(secretKey, token).getSignature();
        } catch (JwtException e) {
            LOGGER.error("JWS:signature解析失败", e);
        }
        return null;
    }

    /**
     * 解析token中的header
     * @param secretKey
     * @param token
     * @return
     */
    public static JwsHeader parseHeader(String secretKey, String token) {
        try {
            return parseJWS(secretKey, token).getHeader();
        } catch (JwtException e) {
            LOGGER.error("JWS:signature解析失败", e);
        }
        return null;
    }

    /**
     * 解析token中的body
     * @param secretKey
     * @param token
     * @return
     */
    public static Claims parseBody(String secretKey, String token) {
        try {
            return parseJWS(secretKey, token).getBody();
        } catch (JwtException e) {
            LOGGER.error("JWS:body解析失败", e);
        }
        return null;
    }

    /**
     * 获取body某个值
     * @param secretKey
     * @param token
     * @param key
     * @return
     */
    public static Object getVal(String secretKey, String token, String key) {
        try {
            return parseJWS(secretKey, token).getBody().get(key);
        } catch (JwtException e) {
            LOGGER.error("JWS:body:"+ key +"解析失败", e);
        }
        return null;
    }

    /**
     * 获取body的主体（subject）
     * @param secretKey
     * @param token
     * @return
     */
    public static String getSubject(String secretKey, String token) {
        try {
            return parseJWS(secretKey, token).getBody().getSubject();
        } catch (JwtException e) {
            LOGGER.error("JWS（body）（subject）解析失败", e);
        }
        return null;
    }

    /**
     * 验证token是否过期
     * @param secretKey
     * @param token
     * @return
     */
    public static boolean isExpiration(String secretKey, String token) {
        try {
            return parseJWS(secretKey, token).getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (JwtException e) {
            LOGGER.error("JWS:body:Expiration解析失败", e);
            throw e;
        }
    }

}
