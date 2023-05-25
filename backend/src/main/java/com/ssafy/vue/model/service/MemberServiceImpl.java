package com.ssafy.vue.model.service;

import java.sql.SQLException;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.util.MyException;
import com.ssafy.util.OpenCrypt;
import com.ssafy.vue.exception.CryptException;
import com.ssafy.vue.model.MemberDto;
import com.ssafy.vue.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private JwtService jwtService;
	@Autowired
	private SqlSession sqlSession;

	// 회원가입에서 role code 넣기
	@Override
	public MemberDto login(MemberDto memberDto) throws MyException {
		try {
			if (memberDto.getMemberId() == null || memberDto.getMemberPassword() == null)
				return null;
			String salt = sqlSession.getMapper(MemberMapper.class).getSalt(memberDto.getMemberId());
			String hashPw = OpenCrypt.getSHA256(memberDto.getMemberPassword(), salt);
			memberDto.setMemberPassword(hashPw);

			return sqlSession.getMapper(MemberMapper.class).login(memberDto);
		}catch(SQLException e) {
			throw new MyException("서버 오류가 발생했습니다!");
		}catch(CryptException e) {
			throw new MyException("크립토 오류가 발생했습니다!");
		}
		
	}

	@Override
	public MemberDto memberInfo(String memberId) throws MyException {
		try {
			return sqlSession.getMapper(MemberMapper.class).memberInfo(memberId);
		}catch(SQLException e) {
			throw new MyException("서버 오류가 발생했습니다!");
		}
	}

	@Override
	@Transactional
	public boolean regist(MemberDto memberDto) throws MyException {
		try {
			String salt = UUID.randomUUID().toString();
			String hashPw = OpenCrypt.getSHA256(memberDto.getMemberPassword(), salt);

			memberDto.setMemberPassword(hashPw);
			sqlSession.getMapper(MemberMapper.class).regist(memberDto);
			sqlSession.getMapper(MemberMapper.class).registSalt(memberDto.getMemberId(), salt);

			return true;
		} catch (SQLException e) {
			sqlSession.rollback();
			throw new MyException("서버 오류가 발생했습니다!");
		} catch (CryptException e) {
			throw new MyException("크립토 오류가 발생했습니다!");
		}
	}

	@Override
	@Transactional
	public boolean update(MemberDto memberDto) throws MyException {
		try {
			if (memberDto.getMemberPassword() == null) { // 비밀번호 변경 안함
				sqlSession.getMapper(MemberMapper.class).updateMember(memberDto);
			} else { // 비밀번호 변경
				String salt = UUID.randomUUID().toString();
				String hashPw = OpenCrypt.getSHA256(memberDto.getMemberPassword(), salt);

				memberDto.setMemberPassword(hashPw);
				sqlSession.getMapper(MemberMapper.class).updateMemberPw(memberDto);
				String memberId = memberDto.getMemberId();
				sqlSession.getMapper(MemberMapper.class).updateSalt(memberId, salt);
			}
			return true;
		} catch (SQLException e) {
			sqlSession.rollback();
			throw new MyException("서버 오류가 발생했습니다!");
		} catch (CryptException e) {
			throw new MyException("크립토 오류가 발생했습니다!");
		}
	}

	@Override
	public boolean checkAdmin(String boardWriterId) throws MyException {
		try {
			// 토큰과 글쓴이의 member_id가 동일한지 비교
			if (!checkEqualMember(boardWriterId)) return false;
			// 동일 할 시에, 유저가 관리자 계정인지 체킹
			String curMemberRoleCd = sqlSession.getMapper(MemberMapper.class).getMemberRoleCd(boardWriterId);
			return curMemberRoleCd.equals("HOTGUYSSAFYTP01");
		} catch (SQLException e) {
			return false;
		}
	}
	
	@Override
	public boolean checkEqualMember(String boardWriterId) throws MyException{
		return jwtService.getMemberId().equals(boardWriterId);
	}
}
