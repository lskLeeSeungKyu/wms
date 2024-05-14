package com.server.boot.utils;

import org.springframework.http.ResponseCookie;
import org.springframework.util.SerializationUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class CookieUtil {

    public static void addCookie(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("request.getSession().getId() = " + request.getSession().getId());
        ResponseCookie cookie = ResponseCookie.from("JSESSIONID", request.getSession().getId())
                .path("/")
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .maxAge(1000)
                .build();

        response.setHeader("Set-Cookie", cookie.toString());
        System.out.println("cookie.toString() = " + cookie.toString());
    }

}