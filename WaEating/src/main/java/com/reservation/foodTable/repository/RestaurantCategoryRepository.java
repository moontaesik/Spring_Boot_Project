package com.reservation.foodTable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reservation.foodTable.entity.Restaurant;
import com.reservation.foodTable.entity.RestaurantCategory;

public interface RestaurantCategoryRepository extends JpaRepository<RestaurantCategory, Integer> {

	@Query("select rc from RestaurantCategory rc WHERE rc.restaurant = :id")
	RestaurantCategory finbyReataurant(@Param("id")Restaurant restaurant);

}
