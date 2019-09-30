package com.yffd.jysg.shiro.demo08;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class ShiroJavaConfig {

    @Test
    public void test() {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();

        //设置authenticator
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        securityManager.setAuthenticator(authenticator);

        //设置authorizer
        ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
        authorizer.setPermissionResolver(new WildcardPermissionResolver());
        securityManager.setAuthorizer(authorizer);

        //设置Realm
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/easy-shiro");
        ds.setUsername("root");
        ds.setPassword("root123");

        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(ds);
        jdbcRealm.setPermissionsLookupEnabled(true);//常量值会自动类型转换
        securityManager.setRealms(Arrays.asList((Realm) jdbcRealm));

        //将SecurityManager设置到SecurityUtils 方便全局使用
        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        subject.login(token);

        Assert.assertTrue(subject.isAuthenticated());

    }

    /**
     * shiro-config.ini
     * [main]
     #authenticator
     authenticator=org.apache.shiro.authc.pam.ModularRealmAuthenticator
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
