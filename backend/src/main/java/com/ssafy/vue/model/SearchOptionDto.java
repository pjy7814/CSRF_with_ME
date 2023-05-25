package com.ssafy.vue.model;

import com.ssafy.util.MyException;

public class SearchOptionDto {

	private String sidoCode, gugunCode, contentTypeId;
	private String searchQuery;

// 검색조건이 null이어도 검색이 가능함
	public SearchOptionDto(String sidoCode, String gugunCode, String contentTypeId, String searchQuery) {
		super();
		setSidoCode(sidoCode);
		setGugunCode(gugunCode);
		setContentTypeId(contentTypeId);
		setSearchQuery(searchQuery);
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

	public void setSidoCode(String sidoCode) {
//		if (sidoCode < 0) {
//			throw new MyException("올바른 입력을 해주세요.");
//		}
		this.sidoCode = sidoCode;
	}

	public String getGugunCode() {
		return gugunCode;
	}

	public void setGugunCode(String gugunCode) {
//		if (sidoCode < 0) {
//			throw new MyException("올바른 입력을 해주세요.");
//		}
		this.gugunCode = gugunCode;
	}

	public String getContentTypeId() {
		return contentTypeId;
	}

	public void setContentTypeId(String contentTypeId) {
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
