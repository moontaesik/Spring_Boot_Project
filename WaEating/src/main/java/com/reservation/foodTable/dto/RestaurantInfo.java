package com.reservation.foodTable.dto;

import javax.persistence.Transient;

import org.springframework.data.domain.Page;

import com.reservation.foodTable.embeddedType.Score;
import com.reservation.foodTable.entity.Restaurant;

public class RestaurantInfo {

	private Integer id;

	private String name;

	private String address;

	private String phone;

	private String openCloseTimes;

	private float tasteScore;

	private float serviceScore;

	private float priceScore;

	private float totalScore;

	private String imagePathList;

	private double x;
	private double y;
	
	private String ownername;
	
	private String userId;

	public RestaurantInfo(Restaurant restaurant) {

		id = restaurant.getId();
		name = restaurant.getName();
		address = restaurant.totalAdrress();
		phone = restaurant.getPhone();
		openCloseTimes = restaurant.getBusinessHour().openCloseTime();
		Score score = restaurant.getScore();
		tasteScore = score.getTasteScore();
		serviceScore = score.getServiceScore();
		priceScore = score.getPriceScore();
		totalScore = score.getTotalScore();
		x = restaurant.getX();
		y = restaurant.getY();
		imagePathList = restaurant.getImg();
		if(restaurant.getMember() != null) {
			ownername = restaurant.getMember().getName();
			userId = restaurant.getMember().getUserId();
		}
		
	}

	public RestaurantInfo(Integer id, String name, String totalAdrress, String ownername, String phone, String userId) {
		this.id = id;
		this.name = name;
		this.address = totalAdrress;
		this.ownername = ownername;
		this.phone = phone;
		this.userId = userId;
		
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "RestaurantInfo [id=" + id + ", name=" + name + ", address=" + address + ", phone=" + phone
				+ ", openCloseTimes=" + openCloseTimes + ", tasteScore=" + tasteScore + ", serviceScore=" + serviceScore
				+ ", priceScore=" + priceScore + ", totalScore=" + totalScore + ", imagePathList=" + imagePathList
				+ "]";
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOpenCloseTimes() {
		return openCloseTimes;
	}

	public void setOpenCloseTimes(String openCloseTimes) {
		this.openCloseTimes = openCloseTimes;
	}

	public void setTasteScore(float tasteScore) {
		this.tasteScore = tasteScore;
	}

	public void setServiceScore(float serviceScore) {
		this.serviceScore = serviceScore;
	}

	public String formatTasteScore() {

		return String.format("%.2f", tasteScore);
	}

	public String formatServiceScore() {

		return String.format("%.2f", serviceScore);
	}

	public String formatPriceScore() {

		return String.format("%.2f", priceScore);
	}

	public String formatTotalScore() {

		return String.format("%.2f", totalScore);
	}

	public float getTasteScore() {
		return tasteScore;
	}

	public float getServiceScore() {
		return serviceScore;
	}

	public float getPriceScore() {
		return priceScore;
	}

	public float getTotalScore() {
		return totalScore;
	}

	public void setPriceScore(float priceScore) {
		this.priceScore = priceScore;
	}

	public void setTotalScore(float totalScore) {
		this.totalScore = totalScore;
	}

	public String getImagePathList() {
		return imagePathList;
	}

	public void setImagePathList(String imagePathList) {
		this.imagePathList = imagePathList;
	}

	
	public String getOwnername() {
		return ownername;
	}

	public void setOwnername(String ownername) {
		this.ownername = ownername;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Transient
	public String photosImagePath() {
		if (id == null || imagePathList == null) {
			return "/img/12.png";
		}
		return "/restaurant-photos/" + this.id + "/restaurant-Img/" + this.imagePathList;
	}

}
