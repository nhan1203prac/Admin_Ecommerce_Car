package Nhom7.car_ecommerce.modal;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ListFavouriteCar")
public class ListFavouriteCar {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long favouriteId;
	@OneToOne
	private User user;
	@ManyToMany
	private List<Car> cars = new ArrayList<>();
}
