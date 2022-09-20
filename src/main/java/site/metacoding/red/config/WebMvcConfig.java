package site.metacoding.red.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import site.metacoding.red.handler.LoginIntercepter;
/*.excludePathPatterns("/s/boards/**") => /s/boards로 시작하는 모든것을 제외*/

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginIntercepter())
		.addPathPatterns("/s/**");
		//.addPathPatterns("/admin/**")
		//.excludePathPatterns("/s/boards/**");// /s/** => s 뒤에 모든 주소를 읽음 /s/boards/1/2도 가능 
												// /s* => /s/boards, /s/users
	}
}
