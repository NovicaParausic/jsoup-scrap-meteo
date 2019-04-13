package com.scrap.quartz.listeners;

import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.listeners.TriggerListenerSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SimpleTriggerListenerSupport extends TriggerListenerSupport {

	private Logger log = LoggerFactory.getLogger(getClass());

	private static final String LISTENER_NAME = "SimpleTriggerListener";

	//@Autowired
	//private JobService jobService;
	
	@Override
	public String getName() {
		
		return LISTENER_NAME;
	}
	
	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		final JobKey key = trigger.getJobKey();
		log.info("trigger " + key + " fired");
		//jobService.deleteJob(key.getGroup(), key.getName());
		log.info("Job Service Clear");
	}

	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		return false;
	}

	@Override
	public void triggerMisfired(Trigger trigger) {
		final String name = trigger.getJobKey().getName();
		log.info("trigger " + name + " misfired");
	}

	@Override
	public void triggerComplete(
				Trigger trigger,
				JobExecutionContext context,
				CompletedExecutionInstruction triggerInstructionCode) {
		final String name = trigger.getJobKey().getName();
		log.info("trigger " + name + " completed");
	}
}
