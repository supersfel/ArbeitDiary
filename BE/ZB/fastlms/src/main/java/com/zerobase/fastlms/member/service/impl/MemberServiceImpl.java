package com.zerobase.fastlms.member.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.dto.MemberLoginDto;
import com.zerobase.fastlms.admin.mapper.MemberMapper;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.calendar.model.enuum.DayEnum;
import com.zerobase.fastlms.component.MailComponent;
import com.zerobase.fastlms.configuration.token.TokenUtils;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.entity.Work;
import com.zerobase.fastlms.member.exception.MemberNotEmailAllthException;
import com.zerobase.fastlms.member.exception.MemberStopUserException;
import com.zerobase.fastlms.member.model.CustomUserDetails;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.ResetPasswordInput;
import com.zerobase.fastlms.member.repository.MemberRepository;
import com.zerobase.fastlms.member.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService{
	private final MemberRepository memberRepository;
	private final MailComponent mailComponents;
	private final MemberMapper memberMapper;
	
	
	@Autowired
	public MemberServiceImpl(MemberRepository memberRepository, MailComponent mailComponents, MemberMapper memberMapper) {
		this.memberRepository = memberRepository;
		this.mailComponents = mailComponents;
		this.memberMapper = memberMapper;
	}

	
	@Override
	public boolean register(MemberInput parameter) {
		Optional<Member> optionalMember = memberRepository.findById(parameter.getUserId());
		System.out.println("?????? ?????? ?????? ???");
		if(optionalMember.isPresent()) {
			System.out.println("?????????");
			return false;
		}
		
		System.out.println("???????????? ??????");
		String encPassword = BCrypt.hashpw(parameter.getUserPassword(), BCrypt.gensalt());//bCryptPasswordEncoder.encode(parameter.getUserPassword());
		
		String uuid = UUID.randomUUID().toString();
		Member member = Member.builder()
				.userId(parameter.getUserId())
				.userName(parameter.getUserName())
				.password(encPassword)
				.phone(parameter.getUserPhone())
				.regDt(LocalDateTime.now())
				.emailAuthYn(false)
				.emailAuthKey(uuid)
				.userState(Member.MEMBER_STATUS_YET)
				.roles("invalid")
				.refreshToken("invalid")
				.build();		
		
		memberRepository.save(member);
		System.out.println(member.getRoles());
		String email = member.getUserId();
		String subject = "????????? ??????????????????.";
		String text = "<h1>????????? ??????????????????</h1><p>?????? ????????? ???????????? ????????? ???????????????</p>"
				+"<div><a href='http://localhost:3000/done?id="+uuid+"'>?????? ??????</a>????????????</div>";
		mailComponents.sendMail(email, subject, text);
		System.out.println(true);
		return true;
	}
	
	@Override
	public boolean emailAuth(String uuid) {
		Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);
		if(!optionalMember.isPresent()) {
			return false;
		}
		
		Member member = optionalMember.get();
		System.out.println("optionalMember : "+optionalMember.get());
		member.setEmailAuthYn(true);
		member.setEmailAuthDt(LocalDateTime.now());
		member.setUserState(Member.MEMBER_STATUS_ING);
		System.out.println("<member>:"+member);
		memberRepository.save(member);
		
		if(!member.isEmailAuthYn()) {
			return false;
		}
		return true;
	}

	@Transactional
	@Override
	public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("=========== ????????? ?????? ===========");
		Optional<Member> optionalMember = memberRepository.findById(username);
		if(!optionalMember.isPresent()) {
			System.out.println("loadUserByUserName - isNotParent");
			throw new UsernameNotFoundException("?????? ????????? ???????????? ????????????.");
		}
		Member member = optionalMember.get();
		
		System.out.println("????????? ??????_ ???????????? ??? : " + member);
		
		if(Member.MEMBER_STATUS_YET.equals(member.getUserState())) {
			String uuid = member.getEmailAuthKey();
			String email = member.getUserId();
			String subject = "???????????? ?????? ???????????????.";
			String text = "<h1>????????? ?????????????????????.</h1><p>?????? ????????? ???????????? ????????? ???????????????</p>"
					+"<div><a href='http://localhost:8080/member/email-auth?id="+uuid+"'>?????? ??????</a>????????????</div>";
			mailComponents.sendMail(email, subject, text);
			throw new MemberNotEmailAllthException("????????? ????????? ?????? ????????? ????????????.");
		}
		
		if(Member.MEMBER_STATUS_BLACK.equals(member.getUserState())) {
			throw new MemberStopUserException("????????? ?????? ?????????.");
		}
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		if(member.isAdminYn()) {
			System.out.println("=====?????????=====");
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		member.setRole(grantedAuthorities);
		memberRepository.save(member);
		System.out.println(member);
		return new CustomUserDetails(member, grantedAuthorities);
	}

	@Override
	public boolean sendResetPassword(String userId, String userName) {
		Optional<Member> optionalMember = memberRepository.findByUserIdAndUserName(userId, userName);
		if(!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("?????? ????????? ???????????? ????????????.");
		}
		
		return sendEmailData(optionalMember);
	}
	
	private boolean sendEmailData(Optional<Member> optionalMember) {
		String uuid = UUID.randomUUID().toString();
		Member member = optionalMember.get();
		
		member.setResetPasswordKey(uuid);
		member.setResetPasswordDt(LocalDateTime.now().plusDays(1));
		memberRepository.save(member);
		
		String email = member.getUserId();
		String subject = "???????????? ?????? ???????????????.";
		String text = "<h1>???????????? ?????? ???????????????.</h1><p>?????? ????????? ???????????? ??????????????? ???????????????</p>"
				+"<div><a href='http://localhost:3000/member/reset/password?id="+uuid+"'>???????????? ??????</a>????????????</div>";
		mailComponents.sendMail(email, subject, text);
		return true;
	}
	
	@Override
	public boolean resetPassword(String id, String password) {
		Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(id);
		if(!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("????????? ?????? ?????????.");
		}
		
		Member member = optionalMember.get();
		
		if(member.getResetPasswordKey() == null) {
			throw new RuntimeException("????????? ????????? ????????????.");
		}
		if(member.getResetPasswordDt().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("?????? ????????? ???????????????.");
		}
		String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		member.setPassword(encPassword);
		member.setResetPasswordKey("");
		member.setResetPasswordDt(null);
		memberRepository.save(member);
		System.out.println("[????????? ?????? ??????]");
		return true;
	}
	
	@Override
	public boolean checkResetPasswordKey(String uuid) {
		Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);
		if(!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("check????????? ?????? ?????????.");
		}
		
		Member member = optionalMember.get();
		if(member.getResetPasswordKey() == null) {
			throw new RuntimeException("????????? ????????? ????????????.");
		}
		if(member.getResetPasswordDt().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("?????? ????????? ???????????????.");
		}
		
		return true;
	}
	
	@Override
	public List<MemberDto> list(MemberParam memberParm){
		List<MemberDto> list =  memberMapper.selectList(memberParm);
		System.out.println(memberParm.getSearchType());
		System.out.println(memberParm.getSearchValue());
		System.out.println("list : "+list);
		long totalCount = memberMapper.selectListCount(memberParm);
		System.out.println("totalCount"+totalCount);
		if(!CollectionUtils.isEmpty(list)) {
			int i=0;
			for(MemberDto x : list) {
				x.setTotalCount(totalCount);
				x.setMemberNum(totalCount - memberParm.getPageStart() - i);
				i++;
				System.out.println(x);
			}
		}
		return list;	
	}
	
	@Override
	public MemberDto detail(String userId) {
		Optional<Member> optionalMember = memberRepository.findById(userId);
		
		if(!optionalMember.isPresent()) {
			return null;
		}
		Member member = optionalMember.get();
		System.out.println(member);
		MemberDto memberDto = MemberDto.of(member);
		System.out.println(memberDto);
		return memberDto;
	}
	
	@Override
	public boolean updateStatus(String userId, String userStatus) {
		Optional<Member> optionalMember = memberRepository.findById(userId);
		System.out.println(userId+":"+userStatus);
		if(!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("?????? ?????? ???????????? ????????????.");
		}
		Member member = optionalMember.get();
		member.setUserState(userStatus);
		memberRepository.save(member);
		
		return true;
	}
	
	@Override
	public boolean adminSendPasswordEmail(String userId) {
		Optional<Member> optionalMember = memberRepository.findById(userId);
	
		if(!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("?????? ?????? ???????????? ????????????.");
		}
		
		return sendEmailData(optionalMember);
	}

	@Override
	public CustomUserDetails apiUserDetail(String userId) {
		Optional<Member> optionalMember = memberRepository.findById(userId);
		
		if(!optionalMember.isPresent()) {
			return null;
		}
		Member member = optionalMember.get();
		System.out.println("member :" +member);
		CustomUserDetails detail = new CustomUserDetails(member,member.getRoleList());
		System.out.println(detail);
		return detail;
	}
	
	@Override
	public MemberLoginDto getloginToken(CustomUserDetails principal) {
		System.out.println("==============SUCCESS=============");
		System.out.println("=============JWT?????? ??????=========");
		System.out.println("success:"+ principal);
		CustomUserDetails user = principal;
		String accessToken = TokenUtils.generateAccessToken(user);		
		user.setMemberRefreshToken(TokenUtils.generateRefreshToken(user));
		System.out.println("length:"+user.getMemberRefreshToken().length());
		memberRepository.save(user.getMember());
		
		return new MemberLoginDto(principal.getUsername(), accessToken, user.getMemberRefreshToken());
	}

	@Override
	public ServiceResult updateMemberPassword(MemberInput memberInput) {
		// TODO Auto-generated method stub
		Optional<Member> optionalMember = memberRepository.findById(memberInput.getUserId());
		
		if(!optionalMember.isPresent()) {
			return new ServiceResult(false, "?????? X, ????????? ??????");
		}
		
		Member member = optionalMember.get();
		
		if(!BCrypt.checkpw(memberInput.getUserPassword(), member.getPassword())) {
			return new ServiceResult(false, "?????? ???????????? ???????????? ????????????.");
		}
		
		String encPassword = BCrypt.hashpw(memberInput.getNewPassword(), BCrypt.gensalt());
		member.setPassword(encPassword);
		memberRepository.save(member);
		
		return new ServiceResult(true);
	}
	
	@Override
	public List<String> getUserId(String userPhone, String userName) {
		List<Member> memberList = memberRepository.findAllByUserNameAndPhone(userName, userPhone);
		
		List<String> idList = new ArrayList<String>();
		for(Member member: memberList) {
			idList.add(member.getUserId());
		}
		return idList;
	}
	
}
