package com.reservation.foodTable.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.reservation.foodTable.entity.ReservationInfo;

public class ReservationDTOAll{
	
	private Integer id;
	private String restaurantName;
	private LocalDate reservationDate;
	private LocalTime reservationTime;
	private String reservationName;
	private String reservationPhone;
	private String extraRequirement; 
	private int bookPeople;
	private List<Menu> menuList=new ArrayList<>();
	private int totalPrice;
	
	public String formatPrice() {
		return String.format("%,d원", totalPrice);
	}
	public ReservationDTOAll(ReservationInfo reservationInfo) {
		
		id = reservationInfo.getId();
		restaurantName = reservationInfo.getRestaurant().getName();
		reservationDate = reservationInfo.getRestaurantAvaTime().getDate();
		reservationTime = reservationInfo.getRestaurantAvaTime().getRestaurantDefaultTime().getTime();
		reservationName = reservationInfo.getReservationName();
		reservationPhone = reservationInfo.getReservationPhone();
		extraRequirement = reservationInfo.getExtraRequirement();
		bookPeople = reservationInfo.getPeople();
		totalPrice = reservationInfo.getTotalAmount();

		reservationInfo.getReservationOrderInfo().stream().forEach(o->{
		
			System.out.println("들어온 메뉴 id : "+o.getMenu().getId());
			System.out.println("들어온 메뉴 : "+o.getMenu());
			menuList.add(new Menu(o.getMenu().getName(),o.getAmount(),o.getMenu().getPrice()));
			
			}
		);
		
		
		
	}


	

	public ReservationDTOAll(ReservationInfo reservationInfo, OrderInfoDTO data,
			List<com.reservation.foodTable.entity.Menu> menuList) {
		id = reservationInfo.getId();
		restaurantName = reservationInfo.getRestaurant().getName();
		reservationDate = reservationInfo.getRestaurantAvaTime().getDate();
		reservationTime = reservationInfo.getRestaurantAvaTime().getRestaurantDefaultTime().getTime();
		bookPeople = reservationInfo.getPeople();
		totalPrice = data.getPrice();
		reservationName = reservationInfo.getReservationName();
		reservationPhone = reservationInfo.getReservationPhone();
		extraRequirement = reservationInfo.getExtraRequirement();		
		Map<Integer, Integer> myMap = data.getData().stream()
				.collect(Collectors.toMap(OrderMenu::getId, OrderMenu::getQuantity));
		
		menuList.forEach(o->{
			this.menuList.add(new Menu(o.getName(),myMap.get(o.getId()),o.getPrice()));
		});
	}



	static class Menu{
		String name;
		int quantity;
		int price;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		public int getPrice() {
			return price;
		}
		public void setPrice(int price) {
			this.price = price;
		}
		
		@Override
		public String toString() {
			return "Menu [name=" + name + ", quantity=" + quantity + ", price=" + price + "]";
		}
		public Menu(String name, Integer amount, int price) {
			// TODO Auto-generated constructor stub
			this.name = name;
			this.quantity = amount;
			this.price = quantity*price;
		}
		
		public String formatPrice() {
			return String.format("%,d원", price);
		}
		
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getRestaurantName() {
		return restaurantName;
	}


	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}


	public LocalDate getReservationDate() {
		return reservationDate;
	}


	public void setReservationDate(LocalDate reservationDate) {
		this.reservationDate = reservationDate;
	}


	public LocalTime getReservationTime() {
		return reservationTime;
	}


	public void setReservationTime(LocalTime reservationTime) {
		this.reservationTime = reservationTime;
	}


	public int getBookPeople() {
		return bookPeople;
	}


	public void setBookPeople(int bookPeople) {
		this.bookPeople = bookPeople;
	}


	public List<Menu> getMenuList() {
		return menuList;
	}


	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}


	public int getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
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
	@Override
	public String toString() {
		return "ReservationDTOAll [id=" + id + ", restaurantName=" + restaurantName + ", reservationDate="
				+ reservationDate + ", reservationTime=" + reservationTime + ", reservationName=" + reservationName
				+ ", reservationPhone=" + reservationPhone + ", extraRequirement=" + extraRequirement + ", bookPeople="
				+ bookPeople + ", menuList=" + menuList + ", totalPrice=" + totalPrice + "]";
	}



	
}
