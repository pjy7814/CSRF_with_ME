package com.ssafy.vue.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.vue.model.SearchOptionDto;
import com.ssafy.vue.model.SidoGugunCodeDto;
import com.ssafy.vue.model.TouristSpotInfoDto;
import com.ssafy.vue.model.mapper.AttractionMapMapper;

@Service
public class AttractionServiceImpl implements AttractionService{

	@Autowired
	private SqlSession sqlSession;
	
	
	@Override
	public List<SidoGugunCodeDto> getSido() throws Exception {
		
		return sqlSession.getMapper(AttractionMapMapper.class).getSido();
	}

	@Override
	public List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception {
		return sqlSession.getMapper(AttractionMapMapper.class).getGugunInSido(sido);
	}

	@Override
	public List<TouristSpotInfoDto> getDestination(SearchOptionDto searchOptionDto) throws Exception {
		return sqlSession.getMapper(AttractionMapMapper.class).getDestination(searchOptionDto);
	}
	
}
