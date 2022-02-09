package com.zerobase.fastlms.project.service.impl;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler.Builder;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.model.MemberResponseDto;
import com.zerobase.fastlms.member.repository.MemberRepository;
import com.zerobase.fastlms.memberProject.dto.UserResponseDto;
import com.zerobase.fastlms.memberProject.dto.sampleDto;
import com.zerobase.fastlms.memberProject.entity.MemberProject;
import com.zerobase.fastlms.memberProject.model.ProjectRoleType;
import com.zerobase.fastlms.memberProject.model.UserListDto;
import com.zerobase.fastlms.memberProject.model.UserListInterface;
import com.zerobase.fastlms.memberProject.repository.MemberProjectRepository;
import com.zerobase.fastlms.project.dto.ProjectDto;
import com.zerobase.fastlms.project.entity.Project;
import com.zerobase.fastlms.project.mapper.ProjeactMapper;
import com.zerobase.fastlms.project.model.ProjectInput;
import com.zerobase.fastlms.project.model.ProjectListInterface;
import com.zerobase.fastlms.project.model.ProjectParam;
import com.zerobase.fastlms.project.repository.ProjectRepository;
import com.zerobase.fastlms.project.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService{
	private final ProjectRepository projectRepository;
	private final MemberProjectRepository memberProjectRepository;
	private final ProjeactMapper projectMapper;
	private final MemberRepository memberRepository;
	
	//@Transactional
	@Override
	public boolean add(String userId, ProjectInput projectInput) {
		// TODO Auto-generated method stub
		System.out.println("===============================================================================");
		System.out.println(projectInput);
		Project project = Project.builder()
				.projectName(projectInput.getProjectName())
				.regDt(LocalDateTime.now())
				.build();
		System.out.println("[Entity Build] : "+ project);
		
		Optional<Member> optionalMember = memberRepository.findById(userId);
		if(!optionalMember.isPresent()) {
			System.out.println("[회원 정보 오류]");
			return false;
		}
		Member member = optionalMember.get();
		System.out.println(member);
		MemberProject memberProject = MemberProject.builder()
				.member(member)
				.project(project)
				.projectRole(ProjectRoleType.MASTER)
				.regDt(LocalDateTime.now())
				.build();
		projectRepository.save(project);
		memberProjectRepository.save(memberProject);
		
		return true;
	}

	@Override
	public List<ProjectDto> list(ProjectParam projectParam) {
		long totalCount = projectMapper.selectListCount(projectParam);
		System.out.println("hi");
		List<ProjectDto> list = projectMapper.selectList(projectParam);
		System.out.println("[list] : "+list);
		if(!CollectionUtils.isEmpty(list)) {
			int i=0;
			for(ProjectDto x : list) {
				x.setTotalCount(totalCount);
				x.setProjectNum(totalCount - projectParam.getPageStart() - i);
				i++;
			}
			return list;
		}
		return null;
	}

	@Override
	public List<sampleDto> showOldProject(String userId) {
		// TODO Auto-generated method stub
		List<MemberProject> list = memberProjectRepository.findAllByMemberId(userId);
		return sampleDto.of(list);
	}
	
	/*
	 * 
	 */
	@Override
	public String responseOldProject(String userId){
		//Optional<MemberProject> mp = memberProjectRepository.findById(7L);
		//System.out.println("Proejct : "+mp.get().getProject());
		//Project project = mp.get().getProject();
		UserListInterface ui = memberRepository.findByUserId(userId);
		//List<MemberProject> ul = memberProjectRepository.findByProject(project);
		System.out.println("???");
		//Optional<Project> optionalProject = projectRepository.findById(4L);
		List<ProjectListInterface> projectUserList = getProjectUserList(ui);
		//System.out.println(optionalProject.get().getMembers().get(0).getId());
		UserResponseDto userResponse =  UserResponseDto.of(ui, projectUserList);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = "";
		try {
			json = ow.writeValueAsString(userResponse);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	public List<ProjectListInterface> getProjectUserList(UserListInterface ui){
		List<ProjectListInterface> projectUserList = new ArrayList<>();
		System.out.println("PUL");
		for(UserListInterface.projects list : ui.getProjects()) {
			projectUserList.add(getProjectUser(list.getProject().getId()));  
		}
		return projectUserList;
	}
	
	public ProjectListInterface getProjectUser(Long id){
		System.out.println("PU");
		ProjectListInterface projectListInterface = projectRepository.findById(id,ProjectListInterface.class);
		System.out.println("[ID] : "+id);
		System.out.println(projectListInterface);
		return projectListInterface;
	}
	

	@Override
	public boolean join(String userId, Long projectId) {
		// TODO Auto-generated method stub
		Optional<Member> optionalMember = memberRepository.findById(userId);
		if(!optionalMember.isPresent()) {
			System.out.println("[JOIN] : 회원정보 오류");
			return false;
		}
		
		Optional<Project> optionalProject = projectRepository.findById(projectId);
		if(!optionalProject.isPresent()) {
			System.out.println("[JOIN] : 프로젝트 오류");
			return false;
		}
		
		Optional<MemberProject> optionalMP = memberProjectRepository.findByProjectIdAndMemberId(projectId, userId);
		System.out.println("optionalMP : "+optionalMP);
		if(optionalMP.isPresent()) {
			System.out.println("이미 존재");
			return false;
		}
		Member member = optionalMember.get();
		MemberProject memberProject = MemberProject.builder()
				.member(member)
				.project(optionalProject.get())
				.projectRole(ProjectRoleType.USER)
				.regDt(LocalDateTime.now())
				.build();
		memberProjectRepository.save(memberProject);
		
		return true;
	}

	@Override
	public boolean out(String userId, ProjectInput projectInput) {
		// TODO Auto-generated method stub
		Optional<MemberProject> optionalMemberProject = memberProjectRepository.findById(projectInput.getJoinId());
		
		if(!optionalMemberProject.isPresent()) {
			System.out.println("[OUT] : 참여하시지 않았습니다.");
			return false;
		}
		
		MemberProject memberProject = optionalMemberProject.get();
		
		System.out.println(memberProject.getProject().getId());
		if(memberProject.getProjectRole().equals(ProjectRoleType.MASTER) ) {
			System.out.println("IM A MASTER");
			Long count = memberProjectRepository.countByProjectId(memberProject.getProject().getId());
			if(!projectInput.getTargetId().equals(userId)){
				System.out.println("MASTER-DELETE-TARGET");
				Optional<MemberProject> optionalTargetProject = memberProjectRepository.findByProjectIdAndMemberId(memberProject.getProject().getId(),projectInput.getTargetId());
				System.out.println(memberProject.getProject().getId()+":"+ projectInput.getTargetId());
				if(!optionalTargetProject.isPresent()){
					System.out.println("타깃 회원 정보 존재 X");
					return false;
				}
				memberProjectRepository.delete(optionalTargetProject.get());
				return true;
			}
			if(count > 1) {
				System.out.println("[아직 회원이 남아있습니다.]");
				return false;
			}
			else if(count == 1){
				Project project = memberProject.getProject();
				memberProjectRepository.delete(memberProject);
				projectRepository.delete(project);
			}
		}
		
		memberProjectRepository.delete(memberProject);
		
		return true;
	}

}
