package com.reservation.foodTable.dto;

import com.reservation.foodTable.entity.OwnerComment;


public class ReplyDTO {
	
	private Integer id;
	private String content;
	
	public ReplyDTO(OwnerComment reply) {
		
		id = reply.getId();
		content = reply.getContent();
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
	
	
}
