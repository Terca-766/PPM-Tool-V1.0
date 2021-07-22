package com.cg.ppmtoolapi.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.ppmtoolapi.domain.Project;
import com.cg.ppmtoolapi.exception.ProjectIdException;
import com.cg.ppmtoolapi.service.ProjectService;
import com.cg.ppmtoolapi.serviceimpl.MapValidationErrorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/project")
@Api(value = "Project", description = "REST Api for Projects", tags = {"Project"})
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@ApiOperation(value = "Create New Project", notes = "Create a New Project in Project Portfolio Managment")
	@PostMapping
	public ResponseEntity<?> createProject(@Valid @RequestBody Project project, BindingResult result) {
		ResponseEntity<Map<String, String>> errorMap = mapValidationErrorService.mapValidationError(result);
		if (errorMap != null)
			return errorMap;
		Project createdProject = projectService.saveOrUpdate(project);
		return new ResponseEntity<Project>(createdProject, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Fetch Single Project", notes = "Fetch a single project based on Project Identifier from Project Portfolio Managment")
	@GetMapping("/{projectIdentifier}")
	public ResponseEntity<Project> getProjectByProjectIdentifier(@PathVariable String projectIdentifier) {
		Project project = projectService.findProjectByProjectIdentifier(projectIdentifier.toUpperCase());
		if (project == null)
			throw new ProjectIdException("Project Id: " + projectIdentifier.toUpperCase() + " does not exist.");
		return new ResponseEntity<Project>(project, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Fetch All Projects", notes = "Fetch all projects from Project Portfolio Managment")
	@GetMapping("/all")
	public Iterable<Project> getAllProjects(){
		return projectService.findAllProjects();
	}
	
	@ApiOperation(value = "Delete Single Project", notes = "Delete/Remove a single project based on Project Identifier from Project Portfolio Managment")
	@DeleteMapping("/{projectIdentifier}")
	public ResponseEntity<String> deleteProject(@PathVariable String projectIdentifier) {
		projectService.deleteProjectByProjectIdentifier(projectIdentifier);
		return new ResponseEntity<String> ("Project Id: " + projectIdentifier + " deleted successfully.", HttpStatus.OK);
	}
}
