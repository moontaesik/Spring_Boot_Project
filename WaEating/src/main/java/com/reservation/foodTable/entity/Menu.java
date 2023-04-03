package com.reservation.foodTable.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;



@Entity
public class Menu {
	
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="menu_id")
	private Integer id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="restaurant_id" ,nullable=false)
	private Restaurant restaurant;
	
	@Column(nullable=false)
	private String name;
	
	@Column(nullable=false)
	private int price;
	
	//@Column(nullable=false)
	private String explanation;
	
	@Column()
	private String imagePath;
	
	@Column(nullable=false)
	private boolean representative;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="category_id")
	private Category category;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="menu_category_id")
	private MenuCategory menuCategory;

	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public MenuCategory getMenuCategory() {
		return menuCategory;
	}

	public void setMenuCategory(MenuCategory menuCategory) {
		this.menuCategory = menuCategory;
	}

	public Menu() {
		super();
	}

	public Menu(Integer id) {
		super();
		this.id = id;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", name=" + name + ", price=" + price + "]";
	}

	public boolean isRepresentative() {
		return representative;
	}

	public void setRepresentative(boolean representative) {
		this.representative = representative;
	}
	@Transient
	public String photosImagePath() {
		if(id==null || imagePath ==null || imagePath=="") return "/img/12.png";
		return this.imagePath;
	}
	
	
	
	
}
