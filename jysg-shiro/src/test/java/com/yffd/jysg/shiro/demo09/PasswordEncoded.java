package com.yffd.jysg.shiro.demo09;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.BlowfishCipherService;
import org.apache.shiro.crypto.DefaultBlockCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.*;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.junit.Assert;
import org.junit.Test;

import java.security.Key;


public class PasswordEncoded {


    @Test
    public void hashServiceTest() {
        DefaultHashService hashService = new DefaultHashService(); //默认算法SHA-512
        hashService.setHashAlgorithmName("SHA-512");
        hashService.setPrivateSalt(new SimpleByteSource("123")); //私盐，默认无，在散列时自动与用户传入的公盐混合产生一个新盐
        hashService.setGeneratePublicSalt(true);//在用户没有传入公盐的情况下是否生成公盐，默认false
        hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());//用于生成公盐。默认就这个
        hashService.setHashIterations(1); //生成Hash值的迭代次数

        HashRequest request = new HashRequest.Builder()
                .setAlgorithmName("MD5").setSource(ByteSource.Util.bytes("hello"))
                .setSalt(ByteSource.Util.bytes("123")).setIterations(2).build();
        String hex = hashService.computeHash(request).toHex();
        System.out.println(hex);
    }

    @Test
    public void codecSupportTest() {
        String str = "hello";
        byte[] bytes = CodecSupport.toBytes(str, "utf-8");
        String str2 = CodecSupport.toString(bytes, "utf-8");
        Assert.assertEquals(str, str2);
    }

    @Test
    public void randomNumTest() {
        //生成随机数
        SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        randomNumberGenerator.setSeed("1234".getBytes());
        String hex = randomNumberGenerator.nextBytes().toHex();
        System.out.println(hex + ">>" + hex.length());
    }

    @Test
    public void base64Test() {
        //base64编码/解码
        String str = "hellohellohellohello";
        String base64encoded = Base64.encodeToString(str.getBytes());
        String str2 = Base64.decodeToString(base64encoded);
        System.out.println(base64encoded);
        Assert.assertEquals(str, str2);
    }

    @Test
    public void hex16Test() {
        //16进制字符串编码/解码
        String str = "hellohellohellohello";
        String hex16Encoded = Hex.encodeToString(str.getBytes());
        String str2 = new String(Hex.decode(hex16Encoded.getBytes()));
        System.out.println(hex16Encoded);
        Assert.assertEquals(str, str2);
    }

    @Test
    public void md5Test() {
        String str = "hello";
        String salt = "123";
        Md5Hash md5Hash = new Md5Hash(str, salt,2);
        System.out.println(md5Hash.toBase64());
        System.out.println(md5Hash.toHex());
        System.out.println(md5Hash.toString());
    }

    @Test
    public void simpleHashTest() {
        String str = "hello";
        String salt = "123";
        SimpleHash simpleHash = new SimpleHash("sha-1", str, salt);
        System.out.println(simpleHash.toBase64());
        System.out.println(simpleHash.toHex());
        System.out.println(simpleHash.toString());
    }

    @Test
    public void testSha1() {
        String str = "hello";
        String salt = "123";
        String sha1 = new Sha1Hash(str, salt).toString();
        System.out.println(sha1);

    }

    @Test
    public void testSha256() {
        String str = "hello";
        String salt = "123";
        String sha1 = new Sha256Hash(str, salt).toString();
        System.out.println(sha1);

    }

    @Test
    public void testSha384() {
        String str = "hello";
        String salt = "123";
        String sha1 = new Sha384Hash(str, salt).toString();
        System.out.println(sha1);

    }

    @Test
    public void testSha512() {
        String str = "hello";
        String salt = "123";
        String sha1 = new Sha512Hash(str, salt).toString();
        System.out.println(sha1);

    }

    @Test
    public void aesCipherServiceTest() {
        //AES对称加密/解密算法
        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setKeySize(128);//设置key长度

        //生成key
        Key key = aesCipherService.generateNewKey();
        System.out.println(key.getAlgorithm());

        String text = "hello";

        //加密
        String encrptText = aesCipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
        //解密
        String text2 = new String(aesCipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());

        Assert.assertEquals(text, text2);
    }

    @Test
    public void blowfishCipherServiceTest() {
        BlowfishCipherService blowfishCipherService = new BlowfishCipherService();
        blowfishCipherService.setKeySize(128);

        //生成key
        Key key = blowfishCipherService.generateNewKey();

        String text = "hello";

        //加密
        String encrptText = blowfishCipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
        //解密
        String text2 = new String(blowfishCipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());

        Assert.assertEquals(text, text2);
    }

    @Test
    public void defaultBlockCipherServiceTest() throws Exception {
        //对称加密，使用Java的JCA（javax.crypto.Cipher）加密API，常见的如 'AES', 'Blowfish'
        DefaultBlockCipherService cipherService = new DefaultBlockCipherService("AES");
        cipherService.setKeySize(128);

        //生成key
        Key key = cipherService.generateNewKey();

        String text = "hello";

        //加密
        String encrptText = cipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
        //解密
        String text2 = new String(cipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());

        Assert.assertEquals(text, text2);
    }
}
