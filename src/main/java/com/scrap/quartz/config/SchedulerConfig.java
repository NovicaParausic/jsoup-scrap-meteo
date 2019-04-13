package com.scrap.quartz.config;

import java.io.IOException;

import org.quartz.spi.JobFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.scrap.quartz.listeners.SimpleJobListener;
import com.scrap.quartz.listeners.SimpleTriggerListenerSupport;

@Configuration
@EnableAsync
@EnableScheduling
public class SchedulerConfig {

	@Bean
	public JobFactory jobFactory(ApplicationContext applicationContext) {
		
		AutowiringSpringBeanFactory jobFactory = new AutowiringSpringBeanFactory();
		jobFactory.setApplicationContext(applicationContext);
		
		return jobFactory;
	}
	
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, 
														SimpleJobListener jobListener,
														SimpleTriggerListenerSupport triggerListener) 
																throws IOException {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		
		factory.setJobFactory(jobFactory);
		factory.setGlobalJobListeners(jobListener);
		factory.setGlobalTriggerListeners(triggerListener);
		
		return factory;
	}
}
