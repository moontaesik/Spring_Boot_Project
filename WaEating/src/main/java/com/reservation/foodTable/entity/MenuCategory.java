package com.reservation.foodTable.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



@Entity
public class MenuCategory {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="menu_category_id")
	private Integer id;
	
	@ManyToOne()
	@JoinColumn(name="restaurant_id")
	private Restaurant restaurant;
	
	@Column(nullable=false)
	private int orderNumber;
	
	@Column(nullable=false)
	private String name;

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

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "MenuCategory [id=" + id +  ", orderNumber=" + orderNumber + ", name="
				+ name + "]";
	}
	
	public MenuCategory() {
		super();
	}

	public MenuCategory(String name,Integer restaurantId, int orderNumber) {
		super();
		this.restaurant = new Restaurant(restaurantId);
		this.orderNumber = orderNumber;
		this.name = name;
	}

	
	
	
}
