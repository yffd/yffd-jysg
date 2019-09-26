package com.yffd.jysg.sso.middleware.cas.credential;

import com.yffd.jysg.sso.middleware.cas.exception.CustomAccountNotFoundException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义表单处理器
 */
public class CustomRememberMeUsernamePasswordAuthenticationHandler extends AbstractPreAndPostProcessingAuthenticationHandler {
    private static final Logger LOG = LoggerFactory.getLogger(CustomRememberMeUsernamePasswordAuthenticationHandler.class);

    public CustomRememberMeUsernamePasswordAuthenticationHandler(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order) {
        super(name, servicesManager, principalFactory, order);
    }

    @Override
    protected AuthenticationHandlerExecutionResult doAuthentication(Credential credential) throws GeneralSecurityException, PreventedException {
        CustomRememberMeUsernamePasswordCredential myCredential = (CustomRememberMeUsernamePasswordCredential) credential;
        LOG.info("自定义CustomRememberMeUsernamePasswordAuthenticationHandler#doAuthentication");
        String requestCaptcha = myCredential.getCaptcha();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Object attribute = attributes.getRequest().getSession().getAttribute("captcha");

        String realCaptcha = attribute == null ? null : attribute.toString();
        LOG.info("自定义CustomRememberMeUsernamePasswordAuthenticationHandler#doAuthentication，requestCaptcha：{}，sessionCaptcha：{}", requestCaptcha, realCaptcha);

//        if(StringUtils.isBlank(requestCaptcha) || !requestCaptcha.toUpperCase().equals(realCaptcha)){
////            throw new FailedLoginException("验证码错误");
//            throw new CustomCaptchaErrorException("验证码错误!");
//        }

        String username = myCredential.getUsername();
        String password = new String(myCredential.getPassword());
        Map<String, Object> user = new HashMap<>();
        user.put("password", "123456");
        user.put("state", "0");
        user.put("name", username);
        user.put("phone", "180123456");

        //可以在这里直接对用户名校验，或者调用 CredentialsMatcher校验
        if (user == null) {
            throw new UnknownAccountException("用户名或密码错误！");
        }
        if ("lisi".equals(username)) {
            throw new CustomAccountNotFoundException("用户不存在!");
        }
        // 可交给 密码比较器 进行对比密码
        if (!password.equals(user.get("password"))) {
            throw new IncorrectCredentialsException("用户名或密码错误！");
        }
        if ("1".equals(user.get("state"))) {
            throw new LockedAccountException("账号已被锁定,请联系管理员！");
        }
//        return createHandlerResult(credential, this.principalFactory.createPrincipal(username));
        return createHandlerResult(credential, this.principalFactory.createPrincipal(username, user));//自定义返回信息给客户端
    }

    @Override
    public boolean supports(Credential credential) {
        return credential instanceof CustomRememberMeUsernamePasswordCredential;
    }
}
