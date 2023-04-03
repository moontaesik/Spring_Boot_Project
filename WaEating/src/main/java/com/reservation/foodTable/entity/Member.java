package com.reservation.foodTable.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.reservation.foodTable.enumClass.Role;

@Entity
public class Member {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="member_id")
	private Integer id;
	
	@Column(unique = true, nullable = false)
	private String userId;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String phone;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@OneToMany(mappedBy="member")
	private Set<MemberInterestCategory> categories = new HashSet<>();
	
	@OneToMany(mappedBy="member")
	private Set<MemberInterestArea> areas = new HashSet<>();
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="restaurant_id")
	private Restaurant restaurant;
	

	public Member(Integer id) {
		super();
		this.id = id;
	}

	public Member() {
		
		super();
		role = Role.ROLE_USER;
	}

	
	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Set<MemberInterestCategory> getCategories() {
		return categories;
	}

	public void setCategories(Set<MemberInterestCategory> categories) {
		this.categories = categories;
	}

	public Set<MemberInterestArea> getAreas() {
		return areas;
	}

	public void setAreas(Set<MemberInterestArea> areas) {
		this.areas = areas;
	}

	
	
}
