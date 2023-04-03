package com.reservation.foodTable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reservation.foodTable.entity.MenuCategory;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Integer> {

	
	List<MenuCategory> findByRestaurantIdOrderByOrderNumberAsc(Integer restaurantId);

	@Modifying
	@Query("UPDATE MenuCategory m SET m.orderNumber =:number WHERE m.id =:id")
	void updateOrder(@Param("number")int i, @Param("id")Integer integer);

	
}
