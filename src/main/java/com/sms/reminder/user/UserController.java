package com.sms.reminder.user;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sms.reminder.notification.Notification;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	// GET - get the register page
	@RequestMapping("/register")
	public void registerUserPage() {
		
	}
	
	// GET - check if the user is not taken
	@RequestMapping("/register/{username}")
	public boolean checkRegisterUser(@PathVariable String username) {
		return userService.checkRegisterUser(username);
	}
	
	// POST - get the info and send it to the userRepo
	// Check if unique username else return fail
	@PostMapping("/register")
	public void registerUser(@RequestBody User user) {
		userService.registerUser(user);
	}
		
	@RequestMapping("/users/{username}/notifications")
	public List<Notification> getUserNotifications(@PathVariable String username) {
		return userService.getUserNotifications(username);
	}
	
}
