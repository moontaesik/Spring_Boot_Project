package com.reservation.foodTable.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.BatchSize;


@BatchSize(size = 100)
@Entity
public class OwnerComment {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="owner_comment_id")
	private Integer id;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="review_id",nullable=false)
	private Review review;
	
	@Column(nullable=false)
	private String content;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
