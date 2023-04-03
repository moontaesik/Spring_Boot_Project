package com.reservation.foodTable.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.reservation.foodTable.entity.ReservationInfo;

public class ReservationDTO {

	private Integer id;
	private Integer restaurantId;
	private String restaurantName;
	private LocalDate reservationDate;
	private LocalTime reservationTime;
	private int bookPeople;
	private int amount;
	private boolean hasComment;
	private String reservationName;
	private String reservationPhone;
	private String extraRequirement; 

	// 예약 시간이 지낫냐?
	public boolean isAfter() {
		return LocalDateTime.now().isAfter(LocalDateTime.of(reservationDate, reservationTime));
	}
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

	public boolean isHasComment() {
		return hasComment;
	}
	public void setHasComment(boolean hasComment) {
		this.hasComment = hasComment;
	}

	public String reservationDate() {
		return LocalDateTime.of(reservationDate, reservationTime).toString().replace("T", " ");
	}

	public ReservationDTO(ReservationInfo reservationInfo) {

		id = reservationInfo.getId();
		restaurantId = reservationInfo.getRestaurant().getId();
		restaurantName = reservationInfo.getRestaurant().getName();
		reservationDate = reservationInfo.getRestaurantAvaTime().getDate();
		reservationTime = reservationInfo.getRestaurantAvaTime().getRestaurantDefaultTime().getTime();
		bookPeople = reservationInfo.getPeople();
		amount = reservationInfo.getTotalAmount();
		hasComment = reservationInfo.getReview() != null;
		reservationName	= reservationInfo.getReservationName();
		reservationPhone = reservationInfo.getReservationPhone();
		extraRequirement = reservationInfo.getExtraRequirement();
	}
	

	public ReservationDTO(Integer id, String restaurantName, LocalDate reservationDate, LocalTime reservationTime,
			int bookPeople) {
		this.id = id;
		this.restaurantName = restaurantName;
		this.reservationDate = reservationDate;
		this.reservationTime = reservationTime;
		this.bookPeople = bookPeople;
	}

	public ReservationDTO() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public LocalDate getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(LocalDate reservationDate) {
		this.reservationDate = reservationDate;
	}

	public LocalTime getReservationTime() {
		return reservationTime;
	}

	public void setReservationTime(LocalTime reservationTime) {
		this.reservationTime = reservationTime;
	}

	public int getBookPeople() {
		return bookPeople;
	}

	public void setBookPeople(int bookPeople) {
		this.bookPeople = bookPeople;
	}

	
	public Integer getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getReservationName() {
		return reservationName;
	}

	public void setReservationName(String reservationName) {
		this.reservationName = reservationName;
	}

	public String getReservationPhone() {
		return reservationPhone;
	}

	public void setReservationPhone(String reservationPhone) {
		this.reservationPhone = reservationPhone;
	}

	public String getExtraRequirement() {
		return extraRequirement;
	}

	public void setExtraRequirement(String extraRequirement) {
		this.extraRequirement = extraRequirement;
	}

	@Override
	public String toString() {
		return "ReservationDTO [id=" + id + ", restaurantName=" + restaurantName + ", reservationDate="
				+ reservationDate + ", reservationTime=" + reservationTime + ", bookPeople=" + bookPeople + "]";
	}

}
