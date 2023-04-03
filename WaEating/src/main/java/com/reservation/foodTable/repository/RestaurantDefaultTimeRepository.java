package com.reservation.foodTable.repository;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reservation.foodTable.entity.Restaurant;
import com.reservation.foodTable.entity.RestaurantDefaultTime;

public interface RestaurantDefaultTimeRepository extends JpaRepository<RestaurantDefaultTime, Integer> {
	
	@Query("SELECT rdt FROM RestaurantDefaultTime rdt WHERE rdt.restaurant =:restaurant AND rdt.enabled = true")
	List<RestaurantDefaultTime> findByparentId(@Param("restaurant") Restaurant id);
	
	@Query("SELECT rdt.time FROM RestaurantDefaultTime rdt WHERE rdt.restaurant.id =:id")
	List<LocalTime> findByRestaurantId(@Param("id")Integer id);
	
	@Modifying
	@Query("UPDATE RestaurantDefaultTime rdt SET rdt.enabled = false WHERE rdt.restaurant.id =:id AND rdt.time in :timelist")
	void disabledInTimeAndRestaurantId(@Param("timelist")List<LocalTime> disabledTimeList, @Param("id")Integer id);

	@Modifying
	@Query("UPDATE RestaurantDefaultTime rdt SET rdt.enabled = true WHERE rdt.restaurant.id =:id AND rdt.time in :timelist")
	void abledInTimeAndRestaurantId(@Param("timelist")List<LocalTime> abledTimeList, @Param("id")Integer id);

}
