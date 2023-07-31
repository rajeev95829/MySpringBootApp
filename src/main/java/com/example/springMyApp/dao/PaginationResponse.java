package com.example.springMyApp.dao;

import java.util.List;

public class PaginationResponse<T> {

	    private List<T> data;
	    private int currentPage;
	    private int pageSize;
	    private int totalItems;
	    
		

	    // Constructor, getters, and setters
	    
	    
	    public List<T> getData() {
			return data;
		}



		public void setData(List<T> data) {
			this.data = data;
		}



		public int getCurrentPage() {
			return currentPage;
		}



		public void setCurrentPage(int currentPage) {
			this.currentPage = currentPage;
		}



		public int getPageSize() {
			return pageSize;
		}



		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}



		public int getTotalItems() {
			return totalItems;
		}



		public void setTotalItems(int totalItems) {
			this.totalItems = totalItems;
		}



		public PaginationResponse(List<T> data, int currentPage, int pageSize, int totalItems) {
			super();
			this.data = data;
			this.currentPage = currentPage;
			this.pageSize = pageSize;
			this.totalItems = totalItems;
		}
}
