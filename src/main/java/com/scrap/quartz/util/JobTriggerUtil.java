package com.scrap.quartz.util;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.UUID;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.scrap.quartz.dto.ScheduleEmailRequest;
import com.scrap.quartz.job.SimpleJob;

public class JobTriggerUtil {

	public static JobDetail buildJobDetail(ScheduleEmailRequest request) {
		
		JobDataMap jobDataMap = new JobDataMap();
		
		jobDataMap.put("email", request.getEmail());
		jobDataMap.put("subject", request.getSubject());
		jobDataMap.put("body", request.getBody());
		
		return JobBuilder.newJob(SimpleJob.class)
					.withIdentity(UUID.randomUUID().toString(), "email-jobs")
					.withDescription("Send Email Job")
					.usingJobData(jobDataMap)
					.storeDurably()
					.build();
	}
	
	public static Trigger buildJobTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
		
		return TriggerBuilder.newTrigger()
					.forJob(jobDetail)
					.withIdentity(jobDetail.getKey().getName(), "email-triggers")
					.withDescription("Send Email Trigger")
					.startAt(Date.from(startAt.toInstant()))
					.withSchedule(simpleScheduleBuilder())
					.build();
	}
	
	private static SimpleScheduleBuilder simpleScheduleBuilder() {
		
		return SimpleScheduleBuilder.simpleSchedule()
					.withIntervalInSeconds(10)
					.repeatForever()
					.withMisfireHandlingInstructionFireNow();
	}
}
