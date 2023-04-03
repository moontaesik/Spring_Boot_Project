package com.reservation.foodTable.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.reservation.foodTable.entity.ReservationInfo;
import com.reservation.foodTable.entity.ReservationOrderInfo;

public class OrderinfoDetailsDTO {
	
	private Integer reservationId;
	private String reservationName;
	private String reservationPhone;
	private String extraRequirement;
	private LocalDate date;
	private LocalTime time;
	private int people;
	private int totalAmount;
	private List<OrderDTO> menus = new ArrayList<>();

	@Override
	public String toString() {
		return "OrderinfoDetailsDTO [reservationName=" + reservationName + ", reservationPhone=" + reservationPhone
				+ ", extraRequirement=" + extraRequirement + ", date=" + date + ", time=" + time + ", people=" + people
				+ ", totalAmount=" + totalAmount + ", menus=" + menus + "]";
	}

	public OrderinfoDetailsDTO(ReservationInfo reservationInfo) {

		if (reservationInfo == null) {
			// 예외 던져주자
			return;
		}
		reservationId = reservationInfo.getId();
		reservationName = reservationInfo.getReservationName();
		reservationPhone = reservationInfo.getReservationPhone();
		extraRequirement = reservationInfo.getExtraRequirement();
		people = reservationInfo.getPeople();
		totalAmount = reservationInfo.getTotalAmount();
		for (ReservationOrderInfo data : reservationInfo.getReservationOrderInfo()) {
			menus.add(new OrderDTO(data.getMenu().getName(), data.getAmount(), data.getMenu().getPrice()));
		}
		date = reservationInfo.getRestaurantAvaTime().getDate();
		time = reservationInfo.getRestaurantAvaTime().getRestaurantDefaultTime().getTime();
	}

	public OrderinfoDetailsDTO(ReservationInfo reservationInfo,boolean a) {

		if (reservationInfo == null) {
			// 예외 던져주자
			return;
		}
		reservationId = reservationInfo.getId();
		reservationName = reservationInfo.getReservationName();
		reservationPhone = reservationInfo.getReservationPhone();
		extraRequirement = reservationInfo.getExtraRequirement();
		people = reservationInfo.getPeople();
		totalAmount = reservationInfo.getTotalAmount();
		date = reservationInfo.getRestaurantAvaTime().getDate();
		time = reservationInfo.getRestaurantAvaTime().getRestaurantDefaultTime().getTime();
	}

	public OrderinfoDetailsDTO(String reservationName, String reservationPhone, String extraRequirement, LocalDate date,
			LocalTime time, int people, int totalAmount) {
		super();
		this.reservationName = reservationName;
		this.reservationPhone = reservationPhone;
		this.extraRequirement = extraRequirement;
		this.date = date;
		this.time = time;
		this.people = people;
		this.totalAmount = totalAmount;
	}

	public Integer getReservationId() {
		return reservationId;
	}

	public void setReservationId(Integer reservationId) {
		this.reservationId = reservationId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public OrderinfoDetailsDTO() {
		super();
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

	public int getPeople() {
		return people;
	}

	public void setPeople(int people) {
		this.people = people;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<OrderDTO> getMenus() {
		return menus;
	}

	public void setMenus(List<OrderDTO> menus) {
		this.menus = menus;
	}
	public String ids() {
		return time.format(DateTimeFormatter.ofPattern("HH-mm"));
	}
}
