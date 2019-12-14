package com.scrap.component;

import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;

import javax.transaction.Transactional;

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
import com.scrap.repository.UserIcaoRepository;
import com.scrap.util.MetarUtil;

import io.github.mivek.model.Metar;

@Component
public class ActiveComponent {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private Scheduler scheduler;
	
	@Autowired
	private MetarEntityRepository meRepo;
	
	@Autowired
	private UserIcaoRepository uiRepo;
	
	@Async("inner")
	@Transactional
	public void save(Airport airport) {
		
		String code = airport.getCode();
		String url = airport.getUrl();
			
		MetarEntity entity = meRepo.findById(code).orElse(null);
			
		if (entity == null) {	
			CompletableFuture<String> futureRaw = MetarUtil.getRawMetarFromUrl(url);
			String raw = futureToString(futureRaw);
			log.info("Save" + raw);
			Metar metar = MetarUtil.decodeMetarFromString(raw);
			
			ZonedDateTime executionZDTime = JobTriggerUtil.createExecutionTime(metar);	
			log.info("Job " + code + " scheduled for " + executionZDTime);	
			createJob(code, url, executionZDTime);
				
			meRepo.save(new MetarEntity(code, metar.getMessage()));
		}
	}
	
	@Async("inner")
	@Transactional
	public void doTheJob(String code, String url) {
		
		CompletableFuture<String> futureRaw = MetarUtil.getRawMetarFromUrl(url);
		String rawMetar = futureToString(futureRaw);
		
		Metar metar = MetarUtil.decodeMetarFromString(rawMetar);
		meRepo.save(new MetarEntity(code, metar.getMessage()));
			
	
	}
	
	public void clearScheduler() {
		
		try {
			scheduler.clear();
			log.info("Cart cleared");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void deleteByUserAndIcao(String user, String icao) {
		uiRepo.deleteByUserAndIcao(user, icao);
		log.info("Deleted");
	}
	
	@Transactional
	public void deleteJob(String code) {
		
		try {
			scheduler.deleteJob(new JobKey(code, "scrap-job"));
			meRepo.deleteById(code);
			log.info("Job " + code + " deleted");
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
	
	private String futureToString(CompletableFuture<String> future){
		String futureRaw = null;
		try {
			futureRaw= future.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return futureRaw;
	}
}

