package com.yffd.jysg.springboot.demo.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 启用自定义全局错误处理后，springboot默认的处理规则将不起作用
 */
@Controller
public class CustomErrorController extends BasicErrorController {
    private static Logger LOG = LoggerFactory.getLogger(CustomErrorController.class);

    public CustomErrorController() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    //通过浏览器访问的url 如果发生异常全部会被浏览并跳转到customError.html页面
    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        LOG.info(">>>>>>>>>>errorHtml");
        HttpStatus status = this.getStatus(request);
        Map<String, Object> model = Collections.unmodifiableMap(this.getErrorAttributes(request, this.isIncludeStackTrace(request, MediaType.TEXT_HTML)));
        response.setStatus(status.value());
        ModelAndView modelAndView = this.resolveErrorView(request, response, status, model);
        //指定自定义的视图
        return modelAndView != null ? modelAndView : new ModelAndView("customError", model);
    }

    //通过http client访问的接口如果发生异常会调用这个方法
    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        LOG.info(">>>>>>>>>>error");
        //输出自定义的Json格式
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", false);
        map.put("msg", "");

        HttpStatus status = this.getStatus(request);
        if (status == HttpStatus.NO_CONTENT) {
            return new ResponseEntity<Map<String, Object>>(map, status);
        } else {
            Map<String, Object> body = this.getErrorAttributes(request, this.isIncludeStackTrace(request, MediaType.ALL));
            //输出自定义的Json格式
            map.put("msg", body.get("message"));
            return new ResponseEntity<Map<String, Object>>(map, status);
        }
    }
}
