package com.server.boot.interceptor;

import com.server.boot.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        CookieUtil cookieUtil = new CookieUtil();
//        cookieUtil.addCookie(request, response);



        HttpSession session = request.getSession();
        if(session.getAttribute("user") == null) {
            System.out.println("No Session");
            //return false;
        }
        System.out.println("session.getAttribute(\"user\") = " + session.getAttribute("user"));
        return true;
    }
}
