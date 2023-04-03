package com.reservation.foodTable.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reservation.foodTable.dto.OneToOneDTO;
import com.reservation.foodTable.entity.OneToOneInquiry;
import com.reservation.foodTable.enumClass.InquiryState;

public interface OneToOneInquiryRepository extends JpaRepository<OneToOneInquiry, Integer> {

	List<OneToOneInquiry> findByMemberId(Integer id);
	
	//================================= 태식-manager에서 전체 문의 ==========================================
	Page<OneToOneInquiry> findByTitleContaining(String keyword, Pageable pageable);

	Page<OneToOneInquiry> findByContentContaining(String keyword, Pageable pageable);

	Page<OneToOneInquiry> findByTitleContainingOrContentContaining(String keyword, String keyword2, Pageable pageable);
	
	Page<OneToOneInquiry> findByMemberUserId(String keyword, Pageable pageable);

	Page<OneToOneInquiry> findByState(InquiryState inquiryState, Pageable pageable);

	//===================================== 태식-manager에서 이전목록 다음 목록 ==========================================
	@Query("SELECT new com.reservation.foodTable.dto.OneToOneDTO(o.id,o.member.userId,o.title,o.state,o.category.name) FROM OneToOneInquiry o "
         + "WHERE o.id < :id ORDER BY o.id asc")
	List<OneToOneDTO> findPrevious(@Param("id") Integer oneToOneId, Pageable pageable);

	@Query("SELECT new com.reservation.foodTable.dto.OneToOneDTO(o.id,o.member.userId,o.title,o.state,o.category.name) FROM OneToOneInquiry o  "
         + "WHERE o.id > :id ORDER BY o.id asc")
	List<OneToOneDTO> findNext(@Param("id") Integer oneToOneId, Pageable pageable);

	//==================================== 태식-mypage에서 문의 =============================================
	Page<OneToOneInquiry> findByMemberId(Integer id, Pageable pageable);
	
	Page<OneToOneInquiry> findByMemberIdAndTitleContaining(Integer id, String keyword, Pageable pageable);

	Page<OneToOneInquiry> findByMemberIdAndContentContaining(Integer id, String keyword, Pageable pageable);

	Page<OneToOneInquiry> findByMemberIdAndTitleContainingOrContentContaining(Integer id, String keyword, String keyword2, Pageable pageable);

	Page<OneToOneInquiry> findByMemberIdAndState(Integer id, InquiryState inquiryState, Pageable pageable);

}
