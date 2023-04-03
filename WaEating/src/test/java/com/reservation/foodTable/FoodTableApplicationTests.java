package com.reservation.foodTable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.reservation.foodTable.entity.FAQ;
import com.reservation.foodTable.entity.FAQCategory;
import com.reservation.foodTable.entity.RestaurantAvaTime;
import com.reservation.foodTable.entity.RestaurantDefaultTime;
import com.reservation.foodTable.repository.FAQCategoryRepository;
import com.reservation.foodTable.repository.FAQRepository;

@SpringBootTest
class FoodTableApplicationTests {
	
	@Autowired
	EntityManager em;
	
	@Autowired
	FAQRepository fAQRepository;
	
	@Autowired
	FAQCategoryRepository fAQCategoryRepository;

	@Test
	@Commit
	@Transactional
	void insertFAQCategory() {
		
		List<FAQCategory> fAQCategories = 
				new ArrayList<>();
		
		fAQCategories.add(new FAQCategory("포인트"));
		fAQCategories.add(new FAQCategory("환불"));
		fAQCategories.add(new FAQCategory("예약"));
		fAQCategories.add(new FAQCategory("회원"));

		
		for(FAQCategory fc : fAQCategories) {
			em.persist(fc);
		}

		em.flush();
		
	}
	

	@Test
	@Commit
	@Transactional
	void insertFAQ() {
		
		List<FAQ> fAQ = new ArrayList<>();
		
		// FAQ( String title, String content, Integer fAQCategoryId)
		//========포인트 관련 
		fAQ.add(new FAQ("포인트의 유효기간은 얼마인가요?","받은 포인트의 시점으로부터 1년 까지 사용 가능합니다.",1));
		fAQ.add(new FAQ("포인트는 몇퍼센트씩 적립이 되나요?","일반 : 3% <br> 우수 : 4% <br> vip : 5%",1));
		
		// =============== 환불 관련
		fAQ.add(new FAQ("환불시 예약금액은 언제 들어오나요?","환불 요청 이후 1시간 이내에 송금됩니다.",2));
		fAQ.add(new FAQ("포인트의 유효기간은 얼마인가요?","받은 포인트의 시점으로부터 1년 까지 사용 가능합니다.",2));
		
		
		// ============== 예약 관련
		fAQ.add(new FAQ("예약 날짜를 변경하는 방법.","마이페이지 첫 화면에서 해당 예약 수정 버튼을 클릭하시거나 고객센터(1111-1111)로 문의하시면 변경하실 수 있습니다.",3));
		fAQ.add(new FAQ("예약 주문 메뉴를 변경하는 방법.","마이페이지 첫 화면에서 해당 예약 수정 버튼을 클릭하시거나 고객센터(1111-1111)로 문의하시면 변경하실 수 있습니다.",3));
		fAQ.add(new FAQ("예약 확인하는 방법","마이페이지를 들어가시면 첫화면에서 확인하실 수 있습니다.",3));
		fAQ.add(new FAQ("예약 취소하는 방법","마이페이지 첫 화면에서 해당 예약의 예약 취소 버튼을 클릭하시거나 고객센터(1111-1111)로 문의하시면 변경하실 수 있습니다.",3));
		
		
		// ============= 회원 관련
		fAQ.add(new FAQ("탈퇴시 개인정보는 모두 삭제 되나요?","탈퇴 즉시 고객님의 개인정보는 모두 삭제됩니다.<br>"
				+ "단, 전자상거래 및 소비자 권리 보호를 위해 개인정보 외의 거래정보 등은 법률에서 정한 기간 동안 보관될 수 있습니다.",4));
		fAQ.add(new FAQ("포인트의 유효기간은 얼마인가요?","아이핀으로 본인확인 후 비밀번호 변경이 가능합니다.<br>"
				+ "아이핀이 없으신 경우, 아이핀 사이트에서 신규발급이 가능합니다.<br>"
				+ "KISA 아이핀사이트 : <a href='http://i-pin.kisa.or.kr/kor/main.jsp'>http://i-pin.kisa.or.kr/kor/main.jsp</a>",4));
		fAQ.add(new FAQ("아이디/비밀번호를 잃어버렸어요.","WaEating 사이트의 로그인 화면의 비밀번호 입력 밑의 버튼을 클릭하시거나 1111-1111(고객센터)로 "
				+ "문의하시면 아이디/비밀번호를 확인하실 수 있습니다.",4));
		
		//=== save
		
		fAQRepository.saveAll(fAQ);
		
		
	}
	@Test
	@Commit
	@Transactional
	void insertQueryCreate() {
		
		LocalDate yesterday = LocalDate.now().minusDays(1);
		LocalDate date;
		
		int[] remainTable = {0,5,10,15,20,25,30,3,7,13,18,21,24,28};
		int length = remainTable.length;
		
		for(int i=0; i<14;i++) {

			date = yesterday.plusDays(1);
			
			for(int j=1; j<10;j++) {
				
				int randomTableNum = remainTable[(int)(Math.random()*length)];
				RestaurantAvaTime data = new RestaurantAvaTime(date,new RestaurantDefaultTime(randomTableNum),j);
				em.persist(data);
			}
			em.flush();
		}
	}
}
