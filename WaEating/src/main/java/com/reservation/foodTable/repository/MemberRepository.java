package com.reservation.foodTable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reservation.foodTable.entity.Member;


public interface MemberRepository extends JpaRepository<Member, Integer> {

	Member findByUserId(String userid);

	Member findByPhone(String phonenum);

	Member findByName(String name);

	@Query("SELECT m.restaurant.id FROM Member m WHERE m.userId =:userId")
	int findRestaurantIdByUserId(@Param("userId")String loginOwnerId);

}
