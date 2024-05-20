package com.server.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.InitBinderDataBinderFactory;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestDataBinderFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@EnableAspectJAutoProxy
public class BootApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootApplication.class, args);
	}

	@Bean
	public WebMvcRegistrations mvcRegistrations() {
		return new WebMvcRegistrations() {
			@Override
			public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter() {
				return new ExtendedRequestMappingHandlerAdapter();
			}
		};
	}

	private static class ExtendedRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {

		@Override
		protected InitBinderDataBinderFactory createDataBinderFactory(List<InvocableHandlerMethod> methods) {

			return new ServletRequestDataBinderFactory(methods, getWebBindingInitializer()) {

				@Override
				protected ServletRequestDataBinder createBinderInstance(
						Object target, String name, NativeWebRequest request) throws Exception {

					ServletRequestDataBinder binder = super.createBinderInstance(target, name, request);
					String[] fields = binder.getDisallowedFields();
					List<String> fieldList = new ArrayList<>(fields != null ? Arrays.asList(fields) : Collections.emptyList());
					fieldList.addAll(Arrays.asList("class.*", "Class.*", "*.class.*", "*.Class.*"));
					binder.setDisallowedFields(fieldList.toArray(new String[] {}));
					return binder;
				}
			};
		}
	}

}
