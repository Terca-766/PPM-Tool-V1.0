package com.cg.ppmtoolapi.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.ppmtoolapi.domain.ProjectTask;
import com.cg.ppmtoolapi.service.ProjectTaskService;
import com.cg.ppmtoolapi.serviceimpl.MapValidationErrorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/backlog")
@Api(value = "Project Task", description = "REST Api for Project Task", tags = {"Project Task"})
public class BacklogController {

	@Autowired
	private ProjectTaskService projectTaskService;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@ApiOperation(value = "Create New Project Task", notes = "Requires Project Identifier for creating a Project Task")
	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> addPTToBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
			@PathVariable String backlog_id) {
		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationError(result);
		if (errorMap != null)
			return errorMap;
		ProjectTask createdProjectTask = projectTaskService.addProjectTask(backlog_id, projectTask);
		return new ResponseEntity<ProjectTask>(createdProjectTask, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Fetch Single Project Task", notes = "Requires Project Identifier and Project Task Sequence to fetch a single Project Task")
	@GetMapping("/{backlog_id}/{sequence}")
	public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String sequence) {
		ProjectTask projectTask = projectTaskService.findProjectTaskByProjectSequence(backlog_id, sequence);
		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Update Single Project Task", notes = "Requires Project Identifier and Project Task Sequence for updating a Project Task")
	@PatchMapping("/{backlog_id}/{sequence}")
	public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask,
			@PathVariable String backlog_id, @PathVariable String sequence, BindingResult result) {
		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationError(result);
		if (errorMap != null)
			return errorMap;
		ProjectTask updateProjectTask = projectTaskService.updateProjectTaskByProjectSequence(projectTask, backlog_id,
				sequence);
		return new ResponseEntity<ProjectTask>(updateProjectTask, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Delete Single Project Task", notes = "Requires Project Identifier and Project Task Sequence for deleting a Project Task")
	@DeleteMapping("/{backlog_id}/{sequence}")
	public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String sequence) {
		projectTaskService.deleteProjectTaskByProjectSequence(backlog_id, sequence);
		return new ResponseEntity<String>("ProjectTask Id: " + sequence + " delete successfully.", HttpStatus.OK);
	}
}
