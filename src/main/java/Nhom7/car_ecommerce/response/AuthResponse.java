package Nhom7.car_ecommerce.response;



import Nhom7.car_ecommerce.Domain.ROLE;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
	private String jwt;
	private String message;
	private boolean status;
	private Long userId;
	
	private String role;
	
	
}
