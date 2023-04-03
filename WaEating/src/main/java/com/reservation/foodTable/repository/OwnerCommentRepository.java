package com.reservation.foodTable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reservation.foodTable.entity.OwnerComment;

public interface OwnerCommentRepository extends JpaRepository<OwnerComment, Integer> {
	
	@Query("SELECT o FROM OwnerComment o WHERE o.review.id in :reviewId")
	public List<OwnerComment> findByReviewId(@Param("reviewId")List<Integer> reviewId);
	
	public void save(int ownerReviewId);
}
