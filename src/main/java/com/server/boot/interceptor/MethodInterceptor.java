package com.server.boot.interceptor;

import com.server.boot.dto.UserDTO;
import com.server.boot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class MethodInterceptor implements HandlerInterceptor {

    private final UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        HttpSession session = request.getSession();
//        UserDTO user = (UserDTO)session.getAttribute("user");

        Map<String, String> map = new HashMap<>();

        SimpleDateFormat dateFormatMonthDay = new SimpleDateFormat("MM월 dd일 HH시 mm분");
        Date currentDate = new Date();

        map.put("requestURL", String.valueOf(request.getRequestURL()));
        map.put("handlerMethod", handler.toString());
//        map.put("userId", user.getId());
//        map.put("userName", user.getName());
        map.put("userIp", request.getRemoteAddr());
        map.put("time", dateFormatMonthDay.format(currentDate));
        map.put("userAgent", request.getHeader("user-Agent"));

        userService.requestLog(map);

        return true;
    }
}
