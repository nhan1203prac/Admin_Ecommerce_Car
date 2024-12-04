package Nhom7.car_ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Nhom7.car_ecommerce.modal.Car;
import Nhom7.car_ecommerce.modal.ListFavouriteCar;
import Nhom7.car_ecommerce.modal.User;
import Nhom7.car_ecommerce.response.WatchListResponse;
import Nhom7.car_ecommerce.service.ListFavouriteService;
import Nhom7.car_ecommerce.service.UserService;

@RestController
@RequestMapping("/api")
public class ListFavouriteController {
	@Autowired
	private ListFavouriteService listFavouriteService;
	
	@Autowired
	private UserService userService;
	@GetMapping("/list/{listId}")
	ResponseEntity<WatchListResponse> findById(@PathVariable("listId") Long listId) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(listFavouriteService.findById(listId));
	}
	
	@GetMapping("/list")
	ResponseEntity<WatchListResponse> findById(@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserByJwt(jwt);
		return ResponseEntity.status(HttpStatus.OK).body(listFavouriteService.findUserList(user.getUserId()));
	}
	
	@PatchMapping("/list/add/coin/{carId}")
	ResponseEntity<ListFavouriteCar> findById(@RequestHeader("Authorization") String jwt, @PathVariable("carId") Long carId) throws Exception{
		User user = userService.findUserByJwt(jwt);
		return ResponseEntity.status(HttpStatus.OK).body(listFavouriteService.addCarToList(carId, user));
	}
}
