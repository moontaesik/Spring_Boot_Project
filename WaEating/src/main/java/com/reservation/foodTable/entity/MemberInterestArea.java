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
public class MemberInterestArea {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="member_interest_area_id")
	private Integer id;
		
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="area_id",nullable=false)
	private Area area;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="member_id",nullable=false)
	private Member member;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public MemberInterestArea(int areaId, int memberId) {
		super();
		this.area = new Area(areaId);
		this.member = new Member(memberId);
	}

	public MemberInterestArea() {
		
	}
	
	
}
