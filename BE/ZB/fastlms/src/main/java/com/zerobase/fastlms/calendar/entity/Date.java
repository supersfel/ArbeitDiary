package com.zerobase.fastlms.calendar.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zerobase.fastlms.comment.entity.Comment;
import com.zerobase.fastlms.datemember.entity.DateMember;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
//@IdClass(DateId.class)
@Table(name = "date")
@Entity
public class Date {
	@Id
	@Column(name = "date_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long dateId;
	
	Integer year;
	Integer month;
	Integer day;
	String dayOfWeek;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "calendar_id")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "calendarId") 
	@JsonIdentityReference(alwaysAsId = true)
	Calendar calendar;
	
	@OneToMany(mappedBy = "date")
	List<Comment> comments = new ArrayList<>();
	
	@OneToMany(mappedBy = "date")
	List<DateMember> dateMember = new ArrayList<>();
}
