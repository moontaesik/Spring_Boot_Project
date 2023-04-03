package com.reservation.foodTable.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import com.reservation.foodTable.entity.OneToOneInquiry;

public class OneToOnePageDTO {
   private List<OneToOneDTO> oneList;
   private boolean prevIsEnabled;
   private boolean nextIsEnabled;
   private List<Integer> pageList = new ArrayList<>();
   private int currentPage;
   
   public OneToOnePageDTO(Page<OneToOneInquiry> onePage) {
      oneList = onePage.map(oneToOneInquiry->{
         return new OneToOneDTO(oneToOneInquiry.getId(),oneToOneInquiry.getMember().getUserId(),oneToOneInquiry.getTitle(),oneToOneInquiry.getState(),oneToOneInquiry.getCategory().getName());
         }).getContent();
      
      int totalPage = onePage.getTotalPages();
      currentPage = onePage.getNumber()+1;
      int pages = (int)Math.ceil(currentPage/5.0); 
      
      prevIsEnabled =  pages!= 1;
      
      nextIsEnabled = pages < (int)Math.ceil(totalPage/5.0);
      for(int i =(pages-1)*5+1; i<pages*5+1 && i <= totalPage; i++) {
         pageList.add(i);
      }
   }

   public List<OneToOneDTO> getOneList() {
      return oneList;
   }

   public void setOneList(List<OneToOneDTO> oneList) {
      this.oneList = oneList;
   }

   public boolean isPrevIsEnabled() {
      return prevIsEnabled;
   }

   public void setPrevIsEnabled(boolean prevIsEnabled) {
      this.prevIsEnabled = prevIsEnabled;
   }

   public boolean isNextIsEnabled() {
      return nextIsEnabled;
   }

   public void setNextIsEnabled(boolean nextIsEnabled) {
      this.nextIsEnabled = nextIsEnabled;
   }

   public List<Integer> getPageList() {
      return pageList;
   }

   public void setPageList(List<Integer> pageList) {
      this.pageList = pageList;
   }

   public int getCurrentPage() {
      return currentPage;
   }

   public void setCurrentPage(int currentPage) {
      this.currentPage = currentPage;
   }

   
}