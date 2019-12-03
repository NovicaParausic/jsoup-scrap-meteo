package com.scrap.component;

import java.time.ZonedDateTime;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.scrap.entity.Airport;
import com.scrap.entity.MetarEntity;
import com.scrap.quartz.util.JobTriggerUtil;
import com.scrap.repository.MetarEntityRepository;
import com.scrap.util.MetarUtil;

import io.github.mivek.model.Metar;

@Component
public class ActiveComponent {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private Scheduler scheduler;
	
	@Autowired
	private MetarEntityRepository meRepo;
	
	@Async
	public void save(Airport airport) {
		
		String code = airport.getCode();
		String url = airport.getUrl();
			
		MetarEntity entity = meRepo.findById(code).orElse(null);
			
		if (entity == null) {	
			Metar metar = MetarUtil.getMetarFromUrl(url);
			ZonedDateTime executionZDTime = JobTriggerUtil.createExecutionTime(metar);	
			log.info("Job " + code + " scheduled for " + executionZDTime);	
			createJob(code, url, executionZDTime);
				
			meRepo.save(new MetarEntity(code, metar.getMessage()));
		}
	}
	
	public void clearScheduler() {
		
		try {
			scheduler.clear();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteJob(String code) {
		
		try {
			scheduler.deleteJob(new JobKey(code, "scrap-job"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createJob(String code, String url, ZonedDateTime executionZDTime) {
		
		try {
			JobDetail jobDetail = JobTriggerUtil.buildScrapJobDetail(code, url);
			Trigger trigger = JobTriggerUtil.buildScrapJobTrigger(jobDetail, executionZDTime);
			scheduler.scheduleJob(jobDetail, trigger);
				
			log.info("Job " + code + " scheduled succesfully");
		} catch (SchedulerException ex) {
			log.error("Error scheduling " + code + " job due to : {}", ex.getLocalizedMessage());
		}
	}
}

/*@Async
	public void save(Airport airport) {
		
		String code = airport.getCode();
		String url = airport.getUrl();
			
		MetarEntity entity = meRepo.findById(code).orElse(null);
			
		if (entity == null) {	
			Metar metar = MetarUtil.getMetarFromUrl(url);
			ZonedDateTime executionZDTime = JobTriggerUtil.createExecutionTime(metar);	
				
			ScrapJobRequest request = new ScrapJobRequest(code, url, executionZDTime); 
			createJob(code , request);
				
			meRepo.save(new MetarEntity(code, metar.getMessage()));
		}
	}
	
	private void createJob(String group, ScrapJobRequest request) {
		
		String code = request.getCode();
		
		try {
			ZonedDateTime zdt = request.getZonedDateTime();
			
			if (zdt.isBefore(ZonedDateTime.now())) {
				log.info("DateTime for airport " + code + "must be after current time");
			}
				
			JobDetail jobDetail = JobTriggerUtil.buildScrapJobDetail(request);
			Trigger trigger = JobTriggerUtil.buildScrapJobTrigger(jobDetail, zdt);
			scheduler.scheduleJob(jobDetail, trigger);
				
			log.info("Job " + code + " scheduled succesfully");
		} catch (SchedulerException ex) {
			log.error("Error scheduling " + code + " job due to : {}", ex.getLocalizedMessage());
		}
	}*/
