package com.scrap.quartz.listeners;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SimpleJobListener implements JobListener {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	private static final String LISTENER_NAME = "SimpleJobListener";
	
	@Override
	public String getName() {
		
		return LISTENER_NAME;
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		
		String jobName = context.getJobDetail().getKey().getName();
		log.info(jobName + " to be executed");		
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {

		log.info("bla bla bla");
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		
		String jobName = context.getJobDetail().getKey().getName();
		log.info(jobName + " was executed");		
	}
}
