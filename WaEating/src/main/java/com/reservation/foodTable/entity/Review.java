package com.reservation.foodTable.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.reservation.foodTable.embeddedType.Score;



@Entity
public class Review {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="review_id")
	private Integer id;
	
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="reservationInfo_id", nullable=false)
	private ReservationInfo reservationInfo;
	
	//db에 직접 text로 바꾸는게 좋을 듯 하다.
	@Column(nullable=false)
	private String content;
	
	@Embedded
	private Score score;
	
	// 영속성 전이 없이 해당 리뷰를 지울 때 영속성 전이 속성은 안넣도 되는지 생각해보자
	@OneToOne(mappedBy="review" ,fetch=FetchType.LAZY,cascade=CascadeType.REMOVE)
	private OwnerComment ownerComment;

	
	   @Override
	   public String toString() {
	       StringBuilder sb = new StringBuilder();
	       sb.append("Review [id=").append(id)
	           .append(", reservationInfo=").append(reservationInfo)
	           .append(", content=").append(content)
	           .append(", score=").append(score.toString());
	       
	       if (ownerComment != null) {
	           sb.append(", ownerComment=").append(ownerComment.getContent())
	               .append(", ownerCommentId=").append(ownerComment.getId());
	       } else {
	           sb.append(", ownerComment=null, ownerCommentId=null");
	       }
	       
	       sb.append("]");
	       return sb.toString();
	   }
	   
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ReservationInfo getReservationInfo() {
		return reservationInfo;
	}

	public void setReservationInfo(ReservationInfo reservationInfo) {
		this.reservationInfo = reservationInfo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	public OwnerComment getOwnerComment() {
		return ownerComment;
	}

	public void setOwnerComment(OwnerComment ownerComment) {
		this.ownerComment = ownerComment;
	}
	

	
	
}
