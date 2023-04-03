package com.reservation.foodTable.dto;

import com.reservation.foodTable.entity.FAQ;

public class FAQDTO {

	private Integer id;
	private String title;
	private String content;
	private String categoryName;
	
	
	public FAQDTO(FAQ fAQ) {
		
		id=fAQ.getId();
		title=fAQ.getTitle();
		content = fAQ.getContent();
		categoryName = fAQ.getfAQCategory().getName();
	}
	
	public FAQDTO() {
		

	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public String toString() {
		return "FAQDTO [id=" + id + ", title=" + title + ", content=" + content + ", categoryName=" + categoryName
				+ "]";
	}
	
	
	
}
