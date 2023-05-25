package com.ssafy.vue.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.model.MemberDto;
import com.ssafy.vue.model.SearchOptionDto;
import com.ssafy.vue.model.SidoGugunCodeDto;
import com.ssafy.vue.model.TouristSpotInfoDto;
import com.ssafy.vue.model.service.AttractionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/map")
@Api("Map 컨트롤러  API V1")
public class AttractionController {


	@Autowired
	private AttractionService attractionService;

	@ApiOperation(value = "시도 정보", notes = "전국의 시도를 반환한다.", response = List.class)
	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunCodeDto>> sido() throws Exception {
		return new ResponseEntity<List<SidoGugunCodeDto>>(attractionService.getSido(), HttpStatus.OK);
	}

	@ApiOperation(value = "구군 정보", notes = "전국의 구군을 반환한다.", response = List.class)
	@GetMapping("/gugun")
	public ResponseEntity<List<SidoGugunCodeDto>> gugun(
			@RequestParam("sido") @ApiParam(value = "시도코드.", required = true) String sido) throws Exception {
		return new ResponseEntity<List<SidoGugunCodeDto>>(attractionService.getGugunInSido(sido), HttpStatus.OK);
	}
	
	@ApiOperation(value = "여행지 정보", notes = "여행지 정보를 반환한다.", response = List.class)
	@GetMapping("/destination")
	public ResponseEntity<List<TouristSpotInfoDto>> destination(
			@ApiParam(value = "시도코드.", required = true) SearchOptionDto searchOptionDto) throws Exception {
		return new ResponseEntity<List<TouristSpotInfoDto>>(attractionService.getDestination(searchOptionDto), HttpStatus.OK);
	}

}
