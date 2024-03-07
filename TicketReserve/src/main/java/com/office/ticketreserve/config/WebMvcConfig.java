package com.office.ticketreserve.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.office.ticketreserve.interceptor.AdminInterceptor;
import com.office.ticketreserve.interceptor.UserLoginInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Bean
	public RestTemplate restTemplate() {
		
		return new RestTemplate();
		
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 어드민 인터셉터
		registry.addInterceptor(new AdminInterceptor())
				.addPathPatterns("/admin/**");
		
		// 유저 인터셑터
	    registry.addInterceptor(new UserLoginInterceptor())
	    		.addPathPatterns("/user/**")
	    		.excludePathPatterns("/user/create_account_form")
	    		.excludePathPatterns("/user/user_login_form");
	}
	
}
