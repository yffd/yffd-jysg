package com.yffd.jysg.sso.middleware.cas.authentication;

import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.UsernamePasswordCredential;
import org.apereo.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.AccountNotFoundException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

/**
 * 自定义验证处理器
 */
public class CustomUsernamePasswordAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {
    private static final Logger LOG = LoggerFactory.getLogger(CustomUsernamePasswordAuthenticationHandler.class);

    public CustomUsernamePasswordAuthenticationHandler(String name, ServicesManager servicesManager,
                                                       PrincipalFactory principalFactory, Integer order) {
        super(name, servicesManager, principalFactory, order);
    }

    @Override
    protected AuthenticationHandlerExecutionResult authenticateUsernamePasswordInternal(UsernamePasswordCredential credential,
                                                                                        String originalPassword) throws GeneralSecurityException, PreventedException {
        LOG.info("自定义认证处理器>>CustomUsernamePasswordAuthenticationHandler，username：{}, password：{}, originalPassword：{}", credential.getUsername(), credential.getPassword(), originalPassword);
        if("guest".equals(credential.getUsername())){
            return createHandlerResult(credential,
                    this.principalFactory.createPrincipal(credential.getUsername()),
                    new ArrayList<>(0));
        } else {
            throw new AccountNotFoundException("必须是guest用户才允许通过");
        }
    }
}
