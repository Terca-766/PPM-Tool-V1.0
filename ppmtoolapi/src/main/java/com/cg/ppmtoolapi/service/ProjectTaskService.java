package com.cg.ppmtoolapi.service;

import com.cg.ppmtoolapi.domain.ProjectTask;

public interface ProjectTaskService {

	public static final Integer PRIORITY_HIGH = 1;
	public static final Integer PRIORITY_MEDIUM = 2;	
	public static final Integer PRIORITY_LOW = 3;
	
	public static final String TO_DO = "TO_DO";
	public static final String IN_PROGRESS = "IN_PROGRESS";
	public static final String DONE = "DONE";
	
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask);
	ProjectTask findProjectTaskByProjectSequence(String projectIdentifier, String sequence);
	public ProjectTask updateProjectTaskByProjectSequence(ProjectTask updateProjectTask, String backlog_id, String sequence);
	public void deleteProjectTaskByProjectSequence(String backlog_id, String sequence);
}
