package Nhom7.car_ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Nhom7.car_ecommerce.modal.ImageCar;

public interface ImageCarRepository extends JpaRepository<ImageCar, Long> {
//	List<ImageCar> findByCar_CarId(long carId);
}
