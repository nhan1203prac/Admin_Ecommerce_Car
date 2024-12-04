package Nhom7.car_ecommerce.modal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import Nhom7.car_ecommerce.Domain.CAR_STATUS;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="Call_history")
public class Call {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long callId;
	@ManyToOne 
	private User sender; 

	@ManyToOne
	private User receiver;
	private LocalDateTime time;
	
}
