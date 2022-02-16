package com.zerobase.fastlms.calendar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zerobase.fastlms.calendar.entity.Calendar;
import com.zerobase.fastlms.calendar.entity.DateId;
import com.zerobase.fastlms.calendar.model.CalendarInterface;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long>{

	boolean existsByProjectId(Long id);

	//Optional<Calendar> findByProjectId(Long projectId);
	
	@Query("SELECT c FROM Calendar c WHERE c.project.id = :projectId")
	CalendarInterface findByProjectId(@Param("projectId") Long pojectId);
}
