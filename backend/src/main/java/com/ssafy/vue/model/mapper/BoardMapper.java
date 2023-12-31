package com.ssafy.vue.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.model.BoardDto;
import com.ssafy.vue.model.BoardImgDto;
import com.ssafy.vue.model.BoardParameterDto;

@Mapper
public interface BoardMapper {
	
	public int writeArticle(BoardDto boardDto) throws SQLException;
	public List<BoardDto> listArticle(BoardParameterDto boardParameterDto) throws SQLException;
	public int getTotalCount(BoardParameterDto boardParameterDto) throws SQLException;
	public BoardDto getArticle(int articleno, String boardType) throws SQLException;
	public int modifyArticle(BoardDto boardDto) throws SQLException;
	public int deleteArticle(int articleno) throws SQLException;
	public int uploadImages(BoardImgDto boardImgDto) throws SQLException;
	public List<BoardImgDto> getArticleImg(int articleno) throws SQLException;
	
}