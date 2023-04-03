package com.reservation.foodTable.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import com.reservation.foodTable.entity.ReservationInfo;

public class OrderinfoDetailsPageDTO {
	
	private List<OrderinfoDetailsDTO>	reservationInfoList;
	private boolean prevIsEnabled;
	
	private boolean nextIsEnabled;
	private List<Integer> pageList = new ArrayList<>();
	
	private int currentPage;
	
	public OrderinfoDetailsPageDTO(Page<ReservationInfo> reservationInfoPage) {
		
		reservationInfoList = reservationInfoPage.map(reservationInfo->{
			return new OrderinfoDetailsDTO(reservationInfo,false);}).getContent();
		
		int totalPage = reservationInfoPage.getTotalPages();
		currentPage = reservationInfoPage.getNumber()+1;
		int pages = (int)Math.ceil(currentPage/5.0); 
		
		prevIsEnabled =  pages!= 1;
		
		nextIsEnabled = pages < (int)Math.ceil(totalPage/5.0);
		for(int i =(pages-1)*5+1; i<pages*5+1 && i <= totalPage; i++) {
			pageList.add(i);
		}
		
	}

	public List<OrderinfoDetailsDTO> getReservationInfoList() {
		return reservationInfoList;
	}

	public void setReservationInfoList(List<OrderinfoDetailsDTO> reservationInfoList) {
		this.reservationInfoList = reservationInfoList;
	}

	public boolean isPrevIsEnabled() {
		return prevIsEnabled;
	}

	public void setPrevIsEnabled(boolean prevIsEnabled) {
		this.prevIsEnabled = prevIsEnabled;
	}

	public boolean isNextIsEnabled() {
		return nextIsEnabled;
	}

	public void setNextIsEnabled(boolean nextIsEnabled) {
		this.nextIsEnabled = nextIsEnabled;
	}

	public List<Integer> getPageList() {
		return pageList;
	}

	public void setPageList(List<Integer> pageList) {
		this.pageList = pageList;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	@Override
	public String toString() {
		return "OrderinfoDetailsPageDTO [reservationInfoList=" + reservationInfoList + ", prevIsEnabled="
				+ prevIsEnabled + ", nextIsEnabled=" + nextIsEnabled + ", pageList=" + pageList + ", currentPage="
				+ currentPage + "]";
	}
	
	
	

}
