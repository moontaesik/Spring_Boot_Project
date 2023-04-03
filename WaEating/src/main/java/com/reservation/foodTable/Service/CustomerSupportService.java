package com.reservation.foodTable.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.reservation.foodTable.dto.FAQCategoryDTO;
import com.reservation.foodTable.dto.FAQDTO;
import com.reservation.foodTable.dto.FAQPageDTO;
import com.reservation.foodTable.dto.NoticeDTO;
import com.reservation.foodTable.dto.NoticePageDTO;
import com.reservation.foodTable.dto.OneToOneDTO;
import com.reservation.foodTable.dto.OneToOnePageDTO;
import com.reservation.foodTable.entity.Banner;
import com.reservation.foodTable.entity.FAQ;
import com.reservation.foodTable.entity.Notice;
import com.reservation.foodTable.entity.OneToOneInquiry;
import com.reservation.foodTable.entity.OneToOneInquiryComment;
import com.reservation.foodTable.enumClass.InquiryState;
import com.reservation.foodTable.repository.BannerRepository;
import com.reservation.foodTable.repository.FAQCategoryRepository;
import com.reservation.foodTable.repository.FAQRepository;
import com.reservation.foodTable.repository.NoticeRepository;
import com.reservation.foodTable.repository.OneToOneInquiryCommentRepository;
import com.reservation.foodTable.repository.OneToOneInquiryRepository;

@Service
@Transactional(readOnly=true)
public class CustomerSupportService {

   private final FAQRepository fAQRepository;
   private final FAQCategoryRepository fAQCategoryRepository;
   private final NoticeRepository noticeRepository;
   private final OneToOneInquiryRepository oneTooneInquiryRepository;
   private final OneToOneInquiryCommentRepository oneToOneInquiryCommentRepository;
   private final BannerRepository bannerRepository;
   
   public CustomerSupportService(FAQRepository fAQRepository, FAQCategoryRepository fAQCategoryRepository, BannerRepository bannerRepository
         ,NoticeRepository noticeRepository, OneToOneInquiryRepository oneTooneInquiryRepository, OneToOneInquiryCommentRepository oneToOneInquiryCommentRepository) {
      this.fAQRepository = fAQRepository;
      this.fAQCategoryRepository = fAQCategoryRepository;
      this.noticeRepository = noticeRepository;
      this.oneTooneInquiryRepository = oneTooneInquiryRepository;
      this.oneToOneInquiryCommentRepository = oneToOneInquiryCommentRepository;
      this.bannerRepository = bannerRepository;
   }
   
   public List<FAQCategoryDTO> getCategories() {
      
      
      return fAQCategoryRepository.findAll().stream().map(FAQCategoryDTO::new).toList();
      
   }

   public FAQPageDTO findFAQ(String keyword,Pageable pageable) {
      
      
      return new FAQPageDTO(fAQRepository.getAllWithCategoryName(keyword,pageable));
      
   }
   
   public FAQPageDTO findSpecificCategoryFAQ(Pageable pageable,Integer categoryId) {
      
      
      return new FAQPageDTO(fAQRepository.getByCategoryIdWithCategoryName(categoryId,pageable));
      
   }

   
   
   
   public NoticePageDTO getNoticeList(String keyword,Integer target,Pageable pageable){
      
      if(keyword.equals("")) {
         return new NoticePageDTO(noticeRepository.findAll(pageable));
      }
      
      switch(target) {
      case 1: // 제목으로 검색
         return new NoticePageDTO(noticeRepository.findByTitleContaining(keyword,pageable));
         
      case 2: // 내용 으로 검색
         return new NoticePageDTO(noticeRepository.findByContentContaining(keyword,pageable));
         
      case 3: // 제목 + 내용으로 검색
         return new NoticePageDTO(noticeRepository.findByTitleContainingOrContentContaining(keyword,keyword,pageable));

      }
      
      return null; // 여기 에러
   }

   public Map<String,NoticeDTO> getNoticePageById(Integer noticeId) {
      // TODO Auto-generated method stub
      Notice notice = noticeRepository.findById(noticeId).get();
      notice.increaseViewCount();
      Map<String,NoticeDTO> noticePage = new HashMap<>();
      noticePage.put("current", new NoticeDTO(notice));
      Pageable pageable = PageRequest.of(0,1);
      // 다음글이 더 빠른글
      List<NoticeDTO> noticeDTOPreviousList = noticeRepository.findPrevious(noticeId,pageable);
      noticePage.put("previous", noticeDTOPreviousList.size() == 0 ? null : noticeDTOPreviousList.get(0));
      List<NoticeDTO> noticeDTONextList = noticeRepository.findNext(noticeId,pageable);
      noticePage.put("next", noticeDTONextList.size() ==0?null:noticeDTONextList.get(0));
      
      return noticePage;
   }
   
   @Transactional
   public void saveFAQ(FAQDTO faqDTO, int categoryId) {
   
      Integer id = faqDTO.getId();
      FAQ saveFAQ;
      if(id == null) {
         saveFAQ = new FAQ(faqDTO.getId(),faqDTO.getTitle(),faqDTO.getContent(),
               categoryId);
         
         fAQRepository.save(saveFAQ);
      }else {
         saveFAQ = fAQRepository.findById(id).get();
         saveFAQ.changeFAQDetails(faqDTO.getTitle(),faqDTO.getContent(), categoryId);
      }
   }
   @Transactional
   public void deleteFAQ(int fAQId) {
      // TODO Auto-generated method stub
      fAQRepository.deleteById(fAQId);
   }

   public FAQDTO findFAQDTOByFAQId(int fAQId) {
      
      
      return new FAQDTO(fAQRepository.findById(fAQId).get());
   }
   @Transactional
   public void deleteNotice(int noticeId) {
      // TODO Auto-generated method stub
      noticeRepository.deleteById(noticeId);
   }

   public NoticeDTO findNoticeDTOByNoticeId(int noticeId) {
      // TODO Auto-generated method stub
      return new NoticeDTO(noticeRepository.findById(noticeId).get());
   }
   
   @Transactional
   public void saveOrUpdateNotice(NoticeDTO noticeDTO) {
      // TODO Auto-generated method stub
      Integer id = noticeDTO.getId();
      Notice notice;
      if(id==null) {
         notice= new Notice(noticeDTO.getTitle(),noticeDTO.content());
         noticeRepository.save(notice);
         
      }else {
         notice = noticeRepository.findById(id).get();
         notice.changeNoticeDetails(noticeDTO.getTitle(),noticeDTO.content());
      }
   }

   //태식 - 매니저에서 유저 문의 전체 보기, 페이징 처리하기, 검색하기
   public OneToOnePageDTO getOneToOneList(String keyword, Integer target, Pageable pageable) {
      if(keyword.equals("")) {
         return new OneToOnePageDTO(oneTooneInquiryRepository.findAll(pageable));
      }
      switch(target) {
      case 1: // 제목으로 검색
         return new OneToOnePageDTO(oneTooneInquiryRepository.findByTitleContaining(keyword,pageable));
      case 2: // 내용 으로 검색
         return new OneToOnePageDTO(oneTooneInquiryRepository.findByContentContaining(keyword,pageable));  
      case 3: // 제목 + 내용으로 검색
         return new OneToOnePageDTO(oneTooneInquiryRepository.findByTitleContainingOrContentContaining(keyword,keyword,pageable));         
      case 4: //회원ID
         return new OneToOnePageDTO(oneTooneInquiryRepository.findByMemberUserId(keyword, pageable));         
      case 5: //상태
         return new OneToOnePageDTO(oneTooneInquiryRepository.findByState(InquiryState.valueOf(keyword), pageable));
      }    
      return null; // 여기 에러
   }
   //태식 - 매니저에서 현재랑, 이전목록, 다음 목록 조회
   public Map<String,OneToOneDTO> getOneToOnePageById(Integer oneToOneId) {
      OneToOneInquiry oneToOneInquiry = oneTooneInquiryRepository.findById(oneToOneId).get();
      Map<String,OneToOneDTO> onePage = new HashMap<>();
      onePage.put("current", new OneToOneDTO(oneToOneInquiry));
      Pageable pageable = PageRequest.of(0,1);
      // 다음글이 더 빠른글
      List<OneToOneDTO> oneToOneDTOPreviousList = oneTooneInquiryRepository.findPrevious(oneToOneId,pageable);
      onePage.put("previous", oneToOneDTOPreviousList.size() == 0 ? null : oneToOneDTOPreviousList.get(0));
      List<OneToOneDTO> oneToOneDTONextList = oneTooneInquiryRepository.findNext(oneToOneId,pageable);
      onePage.put("next", oneToOneDTONextList.size() ==0?null:oneToOneDTONextList.get(0));
      
      return onePage;
   }
   //태식 - 매니저에서 현재 문의에 대한 답변 조회
   public OneToOneInquiryComment findOneToOneInquiryId(Integer oneToOneId) {
      return oneToOneInquiryCommentRepository.findByOneToOneInquiryId(oneToOneId);
   }
   //태식 - 문의 상세보기에서 답변을 처음 달때 문의에 대한 정보 가져오기
   public OneToOneInquiry findById(Integer oneToOneId) {
      return oneTooneInquiryRepository.findById(oneToOneId).get();
   }
   //태식 - 답변 저장하기
   @Transactional
   public void saveComment(OneToOneInquiryComment comment) {
      oneToOneInquiryCommentRepository.save(comment);
   }
   //태식 - 답변 상태 바꾸기
   @Transactional
   public OneToOneInquiry save(OneToOneInquiry one) {
      return oneTooneInquiryRepository.save(one);
   } 
   
   //////////////////////////////////////하지원/////////////////////////////////////
   @Transactional
   public Banner saveBanner(Banner banner) {
      return bannerRepository.save(banner);
   }
   
   public List<Banner> findAllBanner() {      
      return bannerRepository.findAll();
   }

   @Transactional
   public void saveBanner(Banner banner, MultipartFile file) {
   }

   @Transactional
   public void deleteBannerById(int id) {
      bannerRepository.deleteById(id);
   }

   public Optional<Banner> findBannerById(int id) {
      return bannerRepository.findById(id);
      
   }
}