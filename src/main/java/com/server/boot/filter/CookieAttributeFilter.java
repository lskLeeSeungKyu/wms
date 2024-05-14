package com.server.boot.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class CookieAttributeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        chain.doFilter(request, response);
//        addSameSite(httpServletResponse, "None");

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

/*    private void addSameSite(HttpServletResponse response, String sameSite) {
        Collection<String> headers = response.getHeaders(HttpHeaders.SET_COOKIE);
        System.out.println("headers1 = " + headers);
        boolean firstHeader = true;
        for (String header : headers) {
            System.out.println("header = " + header);
            if (firstHeader) {
                response.setHeader(HttpHeaders.SET_COOKIE, String.format("%s; Secure; %s", header, "SameSite=" + sameSite));
                firstHeader = false;
                continue;
            }
            response.addHeader(HttpHeaders.SET_COOKIE, String.format("%s; Secure; %s", header, "SameSite=" + sameSite));
        }
    }*/


}
