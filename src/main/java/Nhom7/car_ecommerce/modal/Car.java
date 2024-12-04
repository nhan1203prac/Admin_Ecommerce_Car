package Nhom7.car_ecommerce.modal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import Nhom7.car_ecommerce.Domain.CAR_STATUS;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="Cars")
public class Car {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long carId;
	@ManyToOne
	private User user;
	private String brand;
	private String name;
	private String description;
	private double price;
	
	@OneToMany
	private List<ImageCar> images = new ArrayList<>();
	
	@OneToMany
	@JsonManagedReference 
	private List<Comment> comments = new ArrayList<>();
	
	 
	@Enumerated(EnumType.STRING)
	private CAR_STATUS status = CAR_STATUS.PENDING;
	private LocalDate createdAt;
	
}
