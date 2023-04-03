package com.reservation.foodTable.dto;

import com.reservation.foodTable.entity.Area;

public class AreaDTO {
	
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
	
	public AreaDTO(Area area) {
		id=area.getId();
		name=area.getName();
	}

}
