package com.zerobase.fastlms.member.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginInput {
	private String userId;
	private String userPassword;
}
