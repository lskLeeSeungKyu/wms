package com.server.boot.config;

import com.server.boot.filter.CookieAttributeFilter;
import com.server.boot.filter.RCEDefenseFilter;
import com.server.boot.interceptor.MethodInterceptor;
import com.server.boot.interceptor.UserInterceptor;
import com.server.boot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Component
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final UserService userService;
    private final ObjectProvider<UserService> userServiceProvider;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new UserInterceptor())
//                .order(1)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/", "/login.wms", "/logout.wms");

        registry.addInterceptor(new MethodInterceptor(userService))
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/logout.wms", "/sessionInfo");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://10.101.52.96:8081")
                .allowCredentials(true); // 자격 증명 정보 허용. (restful한 프로젝트의 서버와 클라이언트간의 쿠키까지 전송 가능)
                                         // @CrossOrigin(origins = "*", allowedHeaders = "*", 이 속성 ㅡ> allowCredentials = "true")
    }

    @Bean
    public FilterRegistrationBean TestFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new RCEDefenseFilter(userServiceProvider));
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }
}
