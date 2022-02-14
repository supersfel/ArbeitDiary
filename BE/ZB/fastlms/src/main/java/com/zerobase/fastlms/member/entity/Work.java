package com.zerobase.fastlms.member.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "work")
public class Work {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "work_id")
	Long workId;
	
	String workDay;
	@Column(length = 48)
	String workTime;
	String workTimeActive;
	
	Long projectId;
	
	
	@ManyToOne
	@JoinColumn(name = "userId")
	Member member;
}
