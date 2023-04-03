package com.reservation.foodTable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reservation.foodTable.entity.Banner;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer> {
	

}
