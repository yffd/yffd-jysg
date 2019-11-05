package com.yffd.jysg.springboot.demo.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static Logger LOG = LoggerFactory.getLogger(CustomGlobalExceptionHandler.class);
    /**
     * 统一异常拦截
     * 注：无论是访问返回视图接口，还是返回json串接口，只要抛出的excetion异常全部由这个方法拦截，并统一返回json串
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    Map<String,String> handleControllerException(HttpServletRequest request, Throwable ex) {
        LOG.info(">>>>>>>>>handleControllerException");
        HttpStatus status = getStatus(request);
        Map<String, String> map = new HashMap();
        map.put("code", String.valueOf(status.value()));
        map.put("msg", ex.getMessage());
        return map;
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

    /**
     * 拦截抛出CustomViewException 异常的方法并返回视图
     * @param request
     * @param ex
     * @return
     */
   /* @ExceptionHandler(CustomViewException.class)
    ModelAndView handleControllerCustomExceptionView(HttpServletRequest request, CustomViewException ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        HttpStatus status = getStatus(request);
        Map<String,String> map = new HashMap();
        map.put("code", String.valueOf(status.value()));
        map.put("msg", "customer error msg");
        return modelAndView;
    }*/

}
