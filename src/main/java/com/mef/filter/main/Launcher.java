package com.mef.filter.main;

import com.mef.filter.main.config.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class Launcher {
	public static final Logger logger = LoggerFactory.getLogger(Launcher.class);

	public static void main(String[] args) {
		logger.info("::==========APPLICATION STARTED::===========");
		new SpringApplicationBuilder(Launcher.class).headless(false).run(args);

	}

}
