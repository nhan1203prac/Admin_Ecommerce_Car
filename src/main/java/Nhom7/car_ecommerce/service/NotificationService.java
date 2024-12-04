package Nhom7.car_ecommerce.service;

import java.lang.foreign.Linker.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Nhom7.car_ecommerce.Domain.IsRead;
import Nhom7.car_ecommerce.modal.Notification;
import Nhom7.car_ecommerce.modal.User;
import Nhom7.car_ecommerce.repository.NotificationRepositoy;

@Service
public class NotificationService {
	@Autowired
	private NotificationRepositoy notificationRepositoy;
	
	public Notification findById(Long notifId) throws Exception {
		Optional<Notification> option = notificationRepositoy.findById(notifId);
		if(option.isEmpty()) {
			throw new Exception("Notif not found");
			
		}
		return option.get();
	}
	public Notification createNotification(User user,String title,String message) {
		Notification notification = new Notification();
		notification.setCreatedAt(LocalDate.now());
		notification.setMessage(message);
		notification.setTitle(title);
		notification.setUser(user);
		
		return notificationRepositoy.save(notification);
	}
	
	public List<Notification> getUserNotifications(User user){
		return notificationRepositoy.findByUser_UserId(user.getUserId());
	}
	
	public Notification ReadNotification(Long notificationId) throws Exception {
		Notification notif =  findById(notificationId);
		notif.setStatus(IsRead.READ);
		
		return notificationRepositoy.save(notif);
	}
	public List<Notification> getRead(User user){
		return notificationRepositoy.findByUser_UserIdAndStatus(user.getUserId(), IsRead.READ);
	}
	public List<Notification> getUnRead(User user){
		return notificationRepositoy.findByUser_UserIdAndStatus(user.getUserId(), IsRead.UNREAD);
	}
	
	
}
