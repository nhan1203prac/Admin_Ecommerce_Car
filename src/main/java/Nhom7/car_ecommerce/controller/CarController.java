package Nhom7.car_ecommerce.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Nhom7.car_ecommerce.modal.Car;
import Nhom7.car_ecommerce.modal.ImageCar;
import Nhom7.car_ecommerce.modal.User;
import Nhom7.car_ecommerce.request.CarRequest;
import Nhom7.car_ecommerce.response.CarResponse;
import Nhom7.car_ecommerce.service.CarService;
import Nhom7.car_ecommerce.service.UserService;

@RestController
@RequestMapping("/api")
public class CarController {
	@Autowired
	private CarService carService;
	@Autowired
	private UserService userService;
	@PostMapping("/car")
	public ResponseEntity<CarResponse> createCarPost(@RequestBody CarRequest req, @RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserByJwt(jwt);
		return ResponseEntity.status(HttpStatus.CREATED).body(carService.createCarPost(user, req.getBrand(), req.getName(), 
				req.getDescription(), req.getPrice(),req.getPictures()));
	}
	@GetMapping("/car/{carId}")
	public ResponseEntity<CarResponse> findCarById(@PathVariable("carId") Long carId) throws Exception{
		Car car = carService.findCarPostById(carId);
				
		CarResponse carResponse = new CarResponse();
		carResponse.setBrand(car.getBrand());
		carResponse.setCarId(car.getCarId());
		carResponse.setComments(car.getComments());
		carResponse.setDescription(car.getDescription());
		
		List<String> imageUrls = new ArrayList<>();
        for (ImageCar image : car.getImages()) {
            imageUrls.add(image.getUrl()); 
        }
        carResponse.setName(car.getName());
        carResponse.setPrice(car.getPrice());
        carResponse.setUser(car.getUser());
        
        carResponse.setImages(imageUrls);
		return ResponseEntity.status(HttpStatus.OK).body(carResponse);
	}
	
	@PatchMapping("/car/{carId}/{accept}")
	public ResponseEntity<Car> updateCarPostStatus(@PathVariable("carId") Long carId,@PathVariable("accept") boolean accept) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(carService.updateCarPostStatus(carId, accept));
	}
	
	@PutMapping("/car/{carId}")
	public ResponseEntity<Car> updateCarPost(@PathVariable("carId") Long carId, CarRequest req) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(carService.updateCarPost(carId, req));
	}
//	@GetMapping("/car")
//	public ResponseEntity<List<Car>> getListStatusSuccess() throws Exception{
//		return ResponseEntity.status(HttpStatus.OK).body(carService.getListPostCarStatusSuccess());
//	}
	@GetMapping("/car")
	public ResponseEntity<List<Car>> getListStatusSuccessAndBrand(@RequestParam(value = "brand", required = false) String brand) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(carService.getListPostCarStatusSuccessAndBrand(brand));
	}
	@GetMapping("/car/status/pending")
	public ResponseEntity<List<Car>> getListStatusPendingAndBrand() throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(carService.getListPostCarStatusPendingAndBrand());
	}
	
	@GetMapping("/car/all")
	public ResponseEntity<List<Car>> getAll() throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(carService.getAllPostCar());
	}
	
	@GetMapping("/car/search")
	public ResponseEntity<List<Car>> searchCar(@RequestParam("name") String name) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(carService.searchNameCar(name));
	}
	 
}
