package com.server.boot.config;

import com.server.boot.filter.CookieAttributeFilter;
import com.server.boot.interceptor.UserInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Component
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/login.wms", "/logout.wms");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8081")
                .allowCredentials(true); // 자격 증명 정보 허용. (restful한 프로젝트의 서버와 클라이언트간의 쿠키까지 전송 가능)
                                         // @CrossOrigin(origins = "*", allowedHeaders = "*", 이 속성 ㅡ> allowCredentials = "true")
    }

    @Bean
    public FilterRegistrationBean TestFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new CookieAttributeFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }
}
