package com.scrap.quartz.service;

import com.scrap.quartz.dto.ScheduleEmailRequest;
import com.scrap.quartz.dto.ScheduleEmailResponse;

public interface JobService {

	ScheduleEmailResponse createJob(String group, ScheduleEmailRequest request);
	
	ScheduleEmailResponse findJob(String group, String name);
	
	ScheduleEmailResponse updateJob(String group, String name, ScheduleEmailRequest request);
	
	ScheduleEmailResponse deleteJob(String group, String name);
	
	ScheduleEmailResponse pauseJob(String group, String name);
	
	ScheduleEmailResponse resumeJob(String group, String name);
	
	void stopAllJobs();
	
	void clear();
	
	void resume();
}
