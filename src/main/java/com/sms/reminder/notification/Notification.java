package com.sms.reminder.notification;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Notification {
	

//	@ManyToOne
//	@JoinColumn(name = "notification_id") // see if this links okay
	@Id
	private String id; // refers back to a unique notification id
	private String dateMade;
	private String dateSend;
	private String timeSend;
	private String phoneNumber;
	private String message;
	private String status;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
		
	public String getDateMade() {
		return dateMade;
	}
	public void setDateMade(String dateMade) {
		this.dateMade = dateMade;
	}
	public String getDateSend() {
		return dateSend;
	}
	public void setDateSend(String dateSend) {
		this.dateSend = dateSend;
	}
	public String getTimeSend() {
		return timeSend;
	}
	public void setTimeSend(String timeSend) {
		this.timeSend = timeSend;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
