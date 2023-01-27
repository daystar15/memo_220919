package com.memo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.memo.common.FileManagerService;
import com.memo.interceptor.PermissionInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	private PermissionInterceptor interceptor;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry
		.addResourceHandler("/images/**") // 웹 이미지 주소 http://localhost:8080/images/aaaa_162090932/sun.png **은 자손도 포함한다.
		.addResourceLocations("file:///" + FileManagerService.FILE_UPLOAD_PATH); // 실제 파일 위치 윈도우는 슬래시 3개, 맥은 2개
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry
		.addInterceptor(interceptor)
		.addPathPatterns("/**")     //    /** 아래 디렉토리까지 확인
		.excludePathPatterns("/favicon.ico", "/error", "/static/**", "/user/sign_out");     // 이 주소로 들어오는 것은 제외할거다
	}
}
