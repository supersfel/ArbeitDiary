package com.zerobase.fastlms.calendar.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zerobase.fastlms.calendar.entity.Date;
import com.zerobase.fastlms.calendar.entity.DateId;

public interface DateRepository extends JpaRepository<Date, Long>{
	@Query("SELECT d FROM Date d WHERE d.calendar.calendarId = :calendarId")
	List<Date> findAllByCalendarId(@Param("calendarId") Long calendarId);

	@Query("SELECT d FROM Date d WHERE d.calendar.calendarId = :calendarId AND d.dateId >= :todayId")
	List<Date> findAllByCalendarIdAndDateId(@Param("calendarId") Long calendarId, @Param("todayId") Long todayId);
	
	Optional<Date> findByYearAndMonthAndDay(int year, int month, int day);

	Optional<Date> findByCalendarCalendarIdAndYearAndMonthAndDay(Long calendarId, int i, int j, int k);
	
	

	
}
