package Nhom7.car_ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Nhom7.car_ecommerce.modal.ListFavouriteCar;
import Nhom7.car_ecommerce.modal.User;

public interface ListFavouriteCarRepository extends JpaRepository<ListFavouriteCar, Long>{
	ListFavouriteCar findByUser_UserId(Long userId);
}
