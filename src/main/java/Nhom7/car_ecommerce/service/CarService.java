package Nhom7.car_ecommerce.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import Nhom7.car_ecommerce.Domain.CAR_STATUS;
import Nhom7.car_ecommerce.modal.Car;
import Nhom7.car_ecommerce.modal.ImageCar;
import Nhom7.car_ecommerce.modal.User;
import Nhom7.car_ecommerce.repository.CarRepository;
import Nhom7.car_ecommerce.repository.ImageCarRepository;
import Nhom7.car_ecommerce.repository.NotificationRepositoy;
import Nhom7.car_ecommerce.request.CarRequest;
import Nhom7.car_ecommerce.response.CarResponse;
import jakarta.transaction.Transactional;

@Service
public class CarService {
	@Autowired
	private CarRepository carRepository;
	@Autowired
	private ImageCarRepository imageCarRepository;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private NotificationRepositoy notificationRepositoy;
	@Autowired
	private UserService userService;
	public CarResponse createCarPost(User user,String brand,String name,String description, double price, List<String> pictures) {
		Car newCar = new Car();
		newCar.setBrand(brand);
		newCar.setCreatedAt(LocalDate.now());
		newCar.setName(name);
		newCar.setDescription(description);
		newCar.setPrice(price);
		newCar.setUser(user);
		
		carRepository.save(newCar);
		List<ImageCar> imageCar = new ArrayList<>();
		for (String url : pictures) {
			ImageCar image = new ImageCar();
//			image.setCar(newCar);
			image.setUrl(url);
			imageCarRepository.save(image);
			imageCar.add(image);
		}
		newCar.setImages(imageCar);
		carRepository.save(newCar);
		
//		create carResponse
		CarResponse carResponse = new CarResponse();
		carResponse.setBrand(newCar.getBrand());
		carResponse.setName(newCar.getName());
		carResponse.setCreatedAt(newCar.getCreatedAt());
		carResponse.setDescription(newCar.getDescription());
		carResponse.setUser(newCar.getUser());
		carResponse.setPrice(newCar.getPrice());
		carResponse.setImages(pictures);
	
		return carResponse;
	}
	
	public Car findCarPostById(Long id) throws Exception {
		Optional<Car> optionCar = carRepository.findById(id);
		if(optionCar.isEmpty())
			throw new Exception("Car post not found");
		
		
		return optionCar.get();
	}
	@Transactional
	public Car updateCarPostStatus(Long id, boolean accept) throws Exception {
	    Car isExistCar = findCarPostById(id);
	    if (isExistCar == null) {
	        throw new Exception("Car not found with id " + id);
	    }

	    if (accept) {
	        isExistCar.setStatus(CAR_STATUS.SUCCESS);
	        notificationService.createNotification(isExistCar.getUser(), "Bài đăng có mã "+ isExistCar.getCarId() +"đã được duyệt thành công", "Sản phẩm của bạn đã được duyệt");
	        
	    } else {
	        List<ImageCar> image = isExistCar.getImages();
	        if (!image.isEmpty()) {
	            imageCarRepository.deleteAll(image);
	        }
	        notificationService.createNotification(isExistCar.getUser(), "Bài đăng có mã "+ isExistCar.getCarId() +"duyệt thất bại", "Sản phẩm của bạn không được duyệt vì lý do cộng đồng");
	        carRepository.delete(isExistCar);
	        return null; 
	    }

	    return carRepository.save(isExistCar);
	}

	
	public Car updateCarPost(Long id, CarRequest req) throws Exception {
		Car isExistCar = findCarPostById(id);
		isExistCar.setBrand(req.getBrand());
		isExistCar.setDescription(req.getDescription());
		isExistCar.setName(req.getName());
		isExistCar.setPrice(req.getPrice());
		
		isExistCar.setImages(new ArrayList<ImageCar>());
		
		List<ImageCar> imageCar = new ArrayList<>();
		for (String picture : req.getPictures()) {
			ImageCar img = new ImageCar();
//			img.setCar(isExistCar);
			img.setUrl(picture);
			imageCar.add(img);
		}
		isExistCar.setImages(imageCar);
		return carRepository.save(isExistCar);
	}
	
//	public List<Car> getListPostCarStatusSuccess(){
//		return carRepository.findByStatus(CAR_STATUS.SUCCESS);
//	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<Car> getAllPostCar(){
		return carRepository.findAll();
	}
	public List<Car> getListPostCarStatusSuccessAndBrand(String brand){
		
		List<Car> carResponses = new ArrayList<>(); 
		List<Car> cars = new ArrayList<Car>();
		if(brand==null) {
			cars = carRepository.findByStatus(CAR_STATUS.SUCCESS);
			 
		}
		else {
			
			cars =  carRepository.findByStatusAndBrand(CAR_STATUS.SUCCESS,brand);
		}
		for (Car car : cars) {
			System.out.println("------carId"+ car.getCarId());
	        Car carResponse = new Car();
	        
	        // Set values from Car to CarResponse
	        carResponse.setName(car.getName());
	        carResponse.setPrice(car.getPrice());
	        carResponse.setDescription(car.getDescription());
	        carResponse.setBrand(car.getBrand());
	        carResponse.setCarId(car.getCarId());
	        
	        // Convert car.getImages to a list of URLs
//	        List<String> imageUrls = new ArrayList<>();
//	        for (ImageCar image : car.getImages()) {
//	            imageUrls.add(image.getUrl()); // Assuming 'getUrl()' returns the image URL
//	        }

	        // Set the list of URLs in carResponse
//	        carResponse.setImages(imageUrls); 

	        // Add to the list of car responses
//	        carResponses.add(carResponse);
	        carResponse.setImages(car.getImages());
	        carResponses.add(carResponse);
	    }

	    return carResponses;
		
	}
	
public List<Car> getListPostCarStatusPendingAndBrand(){
		
		List<Car> carResponses = new ArrayList<>(); 
		List<Car> cars = new ArrayList<Car>();
		
		cars = carRepository.findByStatus(CAR_STATUS.PENDING);
			 
		
		
		for (Car car : cars) {
			System.out.println("------carId"+ car.getCarId());
	        Car carResponse = new Car();
	        
	        // Set values from Car to CarResponse
	        carResponse.setName(car.getName());
	        carResponse.setPrice(car.getPrice());
	        carResponse.setDescription(car.getDescription());
	        carResponse.setBrand(car.getBrand());
	        carResponse.setCarId(car.getCarId());
	        
	        // Convert car.getImages to a list of URLs
//	        List<String> imageUrls = new ArrayList<>();
//	        for (ImageCar image : car.getImages()) {
//	            imageUrls.add(image.getUrl()); // Assuming 'getUrl()' returns the image URL
//	        }

	        // Set the list of URLs in carResponse
//	        carResponse.setImages(imageUrls); 

	        // Add to the list of car responses
//	        carResponses.add(carResponse);
	        carResponse.setImages(car.getImages());
	        carResponses.add(carResponse);
	    }

	    return carResponses;
		
	}
	
	
	
	public List<Car> searchNameCar(String name){
		String nameLowercase = name.toLowerCase();
		return carRepository.findAll().stream().filter(car->car.getName().toLowerCase().contains(nameLowercase)).collect(Collectors.toList());
	}
	
	
}
