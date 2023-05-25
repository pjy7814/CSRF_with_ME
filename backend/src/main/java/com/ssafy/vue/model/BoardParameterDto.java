package com.ssafy.vue.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "BoardParameterDto : 게시판 파라미터 정보", description = "게시판의 글을 얻기위한 부가적인 파라미터정보.")
public class BoardParameterDto {

	@ApiModelProperty(value = "현재 페이지 번호")
	private int pg;
	@ApiModelProperty(value = "페이지당 글갯수")
	private int spp;
	@ApiModelProperty(value = "페이지의 시작 글번호")
	private int start;
	@ApiModelProperty(value = "검색 조건")
	private String key;
	@ApiModelProperty(value = "검색어")
	private String word;
	@ApiModelProperty(value = "게시글의 타입")
	private String boardType;
	
	public BoardParameterDto() {
		pg = 1;
		spp = 20;
	}

	public int getPg() {
		return pg;
	}

	public void setPg(int pg) {
		if (pg >= 0) {
			pg = pg == 0 ? 1 : pg;
			this.pg = pg;
		}
	}

	public int getSpp() {
		return spp;
	}

	public void setSpp(int spp) {
		if (spp >= 0) {
			this.spp = spp;
		}
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		if (start >= 0) {
			this.start = start;
		}
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		// 검색어 null이어 됨
		this.key = key;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		// word null이어도 됨
		this.word = word;
	}

	public String getBoardType() {
		return boardType;
	}

	public void setBoardType(String boardType) {
		if (boardType != null) {
			this.boardType = boardType;
		}
	}

	@Override
	public String toString() {
		return "BoardParameterDto [pg=" + pg + ", spp=" + spp + ", start=" + start + ", key=" + key + ", word=" + word
				+ ", boardType=" + boardType + "]";
	}

	

}

