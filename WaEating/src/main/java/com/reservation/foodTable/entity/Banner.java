package com.reservation.foodTable.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Banner {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="banner_id")
	private Integer id;
	
	@Column(name="banner_name")
	private String photos;

	@Override
	public String toString() {
		return "Banner [id=" + id + ", photos=" + photos + "]";
	}

	public Banner(Integer id, String photos) {
		super();
		this.id = id;
		this.photos = photos;
	}
	
	@Transient
	public String getPhotosImagePath() {
		if (id == null || photos == null) return "/banner/default-img.png";
		return "/banner/" + this.id + "/" + this.photos;
	}

	public Banner() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}
	
	
}
