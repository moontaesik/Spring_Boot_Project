package com.reservation.foodTable.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reservation.foodTable.entity.OneToOneInquiryComment;

public interface OneToOneInquiryCommentRepository extends JpaRepository<OneToOneInquiryComment, Integer> {

	OneToOneInquiryComment findByOneToOneInquiryId(Integer oneToOneId);
}
