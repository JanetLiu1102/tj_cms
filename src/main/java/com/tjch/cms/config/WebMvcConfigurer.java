package com.tjch.cms.config;


import com.tjch.cms.interceptor.UserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 适配器
 *
 * @author liuyuzhen
 * @date 2019/12/11
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurationSupport {

	@Bean
    public UserInterceptor getUserInterceptor() {
        return new UserInterceptor();
    }
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		/**
		 * 拦截器按照顺序执行
		 */
		registry.addInterceptor(getUserInterceptor()).excludePathPatterns()
				//拦截所有请求
		.addPathPatterns("/**")
        .excludePathPatterns("/user/login")
				.excludePathPatterns("/user/saveUser")
				.excludePathPatterns("/project/savePro")
				.excludePathPatterns("/project/*")
				.excludePathPatterns("/upload/*");
		super.addInterceptors(registry);
	}
}
