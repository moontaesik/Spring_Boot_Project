package com.reservation.foodTable.dto;

import com.reservation.foodTable.entity.Menu;

public class MenuDTO {

	private Integer id;
	private String name;
	private String imagePath;
	private int price;
	private String formatPrice;
	private boolean representative;
	
	public boolean isRepresentative() {
		return representative;
	}

	@Override
	public String toString() {
		return "MenuInfo [name=" + name + ", imagePath=" + imagePath + ", price=" + price + ", formatPrice="
				+ formatPrice +  "]";
	}

	public MenuDTO(Menu menu) {
		id = menu.getId();
		name = menu.getName();
		imagePath = menu.getImagePath();
		price = menu.getPrice();
		formatPrice = String.format("%,d 원", price);
		representative = menu.isRepresentative();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getImagePath() {
		return imagePath;
	}

	public int getPrice() {
		return price;
	}

	public String getFormatPrice() {
		return formatPrice;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setFormatPrice(String formatPrice) {
		this.formatPrice = formatPrice;
	}


	public void setRepresentative(boolean representative) {
		this.representative = representative;
	}
	

}
