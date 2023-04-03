package com.reservation.foodTable.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import com.reservation.foodTable.entity.FAQ;

public class FAQPageDTO {
	
	private List<FAQDTO> FAQList;
	private boolean prevIsEnabled;
	
	private boolean nextIsEnabled;
	private List<Integer> pageList = new ArrayList<>();
	
	private int currentPage;
	
	public FAQPageDTO(Page<FAQ> fAQPage) {
		
		FAQList = fAQPage.map(FAQDTO::new).getContent();
		
		int totalPage = fAQPage.getTotalPages();
		
		currentPage = fAQPage.getNumber()+1;

		int pages = (int)Math.ceil(currentPage/5.0); 
		
		prevIsEnabled =  pages!= 1;
		
		nextIsEnabled = pages < (int)Math.ceil(totalPage/5.0);
		
		for(int i =(pages-1)*5+1; i<pages*5+1 && i <= totalPage; i++) {
			pageList.add(i);
		}
		
	}
	
	
	public List<FAQDTO> getFAQList() {
		return FAQList;
	}

	public void setFAQList(List<FAQDTO> fAQList) {
		FAQList = fAQList;
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
	
	

}
