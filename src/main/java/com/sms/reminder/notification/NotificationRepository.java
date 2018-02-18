package com.sms.reminder.notification;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface NotificationRepository extends CrudRepository<Notification, Long>{
	
	//Return a list of notifications with the current date/time with "Pending" status
	public List<Notification> findByDateSendAndTimeSendAndStatus(String dateSend, String timeSend, String status);
	
	// Check if there is any "Pending" for the current date/time
	// Simpler to check if it exists first before creating a List in next function
	@Query("SELECT CASE WHEN COUNT(n) > 0 THEN true ELSE false END FROM Notification n WHERE n.dateSend= :dateSend AND n.timeSend = :timeSend AND n.status = :status")
	public boolean existsByStatus(@Param("dateSend") String dateSend, @Param("timeSend") String timeSend,@Param("status") String status);
	
	// Update the repo for sent, yet "Pending" notifications for current date/time
	@Transactional
	@Modifying
	@Query("UPDATE Notification n SET n.status = ?1 WHERE n.dateSend= ?2 AND n.timeSend = ?3 AND n.status = ?4")
	public void setFixedStatus(String newStatus, String dateSend, String timeSend, String oldStatus);
	
	//
	public List<Notification> findByUsername(String username);
	
}
