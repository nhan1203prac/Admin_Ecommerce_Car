package Nhom7.car_ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Nhom7.car_ecommerce.modal.Notification;
import Nhom7.car_ecommerce.modal.User;
import Nhom7.car_ecommerce.request.NotificationRequest;
import Nhom7.car_ecommerce.service.NotificationService;
import Nhom7.car_ecommerce.service.UserService;

@RestController
@RequestMapping("/api")
public class NotificationController {
	@Autowired
	private UserService userService;
	@Autowired
	private NotificationService notificationService;
	@PostMapping("/notification")
	public ResponseEntity<Notification> createNotif(@RequestHeader("Authorization") String jwt,@RequestBody NotificationRequest req) throws Exception{
		User user = userService.findUserByJwt(jwt);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.createNotification(user, req.getTitle(), req.getMessage()));
	}
	
	@GetMapping("/notification/user")
	public ResponseEntity<List<Notification>> getUserNotifications(@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserByJwt(jwt);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.getUserNotifications(user));
	}
	
	@PatchMapping("/notification/{notificationId}/read")
	public ResponseEntity<Notification> readNotification(@PathVariable("notificationId") Long id) throws Exception{
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.ReadNotification(id));
	}
	
	@GetMapping("/notification/unread")
	public ResponseEntity<List<Notification>> getUnread(@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user = userService.findUserByJwt(jwt);
		return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.getUnRead(user));
	}
	
	@GetMapping("/notification/read")
	public ResponseEntity<List<Notification>> getRead(@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user = userService.findUserByJwt(jwt);
		return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.getRead(user));
	}
	
	
}
