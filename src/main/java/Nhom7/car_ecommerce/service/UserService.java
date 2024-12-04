package Nhom7.car_ecommerce.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Nhom7.car_ecommerce.config.JwtProvider;
import Nhom7.car_ecommerce.modal.User;
import Nhom7.car_ecommerce.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	private JavaMailSender javaMailSender;
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	public User findUserByJwt(String jwt) throws Exception {
		String email = JwtProvider.getEmaiFromToken(jwt);
		User user = userRepository.findByEmail(email);
		if(user==null) {
			throw new Exception("User not found");
		}
		return user;
	}
	public User findUserByEmail(String email) throws Exception {
		User user = userRepository.findByEmail(email);
		if(user==null) {
			throw new Exception("User not found");
		}
		return user;
	}
//	@PreAuthorize("hasRole('ADMIN')")
	public User findByUserId(Long userId) throws Exception {
		Optional<User> optionUser = userRepository.findById(userId);
		if(optionUser.isEmpty()) {
			throw new Exception("User not found");
		}
		return optionUser.get();
	}
	
	public User updatePassword(User user, String newPassword) {
		user.setPassword(passwordEncoder.encode(newPassword));
		
		return userRepository.save(user);
	}
	
	public String generatePassword() {
		
		int otpLength = 6;
		Random random = new Random();
		
		StringBuilder otp = new StringBuilder();
		
		for(int i=1;i <= otpLength;i++) {
			otp.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
		}
		return otp.toString();
	}
	
	
	
	
}
