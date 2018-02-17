package com.sms.reminder.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	// GET - get the register page
	@RequestMapping("/register")
	public void registerUserPage() {
		
	}
	
	// POST - get the info and send it to the userRepo
	// Check if unique username else return fail
	@PostMapping("/register")
	public void registerUser(@RequestBody User user) {
		userService.registerUser(user);
	}
		
	@RequestMapping("/notifications/users/{id}")
	public void getUserNotifications(@PathVariable Long id) {
		
	}
	
}
