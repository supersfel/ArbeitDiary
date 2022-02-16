package com.zerobase.fastlms.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.memberProject.model.UserListInterface;

@Repository
public interface MemberRepository extends JpaRepository<Member, String>{
	Optional<Member> findByEmailAuthKey(String emailAuthKey);
	Optional<Member> findByUserIdAndUserName(String userId, String userName);
	Optional<Member> findByResetPasswordKey(String uuid);
	UserListInterface findByUserId(String userId);

    List<Member> findAllByUserNameAndPhone(String userName, String userPhone);
}
