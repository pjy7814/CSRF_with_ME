package com.ssafy.vue.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.model.MemberDto;

@Mapper
public interface MemberMapper {

	public MemberDto login(MemberDto memberDto) throws SQLException;
	public MemberDto userInfo(String userid) throws SQLException;
	public void saveRefreshToken(Map<String, String> map) throws SQLException;
	public Object getRefreshToken(String userid) throws SQLException;
	public void deleteRefreshToken(Map<String, String> map) throws SQLException;
	public MemberDto memberInfo(String memberId) throws SQLException;
	public void regist(MemberDto memberDto) throws SQLException;
	public void registSalt(String memberId, String salt) throws SQLException;
	public String getSalt(String memberId) throws SQLException;
	public void updateMember(MemberDto memberDto) throws SQLException;
	public void updateMemberPw(MemberDto memberDto) throws SQLException;
	public void updateSalt(String memberId, String salt) throws SQLException;
	public String getMemberRoleCd(String memberId) throws SQLException;
}
