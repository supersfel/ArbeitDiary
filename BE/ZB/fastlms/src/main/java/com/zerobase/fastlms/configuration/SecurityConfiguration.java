package com.zerobase.fastlms.configuration;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.zerobase.fastlms.configuration.login.JwtAuthenticationFilter;
import com.zerobase.fastlms.configuration.login.JwtAuthorizationFilter;
import com.zerobase.fastlms.configuration.login.UserAuthenticationProvider;
import com.zerobase.fastlms.configuration.token.TokenUtils;
import com.zerobase.fastlms.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity//(debug = true)
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	private final MemberService memberService;
	private final CorsFilter corsFilter;
	
	@Bean
	PasswordEncoder getPasswodEncoder() {
		System.out.println("Bean getPass");
		return new BCryptPasswordEncoder();
	}
/*
	@Bean
	UserAuthenticationFailureHanler getFailureHandler() {
		System.out.println("Bean User");
		return new UserAuthenticationFailureHanler();
	}
*/	
	@Bean
	UserAuthenticationSuccessHandler getSuccessHandler() {
		System.out.println("Success Handler");
		return new UserAuthenticationSuccessHandler();
	}
	
	@Bean 
	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager());
		//jwtAuthenticationFilter.setFilterProcessesUrl("/member/login"); 
		jwtAuthenticationFilter.setUsernameParameter("userId"); 
		jwtAuthenticationFilter.setPasswordParameter("userPassword");
		//jwtAuthenticationFilter.setAuthenticationSuccessHandler(getSuccessHandler());
		//jwtAuthenticationFilter.setAuthenticationFailureHandler(getFailureHandler());
		//jwtAuthenticationFilter.afterPropertiesSet();
		return jwtAuthenticationFilter;

	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		System.out.println("?????????");
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserAuthenticationProvider userAuthenticationProvider() {
		return new UserAuthenticationProvider(memberService, bCryptPasswordEncoder());
	}
	
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		System.out.println("configure");
		http.csrf().disable();
		//http.cors();
		// ?????? ?????? X
		// http ????????? ?????? X
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilter(corsFilter)
//			.formLogin()//.disable()
//				.loginPage("/member/login")
//				.usernameParameter("userId")
//				.passwordParameter("userPassword")
//				.successHandler(getSuccessHandler())
//				.permitAll()
//			.and()
			.httpBasic().disable()
			.addFilter(jwtAuthenticationFilter())
			.addFilter(new JwtAuthorizationFilter(authenticationManager(), memberService));
		
		//http.headers().frameOptions().sameOrigin();
		// ?????? URL ?????? ??????
		http.authorizeRequests()
			.antMatchers("/"
				,"/member/login"
				,"/member/register"
				,"/member/email-auth"
				,"/member/find/password"
				,"/member/find/email"
				,"/member/reset/password"
				,"/login")
			    .permitAll();
		//http.addFilterBefore(new Myfilter3(), SecurityContextPersistenceFilter.class);
		http.authorizeRequests()
			.antMatchers("/api/**"
					,"/api/userToken*"
					,"/api/find/password"
					,"/api/reset/password")
			.permitAll();
		http.authorizeRequests()
			.antMatchers("/admin/**")
			.hasAuthority("ROLE_ADMIN");
		
		http.authorizeRequests()
			.antMatchers("/member/info")
			.access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')");
		/*
		http.formLogin()
			.and()
			.addFilterBefore(jwtAuthenticationFilter(), JwtAuthenticationFilter.class);
		*/
		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
			.logoutSuccessUrl("/")
			.invalidateHttpSession(true);
		
		http.exceptionHandling()
			.accessDeniedPage("/error/denied");
		
		super.configure(http);
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		System.out.println("configue - auth");
		//auth.parentAuthenticationManager(authenticationManagerBean())
		//.userDetailsService(memberService);
		auth.authenticationProvider(userAuthenticationProvider());
		/*
			.userDetailsService(memberService)
			.passwordEncoder(getPasswodEncoder());
		*/
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring()
			.antMatchers("/"
					,"/member/regist"
					,"/api/login");
	}

	

}
