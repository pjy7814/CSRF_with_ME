package com.ssafy.vue.model.service;

import com.ssafy.vue.model.MemberDto;

public interface MemberService {

	public MemberDto login(MemberDto memberDto) throws Exception;
	public MemberDto memberInfo(String memberId) throws Exception;
	public boolean regist(MemberDto memberDto) throws Exception;
	public boolean update(MemberDto memberDto) throws Exception;
	public boolean checkAdmin(String boardWriterId) throws Exception;
	public boolean checkEqualMember(String boardWriterId) throws Exception;
}
