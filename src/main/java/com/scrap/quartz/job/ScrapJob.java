package com.scrap.quartz.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scrap.component.ActiveComponent;

@Component
public class ScrapJob implements Job {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ActiveComponent activeComponent;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		JobDataMap map = context.getMergedJobDataMap();
		String url = map.getString("url");
		String code = map.getString("code");
		log.info(code + " start scrap job execution");
		
		activeComponent.doTheJob(code, url);
	}
}

