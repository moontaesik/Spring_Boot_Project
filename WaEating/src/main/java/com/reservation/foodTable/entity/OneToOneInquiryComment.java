package com.reservation.foodTable.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class OneToOneInquiryComment {
   
   @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
   @Column(name="one_to_one_comment_id")
   private Integer id;
   
   @OneToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="one_to_one_id", nullable=false)
   private OneToOneInquiry oneToOneInquiry;
   
   @Column(nullable=false)
   private String content;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public OneToOneInquiry getOneToOneInquiry() {
      return oneToOneInquiry;
   }

   public void setOneToOneInquiry(OneToOneInquiry oneToOneInquiry) {
      this.oneToOneInquiry = oneToOneInquiry;
   }

   public String getContent() {
      return content;
   }

   public void setContent(String content) {
      this.content = content;
   }

}