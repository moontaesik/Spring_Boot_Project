package com.reservation.foodTable.entity;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



@Entity
public class RestaurantDefaultTime {



	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="restaurant_default_time_id")
	private Integer id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="restaurant_id",nullable=false)
	private Restaurant restaurant; 

	@Column(nullable=false)
	private LocalTime time;
	
	@Column
	private boolean enabled;

	public RestaurantDefaultTime() {
		super();
	}
	public RestaurantDefaultTime(Integer id) {
		super();
		this.id = id;
	}

	public RestaurantDefaultTime(String time, Restaurant addRestaurant) {
		this.restaurant = addRestaurant;
		this.time = LocalTime.parse(time);
		enabled = true;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}
	
	
}
