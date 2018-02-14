package com.twilio.sms.reminder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SmsTwilioReminderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmsTwilioReminderApplication.class, args);
	}
}
