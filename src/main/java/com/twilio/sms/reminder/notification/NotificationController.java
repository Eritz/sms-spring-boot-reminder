package com.twilio.sms.reminder.notification;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {
	
	@Autowired
	private NotificationService notificationService;
	
	private String datePattern ="MM-dd-yyyy";
	private String timePattern ="HH:mm";
	private SimpleDateFormat simpleDate = new SimpleDateFormat(datePattern);
	private SimpleDateFormat simpleTime = new SimpleDateFormat(timePattern);
	
	// GET - get all notifications in the repository
	@RequestMapping("/notifications")
	public List<Notification> getAllNotifications() {
		return notificationService.getAllNotifications();
	}
	
	// POST - add a new notification to the repository
	@PostMapping("/notifications")
	public void addNotification(@RequestBody Notification notification) {
		notificationService.addNotification(notification);
	}
	
	///////////////////// GET - get all of a users in a repository
	///////////////////// Separate method in separate folder
	@RequestMapping("/users")
	public void getAllUsers() {
		//
	}
	
	
	// Every minute, check if dateSend matches. If it does, then use Twilio to send
	// Currently using apache, delete when working with mysql
	@Scheduled(cron = "0 * * * * ?")
	public void checkNotification() {
		String currentDate = simpleDate.format(new Date()); //02-13-2018
		String currentTime = simpleTime.format(new Date()); //15:12

		if (notificationService.checkNotification(currentDate, currentTime)) {
			sendNotification(currentDate, currentTime);
			updateNotification("Finished",currentDate, currentTime, "Pending");
		} else {
			System.out.println("nothing");
		}
	}
	
	// then in notificationService do logic for twilio if true
	// it'll be a separate notification service void function
	
	// POST - send "Pending" notifications at specified date & time
	@PostMapping("notifications/send")
	public void sendNotification(String dateSend, String timeSend) {
		notificationService.sendNotification(dateSend, timeSend);
	}
	
	// PUT - update "Pending" to "Finished" notifications for specified date & time
	@PutMapping("notifications/send/{newStatus}")
	public void updateNotification(@PathVariable String newStatus,String dateSend, String timeSend, String oldStatus) {
		notificationService.updateNotification(newStatus, dateSend, timeSend, oldStatus);
	}

	
	
}
