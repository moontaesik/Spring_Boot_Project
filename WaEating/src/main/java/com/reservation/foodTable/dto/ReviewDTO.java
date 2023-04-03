package com.reservation.foodTable.dto;

import com.reservation.foodTable.embeddedType.Score;
import com.reservation.foodTable.entity.OwnerComment;
import com.reservation.foodTable.entity.Review;


public class ReviewDTO {
	/*
	 * 여기는 댓글에 대한 정보 필드
	 */
	
	private Integer id;
	
	private String content;
	
	// 여기에 닉네임 들어가면 좋겟다.
	private Integer writer;
	
	/*
	 * 여기서 부터는 점수에 대한 필드
	 */
	
	private float tasteScore;
	
	private float serviceScore;
	
	private float priceScore;
	
	private float totalScore;
	
	/*
	 *  댓글에 사장님의 대댓글넣어주는 부분
	 */
	
	private ReplyDTO reply;
	
	
	
	public ReviewDTO(Review review) {
		id = review.getId();
		content = review.getContent();
		
		Score score = review.getScore();
		
		tasteScore = score.getTasteScore();
		serviceScore = score.getServiceScore();
		priceScore = score.getPriceScore();
		totalScore = score.getTotalScore();
		
		OwnerComment ownerComment = review.getOwnerComment();
		if(ownerComment != null) reply = new ReplyDTO(review.getOwnerComment());
		
		// 이부분 아직 기달 ( 닉네임 없어서 id로 저장 )
		writer = review.getReservationInfo().getMember().getId();
	}

	public ReviewDTO() {
		
		tasteScore=5.0f;
		serviceScore=5.0f;
		priceScore=5.0f;
		totalScore=5.0f;
	}
	
	public ReviewDTO(Score score) {
		
		tasteScore = score.getTasteScore();
		serviceScore = score.getServiceScore();
		priceScore = score.getPriceScore();
		totalScore = score.getTotalScore();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getWriter() {
		return writer;
	}

	public void setWriter(Integer writer) {
		this.writer = writer;
	}

	public float getTasteScore() {
		return tasteScore;
	}

	public void setTasteScore(float tasteScore) {
		this.tasteScore = tasteScore;
	}

	public float getServiceScore() {
		return serviceScore;
	}

	public void setServiceScore(float serviceScore) {
		this.serviceScore = serviceScore;
	}

	public float getPriceScore() {
		return priceScore;
	}

	public void setPriceScore(float priceScore) {
		this.priceScore = priceScore;
	}

	public float getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(float totalScore) {
		this.totalScore = totalScore;
	}

	public ReplyDTO getReply() {
		return reply;
	}

	public void setReply(ReplyDTO reply) {
		this.reply = reply;
	}
	
	
}
