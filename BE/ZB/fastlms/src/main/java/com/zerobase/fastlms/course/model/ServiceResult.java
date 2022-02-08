package com.zerobase.fastlms.course.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServiceResult {
	public ServiceResult(boolean result, String message) {
		// TODO Auto-generated constructor stub
		this.result = result;
		this.message = message;
	}
	public ServiceResult(boolean result) {
		// TODO Auto-generated constructor stub
		this.result = result;
	}
	boolean result;
	String message;
}
