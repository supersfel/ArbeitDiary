package com.zerobase.fastlms.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ApiErrorController {
	@GetMapping("/api/unauthorized")
	public ResponseEntity<?> unauthorized(){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
}
