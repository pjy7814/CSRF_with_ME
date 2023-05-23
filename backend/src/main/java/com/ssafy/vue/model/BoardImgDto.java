package com.ssafy.vue.model;

public class BoardImgDto {
	private int boardId;
    private String boardImgSrc;
    private int boardImgOrder;
    
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public String getBoardImgSrc() {
		return boardImgSrc;
	}
	public void setBoardImgSrc(String boardImgSrc) {
		this.boardImgSrc = boardImgSrc;
	}
	public int getBoardImgOrder() {
		return boardImgOrder;
	}
	public void setBoardImgOrder(int boardImgOrder) {
		this.boardImgOrder = boardImgOrder;
	}
	@Override
	public String toString() {
		return "BoardImgDto [boardId=" + boardId + ", boardImgSrc=" + boardImgSrc + ", boardImgOrder=" + boardImgOrder
				+ "]";
	}
}
