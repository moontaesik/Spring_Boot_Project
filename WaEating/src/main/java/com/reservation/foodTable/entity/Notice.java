package com.reservation.foodTable.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Notice {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="notice_id")
	private Integer id;
	
	@Column
	private String title;
	
	@Lob
	@Column
	private String content;
	
	@Column
	private Integer viewCount;
	
	@CreatedDate
	private LocalDateTime createDate;
	@LastModifiedDate
	private LocalDateTime lastUpdateDate;
	
	public void changeNoticeDetails(String title, String content) {
		this.title = title;
		this.content = content;
	}
	
	
	public Notice(String title, String content) {
	
		this();
		this.title = title;
		this.content = content;
	
	}
	public Notice() {
		
		viewCount=1;
	}
	public void increaseViewCount() {
		viewCount++;
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
	public Integer getViewCount() {
		return viewCount;
	}
	public LocalDateTime getCreateDate() {
		return createDate;
	}
	public LocalDateTime getLastUpdateDate() {
		return lastUpdateDate;
	}
	
	public LocalDate registerDate() {
		return createDate.toLocalDate();
	}
	
	
	
}
