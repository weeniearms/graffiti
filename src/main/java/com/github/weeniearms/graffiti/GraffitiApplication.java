package com.github.weeniearms.graffiti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class GraffitiApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(GraffitiApplication.class);
		Map<String, Object> defaultProperties = new HashMap<>();
		defaultProperties.put("spring.profiles.default", "dev");
		application.setDefaultProperties(defaultProperties);
		application.run(args);
	}
}
