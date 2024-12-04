package Nhom7.car_ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Nhom7.car_ecommerce.modal.Car;
import Nhom7.car_ecommerce.modal.Comment;
import Nhom7.car_ecommerce.modal.User;
import Nhom7.car_ecommerce.request.CommentRequest;
import Nhom7.car_ecommerce.service.CarService;
import Nhom7.car_ecommerce.service.CommentService;
import Nhom7.car_ecommerce.service.UserService;

@RestController
@RequestMapping("/api")
public class CommentController {
	
	@Autowired
	private CarService carService;
	@Autowired
	private UserService userService;
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/{carId}/like")
    public ResponseEntity<Comment> likeCar(@PathVariable("carId") long carId, @RequestHeader("Authorization") String jwt) throws Exception {
	 
        Comment updatedCar = commentService.addLike(carId, userService.findUserByJwt(jwt));
        return ResponseEntity.ok(updatedCar);
    }

    @PostMapping("/{carId}/dislike")
    public ResponseEntity<Comment> dislikeCar(@PathVariable("carId") long carId, @RequestHeader("Authorization") String jwt) throws Exception {
    	Comment updatedCar = commentService.subLike(carId, userService.findUserByJwt(jwt));
        return ResponseEntity.ok(updatedCar);
    }
    
    @PostMapping("/comment/car/{carId}")
    public ResponseEntity<Comment> postComment(@RequestBody CommentRequest req,@PathVariable("carId") Long carId, @RequestHeader("Authorization") String jwt) throws Exception{
    	Comment newPost = commentService.postComment(req.getContent(), carId, userService.findUserByJwt(jwt));
    	return ResponseEntity.status(HttpStatus.CREATED).body(newPost) ;
    }
    
    @GetMapping("/comment/car/{carId}")
    public ResponseEntity<List<Comment>> getCarComments(@PathVariable("carId") Long carId){
    	return ResponseEntity.status(HttpStatus.OK).body(commentService.getCarComments(carId));
    }
    @GetMapping("/comment/car/{carId}/rating/{rating}")
    public ResponseEntity<List<Comment>> getCarCommentsByRating(@PathVariable("carId") Long carId, @PathVariable("rating") int rating){
    	return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentByFilter(rating, carId));
    }
    @GetMapping("/comment/car/{carId}/user")
    public ResponseEntity<List<Comment>> getCarComments(@PathVariable("carId") Long carId, @RequestHeader("Authorization") String jwt) throws Exception{
    	User user = userService.findUserByJwt(jwt);
    	return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentByUserIdAndCarId(user.getUserId(),carId));
    }
}
