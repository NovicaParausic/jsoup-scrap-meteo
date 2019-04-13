package com.scrap.quartz.service.impl;

import java.time.ZonedDateTime;
import java.util.Objects;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scrap.quartz.dto.ScheduleEmailRequest;
import com.scrap.quartz.dto.ScheduleEmailResponse;
import com.scrap.quartz.service.JobService;
import com.scrap.quartz.util.JobTriggerUtil;

@Service
@Transactional
public class SimpleServiceImpl implements JobService {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private Scheduler scheduler;

	@Override
	public ScheduleEmailResponse createJob(String group, ScheduleEmailRequest request) {
		
		try {
			ZonedDateTime dateTime = ZonedDateTime.of(request.getDateTime(), request.getZoneId());
			
			if (dateTime.isBefore(ZonedDateTime.now())) {
				return new ScheduleEmailResponse(false, "DateTime must be after current time");
			}
					
			JobDetail jobDetail = JobTriggerUtil.buildJobDetail(request);
			Trigger trigger = JobTriggerUtil.buildJobTrigger(jobDetail, dateTime);
			scheduler.scheduleJob(jobDetail, trigger);
			
			return new ScheduleEmailResponse(true, 
							jobDetail.getKey().getName(),
							jobDetail.getKey().getGroup(),
							"Email scheduled succesfully");
		} catch (SchedulerException ex) {
			String message = "Error scheduling email";
			log.error(message, ex);
			return new ScheduleEmailResponse(false, message);
		}		
	}

	@Override
	@Transactional(readOnly = true)
	public ScheduleEmailResponse findJob(String group, String name) {
		
		try {
			JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(name, group));
			
			if (Objects.nonNull(jobDetail)) {
				return new ScheduleEmailResponse(true, name, group);
			}
		} catch (SchedulerException ex) {
			
			String message = "Could not find job with key " + group + "." + name + " due to error " + ex.getLocalizedMessage();
			log.error(message);
			return new ScheduleEmailResponse(false, message);
		}
		
		String message ="Could not find job with key" + group + "." + name;
		log.warn(message);
		return new ScheduleEmailResponse(false, message);
	}

	@Override
	public ScheduleEmailResponse updateJob(String group, String name, ScheduleEmailRequest request) {
		
		try {
			JobDetail oldJobDetail = scheduler.getJobDetail(JobKey.jobKey(name, group));
			
			if (Objects.nonNull(oldJobDetail)) {
				JobBuilder jb = oldJobDetail.getJobBuilder();
				JobDetail newJobDetail = jb.storeDurably().build();
				scheduler.addJob(newJobDetail, true);
				
				log.info("Updated job with the key - {}", newJobDetail.getKey());
				return new ScheduleEmailResponse(true, name, group);
			}
			
			String message = "Couldn't find job with the key " + group + "." + name;
			log.error(message);
			return new ScheduleEmailResponse(false, message);
		} catch (SchedulerException ex) {
			
			String message = "Could not find job with the key " + group + "." + name + " due to error " + ex.getLocalizedMessage();
			log.error(message);
			return new ScheduleEmailResponse(false, message);
		}
	}

	@Override
	public ScheduleEmailResponse deleteJob(String group, String name) {
		
		try {
			scheduler.deleteJob(JobKey.jobKey(name, group));
			
			String message = "Deleted job with key - " + group + "." + name;
			log.info(message);
			return new ScheduleEmailResponse(true, message);
		} catch (SchedulerException ex) {
			
			String message = "Could not delete job with the key " + group + "." + name + " due to error " + ex.getLocalizedMessage();
			log.error(message);
			return new ScheduleEmailResponse(false, message);
		}
	}

	@Override
	public ScheduleEmailResponse pauseJob(String group, String name) {
		
		try {
			scheduler.pauseJob(JobKey.jobKey(name, group));
			
			String message = "Paused job with key - " + group + "." + name;
			log.info(message);
			return new ScheduleEmailResponse(true, message);
		} catch (SchedulerException ex) {
			
			String message = "Could not pause job with the key " + group + "." + name + " dueto error " + ex.getLocalizedMessage();
			log.error(message);
			return new ScheduleEmailResponse(false, message);
		}
	}

	@Override
	public ScheduleEmailResponse resumeJob(String group, String name) {
		
		try {
			scheduler.resumeJob(JobKey.jobKey(name, group));
			
			String message = "Paused job with key - " + group + "." + name;
			log.info(message);
			return new ScheduleEmailResponse(true, message);
		} catch (SchedulerException ex) {
			
			String message = "Could not pause job with the key " + group + "." + name + " due to error " + ex.getLocalizedMessage();
			log.error(message);
			return new ScheduleEmailResponse(false, message);
		}
	}

	@Override
	public void stopAllJobs() {
		
		try {
			scheduler.pauseAll();
		} catch (SchedulerException ex) {
			log.error(ex.getLocalizedMessage());
		}		
	}

	@Override
	public void clear() {
		
		try {
			scheduler.clear();
		} catch (SchedulerException ex) {
			log.error(ex.getLocalizedMessage());
		}			
	}

	@Override
	public void resume() {

		try {
			scheduler.resumeAll();
		} catch (SchedulerException ex) {
			log.error(ex.getLocalizedMessage());
		}	
	}
	
	
	
}
