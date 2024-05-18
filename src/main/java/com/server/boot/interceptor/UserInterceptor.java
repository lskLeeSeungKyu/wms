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

        //브라우저 네트워크 탭에서 api 두 번 요청하는 이유가 뭘까? 하나는 세션이 없는 것.
        //그래서 MethodInterceptor에서도 user.id를 넣지못함.
        //일단 인터셉터 적용 x

        HttpSession session = request.getSession();
        if(session.getAttribute("user") == null) {
            System.out.println("No Session");
            //return false;
        }
        System.out.println("session.getAttribute(\"user\") = " + session.getAttribute("user"));
        return true;
    }
}
