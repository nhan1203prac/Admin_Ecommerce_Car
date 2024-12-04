package Nhom7.car_ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Nhom7.car_ecommerce.modal.User;

public interface UserRepository extends JpaRepository<User,Long>{
	User findByEmail(String email);
}
