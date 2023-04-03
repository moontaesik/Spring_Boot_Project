package com.reservation.foodTable.dto;

import javax.persistence.Transient;

import com.reservation.foodTable.entity.OneToOneInquiry;
import com.reservation.foodTable.enumClass.InquiryState;

public class OneToOneDTO {
   private Integer id;
   private String title;
   private String content;
   private String filePath;
   private InquiryState state;
   private String memberId;
   private String categoryName;
   private String oneToOneInquiryCommentContent;
   
   public OneToOneDTO(OneToOneInquiry oneToOneInquiry) {
      id = oneToOneInquiry.getId();
      title = oneToOneInquiry.getTitle();
      content = oneToOneInquiry.getContent();
      filePath = oneToOneInquiry.getFilePath();
      state = oneToOneInquiry.getState();
      memberId = oneToOneInquiry.getMember().getUserId();
      categoryName = oneToOneInquiry.getCategory().getName();
      if(oneToOneInquiry.getOneToOneInquiryComment()== null) {
         oneToOneInquiryCommentContent = "";
      }else {
         oneToOneInquiryCommentContent = oneToOneInquiry.getOneToOneInquiryComment().getContent();
      }
   }
   
   @Transient
   public String getPhotosImagePath() {
      if(id == null || filePath == null) {
         return "/img/12.png";
      }
      return "/onetoone-image/" + this.id + "/" + this.filePath;
   }

   public OneToOneDTO(Integer id,String memberId, String title, InquiryState state, String categoryName) {
      super();
      this.id = id;
      this.memberId = memberId;
      this.title = title;
      this.state = state;
      this.categoryName = categoryName;
   }
   
   public String ContentWithEnter() {
      
      return content;
   }


   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getContent() {
      if(content==null) return "";
      return content.replaceAll("<br>", "\n");
   }

   public void setContent(String content) {
      this.content = content.replaceAll( "\n","<br>");
   }

   public String getFilePath() {
      return filePath;
   }

   public void setFilePath(String filePath) {
      this.filePath = filePath;
   }

   public InquiryState getState() {
      return state;
   }

   public void setState(InquiryState state) {
      this.state = state;
   }

   public String getMemberId() {
      return memberId;
   }

   public void setMemberId(String memberId) {
      this.memberId = memberId;
   }

   public String getCategoryName() {
      return categoryName;
   }

   public void setCategoryName(String categoryName) {
      this.categoryName = categoryName;
   }

   public String getOneToOneInquiryCommentContent() {
      if(oneToOneInquiryCommentContent==null) return "";
      return oneToOneInquiryCommentContent.replaceAll("<br>", "\n");
   }

   public void setOneToOneInquiryCommentContent(String oneToOneInquiryCommentContent) {
      this.oneToOneInquiryCommentContent = oneToOneInquiryCommentContent.replaceAll( "\n","<br>");
   }

   @Override
   public String toString() {
      return "OneToOneDTO [id=" + id + ", title=" + title + ", content=" + content + ", filePath=" + filePath
            + ", state=" + state + ", memberId=" + memberId + ", categoryName=" + categoryName
            + ", oneToOneInquiryCommentContent=" + oneToOneInquiryCommentContent + "]";
   }

}