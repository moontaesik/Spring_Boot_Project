package com.reservation.foodTable.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reservation.foodTable.dto.NoticeDTO;
import com.reservation.foodTable.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
		
	@Query("SELECT new com.reservation.foodTable.dto.NoticeDTO(n.id,n.title,n.createDate) FROM Notice n "
			+ "WHERE n.id < :id ORDER BY n.id desc")
	List<NoticeDTO> findPrevious(@Param("id")Integer id,Pageable pageable);
	
	@Query("SELECT new com.reservation.foodTable.dto.NoticeDTO(n.id,n.title,n.createDate) FROM Notice n "
			+ "WHERE n.id > :id ORDER BY n.id asc")
	List<NoticeDTO> findNext(@Param("id")Integer id,Pageable pageable);
	
	Page<Notice> findByTitleContaining(String keyword,Pageable pageable);
	
	Page<Notice> findByContentContaining(String keyword,Pageable pageable);
	
	Page<Notice> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword,Pageable pageable);
	
}
