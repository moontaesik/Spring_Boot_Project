package com.reservation.foodTable.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.reservation.foodTable.entity.Notice;

public class NoticeDTO {

	public String getContent() {
		if (content == null)
			return "";
		return content.replaceAll("<br>", "\n");
	}

	public void setContent(String content) {

		this.content = content.replaceAll("\n", "<br>");
	}

	public String content() {
		return content;
	}

	private Integer id;

	private String title;

	private String content;

	private Integer viewCount;

	private LocalDate registerDate;

	public NoticeDTO(Notice notice) {
		id = notice.getId();
		title = notice.getTitle();
		viewCount = notice.getViewCount();
		content = notice.getContent();
		registerDate = notice.getCreateDate().toLocalDate();
	}

	public NoticeDTO(Integer id, String title, LocalDateTime time) {
		this.id = id;
		this.title = title;
		this.registerDate = time.toLocalDate();
	}

	public NoticeDTO() {
		super();
	}

	public NoticeDTO(Integer id, String title, String content, Integer viewCount) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.viewCount = viewCount;
	}

	public LocalDate getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(LocalDate registerDate) {
		this.registerDate = registerDate;
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

	public String ContentWithEnter() {

		return content;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	@Override
	public String toString() {
		return "NoticeDTO [id=" + id + ", title=" + title + ", content=" + content + ", viewCount=" + viewCount
				+ ", registerDate=" + registerDate + "]";
	}

}
