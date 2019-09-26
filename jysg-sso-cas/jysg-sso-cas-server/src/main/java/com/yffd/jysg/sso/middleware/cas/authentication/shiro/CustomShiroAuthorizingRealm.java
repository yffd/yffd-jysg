package com.yffd.jysg.sso.middleware.cas.authentication.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 在Shiro中，最终是通过Realm来获取应用程序中的用户、角色及权限信息的。可以说，Realm是专用于安全框架的DAO.
 */
public class CustomShiroAuthorizingRealm extends AuthorizingRealm {
    private static final Logger LOG = LoggerFactory.getLogger(CustomShiroAuthorizingRealm.class);

    /**
     * 验证用户身份
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户名、密码 第一种方式
//        String username = (String) token.getPrincipal();
//        String password = new String((char[]) token.getCredentials());

        //获取用户名、密码 第二种方式
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        String password = new String(usernamePasswordToken.getPassword());
        LOG.info("身份认证-CustomShiroAuthorizingRealm#doGetAuthenticationInfo，username：{}， password:{}", username, password);
        // TODO
        Map<String, Object> user = new HashMap<>();
        user.put("password", "123456");
        user.put("state", "0");

        //可以在这里直接对用户名校验，或者调用 CredentialsMatcher校验
        if (user == null) {
            throw new UnknownAccountException("用户名或密码错误！");
        }
        // 可交给 密码比较器 进行对比密码
        if (!password.equals(user.get("password"))) {
            throw new IncorrectCredentialsException("用户名或密码错误！");
        }
        if ("1".equals(user.get("state"))) {
            throw new LockedAccountException("账号已被锁定,请联系管理员！");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, user.get("password"), getName());
        return info;
    }

    /**
     * 授权用户权限
     * @par
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        LOG.info("分配权限-CustomShiroAuthorizingRealm#doGetAuthorizationInfo");
        //添加角色
        SimpleAuthorizationInfo authorizationInfo =  new SimpleAuthorizationInfo();
        //设置登录用户的角色
        Set<String> roles = new HashSet<>();
        roles.add("superadmin");
        roles.add("admin");
        authorizationInfo.addRoles(roles);

        Set<String> permissions = new HashSet<>();
        permissions.add("superadmin:*");
        permissions.add("admin:save");
        authorizationInfo.addStringPermissions(permissions);
        return authorizationInfo;
    }
}
