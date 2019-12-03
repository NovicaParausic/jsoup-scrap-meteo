package com.scrap.quartz.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scrap.component.MetarComponent;

@Component
public class ScrapJob implements Job {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MetarComponent meComponent;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		JobDataMap map = context.getMergedJobDataMap();
		String url = map.getString("url");
		String code = map.getString("code");
		log.info(code + " start scrap job execution");
		
		meComponent.getMetarFromUrl(code, url);
		
		log.info("Job " + code + "completed");
	}
}

/*
 * 	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		JobDataMap map = context.getMergedJobDataMap();
		String url = map.getString("url");
		String icao = map.getString("code");
		log.info(icao + " start scrap job execution");
		
		CompletableFuture<Metar> futureMetar = meComponent.getMetarFromUrl(url);
		
		Metar metar = null;
		
		try {	
			metar = futureMetar.get();
		} catch(Exception e) {
			e.printStackTrace();
		}
		String metarRaw = metar.getMessage();
		System.out.println(url);
		System.out.println(metarRaw);
		log.info(icao);
		
		metarRepository.save(new MetarEntity(icao, metarRaw));
		log.info("Job " + icao + "completed");
	}*/
