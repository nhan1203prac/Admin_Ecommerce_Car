package Nhom7.car_ecommerce.modal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "ImageCar")
public class ImageCar {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long imageId;
//	@ManyToOne
//	@JsonBackReference 
//	private Car car;
	private String url;
}
