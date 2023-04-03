package com.reservation.foodTable.embeddedType;

import javax.persistence.Column;
import javax.persistence.Embeddable;

// 세터 절대 만들지 말것

@Embeddable
public class Score {

	@Column(columnDefinition = "float default 0")
	private float tasteScore;

	@Column(columnDefinition = "float default 0")
	private float serviceScore;

	@Column(columnDefinition = "float default 0")
	private float priceScore;

	@Column(columnDefinition = "float default 0")
	private float totalScore;

	private void calTotalScore() {

		float totalScore = 0.0f;

		totalScore = (tasteScore + serviceScore + priceScore) / (3.0f);
		this.totalScore = (float)(Math.round(totalScore*100)/100.0);
	}

	public Score(float tasteScore, float serviceScore, float priceScore) {

		this.tasteScore = tasteScore;
		this.serviceScore = serviceScore;
		this.priceScore = priceScore;
		this.calTotalScore();
	}

	public Score(double tasteScore, double serviceScore, double priceScore) {

		this.tasteScore = (float) tasteScore;
		this.serviceScore = (float) serviceScore;
		this.priceScore = (float) priceScore;
		this.calTotalScore();
	}

	public Score(double tasteScore, double serviceScore, double priceScore, double totalScore) {

		this.tasteScore = (float) tasteScore;
		this.serviceScore = (float) serviceScore;
		this.priceScore = (float) priceScore;
		this.totalScore = (float) totalScore;
	}

	public Score() {
		this.tasteScore = 0;
		this.serviceScore = 0;
		this.priceScore = 0;
		this.totalScore = 0;

	}

	public float getTasteScore() {
		return tasteScore;
	}

	public float getServiceScore() {
		return serviceScore;
	}

	public float getPriceScore() {
		return priceScore;
	}

	public float getTotalScore() {
		return totalScore;
	}

}
