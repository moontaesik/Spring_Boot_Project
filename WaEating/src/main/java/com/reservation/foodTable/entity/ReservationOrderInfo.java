package com.reservation.foodTable.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



@Entity
public class ReservationOrderInfo {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="reservation_order_info_id")
	private Integer id;
	
	@Column(nullable=false)
	private Integer amount;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="menu_id",nullable=false)
	private Menu menu;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="reservationInfo_id",nullable=false)
	private ReservationInfo reservationInfo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public ReservationInfo getReservationInfo() {
		return reservationInfo;
	}

	public void setReservationInfo(ReservationInfo reservationInfo) {
		this.reservationInfo = reservationInfo;
	}

	public ReservationOrderInfo(Integer amount, Menu menu, ReservationInfo reservationInfo) {
		super();
		this.amount = amount;
		this.menu = menu;
		this.reservationInfo = reservationInfo;
	}

	public ReservationOrderInfo() {
		super();
	}

	@Override
	public String toString() {
		return "ReservationOrderInfo [id=" + id + ", amount=" + amount + "]";
	}
	
	
}
