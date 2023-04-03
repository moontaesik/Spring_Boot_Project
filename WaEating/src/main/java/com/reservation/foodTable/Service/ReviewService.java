package com.reservation.foodTable.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reservation.foodTable.dto.ReviewDTO;
import com.reservation.foodTable.entity.OwnerComment;
import com.reservation.foodTable.entity.Review;
import com.reservation.foodTable.repository.OwnerCommentRepository;
import com.reservation.foodTable.repository.ReviewRepository;

@Transactional(readOnly=true)
@Service
public class ReviewService {
	
	private final ReviewRepository reviewRepository;
	private final OwnerCommentRepository ownerCommentRepository;
	
	public ReviewService(ReviewRepository reviewRepository, OwnerCommentRepository ownerCommentRepository) {
		super();
		this.reviewRepository = reviewRepository;
		this.ownerCommentRepository = ownerCommentRepository;
	}

	//  ================================ < 홍한별 > ================================
	
	@Transactional
	public Page<ReviewDTO> getSortedPagedReviews(int restaurantId,Pageable pagable){	
		
		Page<Review> pageReview = reviewRepository.findByRestaurantId(restaurantId, pagable);

		
		return pageReview.map(ReviewDTO::new);
	}
	
	//  ================================ < 문태식 > ================================
	
	public Review findByReservationInfoId(Integer reservationid) {
		return reviewRepository.findByReservationInfoId(reservationid);
	}

	@Transactional
	public void save(Review review) {	
		reviewRepository.save(review);
	}
	@Transactional
	public void deleteByReservationInfoId(Integer reservationid) {
		reviewRepository.deleteByReservationInfoId(reservationid);
	}
	
//  ================================ < 하지원 > ================================

	   public List<Review> findAllReviewByRestaurantId(int id) {
		      List<Review> reviewList = reviewRepository.findAllReviewByRestaurantId(id);
		      return reviewList;
		   }
		   
		   public static final int USERS_PER_PAGE = 5;
		   
		   public Page<Review> findAllReviewByRestaurantId(int id, int pageNum) {
		       Pageable pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE);
		       return reviewRepository.findAllReviewByRestaurantId(id, pageable);
		   }

		   @Transactional
		   public void deleteByOwnerCommentId(int id) {
		      ownerCommentRepository.deleteById(id);
		   }

		   /*
		    * @Transactional public void addByOwnerCommentId(OwnerComment ownerComment) {
		    * ownerCommentRepository.updateOwnerComment(ownerComment); }
		    */

		   public Review getReviewById(int ownerReviewId) {
		      Review review = reviewRepository.findById(ownerReviewId).get();
		      return review;
		   }

		   public void saveReview(Review review) {
		      reviewRepository.save(review);
		   }
		   
		   @Transactional
		   public void save(OwnerComment ownerComment) {   
		      ownerCommentRepository.save(ownerComment);
		   }
		   
			public Page<Review> findReviewByRestaurantIdHavingOwnerContent(int id, int pageNum) {
			    Pageable pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE);
			    return reviewRepository.findReviewByRestaurantIdHavingOwnerContent(id, pageable);
			}


}
