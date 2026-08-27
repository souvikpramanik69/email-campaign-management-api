package com.kasplo.email_campaign_management_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EmailCampaignManagementApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailCampaignManagementApiApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void logSwaggerUrl() {
		System.out.println("Swagger UI: http://localhost:9000/swagger-ui/index.html");
	}

}
