package com.zerobase.fastlms.calendar.entity;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DateId implements Serializable{
	@Column(name = "year")
	Integer year;
	@Column(name = "month")
	Integer month;
	@Column(name = "day")
	Integer day;
	
}
