package com.cg.ppmtoolapi.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.ppmtoolapi.domain.Backlog;
import com.cg.ppmtoolapi.domain.ProjectTask;
import com.cg.ppmtoolapi.exception.ProjectNotFoundException;
import com.cg.ppmtoolapi.repository.BacklogRepository;
import com.cg.ppmtoolapi.repository.ProjectTaskRepository;
import com.cg.ppmtoolapi.service.ProjectTaskService;

@Service
public class ProjectTaskServiceImpl implements ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	@Override
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		try {
			Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
			projectTask.setBacklog(backlog);
			Integer projectTaskSequence = backlog.getProjectTaskSequence();
			projectTaskSequence++;
			backlog.setProjectTaskSequence(projectTaskSequence);
			projectTask.setProjectIdentifier(projectIdentifier.toUpperCase());
			projectTask.setProjectSequence(projectIdentifier.toUpperCase() + "-" + projectTaskSequence);
			if (projectTask.getPriority() == null)
				projectTask.setPriority(ProjectTaskService.PRIORITY_LOW);
			if (projectTask.getStatus() == null || projectTask.getStatus() == "")
				projectTask.setStatus(ProjectTaskService.TO_DO);
			return projectTaskRepository.save(projectTask);
		} catch (Exception ex) {
			throw new ProjectNotFoundException("Project Not Found.");
		}
	}

	@Override
	public ProjectTask findProjectTaskByProjectSequence(String projectIdentifier, String sequence) {
		Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
		if (backlog == null)
			throw new ProjectNotFoundException("Project id: " + projectIdentifier + " does not exists.");
		ProjectTask projectTask = projectTaskRepository.findProjectTaskByProjectSequence(sequence.toUpperCase());
		if (projectTask == null)
			throw new ProjectNotFoundException("ProjectTask id: " + sequence + " does not exists.");
		if (!projectTask.getProjectIdentifier().equals(projectIdentifier))
			throw new ProjectNotFoundException(
					"ProjectTask id: " + sequence + " not found for Project id: " + projectIdentifier);
		return projectTask;
	}

	@Override
	public ProjectTask updateProjectTaskByProjectSequence(ProjectTask updateProjectTask, String backlog_id,
			String sequence) {
		ProjectTask projectTask = findProjectTaskByProjectSequence(backlog_id, sequence);
		projectTask = updateProjectTask;
		return projectTaskRepository.save(projectTask);
	}

	@Override
	public void deleteProjectTaskByProjectSequence(String backlog_id, String sequence) {
		ProjectTask projectTask = findProjectTaskByProjectSequence(backlog_id, sequence);
		Backlog backlog = projectTask.getBacklog();
		List<ProjectTask> projectTasks = backlog.getProjectTasks();
		projectTasks.remove(projectTask);
		backlogRepository.save(backlog);
		projectTaskRepository.delete(projectTask);
	}

}
