package com.motows.tenant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableCaching
@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = "com")

public class TenantApplication {
	private static final Logger log = LoggerFactory.getLogger(TenantApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TenantApplication.class, args);
	}

}
