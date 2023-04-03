package com.reservation.foodTable.dto;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.reservation.foodTable.entity.Restaurant;
import com.reservation.foodTable.entity.RestaurantDefaultTime;
import com.reservation.foodTable.enumClass.PriceRange;

public class RestaurantEditDTO {
	public Restaurant restaurant;
	private List<RestaurantDefaultTime> restaurantAvaTimes;
	private Integer category;
	private String openTime;
	private String closeTime;
	private String priceRange;

	public RestaurantEditDTO(LocalTime openTime, LocalTime closeTime, PriceRange priceRange, Restaurant restaurant, List<RestaurantDefaultTime> restaurantAvaTimes) {
		LocalTime opentime = openTime;
		LocalTime closetime = closeTime;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss");
		this.openTime = opentime.format(formatter);
		this.closeTime = closetime.format(formatter2);


		this.restaurantAvaTimes = restaurantAvaTimes;
		this.restaurant = restaurant;
		this.priceRange = String.valueOf(priceRange);
	}

	public RestaurantEditDTO() {

	}

	public String getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}





	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}


	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public List<RestaurantDefaultTime> getRestaurantAvaTimes() {
		return restaurantAvaTimes;
	}

	public void setRestaurantAvaTimes(List<RestaurantDefaultTime> restaurantAvaTimes) {
		this.restaurantAvaTimes = restaurantAvaTimes;
	}

}
