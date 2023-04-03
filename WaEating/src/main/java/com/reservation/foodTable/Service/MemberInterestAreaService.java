package com.reservation.foodTable.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.reservation.foodTable.entity.MemberInterestArea;
import com.reservation.foodTable.repository.MemberInterestAreaRepository;

@Service
public class MemberInterestAreaService {
	
	private final MemberInterestAreaRepository memberInterestAreaRepository;
	
	public MemberInterestAreaService(MemberInterestAreaRepository memberInterestAreaRepository) {
		this.memberInterestAreaRepository = memberInterestAreaRepository;
	}

	public List<MemberInterestArea> findByMemberId(Integer id) {
		return memberInterestAreaRepository.findByMemberId(id);
	}
}
