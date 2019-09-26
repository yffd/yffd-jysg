package com.yffd.jysg.sso.cas.app1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cas")
public class CasController {

    @RequestMapping("insert")
    public String insert(String username){
        return "result";
    }


    /**
     * 跳转到默认页面
     * @param session
     * @return
     */
    @RequestMapping("/logout1")
    public String loginOut(HttpSession session){
        session.invalidate();
        //这个是直接退出，走的是默认退出方式
        return "redirect:https://server.cas.com:8443/cas/logout";
    }

    /**
     * 跳转到指定页面
     * @param session
     * @return
     */
    @RequestMapping("/logout2")
    public String loginOut2(HttpSession session){
        session.invalidate();
        //退出登录后，跳转到退成成功的页面，不走默认页面
        return "redirect:https://server.cas.com:8443/cas/logout?service=http://app1.cas.com:8081/login.jsp";
    }
}
