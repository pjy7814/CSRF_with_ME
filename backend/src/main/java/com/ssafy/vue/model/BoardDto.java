package com.ssafy.vue.model;

import java.sql.Timestamp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "BoardDto : 게시글정보", description = "게시글의 상세 정보를 나타낸다.")
public class BoardDto {
	@ApiModelProperty(value = "글번호")
	private int boardId;
	@ApiModelProperty(value = "작성자 아이디")
	private String boardWriterId;
	@ApiModelProperty(value = "글제목")
	private String boardTitle;
	@ApiModelProperty(value = "글내용")
	private String boardContent;
	@ApiModelProperty(value = "관광지 주소")
	private int boardAttractionInfoId;
	@ApiModelProperty(value = "작성일")
	private String createdTime;
	@ApiModelProperty(value = "수정일")
	private String modifiedTime;
	@ApiModelProperty(value = "보드 타입")
	private String boardType;
	
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		if (boardId > 0) {
			this.boardId = boardId;
		}
	}
	public String getBoardWriterId() {
		return boardWriterId;
	}
	public void setBoardWriterId(String boardWriterId) {
		if (boardWriterId != null) {
			this.boardWriterId = boardWriterId;
		}
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		if(boardTitle != null) {
			this.boardTitle = boardTitle;
		}
	}
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		if (boardContent != null) {
			this.boardContent = boardContent;
		}
	}
	public int getBoardAttractionInfoId() {
		return boardAttractionInfoId;
	}
	public void setBoardAttractionInfoId(int boardAttractionInfoId) {
		if (boardAttractionInfoId > 0) {
			this.boardAttractionInfoId = boardAttractionInfoId;
		}
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		if (createdTime != null) {
			this.createdTime = createdTime;
		}
	}
	public String getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(String modifiedTime) {
		//null이어도 됨
		this.modifiedTime = modifiedTime;
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
		return "BoardDto [boardId=" + boardId + ", boardWriterId=" + boardWriterId + ", boardTitle=" + boardTitle
				+ ", boardContent=" + boardContent + ", boardAttractionInfoId=" + boardAttractionInfoId
				+ ", createdTime=" + createdTime + ", modifiedTime=" + modifiedTime + ", boardType=" + boardType + "]";
	}

	

}