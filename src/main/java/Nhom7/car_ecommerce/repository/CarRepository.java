package Nhom7.car_ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Nhom7.car_ecommerce.Domain.CAR_STATUS;
import Nhom7.car_ecommerce.modal.Car;

public interface CarRepository extends JpaRepository<Car, Long>{
	List<Car> findByStatus(CAR_STATUS status);
	List<Car> findByStatusAndBrand(CAR_STATUS status, String brand);
}
