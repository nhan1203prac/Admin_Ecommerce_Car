package Nhom7.car_ecommerce.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Nhom7.car_ecommerce.modal.Call;
import Nhom7.car_ecommerce.modal.User;
import Nhom7.car_ecommerce.repository.CallHistoryRepository;
import Nhom7.car_ecommerce.repository.UserRepository;

@Service
public class CallHistoryService {
	@Autowired
	private UserService userService;
	@Autowired
	private CallHistoryRepository callHistoryRepository;
	@Autowired
	private UserRepository userRepository;
	
	public Call createCall(Long senderId,Long receiverId) throws Exception {
		User sender = userService.findByUserId(senderId);
		User receiver = userService.findByUserId(receiverId);
		Call call = new Call();
		call.setSender(sender);
		call.setReceiver(receiver);
		call.setTime(LocalDateTime.now());
		return callHistoryRepository.save(call);
	}
	
	public List<Call> getListCall(String token) throws Exception {
	  
	    User currentUser = userService.findUserByJwt(token);

	    
	    List<Call> listCalls = callHistoryRepository.findAll().stream()
	        .filter(call -> 
	            (call.getReceiver() != null && call.getReceiver().getUserId().equals(currentUser.getUserId())) || 
	            (call.getSender() != null && call.getSender().getUserId().equals(currentUser.getUserId()))
	        )
	        .sorted((call1, call2) -> call2.getTime().compareTo(call1.getTime())) 
	        .collect(Collectors.toList());

	    return listCalls;
	}

}
