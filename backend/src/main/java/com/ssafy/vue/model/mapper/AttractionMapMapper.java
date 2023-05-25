package com.ssafy.vue.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.model.SearchOptionDto;
import com.ssafy.vue.model.SidoGugunCodeDto;
import com.ssafy.vue.model.TouristSpotInfoDto;

@Mapper
public interface AttractionMapMapper {

	List<SidoGugunCodeDto> getSido() throws SQLException;
	List<SidoGugunCodeDto> getGugunInSido(String sidoCode) throws SQLException;
	List<TouristSpotInfoDto> getDestination(SearchOptionDto searchOptionDto) throws SQLException;
	
}
