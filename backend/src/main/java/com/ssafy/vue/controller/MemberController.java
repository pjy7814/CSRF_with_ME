package com.ssafy.vue.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.model.MemberDto;
import com.ssafy.vue.model.service.JwtServiceImpl;
import com.ssafy.vue.model.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/member")
@Api("사용자 컨트롤러  API V1")
public class MemberController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private JwtServiceImpl jwtService;

	@Autowired
	private MemberService memberService;

	@ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메세지를 반환한다.", response = Map.class)
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) MemberDto memberDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		System.out.println(memberDto);
		try {
			MemberDto loginUser = memberService.login(memberDto);
			if (loginUser != null) {
				String accessToken = jwtService.createAccessToken("memberId", loginUser.getMemberId());// key, data
				resultMap.put("access-token", accessToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "회원정보 받아오기", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@PostMapping("/info")
	public ResponseEntity<Map<String, Object>> getInfo(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		if (jwtService.checkToken(request.getHeader("access-token"))) {
			try {
//				로그인 사용자 정보.
				String memberId = jwtService.getMemberId();
				MemberDto memberDto = memberService.memberInfo(memberId);
				resultMap.put("memberInfo", memberDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			resultMap.put("message", FAIL);
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "로그아웃", notes = "회원 정보를 담은 Token을 제거한다.", response = Map.class)
	@GetMapping("/logout/{memberId}")
	public ResponseEntity<?> removeToken(@PathVariable("memberId") String memberId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}

	@ApiOperation(value = "회원가입", notes = "회원 정보를 가져와 회원가입을 수행한다.", response = Map.class)
	@PostMapping("/regist")
	public ResponseEntity<Map<String, Object>> regist(
			@RequestBody @ApiParam(value = "회원가입 시 필요한 회원정보(아이디, 이름, 이메일, 비밀번호, 시도코드, 군구코드).", required = true) MemberDto memberDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			boolean registMember = memberService.regist(memberDto);
			if (registMember) {
				resultMap.put("message", SUCCESS);

			} else {
				resultMap.put("message", FAIL);
			}
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "수정하기", notes = "회원 정보를 가져와 회원가입을 수정한다.", response = Map.class)
	@PostMapping("/update")
	public ResponseEntity<Map<String, Object>> update(
			@RequestBody @ApiParam(value = "수정하기 시 필요한 회원정보(아이디, 이름, 이메일, 비밀번호, 시도코드, 군구코드).", required = true) MemberDto memberDto,
			HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		if (jwtService.checkToken(request.getHeader("access-token"))) {
			try {
				boolean updateMember = memberService.update(memberDto);
				if (updateMember) {
					resultMap.put("message", SUCCESS);

				} else {
					resultMap.put("message", FAIL);
				}
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			resultMap.put("message", FAIL);
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}
