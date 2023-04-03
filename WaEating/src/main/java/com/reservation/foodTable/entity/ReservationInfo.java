package com.reservation.foodTable.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.BatchSize;



@Entity
@BatchSize(size = 10)
public class ReservationInfo {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="reservation_info_id")
	private Integer id;
	
	@Column()
	private String reservationName;
	@Column()
	private String reservationPhone;
	@Column()
	private String extraRequirement;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="member_id",nullable=false)
	private Member member;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="restaurant_id",nullable=false)
	private Restaurant restaurant;
	

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="restaurant_ava_time_id",nullable=false)
	private RestaurantAvaTime restaurantAvaTime;
	
	@Column(nullable=false)
	private int people;
	
	@Column(nullable=false)
	private int totalAmount;
	
	@Column()
	private String impUid;

	@Column()
	private String merchantUid;
	
	@OneToMany(mappedBy="reservationInfo",cascade = CascadeType.ALL,orphanRemoval=true)
	@BatchSize(size=20)
	private Set<ReservationOrderInfo> reservationOrderInfo = new HashSet<>();
	
	// 리뷰랑 양방향 보류
	// 이것을 가져 왔을 때 Null 이고  restaurantAvaTime의 date 와 time이 현재보다 이전일 때만 댓글을 달 수 있도록
	@OneToOne(mappedBy="reservationInfo",cascade = CascadeType.REMOVE,fetch=FetchType.LAZY)
	private Review review;

	public ReservationInfo(Member member ,int restaurantAvaTime,int restaurant,int people) {
		System.out.println("생성자 주입됨");
		this.member = member;
		this.restaurantAvaTime = new RestaurantAvaTime(restaurantAvaTime);
		this.restaurant = new Restaurant(restaurant);
		this.people = people;
		totalAmount = 0;
	}
	
	
	public ReservationInfo() {
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Member getMember() {
		return member;
	}

	public String getImpUid() {
		return impUid;
	}


	public void setImpUid(String impUid) {
		this.impUid = impUid;
	}


	public String getMerchantUid() {
		return merchantUid;
	}


	public void setMerchantUid(String merchantUid) {
		this.merchantUid = merchantUid;
	}


	public void setMember(Member member) {
		this.member = member;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		System.out.println("Restaurant 세터 주입됨");
		this.restaurant = restaurant;
	}

	public RestaurantAvaTime getRestaurantAvaTime() {
		
		return restaurantAvaTime;
	}

	public void setRestaurantAvaTime(RestaurantAvaTime restaurantAvaTime) {
		System.out.println("RestaurantAvaTime 세터 주입됨");
		this.restaurantAvaTime = restaurantAvaTime;
	}

	public int getPeople() {
		return people;
	}

	public void setPeople(int people) {
		System.out.println("People 세터 주입됨");
		this.people = people;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Set<ReservationOrderInfo> getReservationOrderInfo() {
		return reservationOrderInfo;
	}

	public void setReservationOrderInfo(Set<ReservationOrderInfo> orderInfo) {
		this.reservationOrderInfo = orderInfo;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}
	
	public void changeTotalPrice(int price) {
		this.totalAmount = price;
	}


	@Override
	public String toString() {
		return "ReservationInfo [id=" + id + ", restaurantId=" + restaurant.getId() + ", people=" + people + ", totalAmount="
				+ totalAmount + "]";
	}
	
}
