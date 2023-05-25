package com.ssafy.vue.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.util.MyException;
import com.ssafy.util.PageNavigation;
import com.ssafy.vue.model.BoardDto;
import com.ssafy.vue.model.BoardParameterDto;

public interface BoardService {
	public boolean writeArticle(BoardDto boardDto) throws MyException;
	public List<BoardDto> listArticle(BoardParameterDto boardParameterDto) throws MyException;
	public PageNavigation makePageNavigation(BoardParameterDto boardParameterDto) throws MyException;
	
	public Map<String, Object> getArticle(int articleno, String boardType) throws MyException;
	public boolean modifyArticle(BoardDto boardDto) throws MyException;
	public boolean deleteArticle(int articleno) throws MyException;
	public boolean uploadImages(BoardDto boardDto, List<String> filePathList) throws MyException;
}
