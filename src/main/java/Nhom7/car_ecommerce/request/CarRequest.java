package Nhom7.car_ecommerce.request;

import java.util.List;

import Nhom7.car_ecommerce.modal.User;
import lombok.Data;
@Data
public class CarRequest {
	
	private String brand;
	private String name;
	private String description;
	private double price;
	private List<String> pictures;
}
