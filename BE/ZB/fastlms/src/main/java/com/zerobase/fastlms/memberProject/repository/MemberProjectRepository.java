package com.zerobase.fastlms.memberProject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zerobase.fastlms.memberProject.entity.MemberProject;
import com.zerobase.fastlms.memberProject.model.UserListDto;
import com.zerobase.fastlms.memberProject.model.UserListInterface;
import com.zerobase.fastlms.project.entity.Project;

@Repository
public interface MemberProjectRepository extends JpaRepository<MemberProject, Long>{
	@Query("SELECT mp FROM MemberProject mp WHERE mp.member.userId = :userId")
	List<MemberProject> findAllByMemberId(@Param("userId") String userId);
	
	@Query("SELECT mp FROM MemberProject mp WHERE mp.project.id = :projectId AND mp.member.userId = :userId")
	Optional<MemberProject> findByProjectIdAndMemberId(@Param("projectId") Long projectId, @Param("userId")String userId);
	
	/*
	 * SELECT COUNT(*) FROM member_project mp WHERE mp.project_id = 4
	 */
	Long countByProjectId(Long projectId);
	/*
	@Query("SELECT mp, m.userName, m.phone FROM MemberProject mp INNER JOIN Member m ON mp.member.userId = m.userId WHERE mp.project.id = :projectId")
	List<UserListDto> findByProjectId(@Param("projectId") Long projectId);
	*/
	
	//List<MemberProject> findByProject(Project project); 
//	@Query("SELECT mp, "
//			+ "m.userName, "
//			+ "m.phone "
//			+ "FROM MemberProject mp "
//			+ "INNER JOIN Member m ON mp.member.userId = m.userId "
//			+ "WHERE mp.project.id = :projectId")
//	@Query("SELECT mp FROM MemberProject mp WHERE mp.project.id = :projectId")
//	List<UserListInterface> findByProjectId(@Param("projectId")Long projectId);
}
