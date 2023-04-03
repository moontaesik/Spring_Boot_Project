package com.reservation.foodTable.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

	@Entity
	public class FAQCategory {
	
		@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
		@Column(name="FAQ_Category_id")
		private Integer id;
		
		@Column(unique=true)
		private String name;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public FAQCategory(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public FAQCategory() {
		super();
	}

	public FAQCategory(String name) {
		super();
		this.name = name;
	}
	public FAQCategory(Integer id) {
		super();
		this.id = id;
	}
	
	
	
	
}
