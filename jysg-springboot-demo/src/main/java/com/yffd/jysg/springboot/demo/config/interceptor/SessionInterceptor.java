package com.yffd.jysg.springboot.demo.config.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("session 拦截器 测试》》》》》》》》》》》》》preHandle");
        //1.登录不做拦截
        //2.验证session是否存在
//        Object obj = request.getSession().getAttribute("_session_user");
//        if (null == obj) {
//            response.sendRedirect("/loginView");
//            return false;
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("session 拦截器 测试》》》》》》》》》》》》》postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("session 拦截器 测试》》》》》》》》》》》》》afterCompletion");
    }
}
