package com.sms.reminder.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sms.reminder.notification.Notification;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="USER_ID")
	private long userId;
	
	@Column(name="NAME")
	private String username; // unique username
	
	@Column(name="PASSWORD")
	private String password;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="useraccount", fetch=FetchType.LAZY)
	private List<Notification> notifications = new ArrayList<>();
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Notification> getNotifications() {
		return notifications;
	}
	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	} 
	
	public void addNotifications(Notification notification) {
		notifications.add(notification);
		notification.setUseraccount(this);
	}
	
	public void removeNotifications(Notification notification) {
		notifications.remove(notification);
		notification.setUseraccount(null);
	}
	
}
