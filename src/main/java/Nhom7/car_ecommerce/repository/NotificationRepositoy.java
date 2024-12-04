package Nhom7.car_ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Nhom7.car_ecommerce.Domain.IsRead;
import Nhom7.car_ecommerce.modal.Notification;

public interface NotificationRepositoy extends JpaRepository<Notification, Long>{
	List<Notification> findByUser_UserId(Long userId);
	List<Notification> findByUser_UserIdAndStatus(Long userId, IsRead status);
}
