package com.reservation.foodTable.dto;

import com.reservation.foodTable.entity.FAQCategory;

public class FAQCategoryDTO {

	private Integer id;
	private String name;
	
	public Integer getId() {
		return id;
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
	public FAQCategoryDTO(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public FAQCategoryDTO() {
		super();
	}
	public FAQCategoryDTO(FAQCategory fAQCategory) {
		id=fAQCategory.getId();
		name=fAQCategory.getName();
	}
	
}
