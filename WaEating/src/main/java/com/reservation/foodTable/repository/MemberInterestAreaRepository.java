package com.reservation.foodTable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reservation.foodTable.entity.MemberInterestArea;

public interface MemberInterestAreaRepository extends JpaRepository<MemberInterestArea, Integer> {
	List<MemberInterestArea> findByMemberId(Integer id);

	@Modifying
	@Query("DELETE FROM MemberInterestArea a WHERE a.member.id = :memberId")
	void deleteByMemberId(@Param("memberId") Integer id);

}
