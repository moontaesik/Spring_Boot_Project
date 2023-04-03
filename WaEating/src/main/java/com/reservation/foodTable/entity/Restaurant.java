package com.reservation.foodTable.entity;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.reservation.foodTable.embeddedType.BusinessHour;
import com.reservation.foodTable.embeddedType.Score;
import com.reservation.foodTable.enumClass.PriceRange;

@Entity
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "restaurant_id")
	private Integer id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String phone;

	@Embedded
	private BusinessHour businessHour;

	@Embedded
	private Score score;

	@Column(nullable = false)
	private int maxTable;

	@Column()
	private String resIntro;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PriceRange priceRange;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "si")
	private Area si;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gun")
	private Area gun;

	public String getResIntro() {
		return resIntro;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dong")
	private Area dong;

	@Column(nullable = false)
	private String specificArea;

	@OneToMany(mappedBy = "restaurant")
	private Set<Menu> menuList;

	@OneToMany(mappedBy = "restaurant")
	private Set<ReservationInfo> reservationinfo;

	@OneToMany(mappedBy = "restaurant")
	private Set<RestaurantCategory> restaurantCategory = new HashSet<>();

	@Column
	private String img;

	@OneToOne(mappedBy = "restaurant")
	private Member member;
	
	@Column
	private Double x;
	
	@Column
	private Double y;

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Restaurant(Integer id) {
		super();
		this.id = id;
	}

	public Set<RestaurantCategory> getRestaurantCategory() {
		return restaurantCategory;
	}

	public Restaurant() {

	}

	public PriceRange getPriceRange() {
		return priceRange;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setBusinessHour(BusinessHour businessHour) {
		this.businessHour = businessHour;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	public void setMaxTable(int maxTable) {
		this.maxTable = maxTable;
	}

	public void setResIntro(String resIntro) {
		this.resIntro = resIntro;
	}

	public void setPriceRange(PriceRange priceRange) {
		this.priceRange = priceRange;
	}

	public void setSi(Area si) {
		this.si = si;
	}

	public void setGun(Area gun) {
		this.gun = gun;
	}

	public void setDong(Area dong) {
		this.dong = dong;
	}

	public void setSpecificArea(String specificArea) {
		this.specificArea = specificArea;
	}

	public void setMenuList(Set<Menu> menuList) {
		this.menuList = menuList;
	}

	public void setReservationinfo(Set<ReservationInfo> reservationinfo) {
		this.reservationinfo = reservationinfo;
	}

	public void setRestaurantCategory(Set<RestaurantCategory> restaurantCategory) {
		this.restaurantCategory = restaurantCategory;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Area getSi() {
		return si;
	}

	public Area getGun() {
		return gun;
	}

	public Area getDong() {
		return dong;
	}

	public String getSpecificArea() {
		return specificArea;
	}

	public String getPhone() {
		return phone;
	}

	public BusinessHour getBusinessHour() {
		return businessHour;
	}

	public Set<Menu> getMenuList() {
		return menuList;
	}

	public Score getScore() {
		return score;
	}

	public int getMaxTable() {
		return maxTable;
	}

	public String totalAdrress() {

		return si.getName() + " " + gun.getName() + " " + dong.getName() + " " + specificArea;
	}

	public String getAdrress() {

		return si.getName() + " " + gun.getName() + " " + dong.getName() + " ";
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public LocalTime getOpenTime() {
		return businessHour.getOpenTime();
	}

	public LocalTime getCloseTime() {
		return businessHour.getCloseTime();
	}

	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", name=" + name + ", phone=" + phone + ", maxTable=" + maxTable + "]";
	}

	public void chageScroe(Score score) {
		this.score = score;
	}

	@Transient
	public String photosImagePath() {
		if (id == null || img == null) {
			return "/img/12.png";
		}
		return "/restaurant-photos/" + this.id + "/restaurant-Img/" + this.img;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

}
