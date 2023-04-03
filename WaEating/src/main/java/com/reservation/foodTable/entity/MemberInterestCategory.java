package com.reservation.foodTable.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;


@Entity
public class MemberInterestCategory {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="member_interest_category_id")
	private Integer id;
	

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="category_id")
	private Category category;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="member_id",nullable=false)
	private Member member;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public MemberInterestCategory(int categoryId, int memberId) {
		super();
		this.category = new Category(categoryId);
		this.member = new Member(memberId);
	}

	public MemberInterestCategory() {
		super();
	}
	
	
}
