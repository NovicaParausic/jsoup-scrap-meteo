package com.scrap.quartz.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.scrap.quartz.dto.ScheduleEmailRequest;
import com.scrap.quartz.dto.ScheduleEmailResponse;
import com.scrap.quartz.service.JobService;


@RestController
public class JobScheduleController {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private JobService jobService;
	
	@PostMapping(path = "/api/createJobFrom/{group}")
	public ResponseEntity<ScheduleEmailResponse> createJob(@PathVariable String group, 
															@Valid @RequestBody ScheduleEmailRequest request) {
		
		log.info(group);
		
		ScheduleEmailResponse response = jobService.createJob(group, request);
		
		return fixedResponse(response);
	}
	
	@GetMapping(path = "/api/find/{name}/From/{group}")
	public ResponseEntity<ScheduleEmailResponse> findJob(@PathVariable String group, 
															@PathVariable String name) {
		ScheduleEmailResponse response = jobService.findJob(group, name);
		
		return fixedResponse(response);
	}
	
	@PutMapping(path = "/api/update/{name}/from/{group}")
	public ResponseEntity<ScheduleEmailResponse> updateJob(@PathVariable String group, 
															@PathVariable String name, 
															 @Valid @RequestBody ScheduleEmailRequest request) {
		ScheduleEmailResponse response = jobService.updateJob(group, name, request);
		
		return fixedResponse(response);
	}
	
	@DeleteMapping(path = "/api/delete/{name}/from/{group}")
	public ResponseEntity<ScheduleEmailResponse> deleteJob(@PathVariable String group, 
															@PathVariable String name) { 
		ScheduleEmailResponse response = jobService.deleteJob(group, name);
		
		return fixedResponse(response);
	}
	
	@PatchMapping(path = "/api/pause/{name}/from/{group}")
	public ResponseEntity<ScheduleEmailResponse> pauseJob(@PathVariable String group, 
															@PathVariable String name) { 
		ScheduleEmailResponse response = jobService.pauseJob(group, name);
		
		return fixedResponse(response);
	}
	
	@PatchMapping(path = "/api/resume/{name}/from/{group}")
	public ResponseEntity<ScheduleEmailResponse> resumeJob(@PathVariable String group, 
															@PathVariable String name) { 
		ScheduleEmailResponse response = jobService.resumeJob(group, name);
		
		return fixedResponse(response);
		/*
		if (response.isSuccess()) {
			return ResponseEntity.badRequest().body(response);
		} else {
			return ResponseEntity.ok().body(response);
		}*/
	}
	
	@PatchMapping(path = "/api/pauseAll")
	public ResponseEntity<String> pauseAll() { 
		jobService.stopAllJobs();
		
		return ResponseEntity.ok().build();
	}
	
	@PatchMapping(path = "/api/clear")
	public ResponseEntity<String> clear() { 
		jobService.clear();
		
		return ResponseEntity.ok().build();
	}
	
	@PatchMapping(path = "/api/resume")
	public ResponseEntity<String> resume() { 
		jobService.resume();
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(path = "/api/dateTime")
	public ResponseEntity<LocalDateTime> dateTime() {
		LocalDateTime date = LocalDateTime.now();
			return ResponseEntity.ok(date);
		
	}
	
	@PostMapping(path = "/api/zone")
	public ResponseEntity<String> zone() {
		String date = ZoneId.systemDefault().getId();
			return ResponseEntity.ok(date);
		
	}
	
	private ResponseEntity<ScheduleEmailResponse> fixedResponse(ScheduleEmailResponse response) {
		if (!response.isSuccess()) {
			return ResponseEntity.badRequest().body(response);
		} else {
			return ResponseEntity.ok().body(response);
		}
	}
}
