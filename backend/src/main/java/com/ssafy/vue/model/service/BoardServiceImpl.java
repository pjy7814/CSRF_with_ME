package com.ssafy.vue.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.util.MyException;
import com.ssafy.util.PageNavigation;
import com.ssafy.vue.model.BoardDto;
import com.ssafy.vue.model.BoardImgDto;
import com.ssafy.vue.model.BoardParameterDto;
import com.ssafy.vue.model.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public boolean writeArticle(BoardDto boardDto) throws MyException {
		try {
//			if(boardDto.getBoardTitle() == null || boardDto.getBoardContent() == null) {
//				throw new MyException("값이 존재하지 않습니다.");
//			}
			return sqlSession.getMapper(BoardMapper.class).writeArticle(boardDto) == 1;
		} catch (SQLException e) {
			throw new MyException("SQLException");
		}
	}

	@Override
	public List<BoardDto> listArticle(BoardParameterDto boardParameterDto) throws MyException {
		try {
			int start = boardParameterDto.getPg() == 0 ? 0
					: (boardParameterDto.getPg() - 1) * boardParameterDto.getSpp();
			boardParameterDto.setStart(start);
			return sqlSession.getMapper(BoardMapper.class).listArticle(boardParameterDto);
		} catch (SQLException e) {
			throw new MyException("SQLException");
		}

	}

	@Override
	public PageNavigation makePageNavigation(BoardParameterDto boardParameterDto) throws MyException {

		try {
			int naviSize = 5;
			PageNavigation pageNavigation = new PageNavigation();
			pageNavigation.setCurrentPage(boardParameterDto.getPg());
			pageNavigation.setNaviSize(naviSize);
			int totalCount = sqlSession.getMapper(BoardMapper.class).getTotalCount(boardParameterDto);// 총글갯수 269
			pageNavigation.setTotalCount(totalCount);
			int totalPageCount = (totalCount - 1) / boardParameterDto.getSpp() + 1;// 27
			pageNavigation.setTotalPageCount(totalPageCount);
			boolean startRange = boardParameterDto.getPg() <= naviSize;
			pageNavigation.setStartRange(startRange);
			boolean endRange = (totalPageCount - 1) / naviSize * naviSize < boardParameterDto.getPg();
			pageNavigation.setEndRange(endRange);
			pageNavigation.makeNavigator();
			return pageNavigation;
		} catch (SQLException e) {
			throw new MyException("SQLException");
		}
	}

	@Override
	public Map<String, Object> getArticle(int articleno, String boardType) throws MyException {
		try {
			Map<String, Object> resultMap = new HashMap<>();
			BoardDto boardDto = sqlSession.getMapper(BoardMapper.class).getArticle(articleno, boardType);
			List<BoardImgDto> boardImgDtos = sqlSession.getMapper(BoardMapper.class).getArticleImg(articleno);
			resultMap.put("boardDtos", boardDto);
			resultMap.put("boardImgDtos", boardImgDtos);
			return resultMap;
		} catch (SQLException e) {
			throw new MyException("SQLException");
		}
	}

	@Override
	@Transactional
	public boolean modifyArticle(BoardDto boardDto) throws MyException {
		try {
			return sqlSession.getMapper(BoardMapper.class).modifyArticle(boardDto) == 1;
		} catch (SQLException e) {
			throw new MyException("SQLException");
		}
	}

	@Override
	@Transactional
	public boolean deleteArticle(int articleno) throws MyException {
		try {
			return sqlSession.getMapper(BoardMapper.class).deleteArticle(articleno) == 1;
		} catch (SQLException e) {
			throw new MyException("SQLException");
		}
	}

	@Override
	@Transactional
	public boolean uploadImages(BoardDto boardDto, List<String> filePathList) throws MyException {
		// TODO Auto-generated method stub
		// board_img 테이블에 넣어주는 로직.
		try {

			int boardId = boardDto.getBoardId(); // boardDto에서 boardId를 가져옴

			for (String filePath : filePathList) {
				// filePathList에 있는 이미지의 url을 board_img_src 컬럼에 저장하는 로직
				// 각 이미지에 대해 boardId를 사용하여 board_id 컬럼에 외래키로 설정

				// board_img 테이블에 데이터 삽입
				BoardImgDto boardImgDto = new BoardImgDto(boardId, filePath);
//			        boardImgDto.setBoardId(boardId);
//			        boardImgDto.setBoardImgSrc(filePath);
				sqlSession.getMapper(BoardMapper.class).uploadImages(boardImgDto);
			}

			return true; // 이미지 업로드 성공시 true 반환
		} catch (SQLException e) {
			throw new MyException("SQLException");
		}
	}

}