package Nhom7.car_ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Nhom7.car_ecommerce.modal.Call;

public interface CallHistoryRepository extends JpaRepository<Call, Long>{

}
