package com.reservation.foodTable.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.reservation.foodTable.embeddedType.Score;

public class GraphDTO {

   private List<Float> data = new ArrayList<>();
   private List<String> label = new ArrayList<>();
   private String title;
   

   public String getTitle() {
      return title;
   }
   public void setTitle(String title) {
      this.title = title;
   }
   
   
   
   public List<Float> getData() {
      return data;
   }
   public void setData(List<Float> data) {
      this.data = data;
   }
   public List<String> getLabel() {
      return label;
   }
   public void setLabel(List<String> label) {
      this.label = label;
   }
   public GraphDTO() {
      
   }
   public GraphDTO(String title) {
	      this.title = title;
   }
   public void addData(String label,Float data) {
      this.label.add(label);
      this.data.add(data);
      
   }
   
   
   public GraphDTO(Map<String,Score> scoreMap) {
      
      int size = scoreMap.size();
      data = new ArrayList<>(size);
      label = new ArrayList<>(size);
      scoreMap.keySet().forEach(scoreKey->{
         label.add(scoreKey);
         data.add(scoreMap.get(scoreKey).getTasteScore());
         
      });
      
   }
   
   
}