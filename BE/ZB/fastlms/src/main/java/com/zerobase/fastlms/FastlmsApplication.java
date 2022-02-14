package com.zerobase.fastlms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableScheduling
@SpringBootApplication
public class FastlmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FastlmsApplication.class, args);
	}
}
