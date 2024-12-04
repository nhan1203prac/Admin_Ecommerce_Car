package Nhom7.car_ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Nhom7.car_ecommerce.modal.Car;
import Nhom7.car_ecommerce.modal.ListFavouriteCar;
import Nhom7.car_ecommerce.modal.User;
import Nhom7.car_ecommerce.repository.ListFavouriteCarRepository;
import Nhom7.car_ecommerce.response.CarWatchlishResponse;
import Nhom7.car_ecommerce.response.WatchListResponse;

@Service
public class ListFavouriteService {
	@Autowired
	private ListFavouriteCarRepository listFavouriteCarRepository;
	@Autowired
	private CarService carService;
	
	
	public ListFavouriteCar addCarToList(Long carId, User user) throws Exception {
		Car car = carService.findCarPostById(carId);
		ListFavouriteCar list = listFavouriteCarRepository.findByUser_UserId(user.getUserId());
		
		List<Car> updatedCars = new ArrayList<>(list.getCars());
		if(updatedCars.contains(car)) {
			updatedCars.remove(car);
			
			
		}else {
			updatedCars.add(car);
			
		}
		list.setCars(updatedCars);
		
		return listFavouriteCarRepository.save(list);
		
	}
	
	public WatchListResponse  findById(Long id) throws Exception {
		Optional<ListFavouriteCar> optional = listFavouriteCarRepository.findById(id);
		if(optional.isEmpty())
			throw new Exception("List not found");
		ListFavouriteCar listFavouriteCar = optional.get();
		WatchListResponse watchListResponse = new WatchListResponse();
		watchListResponse.setUser(listFavouriteCar.getUser());
		watchListResponse.setFavouriteId(listFavouriteCar.getFavouriteId());
		
		List<CarWatchlishResponse> carWatchlishResponses = new ArrayList<>();
		for(Car car : listFavouriteCar.getCars()) {
			CarWatchlishResponse carWatchlishResponse = new CarWatchlishResponse();
			carWatchlishResponse.setBrand(car.getBrand());
			carWatchlishResponse.setCarId(car.getCarId());
			carWatchlishResponse.setComments(car.getComments());
			carWatchlishResponse.setDescription(car.getDescription());
			carWatchlishResponse.setImages(car.getImages());
			carWatchlishResponse.setName(car.getName());
			carWatchlishResponse.setPrice(car.getPrice());
			carWatchlishResponse.setUser(car.getUser());
			carWatchlishResponse.setColor("");
			carWatchlishResponses.add(carWatchlishResponse);
			
		}
		watchListResponse.setCars(carWatchlishResponses);
		
		
		return watchListResponse;
	}
	public WatchListResponse findUserList(Long userId) throws Exception {
		ListFavouriteCar list = listFavouriteCarRepository.findByUser_UserId(userId);
		if(list==null) {
			throw new Exception("List not found");
		}
		WatchListResponse watchListResponse = new WatchListResponse();
		watchListResponse.setUser(list.getUser());
		watchListResponse.setFavouriteId(list.getFavouriteId());
		
		List<CarWatchlishResponse> carWatchlishResponses = new ArrayList<>();
		for(Car car : list.getCars()) {
			CarWatchlishResponse carWatchlishResponse = new CarWatchlishResponse();
			carWatchlishResponse.setBrand(car.getBrand());
			carWatchlishResponse.setCarId(car.getCarId());
			carWatchlishResponse.setComments(car.getComments());
			carWatchlishResponse.setDescription(car.getDescription());
			carWatchlishResponse.setImages(car.getImages());
			carWatchlishResponse.setName(car.getName());
			carWatchlishResponse.setPrice(car.getPrice());
			carWatchlishResponse.setUser(car.getUser());
			carWatchlishResponse.setColor("");
			carWatchlishResponses.add(carWatchlishResponse);
			
		}
		watchListResponse.setCars(carWatchlishResponses);
		return watchListResponse;
	}
}
