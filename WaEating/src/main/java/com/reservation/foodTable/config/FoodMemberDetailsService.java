package com.reservation.foodTable.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.reservation.foodTable.entity.Member;
import com.reservation.foodTable.repository.MemberRepository;

public class FoodMemberDetailsService implements UserDetailsService{
	
	@Autowired
	private MemberRepository memRepo;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		Member mem = memRepo.findByUserId(userId); // 입력한 이메일이 db에 없으면 null
		
		if(mem != null) { //db에 이메일 있으면 null아님
			return new FoodMemberDetails(mem); //이메일을 통해서 뽑은 유저 정보를 넘겨줌
		}
		
		throw new UsernameNotFoundException("Could not find user with userId : " + userId); //null 이면 찾을 수 없음 경고메시지
	}

}
