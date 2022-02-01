package com.zerobase.fastlms.configuration.login;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.zerobase.fastlms.configuration.token.AuthConstants;
import com.zerobase.fastlms.configuration.token.TokenUtils;
import com.zerobase.fastlms.member.model.CustomUserDetails;
import com.zerobase.fastlms.member.service.MemberService;

import io.jsonwebtoken.Jwts;

/*
 * 권한 인증 필요 시
 * 현재 권한 사용중이므로 무조건 들어온다.
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter{
	private final MemberService memberService;
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberService memberService) {
		super(authenticationManager);
		this.memberService = memberService;
		// TODO Auto-generated constructor stub
		
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("들어왔나?");
		//System.out.println(SecurityContextHolder.getContext().get);
		Authentication auth = (Authentication) request.getSession().getAttribute(AuthConstants.AUTH_HEADER);
		System.out.println(auth);
		System.out.println(SecurityContextHolder.getContextHolderStrategy().getContext());
		System.out.println(response.getHeader(AuthConstants.AUTH_HEADER));
		String jwtToken = request.getHeader(AuthConstants.AUTH_HEADER);
		System.out.println("header_token : "+ jwtToken);

		
		if(jwtToken == null || !jwtToken.startsWith(AuthConstants.TOKEN_TYPE)) {
			chain.doFilter(request, response);
			return;
		}
		
		String token = request.getHeader(AuthConstants.AUTH_HEADER).replace(AuthConstants.TOKEN_TYPE + " ", "");
		String username = null;
		username = TokenUtils.getUserEmailFromAccessToken(token);
		System.out.println("username :" + username);
		/*
		 * 아래 에러 = 토큰값 완료
		 * io.jsonwebtoken.ExpiredJwtException: JWT expired at
		 */
		if(username != null) {
			CustomUserDetails userDetails = memberService.apiUserDetail(username);
			System.out.println(userDetails);
			Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			chain.doFilter(request, response);
		}
		
	}
}
