package com.reservation.foodTable.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.reservation.foodTable.enumClass.InquiryState;

@Entity
public class OneToOneInquiry {
   

   @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
   @Column(name="one_to_one_id")
   private Integer id;
   
   @Column(nullable=false)
   private String title;
   
   @Column(nullable=false)
   private String content;
   
   @Column
   private String filePath;
   
   @Column
   @Enumerated(EnumType.STRING)
   private InquiryState state;
   
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="member_id",nullable=false)
   private Member member;
   
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="fAQ_category_id",nullable=false)
   private FAQCategory category;
   
   @OneToOne(mappedBy="oneToOneInquiry" ,fetch=FetchType.LAZY,cascade=CascadeType.REMOVE)
   private OneToOneInquiryComment OneToOneInquiryComment;

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
      return content;
   }

   public void setContent(String content) {
      this.content = content;
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

   public Member getMember() {
      return member;
   }

   public void setMember(Member member) {
      this.member = member;
   }

   public FAQCategory getCategory() {
      return category;
   }

   public void setCategory(FAQCategory category) {
      this.category = category;
   }

   public OneToOneInquiryComment getOneToOneInquiryComment() {
      return OneToOneInquiryComment;
   }

   public void setOneToOneInquiryComment(OneToOneInquiryComment oneToOneInquiryComment) {
      this.OneToOneInquiryComment = oneToOneInquiryComment;
   }

   @Transient
   public String getPhotosImagePath() {
      if(id == null || filePath == null) {
         return "/img/12.png";
      }
      return "/onetoone-image/" + this.id + "/" + this.filePath;
   }
   
}