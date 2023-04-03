package com.reservation.foodTable.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.reservation.foodTable.entity.MemberInterestCategory;
import com.reservation.foodTable.repository.MemberInterestCategoryRepository;

@Service
public class MemberInterestCategoryService {
	
	private final MemberInterestCategoryRepository memberInterestCategoryRepository;
	
	public MemberInterestCategoryService(MemberInterestCategoryRepository memberInterestCategoryRepository) {
		this.memberInterestCategoryRepository = memberInterestCategoryRepository;
	}

	public List<MemberInterestCategory> findByMemberId(Integer id) {
		return memberInterestCategoryRepository.findByMemberId(id);
	}

}
