package com.zerobase.fastlms.datemember.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zerobase.fastlms.datemember.entity.DateMember;

public interface DateMemberRepository extends JpaRepository<DateMember, Long>{

	void deleteAllByCalendarId(Long calendarId);
}
