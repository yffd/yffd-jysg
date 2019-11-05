package com.yffd.jysg.springboot.shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.yffd.jysg.springboot.shiro.cache.RedisCacheManager;
import com.yffd.jysg.springboot.shiro.cache.RedisManager;
import com.yffd.jysg.springboot.shiro.filter.KickoutSessionControlFilter;
import com.yffd.jysg.springboot.shiro.filter.ShiroLogoutFilter;
import com.yffd.jysg.springboot.shiro.pwd.RetryLimitHashedCredentialsMatcher;
import com.yffd.jysg.springboot.shiro.realm.ShiroRealm;
import com.yffd.jysg.springboot.shiro.session.ShiroSessionFactory;
import com.yffd.jysg.springboot.shiro.session.ShiroSessionListener;
import com.yffd.jysg.springboot.shiro.session.ShiroSessionManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.apache.shiro.mgt.SecurityManager;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Properties;

@Configuration
public class ShiroConfig {
    /**
     * Shiro主过滤器
     * @param securityManager
     * @return
     */
    @Bean(name = "shirFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //必须设置 SecurityManager,Shiro的核心安全接口
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //配置登录的url，如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/");
        //登录成功后要跳转的链接，登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权界面，这里设置403并不会起作用
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");

        //自定义拦截器限制并发人数
        LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();
        //限制同一帐号同时在线的个数
        filtersMap.put("kickout", kickoutSessionControlFilter());
        //配置自定义登出 覆盖 logout 之前默认的LogoutFilter
        filtersMap.put("logout", shiroLogoutFilter());

        shiroFilterFactoryBean.setFilters(filtersMap);

        // 配置访问权限 必须是LinkedHashMap，因为它必须保证有序
        // 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 一定要注意顺序,否则就不好使了
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //配置不登录可以访问的资源，anon 表示资源都可以匿名访问
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/plugins/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        //解锁用户专用 测试用的
        filterChainDefinitionMap.put("/unlockAccount","anon");
        filterChainDefinitionMap.put("/Captcha.jpg","anon");

        //logout是shiro提供的过滤器
//        filterChainDefinitionMap.put("/logout", "logout");


        //其他资源都需要认证  authc 表示需要认证才能进行访问
//        filterChainDefinitionMap.put("/**", "authc");
        //user表示配置记住我或认证通过可以访问
//        filterChainDefinitionMap.put("/**", "user");
        filterChainDefinitionMap.put("/**", "kickout,user");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 配置核心安全事务管理器
     * @return
     */
    @Bean(name="securityManager")
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        //设置自定义realm.
        securityManager.setRealm(shiroRealm());
        //配置记住我
        securityManager.setRememberMeManager(rememberMeManager());

        //配置 redis缓存管理器
        securityManager.setCacheManager(cacheManager());

        //配置自定义session管理，使用redis
        securityManager.setSessionManager(sessionManager());

        return securityManager;
    }

    /**
     * 配置Shiro生命周期处理器
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     *  身份认证realm; (这个需要自己写，账号密码校验；权限等)
     * @return
     */
    @Bean
    public ShiroRealm shiroRealm(){
        ShiroRealm shiroRealm = new ShiroRealm();

        //开启缓存
        shiroRealm.setCachingEnabled(true);
        //启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
        shiroRealm.setAuthenticationCachingEnabled(true);
        //缓存AuthenticationInfo信息的缓存名称 在ehcache-shiro.xml中有对应缓存的配置
        shiroRealm.setAuthenticationCacheName("authenticationCache");
        //启用授权缓存，即缓存AuthorizationInfo信息，默认false
        shiroRealm.setAuthorizationCachingEnabled(true);
        //缓存AuthorizationInfo信息的缓存名称  在ehcache-shiro.xml中有对应缓存的配置
        shiroRealm.setAuthorizationCacheName("authorizationCache");

        //配置自定义密码比较器
        shiroRealm.setCredentialsMatcher(retryLimitHashedCredentialsMatcher());
        return shiroRealm;
    }


    /**
     * 记住我
     * cookie对象，会话Cookie模板，默认为: JSESSIONID，问题: 与SERVLET容器名冲突，重新定义为sid或rememberMe
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie(){
        //cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数
        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;
    }

    /**
     * 记住我
     * cookie管理对象，rememberMe管理器
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }

    /**
     * FormAuthenticationFilter 过滤器 过滤记住我
     * @return
     */
    @Bean
    public FormAuthenticationFilter formAuthenticationFilter(){
        FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
        //shiro登录表单验证时，与前端页面表单中的属性名映射设置
        //对应前端的checkbox的name = rememberMe
        formAuthenticationFilter.setRememberMeParam("rememberMe");
        return formAuthenticationFilter;
    }

    /**
     * shiro用redis实现缓存需要重写cache、cacheManager、SessionDAO和初始化redis配置
     */
    /**
     * shiro缓存管理器;
     * 需要添加到securityManager中
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        //redis中针对不同用户缓存
        redisCacheManager.setPrincipalIdFieldName("username");
        //用户权限信息缓存时间
        redisCacheManager.setExpire(200000);
        return redisCacheManager;
    }
    @Bean
    public RedisManager redisManager(){
        RedisManager redisManager = new RedisManager();
        return redisManager;
    }
    /**
     * 让某个实例的某个方法的返回值注入为Bean的实例
     * Spring静态注入
     * @return
     */
    /*@Bean
    public MethodInvokingFactoryBean getMethodInvokingFactoryBean(){
        MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean();
        factoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        factoryBean.setArguments(new Object[]{securityManager()});
        return factoryBean;
    }*/


    /**
     * shiro的session管理：
     *  1.配置会话ID生成器
     *  2.配置sessionDAO
     *  3.配置sessionId的Cookie
     *  4.配置session管理器
     */
    /**
     * 1.配置会话ID生成器
     * @return
     */
    @Bean
    public SessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }
    /**
     * 2.SessionDAO的作用是为Session提供CRUD并进行持久化的一个shiro组件
     * MemorySessionDAO 直接在内存中进行会话维护
     * EnterpriseCacheSessionDAO  提供了缓存功能的会话维护，默认情况下使用MapCache实现，内部使用ConcurrentHashMap保存缓存的会话。
     * @return
     */
    @Bean
    public SessionDAO sessionDAO() {
        EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
        enterpriseCacheSessionDAO.setCacheManager(cacheManager());
        //设置session缓存的名字 默认为 shiro-activeSessionCache
        enterpriseCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        //sessionId生成器
        enterpriseCacheSessionDAO.setSessionIdGenerator(sessionIdGenerator());
        return enterpriseCacheSessionDAO;
    }
    /**
     * 3.配置保存sessionId的cookie
     * 注意：这里的cookie不是上面的记住我 cookie ，记住我需要一个cookie session管理，也需要自己的cookie
     * @return
     */
    @Bean("sessionIdCookie")
    public SimpleCookie sessionIdCookie(){
        //这个参数是cookie的名称
        SimpleCookie simpleCookie = new SimpleCookie("sid");
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：

        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //maxAge=-1表示浏览器关闭时失效此Cookie
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }
    /**
     * 4.配置会话管理器，设定会话超时及保存；
     * Shiro提供了三个默认实现：
     * DefaultSessionManager：DefaultSecurityManager使用的默认实现，用于JavaSE环境；
     * ServletContainerSessionManager：DefaultWebSecurityManager使用的默认实现，用于Web环境，其直接使用Servlet容器的会话；
     * DefaultWebSessionManager：用于Web环境的实现，可以替代ServletContainerSessionManager，自己维护着会话，直接废弃了Servlet容器的会话管理。
     * @return
     */
    @Bean("sessionManager")
    public SessionManager sessionManager() {
//        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        ShiroSessionManager sessionManager = new ShiroSessionManager();
        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        //配置监听
        listeners.add(sessionListener());
        sessionManager.setSessionListeners(listeners);
        sessionManager.setSessionIdCookie(sessionIdCookie());
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setCacheManager(cacheManager());
        sessionManager.setSessionFactory(sessionFactory());

        //全局会话超时时间（单位毫秒），默认30分钟  暂时设置为10秒钟 用来测试
        sessionManager.setGlobalSessionTimeout(1800000);
        //是否开启删除无效的session对象  默认为true
        sessionManager.setDeleteInvalidSessions(true);
        //是否开启定时调度器进行检测过期session 默认为true
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 默认为 1个小时
        //设置该属性 就不需要设置 ExecutorServiceSessionValidationScheduler 底层也是默认自动调用ExecutorServiceSessionValidationScheduler
        //暂时设置为 5秒 用来测试
        sessionManager.setSessionValidationInterval(3600000);
//        sessionManager.setSessionValidationInterval(5000);

        //取消url后面的 JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);

        return sessionManager;
    }

    @Bean("sessionFactory")
    public ShiroSessionFactory sessionFactory(){
        ShiroSessionFactory sessionFactory = new ShiroSessionFactory();
        return sessionFactory;
    }

    /**
     * 配置session监听
     * @return
     */
    @Bean("sessionListener")
    public ShiroSessionListener sessionListener(){
        ShiroSessionListener sessionListener = new ShiroSessionListener();
        return sessionListener;
    }

    /**
     * 并发登录控制
     * @return
     */
    @Bean
    public KickoutSessionControlFilter kickoutSessionControlFilter(){
        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
        //用于根据会话ID，获取会话进行踢出操作的；
        kickoutSessionControlFilter.setSessionManager(sessionManager());
        //使用cacheManager获取相应的cache来缓存用户登录的会话；用于保存用户—会话之间的关系的；
        kickoutSessionControlFilter.setRedisManager(redisManager());
        //是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；
        kickoutSessionControlFilter.setKickoutAfter(false);
        //同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两个人登录；
        kickoutSessionControlFilter.setMaxSession(1);
        //被踢出后重定向到的地址；
        kickoutSessionControlFilter.setKickoutUrl("/login?kickout=1");
        return kickoutSessionControlFilter;
    }

    /**
     * 配置密码比较器
     * @return
     */
    @Bean("credentialsMatcher")
    public RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher(){
        RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher = new RetryLimitHashedCredentialsMatcher();
        retryLimitHashedCredentialsMatcher.setRedisManager(redisManager());

        //如果密码加密,可以打开下面配置
        //加密算法的名称
        retryLimitHashedCredentialsMatcher.setHashAlgorithmName("MD5");
        //配置加密的次数
        retryLimitHashedCredentialsMatcher.setHashIterations(2);
        //是否存储为16进制
        retryLimitHashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);

        return retryLimitHashedCredentialsMatcher;
    }

    /**
     * 配置LogoutFilter
     * @return
     */
    public ShiroLogoutFilter shiroLogoutFilter(){
        ShiroLogoutFilter shiroLogoutFilter = new ShiroLogoutFilter();
        shiroLogoutFilter.setKickoutSessionControlFilter(kickoutSessionControlFilter());
        //配置登出后重定向的地址
        shiroLogoutFilter.setRedirectUrl("/login");
        return shiroLogoutFilter;
    }

    /**
     * 必须（thymeleaf页面使用shiro标签控制按钮是否显示）
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    /**
     * 开启shiro 注解模式
     * 可以在controller中的方法前加上注解
     * 如 @RequiresPermissions("userInfo:add")
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 解决： 无权限页面不跳转 shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized") 无效
     * shiro的源代码ShiroFilterFactoryBean.Java定义的filter必须满足filter instanceof AuthorizationFilter，
     * 只有perms，roles，ssl，rest，port才是属于AuthorizationFilter，而anon，authcBasic，auchc，user是AuthenticationFilter，
     * 所以unauthorizedUrl设置后页面不跳转 Shiro注解模式下，登录失败与没有权限都是通过抛出异常。
     * 并且默认并没有去处理或者捕获这些异常。在SpringMVC下需要配置捕获相应异常来通知用户信息
     * @return
     */
    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver simpleMappingExceptionResolver=new SimpleMappingExceptionResolver();
        Properties properties=new Properties();
        //这里的 /unauthorized 是页面，不是访问的路径
        properties.setProperty("org.apache.shiro.authz.UnauthorizedException","/unauthorized");
        properties.setProperty("org.apache.shiro.authz.UnauthenticatedException","/unauthorized");
        simpleMappingExceptionResolver.setExceptionMappings(properties);
        return simpleMappingExceptionResolver;
    }
}
