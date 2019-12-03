package com.scrap.quartz.util;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.scrap.common.ZonedDateTimeProvider;
import com.scrap.quartz.job.ScrapJob;

import io.github.mivek.model.Metar;

public class JobTriggerUtil {

	public static ZonedDateTime createExecutionTime(Metar metar) {
		
		LocalTime lastRequestUtcTime = metar.getTime().plusMinutes(1);
		LocalTime utcTimeNow = ZonedDateTimeProvider.now().toLocalTime();
		
		while (lastRequestUtcTime.isBefore(utcTimeNow)) {
			lastRequestUtcTime = lastRequestUtcTime.plusMinutes(10);
		}
		
		LocalDateTime executionLDTime = lastRequestUtcTime.atDate(ZonedDateTimeProvider.now().toLocalDate());
		ZonedDateTime executionUtcTime = ZonedDateTime.of(executionLDTime, ZoneId.of("UTC"));
		ZonedDateTime executionBelgradeTime = executionUtcTime.withZoneSameInstant(ZoneId.of("Europe/Belgrade"));
		
		return executionBelgradeTime;
	}
	
	public static JobDetail buildScrapJobDetail(String code, String url) {
		
		JobDataMap jobDataMap = new JobDataMap();
		
		jobDataMap.put("code", code);
		jobDataMap.put("url", url);
		
		return JobBuilder.newJob(ScrapJob.class)
							.withIdentity(code, "scrap-job")
							.usingJobData(jobDataMap)
							.build();
	}
	
	public static Trigger buildScrapJobTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
		
		return TriggerBuilder.newTrigger()
								.forJob(jobDetail)
								.withIdentity(jobDetail.getKey().getName(), "scrap-trigger")
								.startAt(Date.from(startAt.toInstant()))
								.withSchedule(simpleScheduleBuilder())
								.build();
	}
	
	private static SimpleScheduleBuilder simpleScheduleBuilder() {
		
		return SimpleScheduleBuilder.simpleSchedule()
										.withIntervalInSeconds(30)
										.repeatForever()
										.withMisfireHandlingInstructionFireNow();
	}
}
