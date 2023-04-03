package com.reservation.foodTable.Service;

import org.springframework.stereotype.Service;

import com.reservation.foodTable.entity.Category;
import com.reservation.foodTable.entity.Restaurant;
import com.reservation.foodTable.entity.RestaurantCategory;
import com.reservation.foodTable.repository.RestaurantCategoryRepository;
import com.reservation.foodTable.repository.RestaurantRepository;

@Service
public class RestaurantCategoryService {

private final RestaurantCategoryRepository restaurantCategoryRepository;
private final RestaurantRepository restaurantRepository;
	
	public RestaurantCategoryService(RestaurantRepository restaurantRepository,RestaurantCategoryRepository restaurantCategoryRepository) {
		this.restaurantCategoryRepository = restaurantCategoryRepository;
		this.restaurantRepository = restaurantRepository;
	}

	public void save(Category category, Restaurant saveRestaurant) {
		RestaurantCategory restaurantCategory = new RestaurantCategory();
		restaurantCategory.setCategory(category);
		restaurantCategory.setRestaurant(saveRestaurant);
		restaurantCategoryRepository.save(restaurantCategory);
	}

	public int get(Integer id) {
		return restaurantCategoryRepository.finbyReataurant(restaurantRepository.findById(id).get()).getCategory().getId();
	}

	public void update(Category category, Restaurant saveRestaurant) {
		RestaurantCategory restaurantCategory = new RestaurantCategory();
		restaurantCategory.setCategory(category);
		restaurantCategory.setId(restaurantCategoryRepository.finbyReataurant(saveRestaurant).getId());
		restaurantCategory.setRestaurant(saveRestaurant);
		restaurantCategoryRepository.save(restaurantCategory);
	}

}
