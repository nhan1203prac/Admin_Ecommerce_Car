package Nhom7.car_ecommerce.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Nhom7.car_ecommerce.modal.Car;
import Nhom7.car_ecommerce.modal.Comment;
import Nhom7.car_ecommerce.modal.User;
import Nhom7.car_ecommerce.repository.CarRepository;
import Nhom7.car_ecommerce.repository.CommentRepository;

@Service
public class CommentService {
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private CarService carService;
	@Autowired
	private UserService userService;
	@Autowired
	private CarRepository carRepository;
	
	public Comment postComment(String content,Long carId,User user) throws Exception {
		Car car = carService.findCarPostById(carId);
		Comment newComment = new Comment();
		newComment.setCar(car);
		newComment.setContent(content);
		newComment.setCreatedAt(LocalDate.now());
		newComment.setUser(user);
		Comment comment = commentRepository.save(newComment);
		car.getComments().add(newComment);
		carRepository.save(car);
		return comment;
		
	}
	public List<Comment> getCarComments(Long carId){
		return commentRepository.findByCar_CarId(carId);
	}
	public List<Comment> getCommentByUserIdAndCarId(Long userId, Long carId){
		return commentRepository.findByUser_UserIdAndCar_CarId(userId, carId);
	}
	public Comment findById(Long id) throws Exception {
		Optional<Comment> option = commentRepository.findById(id);
		if(option.isEmpty()) {
			throw new Exception("Comment not found");
		}
		return option.get();
	}
	
	public Comment addLike(Long commentId, User user) throws Exception {
		
		Comment comment = findById(commentId);
		if(comment.getDislikedByUsers().contains(user.getUserId())) {
			comment.getDislikedByUsers().remove(user.getUserId());
			comment.setDislike(comment.getDislike() - 1);
		}
		
		 if (comment.getLikedByUsers().contains(user.getUserId())) {
			 comment.getLikedByUsers().remove(user.getUserId());
			 comment.setLikes(comment.getLikes() - 1);
	        } else {
	        	comment.getLikedByUsers().add(user.getUserId());
	        	comment.setLikes(comment.getLikes() + 1);
	        }
		 commentRepository.save(comment);

		    
		    double rating;
		    if (comment.getLikes() + comment.getDislike() == 0) {
		        rating = 0; 
		    } else {
		        rating = ((double)(comment.getLikes() - comment.getDislike()) / (comment.getLikes() + comment.getDislike())) * 5;
		        rating = Math.abs(rating);
		    }
		 
		 comment.setRating(rating);
		
		return commentRepository.save(comment);
		
	}
	public Comment subLike(Long commentId,User user) throws Exception {
		Comment comment = findById(commentId);
		if (comment.getLikedByUsers().contains(user.getUserId())) {
			comment.getLikedByUsers().remove(user.getUserId());
			comment.setLikes(comment.getLikes() - 1);
        }

        if (comment.getDislikedByUsers().contains(user.getUserId())) {
        	comment.getDislikedByUsers().remove(user.getUserId());
        	comment.setDislike(comment.getDislike() - 1);
        } else {
        	comment.getDislikedByUsers().add(user.getUserId());
        	comment.setDislike(comment.getDislike() + 1);
        }
        
        commentRepository.save(comment);

        // Tính rating an toàn
        double rating;
        if (comment.getLikes() + comment.getDislike() == 0) {
            rating = 0; // Trường hợp không có tương tác
        } else {
            rating = ((double)(comment.getLikes() - comment.getDislike()) / (comment.getLikes() + comment.getDislike())) * 5;
            rating = Math.abs(rating);
        }
		 comment.setRating(rating);
		return commentRepository.save(comment);
		
	}
	
	public List<Comment> getCommentByFilter(int countStar, Long carId){
		return commentRepository.findByCar_CarIdAndRating(carId, countStar);
	}
}
