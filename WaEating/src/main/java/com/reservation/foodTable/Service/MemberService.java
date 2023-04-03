package com.reservation.foodTable.Service;

import java.util.List;
import java.util.Random;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.foodTable.dto.KakaOUserProfile;
import com.reservation.foodTable.dto.OAuthToken;
import com.reservation.foodTable.entity.Member;
import com.reservation.foodTable.entity.MemberInterestArea;
import com.reservation.foodTable.entity.MemberInterestCategory;
import com.reservation.foodTable.repository.MemberInterestAreaRepository;
import com.reservation.foodTable.repository.MemberInterestCategoryRepository;
import com.reservation.foodTable.repository.MemberRepository;

@Service
public class MemberService {
	
	private final PasswordEncoder passwordEncoder;
	
	private final MemberRepository memberRepository;
	
	private final MemberInterestCategoryRepository memberInterestCategoryRepository;
	
	private final MemberInterestAreaRepository memberInterestAreaRepository;
	
	public MemberService(PasswordEncoder passwordEncoder, MemberRepository memberRepository,
			MemberInterestAreaRepository memberInterestAreaRepository,MemberInterestCategoryRepository memberInterestCategoryRepository) {
		this.passwordEncoder = passwordEncoder;
		this.memberRepository = memberRepository;
		this.memberInterestAreaRepository = memberInterestAreaRepository;
		this.memberInterestCategoryRepository=memberInterestCategoryRepository;
	}
	
	
	/*
	 *  3월 10일
	 *  태식 / 영수
	 *  
	 *  1. public Member findByUserId(String userId);
	 *  2. public Member findById(Integer id)
	 *  3. public Member save(Member member)
	 *  4. public void encodePassword(Member member)
	 *  5. public boolean isuserIdUnique(Integer id, String userId)
	 *  6. public boolean isphoneUnique(Integer id, String phonenum)
	 *  7. public String isNamePhonefind(String name, String phonenum)
	 *  8. public String isuserIdNamePhoneExist(String id, String phone)
	 *  9. public String generateTemporaryPassword()
	 * */

	public Member findByUserId(String userId) {
		
		return memberRepository.findByUserId(userId) != null ? memberRepository.findByUserId(userId) : new Member();
	}
	
	public Member findById(Integer id) {
		return  memberRepository.findById(id).get();
	}

	public Member save(Member member) {
		//Integer 라서 처음에 초기화 해주지 않고 Member() 를 통해 넘겨줬으므로 회원 가입시에는 Null
		boolean isUpdatingUser = (member.getId() != null);
		
		if(isUpdatingUser) {
			Member existingUser = memberRepository.findById(member.getId()).get();
			
			if(member.getPassword().isEmpty()) {
				member.setPassword(existingUser.getPassword());
			}else {
				encodePassword(member);
			}
		}else {
			encodePassword(member);
		}
		
		return memberRepository.save(member);
		
	}
	
	public void encodePassword(Member member) {
		String rawPassword = member.getPassword();
		String encodedPassword = passwordEncoder.encode(rawPassword);
		member.setPassword(encodedPassword);
	}

	public boolean isuserIdUnique(Integer id, String userId) {
		
		Member member = memberRepository.findByUserId(userId);
		
		if(member == null) {
			return true;
		}
		
		boolean isCreatingNew = (id == null);
		
		if(isCreatingNew) {
			if (member != null) {
				return false;
			}
		}else {
			if(member.getId() != id) {
				return false;
			}
		}
		
		return true;
	}

	public boolean isphoneUnique(Integer id, String phonenum) {
		Member member = memberRepository.findByPhone(phonenum);
		
		if(member == null) {
			return true;
		}
		
		boolean isCreatingNew = (id == null);
		
		if(isCreatingNew) {
			if (member != null) {
				return false;
			}
		}else {
			if(member.getId() != id) {
				return false;
			}
		}
		
		return true;
	}

//	휴대전화 번호로 null인지 확인 
	public String isNamePhonefind1(String phonenum) {
		Member userFoundByPhone = memberRepository.findByPhone(phonenum);
		
		if(userFoundByPhone == null) {
			return "noPhone";
		}

		return "ok";
	}
	
	// 휴대전화 번호로 null이 아닌 것을 확인하고 이름 + 번호로 ID 찾기
	public String isNamePhonefind2(String name, String phonenum) {
		Member userFoundByName = memberRepository.findByName(name);
		Member userFoundByPhone = memberRepository.findByPhone(phonenum);
		
		if(userFoundByName == null && userFoundByPhone == null) {
			return "noNamePhone";
		}else if(userFoundByName != null && userFoundByPhone == null){
			return "noPhone";
		}else if(userFoundByName == null && userFoundByPhone != null) {
			return "noName";
		}else if(userFoundByName.getId() != userFoundByPhone.getId()) {
			return "입력하신 두 데이터가 일치하지 않습니다.";
		}
		
		return userFoundByPhone.getUserId();
	}
	
	// 비밀번호 찾기
	public String isuserIdNamePhoneExist(String id, String phone) {
		
		Member userFoundByUserId = memberRepository.findByUserId(id);
		Member userFoundByPhone = memberRepository.findByPhone(phone);
		
		if(userFoundByUserId == null && userFoundByPhone == null) {
			return "noIdPhone";
		}else if(userFoundByUserId != null && userFoundByPhone == null){
			return "noPhone";
		}else if(userFoundByUserId == null && userFoundByPhone != null) {
			return "noId";
		}else if(userFoundByUserId.getId() != userFoundByPhone.getId()){
			return "입력하신 두 데이터가 일치하지 않습니다.";
		}
		
		String temporaryPassword = generateTemporaryPassword();
		userFoundByUserId.setPassword(temporaryPassword);
		encodePassword(userFoundByUserId);
		memberRepository.save(userFoundByUserId);
			
		return temporaryPassword;
		
	}
	
	//랜덤 비밀번호 발급
	public String generateTemporaryPassword() {
		int length = 8;
        String temporalCode = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        int  temporalCodeLength = temporalCode.length();
        // Random 클래스의 int를 랜덤하게 만들어주는 nextInt 함수를 사용하기 위한 객체 생성
        Random random = new Random();
        // 문자열을 불변 객체인 String 으로 8번 결합 하는 것 보다 가변 클래스인 스트링 빌더이용
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(temporalCode.charAt(random.nextInt(temporalCodeLength)));
        } 
        return sb.toString();
    }

	
	@Transactional
	public void addInterest(Integer id, List<Integer> areaIds, List<Integer> categoryIds) {
		// TODO Auto-generated method stub
		
		areaIds.forEach(o->{
			memberInterestAreaRepository.save(new MemberInterestArea(o,id));
		});
		categoryIds.forEach(o->{
			memberInterestCategoryRepository.save(new MemberInterestCategory(o,id));
		});
	}


	@Transactional
	public void deleteByMemberId(Integer id) {
		memberInterestAreaRepository.deleteByMemberId(id);
	    memberInterestCategoryRepository.deleteByMemberId(id);
	}


	public Integer myRestaurantId(String loginOwnerId) {
		// TODO Auto-generated method stub
		int restaurantId = memberRepository.findRestaurantIdByUserId(loginOwnerId);
	
		return restaurantId;
	}


	public OAuthToken getToken(String code) {
		RestTemplate rt = new RestTemplate(); //Post방식으로 key=value 데이터를 요청(카카오쪽으로)
		//HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		//HttpBody 오브젝트 생성
		MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "d966d9857b1f0f35c58d4b647af53590");
		params.add("redirect_uri", "http://localhost:2232/FoodTable/member/auth/kakao/callback");
		params.add("code", code);
		//HttpHeader와 HttpBody를 하나의 오브젝트로 담기
		HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest=
				new HttpEntity<>(params,headers);
		//Http 요청하기 - post방식으로 - 그리고 response 변수의 응답 받음.
		ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token",HttpMethod.POST,
				kakaoTokenRequest,String.class);
		ObjectMapper obMapper = new ObjectMapper();
		OAuthToken oAuthToken = null;
		try {
			oAuthToken = obMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("카카오 엑세스 토큰 : "+oAuthToken.getAccess_token());
		return oAuthToken;
	}


	public KakaOUserProfile getUserProfile(OAuthToken oAuthToken) {
		RestTemplate rt2 = new RestTemplate();

		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oAuthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest=
				new HttpEntity<>(headers2);
		
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com//v2/user/me"
				,HttpMethod.POST,
				kakaoProfileRequest,
				String.class);
		
		ObjectMapper obMapper2 = new ObjectMapper();
		KakaOUserProfile userProfile = null;
		try {
			userProfile = obMapper2.readValue(response2.getBody(), KakaOUserProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return userProfile;
	}
}
