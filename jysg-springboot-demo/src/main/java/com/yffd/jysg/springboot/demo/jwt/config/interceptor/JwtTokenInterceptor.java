package com.yffd.jysg.springboot.demo.jwt.config.interceptor;

import com.yffd.jysg.springboot.demo.jwt.annotation.CheckToken;
import com.yffd.jysg.springboot.demo.jwt.config.JwtConstants;
import com.yffd.jysg.springboot.demo.jwt.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class JwtTokenInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //自动排除options请求是cors跨域预请求，设置allow对应头信息
        if(RequestMethod.OPTIONS.toString().equals(request.getMethod())) return true;

        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)) return true;

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //检查方法上是否存在CheckToken注解
        if (method.isAnnotationPresent(CheckToken.class)) {
            CheckToken checkToken = method.getAnnotation(CheckToken.class);
            if (!checkToken.required()) return true;

            // 从http请求头中取出 token
            String token = request.getHeader(JwtConstants.TOKEN_HEADER);
            // 从http请求参数中取出 token
            if (StringUtils.isEmpty(token)) token = request.getParameter(JwtConstants.TOKEN_HEADER);
            if (token == null) {
                LOGGER.info("not found X-YAuth-Token");
                return false;
            }

            String subject = JwtUtil.getSubject(JwtConstants.TOKEN_SECRET, token);
            if (null == subject) {
                LOGGER.info("parse subject from token fail");
                return false;
            }

            boolean isExpiration = JwtUtil.isExpiration(JwtConstants.TOKEN_SECRET, token);
            if (isExpiration) {
                LOGGER.info("token expiration");
                return false;
            }

            /*User user = userService.findUserById(subject);
            if (user == null) {
                throw new RuntimeException("用户不存在，请重新登录");
            }*/

            return true;
        }
        return true;
    }

    /**
     * 根据传入的类型获取spring管理的对应dao
     * @param clazz 类型
     * @param request 请求对象
     * @param <T>
     * @return
     */
    private <T> T getDAO(Class<T> clazz, HttpServletRequest request) {
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        return factory.getBean(clazz);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
