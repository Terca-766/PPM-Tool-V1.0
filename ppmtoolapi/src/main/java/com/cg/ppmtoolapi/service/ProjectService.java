package com.cg.ppmtoolapi.service;

import com.cg.ppmtoolapi.domain.Project;

public interface ProjectService {
	
	public Project saveOrUpdate(Project project);
	public Project findProjectByProjectIdentifier(String projectIdentifier);
	public Iterable<Project> findAllProjects();
	public void deleteProjectByProjectIdentifier(String projectIdentifier);
}
