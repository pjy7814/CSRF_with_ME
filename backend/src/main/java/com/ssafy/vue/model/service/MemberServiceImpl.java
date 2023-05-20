package com.ssafy.vue.model.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.util.OpenCrypt;
import com.ssafy.vue.model.MemberDto;
import com.ssafy.vue.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public MemberDto login(MemberDto memberDto) throws Exception {
		if (memberDto.getMemberId() == null || memberDto.getMemberPassword() == null)
			return null;
		return sqlSession.getMapper(MemberMapper.class).login(memberDto);
	}

	@Override
	public MemberDto memberInfo(String memberId) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).memberInfo(memberId);
	}

	@Override
	@Transactional
	public boolean regist(MemberDto memberDto) throws Exception {
		try {
			String salt = UUID.randomUUID().toString();
	        String hashPw = OpenCrypt.getSHA256(memberDto.getMemberPassword(), salt);
	        
	        memberDto.setMemberPassword(hashPw);
	        sqlSession.getMapper(MemberMapper.class).regist(memberDto);
	        sqlSession.getMapper(MemberMapper.class).registSalt(memberDto.getMemberId(), salt);
			
			return true;
		} catch (Exception e) {
	        sqlSession.rollback();
	        return false;
	    }
	}
}
