package com.reservation.foodTable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.reservation.foodTable.entity.Category;
import com.reservation.foodTable.entity.Member;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query("SELECT c FROM Category c Join MemberInterestCategory mc on mc.category.id = c.id WHERE mc.member=:member")
	List<Category> findMyCategory(@Param("member")Member member);

}
