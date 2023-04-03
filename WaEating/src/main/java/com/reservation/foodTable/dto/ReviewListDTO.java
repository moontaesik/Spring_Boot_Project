package com.reservation.foodTable.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import com.reservation.foodTable.exception.NotFoundReviewException;

public class ReviewListDTO {

	private List<ReviewDTO> reviewList;

	private boolean prevIsEnabled;

	private boolean nextIsEnabled;

	private List<Integer> pageList = new ArrayList<>();

	private int currentPage;

	@Override
	public String toString() {
		return "ReviewListDTO [reviewList=" + reviewList + ", prevIsEnabled=" + prevIsEnabled + ", nextIsEnabled="
				+ nextIsEnabled + ", pageList=" + pageList + ", currentPage=" + currentPage + "]";
	}

	public ReviewListDTO(Page<ReviewDTO> reviewPage) throws NotFoundReviewException {

		reviewList = reviewPage.getContent();
		currentPage = reviewPage.getNumber() + 1;
		if (reviewList.isEmpty()) {

			throw new NotFoundReviewException(currentPage + "페이지는 존재하지 않습니다.");
		}
		int totalPage = reviewPage.getTotalPages();

		int pages = (int) Math.ceil(currentPage / 5.0);
		// 현제 페이지가 1 2 3 4 5 가 아니면 prevIsEnabled 해주면 된다.
		prevIsEnabled = pages != 1;

		nextIsEnabled = pages < (int) Math.ceil(totalPage / 5.0);

//		if(prevIsEnabled) {
//			pageList.add((pages-1)*5);
//		}
		for (int i = (pages - 1) * 5 + 1; i < pages * 5 + 1 && i <= totalPage; i++) {
			pageList.add(i);
		}
//		if(nextIsEnabled) {
//			pageList.add(pages*5+1);
//		}
	}

	public ReviewListDTO() {
		super();
	}

	// Getter Setter

	public List<ReviewDTO> getReviewList() {
		return reviewList;
	}

	public void setReviewList(List<ReviewDTO> reviewList) {
		this.reviewList = reviewList;
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
