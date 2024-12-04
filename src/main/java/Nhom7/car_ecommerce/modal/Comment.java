package Nhom7.car_ecommerce.modal;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Comment")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long commentId;
	@ManyToOne
	private User user;
	@ManyToOne
	@JsonBackReference 
	private Car car;
	private String content;
	private double rating=0;
	private Long likes = (long) 0;
	private Long dislike = (long) 0;
	
	@ElementCollection
    private Set<Long> likedByUsers = new HashSet<>();

    @ElementCollection
    private Set<Long> dislikedByUsers = new HashSet<>();
    
	private LocalDate createdAt;
}
