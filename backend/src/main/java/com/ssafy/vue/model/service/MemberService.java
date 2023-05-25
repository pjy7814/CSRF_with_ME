package com.ssafy.vue.model.service;

import com.ssafy.util.MyException;
import com.ssafy.vue.model.MemberDto;

public interface MemberService {

	public MemberDto login(MemberDto memberDto) throws MyException;
	public MemberDto memberInfo(String memberId) throws MyException;
	public boolean regist(MemberDto memberDto) throws MyException;
	public boolean update(MemberDto memberDto) throws MyException;
	public boolean checkAdmin(String boardWriterId) throws MyException;
	public boolean checkEqualMember(String boardWriterId) throws MyException;
}
