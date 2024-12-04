package Nhom7.car_ecommerce.response;

import java.util.ArrayList;
import java.util.List;

import Nhom7.car_ecommerce.modal.Comment;
import Nhom7.car_ecommerce.modal.ImageCar;
import Nhom7.car_ecommerce.modal.User;
import lombok.Data;
@Data
public class CarWatchlishResponse {

	
private String brand;
private String name;
private String description;
private double price;
private List<ImageCar> images= new ArrayList<>();
private long carId;
private List<Comment> comments = new ArrayList<>();
private User user;
private String color;
	
}
