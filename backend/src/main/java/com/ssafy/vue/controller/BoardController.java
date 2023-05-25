package com.ssafy.vue.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.ssafy.util.MyException;
import com.ssafy.vue.config.RecaptchaConfig;
import com.ssafy.vue.model.BoardDto;
import com.ssafy.vue.model.BoardParameterDto;
import com.ssafy.vue.model.MemberDto;
import com.ssafy.vue.model.service.BoardService;
import com.ssafy.vue.model.service.FileHandlerService;
import com.ssafy.vue.model.service.FileHandlerServiceImpl;
import com.ssafy.vue.model.service.MemberService;
import com.ssafy.vue.model.service.RecaptchaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/board")
@Api("게시판 컨트롤러  API V1")
public class BoardController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private BoardService boardService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private FileHandlerService fileHandlerService;
	@Autowired
	private RecaptchaService recaptchaService;

	@ApiOperation(value = "게시판 글작성", notes = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<Map<String, Object>> writeArticle(
			@ApiParam(value = "게시글 정보.", required = true) BoardDto boardDto,
			@RequestParam(value = "file", required = false) List<MultipartFile> files,
			@RequestParam(value = "recaptchaToken", required = true) String recaptchaToken) throws Exception {

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		String message = "";
		try {
			// 캡챠 확인
			if (!recaptchaService.verifyRecaptcha(recaptchaToken)) {
				throw new MyException("RecaptchaException");
			}

			switch (boardDto.getBoardType()) {
			case "notice":
				// 관리자 권한을 가지고 있는 유저인지 체크해야 한다.
				if (!memberService.checkAdmin(boardDto.getBoardWriterId())) {
					// 관리자 권한이 없을 경우 UNAUTHORIZED : status 401을 반환한다.
					message = "권한에 어긋나는 등록입니다! 사이트를 정상적으로 사용해주세요!";
					status = HttpStatus.UNAUTHORIZED;
				} else if (boardService.writeArticle(boardDto)) {
					message = "글 등록 성공!";
					status = HttpStatus.OK;
				} else {
					// 관리자 권한이 있었으나 글 등록에 실패함.
					message = "서버 에러로 인해 글 등록이 실패했습니다.";
					status = HttpStatus.INTERNAL_SERVER_ERROR;
				}
				break;
			case "share":
				// token 내 memberId와 boardWriterId가 동일한지 체킹해준다.
				if (!memberService.checkEqualMember(boardDto.getBoardWriterId())) {
					message = "권한에 어긋나는 등록입니다! 사이트를 정상적으로 사용해주세요!";
					status = HttpStatus.UNAUTHORIZED;
					break;
				}
				if (boardService.writeArticle(boardDto)) {
					if (files.size() >= 1) {
						List<String> filePathList = fileHandlerService.parseFileInfo(boardDto.getBoardWriterId(),
								files);
						if (boardService.uploadImages(boardDto, filePathList)) {
							message = "글 등록 성공!";
							status = HttpStatus.OK;
						} else {
							message = "글이 등록됬으나, 서버 에러로 인해 이미지 등록에 실패하였습니다!";
							status = HttpStatus.INTERNAL_SERVER_ERROR;
						}
					} else {
						message = "글 등록 성공!";
						status = HttpStatus.OK;
					}
				} else {
					message = "서버 에러로 인해 글 등록이 실패했습니다.";
					status = HttpStatus.INTERNAL_SERVER_ERROR;
				}
				break;
			default:
				message = "비 정상적입 접근입니다! 사이트를 정상적으로 사용해주세요!";
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				break;
			}
		} catch (MyException e) {
			switch (e.getMessage()) {
			case "RecaptchaException":
				message = "캡챠 인증에 실패했습니다! 다시 시도해주세요.";
				status = HttpStatus.BAD_REQUEST;
				break;
			case "SQLException":
				message = "서버 에러로 인해 글 등록이 실패했습니다.";
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				break;
			default:
				message = "비 정상적입 접근입니다! 사이트를 정상적으로 사용해주세요!";
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				break;
			}
		}
		resultMap.put("message", message);
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "게시판 글목록", notes = "모든 게시글의 정보를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<Map<String, Object>> listArticle(
			@ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) BoardParameterDto boardParameterDto)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		String message = "";
		try {
			resultMap.put("boardDtos", boardService.listArticle(boardParameterDto));
			message = SUCCESS;
			status =HttpStatus.OK;
		}catch(MyException e) {
			switch(e.getMessage()) {
			case "SQLException":
				message = "글을 받아오는데 실패했습니다!";
				status =HttpStatus.INTERNAL_SERVER_ERROR;
				break;
			default:
				message = "비 정상적입 접근입니다! 사이트를 정상적으로 사용해주세요!";
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				break;
			}
		}
		resultMap.put("message", message);
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "게시판 글보기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = BoardDto.class)
	@GetMapping("/{boardType}/{articleno}")
	public ResponseEntity<Map<String, Object>> getArticle(
			@PathVariable("boardType") String boardType,
			@PathVariable("articleno") @ApiParam(value = "얻어올 글의 글번호.", required = true) int articleno )
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		String message = "";
		try {
			resultMap.put("article",boardService.getArticle(articleno, boardType));
			status = HttpStatus.OK;
			message=SUCCESS;
		}catch(MyException e) {
			switch(e.getMessage()) {
			case "SQLException":
				message = "글을 받아오는데 실패했습니다!";
				status =HttpStatus.INTERNAL_SERVER_ERROR;
				break;
			default:
				message = "비 정상적입 접근입니다! 사이트를 정상적으로 사용해주세요!";
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				break;
			}
		}
		resultMap.put("message", message);
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "게시판 글수정", notes = "수정할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/modify")
	public ResponseEntity<Map<String, Object>> modifyArticle(@ApiParam(value = "수정할 글정보.", required = true) BoardDto boardDto,
			@RequestParam(value = "file", required = false) List<MultipartFile> files) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		String message = "";
		try {
			switch (boardDto.getBoardType()) {
			case "notice":
				// 관리자 권한을 가지고 있는 유저인지 체크해야 한다.
				if (!memberService.checkAdmin(boardDto.getBoardWriterId())) {
					// 관리자 권한이 없을 경우 UNAUTHORIZED : status 401을 반환한다.
					message = "권한에 어긋나는 수정 작업입니다! 사이트를 정상적으로 사용해주세요!";
					status = HttpStatus.UNAUTHORIZED;
				}else if (boardService.modifyArticle(boardDto)) {
					message = "글 수정 성공!";
					status = HttpStatus.OK;
				}else {
					message = "서버 에러로 인해 글 수정이 실패했습니다.";
					status = HttpStatus.INTERNAL_SERVER_ERROR;
				}
				break;
			case "share":
				// token 내 memberId와 boardWriterId가 동일한지 체킹해준다.
				if (!memberService.checkEqualMember(boardDto.getBoardWriterId())) {
					message = "권한에 어긋나는 수정입니다! 사이트를 정상적으로 사용해주세요!";
					status = HttpStatus.UNAUTHORIZED;
				}else if (boardService.modifyArticle(boardDto)) {
					message = "글 수정 성공!";
					status = HttpStatus.OK;
				}else {
					message = "서버 에러로 인해 글 수정이 실패했습니다.";
					status = HttpStatus.INTERNAL_SERVER_ERROR;
				}
				break;
			default:
				message = "비 정상적입 접근입니다! 사이트를 정상적으로 사용해주세요!";
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				break;
			}
		}catch(MyException e) {
			switch(e.getMessage()) {
			case "SQLException":
				message = "서버 에러로 인해 글 수정이 실패했습니다.";
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				break;
			default:
				message = "비 정상적입 접근입니다! 사이트를 정상적으로 사용해주세요!";
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				break;
			}
		}
		resultMap.put("message", message);
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "게시판 글삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/delete")
	public ResponseEntity<Map<String, Object>> deleteArticle(
			@RequestBody @ApiParam(value = "살제할 글의 글번호.", required = true) int articleno) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		String message = "";
		try {
			if (boardService.deleteArticle(articleno)) {
				message="글 삭제에 성공했습니다! 리스트 페이지로 돌아갑니다!";
				status=HttpStatus.OK;
			}else {
				message="글 삭제에 실패했습니다! 다시, 시도해주세요.";
				status=HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}catch(MyException e) {
			switch(e.getMessage()) {
			case "SQLException":
				message="서버 에러로 인해 글 삭제에 실패했습니다.";
				status=HttpStatus.INTERNAL_SERVER_ERROR;
				break;
			default:
				message = "비 정상적입 접근입니다! 사이트를 정상적으로 사용해주세요!";
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				break;
			}
		}
		resultMap.put("message", message);
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}