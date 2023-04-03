package com.reservation.foodTable.dto;

import java.util.List;

public class OrderInfoDTO {
	
	private List<OrderMenu> data;
	private int price;
	
	
	public List<OrderMenu> getData() {
		return data;
	}


	public void setData(List<OrderMenu> data) {
		this.data = data;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}

	
	@Override
	public String toString() {
		return "OrderInfoDTO [data=" + data + ", price=" + price + "]";
	}



}
