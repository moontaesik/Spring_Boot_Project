package com.reservation.foodTable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reservation.foodTable.entity.Category;
import com.reservation.foodTable.entity.Member;
import com.reservation.foodTable.entity.MemberInterestCategory;

public interface MemberInterestCategoryRepository extends JpaRepository<MemberInterestCategory, Integer> {
	
	List<MemberInterestCategory> findByMemberId(Integer id);

	@Modifying
	@Query("DELETE FROM MemberInterestCategory c WHERE c.member.id = :memberId")
	void deleteByMemberId(@Param("memberId") Integer id);
	
	
}
