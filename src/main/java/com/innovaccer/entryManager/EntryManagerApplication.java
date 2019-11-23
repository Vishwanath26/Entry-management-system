package com.innovaccer.entryManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EntryManagerApplication {

	private static final Logger logger = LoggerFactory.getLogger(EntryManagerApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(EntryManagerApplication.class, args);
	}

}
