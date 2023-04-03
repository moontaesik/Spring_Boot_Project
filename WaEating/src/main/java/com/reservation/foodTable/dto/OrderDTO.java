package com.reservation.foodTable.dto;

import com.reservation.foodTable.entity.Menu;
import com.reservation.foodTable.entity.ReservationOrderInfo;

public class OrderDTO {

	private String name;
	private Integer id;
	private int quantity;
	private int price;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int totalPrice() {
		return price*quantity;
	}
	
	public String totalPriceForm() {
		return String.format("%,d원",price*quantity);
	}
	public OrderDTO(String name, Integer id, int quantity, int price) {
		super();
		this.name = name;
		this.id = id;
		this.quantity = quantity;
		this.price = price;
	}
	
	public OrderDTO(String name, int quantity, int price) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}
	
	public OrderDTO(String name, long quantity) {
		super();
		this.name = name;
		this.quantity = (int)quantity;

	}
	public OrderDTO() {
		super();
	}
	public OrderDTO(ReservationOrderInfo reservationOrderInfo) {
		Menu menu = reservationOrderInfo.getMenu();
		
		id=menu.getId();
		price = menu.getPrice();
		name = menu.getName();
		quantity = reservationOrderInfo.getAmount();
	}
	@Override
	public String toString() {
		return "OrderDTO [name=" + name + ", id=" + id + ", quantity=" + quantity + ", price=" + price + "]";
	}
	
	
}
