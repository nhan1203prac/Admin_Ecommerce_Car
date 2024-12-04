package Nhom7.car_ecommerce.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Nhom7.car_ecommerce.Domain.ROLE;
import Nhom7.car_ecommerce.modal.ListFavouriteCar;
import Nhom7.car_ecommerce.modal.User;
import Nhom7.car_ecommerce.repository.ListFavouriteCarRepository;
import Nhom7.car_ecommerce.repository.UserRepository;
import Nhom7.car_ecommerce.response.DeleteResponse;
import Nhom7.car_ecommerce.service.ListFavouriteService;
import Nhom7.car_ecommerce.service.UserService;

@RestController
//@RequestMapping("/api")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ListFavouriteCarRepository listFavouriteCarRepository;
	@Autowired
	private ListFavouriteService listFavouriteService;
	@GetMapping("/api/user")
	ResponseEntity<List<User>> getUser(){
		return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
	}
	
	@GetMapping("/api/user/profile")
	ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserByJwt(jwt);
		
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
	@GetMapping("/api/user/{userId}")
	ResponseEntity<User> getUserById(@PathVariable("userId") Long userId) throws Exception{
		User user = userService.findByUserId(userId);
		
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
	@GetMapping("/api/user/callList")
	ResponseEntity<List<User>> getCallList(@RequestHeader("Authorization") String jwt) throws Exception{
		User currentUser = userService.findUserByJwt(jwt);
		List<User> listUser = userRepository.findAll().stream()
				.filter(user->!user.getUserId().equals(currentUser.getUserId())).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(listUser);
	}
	@PatchMapping("/api/user/profile")
	ResponseEntity<User> updateUserProfile(@RequestHeader("Authorization") String jwt ,@RequestBody User user) throws Exception{
		
		User updateUser = userService.findUserByJwt(jwt);
		updateUser.setEmail(user.getEmail());
		updateUser.setFullName(user.getFullName());
		updateUser.setPhoneNumber(user.getPhoneNumber());
			
		return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(updateUser));
	}
	@PatchMapping("/api/user")
	ResponseEntity<User> updateUser(@RequestHeader("Authorization") String jwt ,@RequestBody User user) throws Exception{
		
		User updateUser = userService.findByUserId(user.getUserId());
		updateUser.setEmail(user.getEmail());
		updateUser.setFullName(user.getFullName());
		updateUser.setPhoneNumber(user.getPhoneNumber());
		
			updateUser.setPassword(passwordEncoder.encode(user.getPassword()));	
		
		return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(updateUser));
	}
	@GetMapping("/api/user/roleUser")
	ResponseEntity<List<User>> getRoleUser(@RequestHeader("Authorization") String jwt ) throws Exception{
		
		List<User> listUser = userRepository.findAll().stream().filter(u->u.getRole().equals(ROLE.ROLE_USER)).collect(Collectors.toList());
			
		return ResponseEntity.status(HttpStatus.OK).body(listUser);
	}
	
	@DeleteMapping("/api/user/{userId}")
	ResponseEntity<DeleteResponse> deleteUser(@RequestHeader("Authorization") String jwt, @PathVariable("userId") Long userId ) throws Exception{
		
		User user = userService.findByUserId(userId);
		ListFavouriteCar listFavouriteCar = listFavouriteCarRepository.findByUser_UserId(userId);
		listFavouriteCarRepository.delete(listFavouriteCar);
		userRepository.delete(user);
		DeleteResponse deleteResponse = new DeleteResponse();
		deleteResponse.setMessage("Delete User success");
		return ResponseEntity.status(HttpStatus.OK).body(deleteResponse);
	}
}
