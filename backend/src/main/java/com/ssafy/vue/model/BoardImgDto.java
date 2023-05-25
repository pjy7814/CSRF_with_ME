package com.ssafy.vue.model;

public class BoardImgDto {
	private int boardId;
    private String boardImgSrc;
    private int boardImgOrder;
    
    public BoardImgDto(int boardId, String boardImgSrc) {
		super();
		setBoardId(boardId);
		setBoardImgSrc(boardImgSrc);
	}
    
	public BoardImgDto(int boardId, String boardImgSrc, int boardImgOrder) {
		super();
		setBoardId(boardId);
		setBoardImgSrc(boardImgSrc);
		setBoardImgOrder(boardImgOrder);
	}
	
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		if (boardId > 0) {
			this.boardId = boardId;
		}
	}
	public String getBoardImgSrc() {
		return boardImgSrc;
	}
	public void setBoardImgSrc(String boardImgSrc) {
		if (boardImgSrc != null) {
			this.boardImgSrc = boardImgSrc;
	
		}
	}
	public int getBoardImgOrder() {
		return boardImgOrder;
	}
	public void setBoardImgOrder(int boardImgOrder) {
		if (boardImgOrder > 0) {
			this.boardImgOrder = boardImgOrder;
		}
	}
	@Override
	public String toString() {
		return "BoardImgDto [boardId=" + boardId + ", boardImgSrc=" + boardImgSrc + ", boardImgOrder=" + boardImgOrder
				+ "]";
	}
}
