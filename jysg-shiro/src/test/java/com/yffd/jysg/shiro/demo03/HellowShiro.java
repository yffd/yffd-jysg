package com.yffd.jysg.shiro.demo03;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class HellowShiro {
    private static final String SHIR_CFG_FILE_PATH = "classpath:demo03/shiro-jdbc-realm.ini";

    @Test
    public void loginTest() {
        String username = "zhang";
        String password = "123";
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(SHIR_CFG_FILE_PATH);

        //2、获取SecurityManager实例并绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);//全局设置，设置一次即可

        //3、获取Subject，即创建用户名/密码身份验证Token（即用户身份/凭证）
        //Subject，其会自动绑定到当前线程；如果在web环境在请求结束时需要解除绑定
        //调用之前必须通过SecurityUtils.setSecurityManager()设置
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        try {
            //4、登录，即身份验证
            subject.login(token);
        } catch (AuthenticationException e) {
            //5、身份验证失败
            //throw e;
        }

        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录

        //6、退出
        subject.logout();
    }

    @After
    public void tearDown() throws Exception {
        ThreadContext.unbindSubject();//退出时请解除绑定Subject到线程 否则对下次测试造成影响
    }
}
