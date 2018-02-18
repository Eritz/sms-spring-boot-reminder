package com.sms.reminder.user;

import com.sms.reminder.notification.Notification;
import com.sms.reminder.notification.NotificationRepository;
import com.sms.reminder.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// 
@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	// UNCLEAN
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// UserRepo returns a sms.reminder.User
		User user = userRepository.findByUsername(username);
		if (user==null) {
			throw new UsernameNotFoundException(username);
		}
		// This User is from the security.userDetails
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), emptyList());
	}
	
	public void registerUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	public List<Notification> getUserNotifications(String username) {
		// THIS IS VERY UNCLEAN
		return notificationRepository.findByUsername(username);
	}

	public boolean checkRegisterUser(String username) {
		return userRepository.existsByUsername(username);
	}
	
	
}
