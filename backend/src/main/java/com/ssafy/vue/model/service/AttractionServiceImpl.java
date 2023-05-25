package com.ssafy.vue.model.service;

import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ssafy.util.MyException;
import com.ssafy.vue.model.SearchOptionDto;
import com.ssafy.vue.model.SidoGugunCodeDto;
import com.ssafy.vue.model.TouristSpotInfoDto;
import com.ssafy.vue.model.mapper.AttractionMapMapper;

@Service
public class AttractionServiceImpl implements AttractionService {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<SidoGugunCodeDto> getSido() throws MyException {
		try {
			return sqlSession.getMapper(AttractionMapMapper.class).getSido();
		} catch (SQLException e) {
			throw new MyException("서버 오류가 발생했습니다!");
		}
	}

	@Override
	public List<SidoGugunCodeDto> getGugunInSido(String sido) throws MyException {
		try {
			return sqlSession.getMapper(AttractionMapMapper.class).getGugunInSido(sido);
		} catch (SQLException e) {
			throw new MyException("서버 오류가 발생했습니다!");
		}
	}

	@Override
	public List<TouristSpotInfoDto> getDestination(SearchOptionDto searchOptionDto) throws MyException {
		try {
			return sqlSession.getMapper(AttractionMapMapper.class).getDestination(searchOptionDto);
		} catch (SQLException e) {
			throw new MyException("서버 오류가 발생했습니다!");
		}
	}
}
