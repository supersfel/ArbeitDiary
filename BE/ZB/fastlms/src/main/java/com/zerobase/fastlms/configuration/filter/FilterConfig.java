package com.zerobase.fastlms.configuration.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig{

	@Bean
	public FilterRegistrationBean<Myfilter2> filter2(){
		FilterRegistrationBean<Myfilter2> bean = new FilterRegistrationBean<>(new Myfilter2());
		bean.addUrlPatterns("/*");
		bean.setOrder(0);
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<Myfilter1> filter1(){
		FilterRegistrationBean<Myfilter1> bean = new FilterRegistrationBean<>(new Myfilter1());
		bean.addUrlPatterns("/*");
		bean.setOrder(1);
		return bean;
	}
}
