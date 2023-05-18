package com.ssafy.vue.model;

import com.ssafy.util.MyException;

public class SearchOptionDto {

	private String sidoCode, gugunCode, contentTypeId;
	private String searchQuery;


	public SearchOptionDto(String sidoCode, String gugunCode, String contentTypeId, String searchQuery) {
		super();
		this.sidoCode = sidoCode;
		this.gugunCode = gugunCode;
		this.contentTypeId = contentTypeId;
		this.searchQuery = searchQuery;
	}
	
	public String getSearchQuery() {
		return searchQuery;
	}


	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}


	public String getSidoCode() {
		return sidoCode;
	}

	public void setSidoCode(String sidoCode) throws MyException {
//		if (sidoCode < 0) {
//			throw new MyException("올바른 입력을 해주세요.");
//		}
		this.sidoCode = sidoCode;
	}

	public String getGugunCode() {
		return gugunCode;
	}

	public void setGugunCode(String gugunCode) throws MyException {
//		if (sidoCode < 0) {
//			throw new MyException("올바른 입력을 해주세요.");
//		}
		this.gugunCode = gugunCode;
	}

	public String getContentTypeId() {
		return contentTypeId;
	}

	public void setContentTypeId(String contentTypeId) throws MyException {
//		if (contentTypeId < 0) {
//			throw new MyException("올바른 입력을 해주세요.");
//		}
		this.contentTypeId = contentTypeId;
	}




	@Override
	public String toString() {
		return "SearchOptionDto [sidoCode=" + sidoCode + ", gugunCode=" + gugunCode + ", contentTypeId=" + contentTypeId
				+ ", searchQuery=" + searchQuery + "]";
	}


}
