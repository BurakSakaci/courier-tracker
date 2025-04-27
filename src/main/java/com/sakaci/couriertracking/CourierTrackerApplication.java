package com.sakaci.couriertracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.sakaci.couriertracking.domain.repository")
@EntityScan(basePackages = "com.sakaci.couriertracking.domain.entity")
public class CourierTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourierTrackerApplication.class, args);
	}

}
