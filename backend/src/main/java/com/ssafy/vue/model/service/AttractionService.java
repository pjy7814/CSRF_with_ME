package com.ssafy.vue.model.service;

import java.util.List;

import com.ssafy.util.MyException;
import com.ssafy.vue.model.HouseInfoDto;
import com.ssafy.vue.model.SearchOptionDto;
import com.ssafy.vue.model.SidoGugunCodeDto;
import com.ssafy.vue.model.TouristSpotInfoDto;

public interface AttractionService {
	List<SidoGugunCodeDto> getSido() throws Exception;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;
	List<TouristSpotInfoDto> getDestination(SearchOptionDto searchOptionDto) throws Exception;
}
