package ru.myrecord;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class CrmApplication extends SpringBootServletInitializer {
//public class CrmApplication {

	/**
	 * Run via tomcat
	 * */
	public static void main(String[] args) {
		SpringApplication.run(CrmApplication.class, args);
		//SpringApplication.run(new Class<?>[] {CrmApplication.class, JpaConfig.class}, args);
	}

	/**
	 * Run via GAE - https://github.com/GoogleCloudPlatform/getting-started-java/tree/master/appengine-standard-java8/springboot-appengine-standard
	 * */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CrmApplication.class);
	}

}
