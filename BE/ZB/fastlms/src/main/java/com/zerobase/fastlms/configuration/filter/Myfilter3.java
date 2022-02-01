package com.zerobase.fastlms.configuration.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Myfilter3 implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req =  (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		if(req.getMethod().equals("POST")) {
			System.out.println("POST 요청됨");
			System.out.println("필터3");
			String headerAuth = req.getHeader("Authorization");
			if(headerAuth.equals("course")) {
				chain.doFilter(req, res);
			}else {
				res.setCharacterEncoding("UTF-8"); 
				res.setContentType("text/html; charset=UTF-8");
				PrintWriter out = res.getWriter();
				out.println("인증안됨");
			}
			
		}
		chain.doFilter(request, response);
	}
		
}
