package com.reservation.foodTable.dto;

import java.util.List;




public class FilterFormDTO {
		private int filterId;
		private int sortingId;
	 	private List<Integer> categoryList;
	    private List<String> priceList;
	    private List<Integer> areaList;
	    private List<Integer> guList;
	    private List<Integer> dongList;
	    private String searchQuery;
	    
	    
	    
		public List<Integer> getGuList() {
			return guList;
		}
		public void setGuList(List<Integer> guList) {
			this.guList = guList;
		}
		public List<Integer> getDongList() {
			return dongList;
		}
		public void setDongList(List<Integer> dongList) {
			this.dongList = dongList;
		}
		public int getFilterId() {
			return filterId;
		}
		public void setFilterId(int filterId) {
			this.filterId = filterId;
		}
	
		public int getSortingId() {
			return sortingId;
		}
		public void setSortingId(int sortingId) {
			this.sortingId = sortingId;
		}
		public List<Integer> getCategoryList() {
			return categoryList;
		}
		public void setCategoryList(List<Integer> categoryList) {
			this.categoryList = categoryList;
		}
		public List<String> getPriceList() {
			return priceList;
		}
		public void setPriceList(List<String> priceList) {
			this.priceList = priceList;
		}
		public List<Integer> getAreaList() {
			return areaList;
		}
		public void setAreaList(List<Integer> areaList) {
			this.areaList = areaList;
		}
		public String getSearchQuery() {
			return searchQuery;
		}
		public void setSearchQuery(String searchQuery) {
			this.searchQuery = searchQuery;
		}
		
	   
}
