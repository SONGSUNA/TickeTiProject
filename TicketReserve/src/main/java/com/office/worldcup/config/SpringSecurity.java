package com.office.worldcup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@EnableWebSecurity
public class SpringSecurity {
	@Bean
	public PasswordEncoder passwordEncoder() {
			log.info("passwordEncoder");
		
		return new BCryptPasswordEncoder();
	}
	 @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		 return http.build();
	    }
}
