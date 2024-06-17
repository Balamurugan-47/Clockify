package com.clockify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
//@ComponentScan(basePackages = "com.employee.push.notification")
public class ClockifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClockifyApplication.class, args);
	}

}
