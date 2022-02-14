package com.zerobase.fastlms.member.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zerobase.fastlms.calendar.model.CalendarUserList;
import com.zerobase.fastlms.calendar.model.FixedTimeInterface;
import com.zerobase.fastlms.member.entity.Work;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long>{
	//Optional<Work> findByUserIdAndWorkDay(String userId, String day);
	@Query("SELECT w FROM Work w WHERE w.member.userId = :userId AND w.projectId = :projectId AND w.workDay = :day AND w.workDay = :day AND w.workTimeActive = :pa")
	Optional<Work> findByUserIdAndProjectIdAndDayAndPa(@Param("userId") String userId, @Param("projectId")Long projectId, @Param("day")String day, @Param("pa")String pa);

	@Query("SELECT w FROM Work w WHERE w.member.userId = :userId AND w.projectId = :projectId AND w.workDay = :dayId")
	Optional<Work> findByUserIdAndProjectIdAndWorkDay(@Param("userId") String userId, @Param("projectId") Long projectId, @Param("dayId") String dayId);
	
	<T> T findByProjectId(Long projectId, String userId, Class<T> type);

	
	List<FixedTimeInterface> findByProjectIdAndMember_UserId(Long projectId, String userId);
}
