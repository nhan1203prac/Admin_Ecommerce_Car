package Nhom7.car_ecommerce.modal;

import java.time.LocalDate;

import Nhom7.car_ecommerce.Domain.IsRead;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Notification")
public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long notifiId;
	@ManyToOne
	private User user;
	private String title;
	private String message;
	@Enumerated(EnumType.STRING)
	private IsRead status = IsRead.UNREAD;
	private LocalDate createdAt;
	
}
