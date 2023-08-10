package com.alinesno.infra.base.config;

import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertySource;

@SpringBootApplication
public class Application extends SpringBootServletInitializer implements CommandLineRunner {

	@Resource
	private ConfigurableEnvironment springEnv;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		for (PropertySource<?> propertySource : springEnv.getPropertySources()) {
			if (propertySource instanceof EnumerablePropertySource) {
				EnumerablePropertySource<?> enumerablePropertySource = (EnumerablePropertySource<?>) propertySource;
				String[] propertyNames = enumerablePropertySource.getPropertyNames();
				for (String propertyName : propertyNames) {
					System.out.println(propertyName + " = " + enumerablePropertySource.getProperty(propertyName));
				}
			}
		}
	}

}
