package com.reservation.foodTable.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class OrderinfoDTOvOwner {

	private LocalDate date;
	private LocalTime time;
	private Integer people;
	private Integer totalAmount;
	private String phone;
	private String name;
	private String detail;
	
	
	public OrderinfoDTOvOwner(LocalDate date,LocalTime time, Integer people, Integer totalAmount, String phone, String name,
			String detail) {
		super();
		this.date =date;
		this.time = time;
		this.people = people;
		this.totalAmount = totalAmount;
		this.phone = phone;
		this.name = name;
		this.detail = detail;
	}
	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}
	public Integer getPeople() {
		return people;
	}
	public void setPeople(Integer people) {
		this.people = people;
	}
	public Integer getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	@Override
	public String toString() {
		return "OrderinfoDTOvOwner [date=" + date + ", time=" + time + ", people=" + people + ", totalAmount="
				+ totalAmount + ", phone=" + phone + ", name=" + name + ", detail=" + detail + "]";
	}
	
	
}
