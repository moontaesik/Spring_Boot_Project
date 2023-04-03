package com.reservation.foodTable.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.reservation.foodTable.dto.OneToOneDTO;
import com.reservation.foodTable.dto.OneToOnePageDTO;
import com.reservation.foodTable.entity.OneToOneInquiry;
import com.reservation.foodTable.entity.OneToOneInquiryComment;
import com.reservation.foodTable.enumClass.InquiryState;
import com.reservation.foodTable.repository.OneToOneInquiryCommentRepository;
import com.reservation.foodTable.repository.OneToOneInquiryRepository;

@Service
public class OneToOneInquiryService {
	private final OneToOneInquiryRepository oneToOneInquiryRepository;
	private final OneToOneInquiryCommentRepository oneToOneInquiryCommentRepository;
	
	public OneToOneInquiryService(OneToOneInquiryRepository oneToOneInquiryRepository, OneToOneInquiryCommentRepository oneToOneInquiryCommentRepository) {
		this.oneToOneInquiryRepository = oneToOneInquiryRepository;
		this.oneToOneInquiryCommentRepository = oneToOneInquiryCommentRepository;
	}

	public List<OneToOneInquiry> findByMemberId(Integer id) {
		return oneToOneInquiryRepository.findByMemberId(id);
	}

	//태식 - 유저id로 문의 조회하면서 페이징처리하고 검색 기능까지
	public OneToOnePageDTO getOneToOneList(Integer id, String keyword, Integer target, Pageable pageable) {
		if(keyword.equals("")) {
			return new OneToOnePageDTO(oneToOneInquiryRepository.findByMemberId(id, pageable));
		}
		switch(target) {
			case 1: // 제목으로 검색
				return new OneToOnePageDTO(oneToOneInquiryRepository.findByMemberIdAndTitleContaining(id, keyword,pageable));
			case 2: // 내용 으로 검색
				return new OneToOnePageDTO(oneToOneInquiryRepository.findByMemberIdAndContentContaining(id, keyword,pageable));
			case 3: // 제목 + 내용으로 검색
				return new OneToOnePageDTO(oneToOneInquiryRepository.findByMemberIdAndTitleContainingOrContentContaining(id, keyword,keyword,pageable));         
			case 4: //상태
				return new OneToOnePageDTO(oneToOneInquiryRepository.findByMemberIdAndState(id, InquiryState.valueOf(keyword), pageable));
		}
		return null; // 여기 에러
	}
	//태식 - 상세보기페이지에서 현재페이지랑 이전목록 다음목록 보여주기
	public Map<String,OneToOneDTO> getOneToOnePageById(Integer inquiryId) {
		OneToOneInquiry oneToOneInquiry = oneToOneInquiryRepository.findById(inquiryId).get();
		Map<String,OneToOneDTO> onePage = new HashMap<>();
		onePage.put("current", new OneToOneDTO(oneToOneInquiry));
		Pageable pageable = PageRequest.of(0,1);
		// 다음글이 더 빠른글
		List<OneToOneDTO> oneToOneDTOPreviousList = oneToOneInquiryRepository.findPrevious(inquiryId,pageable);
		onePage.put("previous", oneToOneDTOPreviousList.size() == 0 ? null : oneToOneDTOPreviousList.get(0));
		List<OneToOneDTO> oneToOneDTONextList = oneToOneInquiryRepository.findNext(inquiryId,pageable);
		onePage.put("next", oneToOneDTONextList.size() ==0?null:oneToOneDTONextList.get(0));
		
		return onePage;	
	}
	//태식 - 문의에 답변 가져오기
	public OneToOneInquiryComment findOneToOneInquiryId(Integer inquiryId) {
		return oneToOneInquiryCommentRepository.findByOneToOneInquiryId(inquiryId);
	}

	public void deleteById(Integer inquiryId) {
		oneToOneInquiryRepository.deleteById(inquiryId);
		
	}

}
