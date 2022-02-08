package com.zerobase.fastlms.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zerobase.fastlms.project.entity.Project;
import com.zerobase.fastlms.project.model.ProjectListInterface;

public interface ProjectRepository extends JpaRepository<Project, Long> {
//	@Query("SELECT p "
//			+ "FROM Project p "
//			+ "WHERE p.id = :projectId")
//	ProjectListInterface getById(Long id); 
	<T> T findById(Long id, Class<T> type);
}
