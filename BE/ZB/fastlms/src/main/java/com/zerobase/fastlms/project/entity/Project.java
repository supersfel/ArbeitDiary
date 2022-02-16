package com.zerobase.fastlms.project.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zerobase.fastlms.calendar.entity.Calendar;
import com.zerobase.fastlms.memberProject.entity.MemberProject;

import lombok.AllArgsConstructor;
import lombok.Builder;
//import com.zerobase.fastlms.memberProject.MemberProject;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "Project")
@Table(name = "project")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "project_id")
	Long id;
	
	String projectName;
	
	
	LocalDateTime regDt;

//	@JsonManagedReference
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
	        property = "id") 
	@JsonIdentityReference(alwaysAsId = true) //직렬화시 id로만 출력된다
	@JsonIgnore
	@OneToMany(mappedBy = "project",
			fetch = FetchType.EAGER,
			//cascade = CascadeType.ALL,
			orphanRemoval = true)
	List<MemberProject> members = new ArrayList<>();
	
	@OneToOne(mappedBy = "project")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "calendarId") 
	@JsonIdentityReference(alwaysAsId = true)
	Calendar calendar;
	
}

