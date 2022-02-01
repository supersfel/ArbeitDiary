package com.zerobase.fastlms.member.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MemberInput {
	private String userId;
	private String userName;
	private String userPassword;
	private String userPhone;
}
