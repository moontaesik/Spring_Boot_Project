package com.reservation.foodTable.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.reservation.foodTable.exception.NotEnoughAvailableTablesException;



@Entity
public class RestaurantAvaTime {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="restaurant_ava_time_id")
	private Integer id;
	
	
	@Column(nullable=false)
	private LocalDate date;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="restaurant_default_time_id")
	private RestaurantDefaultTime restaurantDefaultTime;
	
	@Column(nullable=false)
	private int tableInventory;
	
	public RestaurantAvaTime() {
		super();
	}

	public RestaurantAvaTime(Integer id) {
		super();
		this.id = id;
	}

	public RestaurantAvaTime(LocalDate date, RestaurantDefaultTime restaurantDefaultTime, int tableInventory) {
		super();
		this.date = date;
		this.restaurantDefaultTime = restaurantDefaultTime;
		this.tableInventory = tableInventory;
	}

	public RestaurantAvaTime(Integer id, LocalDate date, RestaurantDefaultTime restaurantDefaultTime,
			int tableInventory) {
		super();
		this.id = id;
		this.date = date;
		this.restaurantDefaultTime = restaurantDefaultTime;
		this.tableInventory = tableInventory;
	}



	public Integer getId() {
		return id;
	}

	public LocalDate getDate() {
		return date;
	}

	public RestaurantDefaultTime getRestaurantDefaultTime() {
		return restaurantDefaultTime;
	}

	public int getTableInventory() {
		return tableInventory;
	}
	
	public void reduceAvailableTables(int people)throws NotEnoughAvailableTablesException{
		
		int table = (int)Math.ceil(people/4.0);
		if(tableInventory < table) {
			throw new NotEnoughAvailableTablesException("현재 이용가능한 테이블 수 는 "+tableInventory+"입니다");
		}
		tableInventory -=table;
	}
	
	public void expandAvailableTables(int people){
		
		tableInventory +=(int)Math.ceil(people/4.0);
	}
	
	
}
