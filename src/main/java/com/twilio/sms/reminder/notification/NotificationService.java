package com.twilio.sms.reminder.notification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	// GET for "/notifications"
	public List<Notification> getAllNotifications() {
		List<Notification> notifications = new ArrayList<>();
		notificationRepository.findAll()
			.forEach(notifications::add); // lambda to push to the notifications arrayList
		
		return notifications;
	}
	
	// POST for "/notifications"
	public void addNotification(Notification notification) {
		notificationRepository.save(notification);
	}
	
	// Checks if there are any notifications that matches both date and time
	// with a "Pending" status.
	public boolean checkNotification(String currentDate, String currentTime) {
		return notificationRepository.existsByStatus(currentDate, currentTime, "Pending"); 
	}
	
	// Gets all notifications ready to be sent, and uses Twilio to send
	public void sendNotification(String dateSend, String timeSend) {
		List<Notification> notifications = notificationRepository.findByDateSendAndTimeSendAndStatus(dateSend, timeSend, "Pending");
		for (Notification n : notifications) {
			String message = n.getMessage();
			String number = n.getPhoneNumber();
			System.out.println("This is message: " + message);
			System.out.println("This is phone: " + number);
			// use twilio to send
		}
	}
	
	// Find all sent notifications, and change its status from Pending to Finished
	public void updateNotification(String newStatus, String dateSend, String timeSend, String oldStatus) {
		notificationRepository.setFixedStatus(newStatus, dateSend, timeSend, oldStatus);
		System.out.println("Finished updating, go check");
	}
		
	
}
