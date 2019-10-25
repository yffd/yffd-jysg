package com.yffd.jysg.springboot.demo.jwt;

import com.yffd.jysg.springboot.demo.jwt.util.TokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Test;

import javax.xml.bind.DatatypeConverter;
import java.security.Key;

public class JwtTest {

    @Test
    public void createTest() {
        // the key would be read from your application configuration instead.
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        System.out.println(key.getEncoded().length *8);

        //创建签名token
        String jws = Jwts.builder().setSubject("Joe").signWith(key).compact();
        System.out.println(jws);

        //验证token
        assert Jwts.parser().setSigningKey(key).parseClaimsJws(jws).getBody().getSubject().equals("Joe");
    }

    @Test
    public void createTokenTest() {
        String userId = "abcdefghiz10abcdefghizabcdefghiz";
        String issuer = "http://whytfjybj.com";
        String subject = "师范学院";
        long ttlMillis = 1000 * 60;
        String audience = "schoolNo";
        String token = TokenUtil.createJWT(userId, issuer, subject, ttlMillis, audience);
        //打印出token信息
        System.out.println(token);

        //解密token信息
        Claims claims = TokenUtil.getClaims(token);
        System.out.println("---------------------------解密的token信息----------------------------------");
        System.out.println("ID: " + claims.getId());
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issuer: " + claims.getIssuer());
        System.out.println("Expiration: " + claims.getExpiration());

    }


    @Test
    public void test() {
        String str = "user:123";
        // encode data on your side using BASE64
        byte[] bytesEncoded = Base64.encodeBase64(str .getBytes());
        System.out.println("ecncoded value is " + new String(bytesEncoded ));

        // Decode data on other side, by processing encoded data
        byte[] valueDecoded= Base64.decodeBase64(bytesEncoded );
        System.out.println("Decoded value is " + new String(valueDecoded));

        /*byte[] someByteArray = DatatypeConverter.parseBase64Binary(str);
        // encode with padding
        String encoded = Base64.getEncoder().encodeToString(someByteArray);
        // encode without padding
        String encoded1 = Base64.getEncoder().withoutPadding().encodeToString(someByteArray);
        // decode a String
        byte[] barr = Base64.getDecoder().decode(encoded);*/

    }
}
