package com.scrap.quartz.config;

import org.quartz.spi.JobFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.scrap.quartz.listeners.ScrapJobListener;
import com.scrap.quartz.listeners.ScrapTriggerListener;

@Configuration
@EnableAsync
@EnableScheduling
public class SchedulerConfig {

	@Bean
	public JobFactory jobFactory(ApplicationContext context) {
		
		AutowiringSpringBeanFactory jobFactory = new AutowiringSpringBeanFactory();
		jobFactory.setApplicationContext(context);
		return jobFactory;
	}
	
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory,
														ScrapJobListener jobListener,
															ScrapTriggerListener triggerListener) {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		
		factory.setJobFactory(jobFactory);
		factory.setGlobalJobListeners(jobListener);
		factory.setGlobalTriggerListeners(triggerListener);
		
		return factory;
	}
}
