package com.zerobase.fastlms.configuration.token;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.model.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class TokenUtils {
	private static long ACCESS_TOKEN_VALIDATION_SECOND = 1000L *60 * 30;
	private static long REFRESH_TOKEN_VALIDATION_SECCOND = 1000L * 60 * 60 * 24;
	
	private static final String SECRECT_KEY = "secretKey";
	private static final String REFRESH_KEY = "refreshKey";
	
	public TokenUtils() {}
	public TokenUtils (long accessTokenValidation,long refreshTokenValidation) {
		this.ACCESS_TOKEN_VALIDATION_SECOND = accessTokenValidation;
		this.REFRESH_TOKEN_VALIDATION_SECCOND = refreshTokenValidation;
	}
	
	public static String generateAccessToken(CustomUserDetails member) {
		return doGenerateJwtToken(member, ACCESS_TOKEN_VALIDATION_SECOND);
	}
	
	public static String generateRefreshToken(CustomUserDetails member) {
		return doGenerateJwtToken(member, REFRESH_TOKEN_VALIDATION_SECCOND);
	}
	
	public static String doGenerateJwtToken(CustomUserDetails member, Long expireTime){
		String key = SECRECT_KEY;
		if (expireTime == REFRESH_TOKEN_VALIDATION_SECCOND) {
			key = REFRESH_KEY;
		}
		System.out.println("<generateJwtToken>");
		JwtBuilder builder = Jwts.builder()
				.setSubject(member.getUsername())
				.setHeader(createHeader())
				.setClaims(createClaims(member))
				.setExpiration(createExpireDateForOneYear(expireTime))
				.signWith(SignatureAlgorithm.HS256, createSigningKey(key));
		return builder.compact();
	}
	
	private static Map<String, Object> createHeader(){
		System.out.println("<CreateHeader>");
		Map<String, Object> header = new HashMap<>();
		
		header.put("tpy", "JWT");
		header.put("alg", "HS256");
		header.put("regDate", System.currentTimeMillis());
		System.out.println(": "+header);
		return header;
	}
	
	private static Map<String, Object> createClaims(CustomUserDetails member){
		System.out.println("<CreateClaims?");
		Map<String, Object> claims = new HashMap<>();
		claims.put("email", member.getUsername());
		claims.put("password", member.getPassword());
		System.out.println(": "+claims);
		return claims;
	}
	
	private static Date createExpireDateForOneYear(Long expireTime) {
		System.out.println("<createExpireDateForOneYear>");
		Date date = new Date(System.currentTimeMillis() + expireTime);
		System.out.println("만료시간 : "+ date.getTime());
		return date;
	}
	
	private static Key createSigningKey(String key) {
		System.out.println("<createSigningKey>");
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
		System.out.println(": "+apiKeySecretBytes);
		return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
	}
	
	public static boolean isAccessVaildToken(String token) {
		return doVaildToken(token, SECRECT_KEY);
	}
	
	public static boolean isRefreshVaildToken(String token) {
		return doVaildToken(token, REFRESH_KEY);
	}
	
	public static boolean doVaildToken(String token, String key) {
		try {
			Claims claims = getClaimsFromToken(token, key);
			System.out.println(token);
			System.out.println(claims);
			System.out.println("expireTime :" + claims.getExpiration());
			System.out.println("email : " + claims.get("userId"));
			System.out.println("role : " + claims.get("role"));
			return true;
		}catch(ExpiredJwtException e) {
			System.out.println("Token Expired :" + e);
			return false;
		}catch(JwtException e) {
			System.out.println("Token Tampered :" + e);
			return false;
		}
		catch(NullPointerException e) {
			System.out.println("Token null :" + e);
			return false;
		}
	}
	
	private static Claims getClaimsFromToken(String token, String key) {
		return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(key)) 
				.parseClaimsJws(token).getBody();
	}
	
	public static String getUserEmailFromAccessToken(String token) {
		return getUserEamilFromToken(token, SECRECT_KEY);
	}
	
	public static String getUserEmailFromRefreshToken(String token) {
		return getUserEamilFromToken(token, REFRESH_KEY);
	}
	
	private static String getUserEamilFromToken(String token, String key) {
		Claims claims = getClaimsFromToken(token, key);
		return (String)claims.get("email");
	}
	
	public static String getTokenFromHeader(String header) {
		return header.split(" ")[1];
	}
	
	public static String getRoleFromeAccessToken(String token) {
		return getRoleFromeToken(token, SECRECT_KEY);
	}
	
	public static String getRoleFromeRefreshToken(String token) {
		return getRoleFromeToken(token, REFRESH_KEY);
	}
	
	private static String getRoleFromeToken(String token, String key) {
		Claims claims = getClaimsFromToken(token, key);
		return (String)claims.get("role");
	}
	
	public Long getValidationAccessTokenTime() {
		return ACCESS_TOKEN_VALIDATION_SECOND;
	}
	
	public static Map<String, Object> verifyJWT(String jwt) throws UnsupportedEncodingException{
		Map<String, Object> claimMap = null;
		try {
			Claims claims = Jwts.parser()
					.setSigningKey(SECRECT_KEY)
					.parseClaimsJws(jwt)
					.getBody();
			claimMap = claims;
			System.out.println("토큰 검증 : "+ claimMap);
		}catch(ExpiredJwtException e) {
			System.out.println("토큰 만료 : "+e);
		}catch(Exception e) {
			System.out.println("에러 : "+ e);
		}
		return claimMap;
	}
}
