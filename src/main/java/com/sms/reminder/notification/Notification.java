package com.sms.reminder.notification;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sms.reminder.user.User;


@Entity
@Table(name="notifications")
public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="NOTIFICATION_ID")
	private Long id;
	
	@Column(name="DATE_MADE")
	private String dateMade;
	@Column(name="DATE_SEND")
	private String dateSend;
	@Column(name="TIME_SEND")
	private String timeSend;
	@Column(name="PHONE_NUMBER")
	private String phoneNumber;
	@Lob
	@Column(name="MESSAGE")
	private String message;
	@Column(name="STATUS")
	private String status;
	@Column(name="NAME")
	private String username;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name="useraccount")
	private User useraccount;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public User getUseraccount() {
		return useraccount;
	}
	public void setUseraccount(User useraccount) {
		this.useraccount = useraccount;
	}
	
	
	
	

}
