package Nhom7.car_ecommerce.response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import Nhom7.car_ecommerce.Domain.CAR_STATUS;
import Nhom7.car_ecommerce.modal.Comment;
import Nhom7.car_ecommerce.modal.ImageCar;
import Nhom7.car_ecommerce.modal.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
@Data
public class CarResponse {
	
	private long carId;
	
	private User user;
	private String brand;
	private String name;
	private String description;
	private double price;
	
	
	private List<String> images = new ArrayList<>();
	
	
	private List<Comment> comments = new ArrayList<>();
	
	 
	
	private LocalDate createdAt;
}
