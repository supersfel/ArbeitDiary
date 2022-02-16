package com.zerobase.fastlms.member.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.dto.MemberLoginDto;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.admin.model.MemberStatusInput;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.member.model.CustomUserDetails;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.ResetPasswordInput;

public interface MemberService extends UserDetailsService{
	boolean register(MemberInput parameter);
	
	boolean emailAuth(String uuid);
	
	boolean sendResetPassword(String userId, String userName);
	
	boolean resetPassword(String id, String password);
	
	boolean checkResetPasswordKey(String uuid);
	
	List<MemberDto> list(MemberParam memberParm);
	
	MemberDto detail(String userId);

	boolean updateStatus(String userId, String userStatus);

	boolean adminSendPasswordEmail(String userId);

	CustomUserDetails apiUserDetail(String userId);
	
	MemberLoginDto getloginToken(CustomUserDetails principal);
	
	ServiceResult updateMemberPassword(MemberInput memberInput);
}
