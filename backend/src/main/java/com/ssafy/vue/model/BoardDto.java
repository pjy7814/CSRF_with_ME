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
	private String boardAttractinoInfo;
	@ApiModelProperty(value = "작성일")
	private Timestamp createdTime;
	@ApiModelProperty(value = "수정일")
	private Timestamp modifiedTime;
	@ApiModelProperty(value = "보드 타입")
	private String boardType;
	
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public String getBoardWriterId() {
		return boardWriterId;
	}
	public void setBoardWriterId(String boardWriterId) {
		this.boardWriterId = boardWriterId;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public String getBoardAttractinoInfo() {
		return boardAttractinoInfo;
	}
	public void setBoardAttractinoInfo(String boardAttractinoInfo) {
		this.boardAttractinoInfo = boardAttractinoInfo;
	}
	public Timestamp getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}
	public Timestamp getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Timestamp modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	@Override
	public String toString() {
		return "BoardDto [boardId=" + boardId + ", boardWriterId=" + boardWriterId + ", boardTitle=" + boardTitle
				+ ", boardContent=" + boardContent + ", boardAttractinoInfo=" + boardAttractinoInfo + ", createdTime="
				+ createdTime + ", modifiedTime=" + modifiedTime + "]";
	}
	
	

}