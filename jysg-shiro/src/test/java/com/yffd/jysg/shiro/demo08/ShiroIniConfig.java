package com.yffd.jysg.shiro.demo08;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

public class ShiroIniConfig {

    @Test
    public void test() {

        Factory<SecurityManager> factory =
                new IniSecurityManagerFactory("classpath:shiro-config-main.ini");

        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();

        //将SecurityManager设置到SecurityUtils 方便全局使用
        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        subject.login(token);

        Assert.assertTrue(subject.isAuthenticated());

    }

    /**
     * [main]
     securityManager=org.apache.shiro.mgt.DefaultSecurityManager

     #authenticator
     authenticator=com.yffd.jysg.shiro.demo08.MyAuthenticator

     #属性注入，默认需要使用Base64进行编码，也可以使用0x十六进制
     #base64 byte[]
     authenticator.bytes=aGVsbG8=
     #hex byte[]
     authenticator.bytes=0x68656c6c6f

     authenticator.array=1,2,3
     authenticator.set=$jdbcRealm,$jdbcRealm
     authenticator.map=$jdbcRealm:$jdbcRealm,1:1,key:abc

     authenticationStrategy=org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy
     authenticator.authenticationStrategy=$authenticationStrategy
     securityManager.authenticator=$authenticator

     #authorizer
     authorizer=org.apache.shiro.authz.ModularRealmAuthorizer
     permissionResolver=org.apache.shiro.authz.permission.WildcardPermissionResolver
     authorizer.permissionResolver=$permissionResolver
     securityManager.authorizer=$authorizer

     #realm
     dataSource=com.alibaba.druid.pool.DruidDataSource
     dataSource.driverClassName=com.mysql.jdbc.Driver
     dataSource.url=jdbc:mysql://localhost:3306/easy-shiro
     dataSource.username=root
     #dataSource.password=root123

     jdbcRealm=org.apache.shiro.realm.jdbc.JdbcRealm
     jdbcRealm.dataSource=$dataSource
     jdbcRealm.permissionsLookupEnabled=true
     securityManager.realms=$jdbcRealm
     */
}
