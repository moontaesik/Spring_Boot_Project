package com.reservation.foodTable.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class FAQ {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="FAQ_id")
	private Integer id;
	
	@Column
	private String title;
	
	@Lob
	@Column
	private String content;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="FAQ_Category_id")
	private FAQCategory fAQCategory;
	
	public void changeFAQDetails(String title, String content, Integer fAQCategoryId) {
		this.title = title;
		this.content = content;
		this.fAQCategory = new FAQCategory(fAQCategoryId);

	}
	public Integer getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public FAQCategory getfAQCategory() {
		return fAQCategory;
	}

	public FAQ( Integer id,String title, String content, Integer fAQCategoryId) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.fAQCategory = new FAQCategory(fAQCategoryId);
	}
	
	public FAQ( String title, String content, Integer fAQCategoryId) {
		this(null,title,content,fAQCategoryId);

	}
	public FAQ( ) {

	}



	
	
	
	
}
