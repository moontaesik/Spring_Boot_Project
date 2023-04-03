package com.reservation.foodTable.dto;

public class OrderMenu {

	private int id;
	private int quantity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "OrderMenu [id=" + id + ", quantity=" + quantity + "]";
	}

}
