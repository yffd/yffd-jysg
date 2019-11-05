package com.yffd.jysg.springboot.shiro.filter;

import com.yffd.jysg.springboot.shiro.realm.ShiroRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ShiroLogoutFilter extends LogoutFilter {
    private  KickoutSessionControlFilter kickoutSessionControlFilter;

    /**
     * 自定义登出,登出之后,清理当前用户redis部分缓存信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {

        Subject subject = getSubject(request, response);
       /* DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        ShiroRealm shiroRealm = (ShiroRealm) securityManager.getRealms().iterator().next();
        PrincipalCollection principals = subject.getPrincipals();
        shiroRealm.clearCache(principals);*/
        String userName = (String) subject.getPrincipal();
        //登出
        subject.logout();

        //清楚控制并发控制缓存缓存-shiro:cache:kickout:用户名
        kickoutSessionControlFilter.delCache(userName);

        //获取登出后重定向到的地址
        String redirectUrl = getRedirectUrl(request, response, subject);
        //重定向
        issueRedirect(request, response, redirectUrl);
        return false;
    }

    private String getRedisKickoutKey(String username) {
        return "shiro:cache:kickout:" + username;
    }


    public KickoutSessionControlFilter getKickoutSessionControlFilter() {
        return kickoutSessionControlFilter;
    }

    public void setKickoutSessionControlFilter(KickoutSessionControlFilter kickoutSessionControlFilter) {
        this.kickoutSessionControlFilter = kickoutSessionControlFilter;
    }
}
