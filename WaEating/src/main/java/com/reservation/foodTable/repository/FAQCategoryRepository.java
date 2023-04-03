package com.reservation.foodTable.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reservation.foodTable.entity.FAQCategory;

public interface FAQCategoryRepository extends JpaRepository<FAQCategory,Integer> {

	
}
