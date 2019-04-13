package com.scrap.quartz.job;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class SimpleJob extends QuartzJobBean {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		log.info("Executing job with key {}", context.getJobDetail().getKey());
		
		JobDataMap jobDataMap = context.getMergedJobDataMap();
				
		String subject = jobDataMap.getString("subject");
		String body= jobDataMap.getString("body");
		String email = jobDataMap.getString("email");
		
		log.info("Subject: {}, Body: {}, RecipientEmail: {}", subject, body, email);
	}
}
