package com.reservation.foodTable.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.reservation.foodTable.entity.Restaurant;
import com.reservation.foodTable.entity.RestaurantDefaultTime;
import com.reservation.foodTable.repository.RestaurantDefaultTimeRepository;

@Service
public class RestaurantDefaultTimeService {
	
	private final RestaurantDefaultTimeRepository restaurantDefaultTimeRepository;

	public RestaurantDefaultTimeService(RestaurantDefaultTimeRepository restaurantDefaultTimeRepository) {
		this.restaurantDefaultTimeRepository = restaurantDefaultTimeRepository;
	}

	public List<RestaurantDefaultTime> get() {
		return restaurantDefaultTimeRepository.findAll();
	}

	public List<RestaurantDefaultTime> gettime(Restaurant id) {
		return restaurantDefaultTimeRepository.findByparentId(id);
	}
	
	

}
