package com.zerobase.fastlms.admin.dto;

import java.time.LocalDateTime;

import com.zerobase.fastlms.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MemberLoginDto {
	String userId;
	
	String accessToken;
	String refreshToken;
}
