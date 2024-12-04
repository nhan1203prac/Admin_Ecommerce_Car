package Nhom7.car_ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import Nhom7.car_ecommerce.modal.Call;
import Nhom7.car_ecommerce.request.CallRequest;
import Nhom7.car_ecommerce.service.CallHistoryService;

@RestController
public class CallController {
	@Autowired
	private CallHistoryService callHistoryService;
	
	@PostMapping("/call/create")
	public ResponseEntity<Call> createCall(@RequestBody CallRequest callRequest) throws Exception{
		return ResponseEntity.status(HttpStatus.CREATED).body(callHistoryService.createCall(callRequest.getSenderId(), callRequest.getReceiverId()));
	}
	
	@GetMapping("/call")
	public ResponseEntity<List<Call>> getListCall(@RequestHeader("Authorization") String token) throws Exception{
		return ResponseEntity.status(HttpStatus.CREATED).body(callHistoryService.getListCall(token));
	}
}
