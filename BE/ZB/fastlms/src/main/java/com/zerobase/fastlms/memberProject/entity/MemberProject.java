package com.zerobase.fastlms.memberProject.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.memberProject.model.ProjectRoleType;
import com.zerobase.fastlms.project.entity.Project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@IdClass(MemberProjectId.class) // 복합키
@Table(name="member_project")
@Entity
public class MemberProject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Enumerated(EnumType.STRING)
	ProjectRoleType projectRole;
	
	LocalDateTime regDt;
	
	//@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	@JoinColumn(name = "user_id")
	Member member;

//	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	//@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	@JoinColumn(name = "project_id")
	Project project;
	
}

