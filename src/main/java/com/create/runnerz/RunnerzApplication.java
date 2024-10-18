package com.create.runnerz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RunnerzApplication {

	private static final Logger logger = LoggerFactory.getLogger(RunnerzApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RunnerzApplication.class, args);
	}

	/*@Bean
	CommandLineRunner runner(JdbcClientRunRepository runRepository) {
		return args -> {
			Run run = new Run(1, "firstRun", LocalDateTime.now(), LocalDateTime.now().plusHours(1), 5, Location.OUTDOOR);
			logger.info("Run: {}", run);
			runRepository.addRun(run);
		};
	}*/

}
