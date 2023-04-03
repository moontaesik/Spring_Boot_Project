package com.reservation.foodTable.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reservation.foodTable.entity.FAQ;

public interface FAQRepository extends JpaRepository<FAQ,Integer> {

	// 전체를 가져오지만 categoryName 까지 한번에 fetch join

	@Query(value = "SELECT f FROM FAQ f JOIN FETCH f.fAQCategory fc where f.title LIKE %:keyword%",
			 countQuery = "SELECT COUNT(f) FROM FAQ f JOIN f.fAQCategory fc")
	Page<FAQ> getAllWithCategoryName(@Param("keyword")String keyword,Pageable pageable);

	// 한 카테고리만 가져오는 
	@Query(value = "SELECT f FROM FAQ f JOIN FETCH f.fAQCategory fc WHERE fc.id =:id",
			  countQuery = "SELECT COUNT(f) FROM FAQ f JOIN f.fAQCategory fc WHERE fc.id =:id")
	Page<FAQ> getByCategoryIdWithCategoryName(@Param("id")Integer id,Pageable pageable);

	
}
