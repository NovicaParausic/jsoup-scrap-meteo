package com.scrap;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
public class MetarTafNotamApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetarTafNotamApplication.class, args);
	}

	@Bean(name = "outer")
	public Executor ouuterTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setThreadNamePrefix("OST-");
		executor.setCorePoolSize(10);
		executor.initialize();
		return executor;
	}
	
	@Bean(name = "inner")
	public Executor innerTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setThreadNamePrefix("IST-");
		executor.setCorePoolSize(10);
		executor.initialize();
		return executor;
	}
}
