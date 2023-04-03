package com.reservation.foodTable.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reservation.foodTable.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

	@Query("SELECT r FROM Review r join r.reservationInfo ri join ri.restaurantAvaTime rt join rt.restaurantDefaultTime rdt"
			+ " where rdt.restaurant.id =:id")
	public Page<Review> findByRestaurantId(@Param("id") int id, Pageable pagable);

	Review findByReservationInfoId(Integer reservationid);

	public void deleteByReservationInfoId(Integer reservationid);

	@Query("SELECT r FROM Review r \r\n" + "LEFT JOIN OwnerComment oc ON r.id = oc.review "
			+ "WHERE r.reservationInfo.restaurant.id = :id " + "AND (oc IS NULL OR oc.content IS NOT NULL) ORDER BY r.id DESC")
	public List<Review> findAllReviewByRestaurantId(@Param("id") int id);

	@Query("SELECT r FROM Review r \r\n" + "LEFT JOIN OwnerComment oc ON r.id = oc.review "
			+ "WHERE r.reservationInfo.restaurant.id = :id " + "AND (oc IS NULL OR oc.content IS NOT NULL) ORDER BY r.id DESC")
	public Page<Review> findAllReviewByRestaurantId(@Param("id") int id, Pageable pagable);

	@Query("SELECT r FROM Review r \r\n" + "RIGHT JOIN OwnerComment oc ON r.id = oc.review "
			+ "WHERE r.reservationInfo.restaurant.id = :id " + "AND (oc IS NULL OR oc.content IS NOT NULL) ORDER BY r.id DESC")
	public Page<Review> findReviewByRestaurantIdHavingOwnerContent(@Param("id") int id, Pageable pagable);

}
