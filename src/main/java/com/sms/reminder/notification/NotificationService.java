package com.sms.reminder.notification;

import java.util.ArrayList;
import java.util.List;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Value("${twilio.ACCOUNT_SID}")
	private String ACCOUNT_SID;
	
	@Value("${twilio.AUTH_TOKEN}")
	private String AUTH_TOKEN;
	
	@Value("${twilio.NUMBER}")
	private String TWILIO_NUMBER;
	
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
		
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		for (Notification n : notifications) {
			String message = n.getMessage();
			String number = n.getPhoneNumber();
		
		    Message twilioMessage = Message.creator(new PhoneNumber("+1"+number),
		            new PhoneNumber(TWILIO_NUMBER), message).create();
			
		}
	}
	
	// Find all sent notifications, and change its status from Pending to Finished
	public void updateNotification(String newStatus, String dateSend, String timeSend, String oldStatus) {
		notificationRepository.setFixedStatus(newStatus, dateSend, timeSend, oldStatus);
		System.out.println("Finished updating, go check");
	}
		
	
}
