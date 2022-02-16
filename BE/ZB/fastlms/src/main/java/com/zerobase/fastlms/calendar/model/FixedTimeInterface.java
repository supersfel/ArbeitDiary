package com.zerobase.fastlms.calendar.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;


public interface FixedTimeInterface {
		@Value("#{target.workDay}")
		String getDayId();
		
		@Value("#{target.workTime}")
		String getWorktime();
}
