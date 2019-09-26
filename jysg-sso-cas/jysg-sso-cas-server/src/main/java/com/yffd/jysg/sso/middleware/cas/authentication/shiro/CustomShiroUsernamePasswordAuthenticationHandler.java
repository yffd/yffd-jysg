package com.yffd.jysg.sso.middleware.cas.authentication.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apereo.cas.authentication.*;
import org.apereo.cas.authentication.exceptions.AccountDisabledException;
import org.apereo.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;
import java.util.HashSet;
import java.util.Set;

/**
 * cas服务端自定义集成shiro
 * 参考：org.apereo.cas.adaptors.generic.ShiroAuthenticationHandler(org.apereo.cas:cas-server-support-shiro-authentication)
 */
public class CustomShiroUsernamePasswordAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {
    private static final Logger LOG = LoggerFactory.getLogger(CustomShiroUsernamePasswordAuthenticationHandler.class);

    public CustomShiroUsernamePasswordAuthenticationHandler(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order) {
        super(name, servicesManager, principalFactory, order);
    }

    @Override
    protected AuthenticationHandlerExecutionResult authenticateUsernamePasswordInternal(UsernamePasswordCredential credential, String originalPassword) throws GeneralSecurityException, PreventedException {
        LOG.info("自定义认证处理器-shiro>>CustomShiroUsernamePasswordAuthenticationHandler，username：{}, password：{}, originalPassword：{}", credential.getUsername(), credential.getPassword(), originalPassword);
        try {
            final UsernamePasswordToken token = new UsernamePasswordToken(credential.getUsername(),
                    credential.getPassword());

            if (credential instanceof RememberMeUsernamePasswordCredential) {
                token.setRememberMe(RememberMeUsernamePasswordCredential.class.cast(credential).isRememberMe());
            }

            final Subject currentUser = getCurrentExecutingSubject();
            currentUser.login(token);

            checkSubjectRolesAndPermissions(currentUser);
            return createAuthenticatedSubjectResult(credential, currentUser);
        } catch (final UnknownAccountException uae) {
            throw new AccountNotFoundException(uae.getMessage());
        } catch (final IncorrectCredentialsException ice) {
            throw new FailedLoginException(ice.getMessage());
        } catch (final LockedAccountException | ExcessiveAttemptsException lae) {
            throw new AccountLockedException(lae.getMessage());
        } catch (final ExpiredCredentialsException eae) {
            throw new CredentialExpiredException(eae.getMessage());
        } catch (final DisabledAccountException eae) {
            throw new AccountDisabledException(eae.getMessage());
        } catch (final AuthenticationException e){
            throw new FailedLoginException(e.getMessage());
        }  catch (final FailedLoginException e){
            throw new FailedLoginException(e.getMessage());
        }
    }


    protected void checkSubjectRolesAndPermissions(final Subject currentUser) throws FailedLoginException {
        LOG.info("自定义认证处理器-shiro>>CustomShiroUsernamePasswordAuthenticationHandler#checkSubjectRolesAndPermissions");
        //TODO
        Set<String> requiredRoles = new HashSet<>();
        requiredRoles.add("superadmin");
        requiredRoles.add("admin");
        requiredRoles.add("guest");
        if (requiredRoles != null) {
            boolean flag = true;
            for (final String role : requiredRoles) {
                if (currentUser.hasRole(role)) {
                    LOG.info("has role：" + role);
                    flag = false;
                    break;
                }
            }
            if (flag) {
                LOG.error("Required any role " + requiredRoles + " does not exist");
                throw new FailedLoginException("Required any role " + requiredRoles + " does not exist");
            }
        }
        //TODO
        Set<String> requiredPermissions = new HashSet<>();
        requiredPermissions.add("superadmin:*");
        requiredPermissions.add("admin:save");
        requiredPermissions.add("guest:view");
        if (requiredPermissions != null) {
            boolean flag = true;
            for (final String perm : requiredPermissions) {
                if (currentUser.isPermitted(perm)) {
                    LOG.info("has perm：" + perm);
                    flag = false;
                    break;
                }
            }
            if (flag) {
                LOG.error("Required any permission " + requiredPermissions + " does not exist");
                throw new FailedLoginException("Required any permission " + requiredPermissions + " does not exist");
            }
        }
    }

    protected Subject getCurrentExecutingSubject() {
        return SecurityUtils.getSubject();
    }

    protected AuthenticationHandlerExecutionResult createAuthenticatedSubjectResult(final Credential credential, final Subject currentUser) {
        final String username = currentUser.getPrincipal().toString();
        return createHandlerResult(credential, this.principalFactory.createPrincipal(username));
    }

}
