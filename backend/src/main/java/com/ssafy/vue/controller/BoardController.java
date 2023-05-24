package com.ssafy.vue.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.vue.model.BoardDto;
import com.ssafy.vue.model.BoardParameterDto;
import com.ssafy.vue.model.MemberDto;
import com.ssafy.vue.model.service.BoardService;
import com.ssafy.vue.model.service.FileHandlerService;
import com.ssafy.vue.model.service.FileHandlerServiceImpl;
import com.ssafy.vue.model.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

//http://localhost:9999/vue/swagger-ui.html
//@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST} , maxAge = 6000)
@RestController
@RequestMapping("/board")
@Api("게시판 컨트롤러  API V1")
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private BoardService boardService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private FileHandlerService fileHandlerService;
/*
 * 통합...
 */
	@ApiOperation(value = "게시판 글작성", notes = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> writeArticle(@ApiParam(value = "게시글 정보.", required = true) BoardDto boardDto,
			@RequestParam(value="file", required=false) List<MultipartFile> files) throws Exception {
		logger.info("writeArticle - 호출");
		MemberDto memberDto = new MemberDto();
		memberDto.setMemberId(boardDto.getBoardWriterId());
		
		switch(boardDto.getBoardType()) {
		case "notice":
			//관리자 권한을 가지고 있는 유저인지 체크해야 한다. 
			
			if(!memberService.checkAdmin(boardDto.getBoardWriterId())) {
				//관리자 권한이 없을 경우 UNAUTHORIZED : status 401을 반환한다.
				return new ResponseEntity<String>(FAIL, HttpStatus.UNAUTHORIZED);
			}
			if (boardService.writeArticle(boardDto)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		case "share":
			//token 내 memberId와 boardWriterId가 동일한지 체킹해준다.
			if(!memberService.checkEqualMember(boardDto.getBoardWriterId())) {
				return new ResponseEntity<String>(FAIL, HttpStatus.UNAUTHORIZED);
			}
			List<String> filePathList = fileHandlerService.parseFileInfo(memberDto, files);
			if (boardService.writeArticle(boardDto)) {
				if (boardService.uploadImages(boardDto, filePathList)) {
					return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
				}
				return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
			}
		default:
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
		 
	}

	@ApiOperation(value = "게시판 글목록", notes = "모든 게시글의 정보를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<BoardDto>> listShareArticle(
			@ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) BoardParameterDto boardParameterDto)
			throws Exception {
		logger.info("listShareArticle - 호출");
		System.out.println(boardService.listArticle(boardParameterDto));
		return new ResponseEntity<List<BoardDto>>(boardService.listArticle(boardParameterDto), HttpStatus.OK);
	}

	@ApiOperation(value = "게시판 글보기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = BoardDto.class)
	@GetMapping("/{articleno}")
	public ResponseEntity<Map<String, Object>> getArticle(
			@PathVariable("articleno") @ApiParam(value = "얻어올 글의 글번호.", required = true) int articleno)
			throws Exception {
		logger.info("getArticle - 호출 : " + articleno);
        Map<String, Object> resultMap = boardService.getArticle(articleno);

		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}

	@ApiOperation(value = "게시판 글수정", notes = "수정할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping
	public ResponseEntity<String> modifyArticle(
			@RequestBody @ApiParam(value = "수정할 글정보.", required = true) BoardDto boardDto) throws Exception {
		logger.info("modifyArticle - 호출 {}", boardDto);

		if (boardService.modifyArticle(boardDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}

	@ApiOperation(value = "게시판 글삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/{articleno}")
	public ResponseEntity<String> deleteArticle(
			@PathVariable("articleno") @ApiParam(value = "살제할 글의 글번호.", required = true) int articleno)
			throws Exception {
		logger.info("deleteArticle - 호출");
		if (boardService.deleteArticle(articleno)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}