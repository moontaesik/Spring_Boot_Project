package com.reservation.foodTable.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;




@Entity
public class Area {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="area_id")
	private Integer id; 
	
	@Column(nullable=false)
	private String name;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="parent_id")
	private Area parent;
	
	@OneToMany(mappedBy="parent")
	private Set<Area> child;
	
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

	public Area getParent() {
		return parent;
	}

	public void setParent(Area parent) {
		this.parent = parent;
	}

	public Set<Area> getChild() {
		return child;
	}

	public void setChild(Set<Area> child) {
		this.child = child;
	}

	@Override
	public String toString() {
		return "Area [id=" + id + ", name=" + name + "]";
	}

	public Area() {
		super();
	}

	public Area(Integer id) {
		super();
		this.id = id;
	}

	
}
