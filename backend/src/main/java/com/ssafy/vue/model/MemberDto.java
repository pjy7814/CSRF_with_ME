package com.ssafy.vue.model;

import java.sql.Timestamp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "MemberDto : 회원정보", description = "회원의 상세 정보를 나타낸다.")
public class MemberDto {

	@ApiModelProperty(value = "회원 아이디")
	private String memberId;
	@ApiModelProperty(value = "회원 이름")
	private String memberName;
	@ApiModelProperty(value = "회원 비밀번호")
	private String memberPassword;
	@ApiModelProperty(value = "회원 이메일")
	private String memberEmail;
	@ApiModelProperty(value = "시도 코드")
	private int sidoCode; 
	@ApiModelProperty(value = "군구 코드")
	private int gunguCode; 
	@ApiModelProperty(value = "회원 가입일")
	private Timestamp createdAt;
	@ApiModelProperty(value = "회원 권한 코드")
	private String memberRoleCd;
	
	public MemberDto() {
	    // 기본 생성자 내용
	}
	// 로그인 시
	public MemberDto(String memberId, String memberPassword) {
		super();
		setMemberId(memberId);
		setMemberPassword(memberPassword);
	}
	
	// 회원가입 시
	public MemberDto(String memberId, String memberName, String memberPassword, String memberEmail, int sidoCode,
			int gunguCode) {
		super();
		setMemberId(memberId);
		setMemberName(memberName);
		setMemberPassword(memberPassword);
		setMemberEmail(memberEmail);
		setSidoCode(sidoCode);
		setGunguCode(gunguCode);
	}
	
	

	// 로그인시 멤버의 정보 불러오기
	public MemberDto(String memberId, String memberName,String memberEmail) {
		super();
		setMemberId(memberId);
		setMemberName(memberName);
		setMemberEmail(memberEmail);
	}
	
	// mypage	
	public MemberDto(String memberId, String memberName, String memberEmail, int sidoCode, int gunguCode) {
		super();
		setMemberId(memberId);
		setMemberName(memberName);
		setMemberEmail(memberEmail);
		setSidoCode(sidoCode);
		setGunguCode(gunguCode);
	}
	
	public MemberDto(String memberId, String memberName, String memberPassword, String memberEmail, int sidoCode,
			int gunguCode, Timestamp createdAt, String memberRoleCd) {
		super();
		setMemberId(memberId);
		setMemberName(memberName);
		setMemberPassword(memberPassword);
		setMemberEmail(memberEmail);
		setSidoCode(sidoCode);
		setGunguCode(gunguCode);
		setCreatedAt(createdAt);
		setMemberRoleCd(memberRoleCd);
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		if (memberId != null) {
			this.memberId = memberId;
		}
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		if (memberName != null) {
			this.memberName = memberName;
		}
	}
	public String getMemberPassword() {
		return memberPassword;
	}
	public void setMemberPassword(String memberPassword) {
		if (memberPassword != null) {
			this.memberPassword = memberPassword;
		}
	}
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		if (memberEmail != null) {
			this.memberEmail = memberEmail;
		}
	}
	public int getSidoCode() {
		return sidoCode;
	}
	public void setSidoCode(int sidoCode) {
		if (sidoCode > 0) {
			this.sidoCode = sidoCode;
		}
	}
	public int getGunguCode() {
		return gunguCode;
	}
	public void setGunguCode(int gunguCode) {
		if (gunguCode > 0) {
			this.gunguCode = gunguCode;
		}
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		if(createdAt != null) {
			this.createdAt = createdAt;
		}
	}
	
	public String getMemberRoleCd() {
		return memberRoleCd;
	}
	public void setMemberRoleCd(String memberRoleCd) {
		if (memberRoleCd.equals("STPKMCD001") || memberRoleCd.equals("HOTGUYSSAFYTP01")) {	
			this.memberRoleCd = memberRoleCd;
		}
	}
	@Override
	public String toString() {
		return "MemberDto [memberId=" + memberId + ", memberName=" + memberName + ", memberPassword=" + memberPassword
				+ ", memberEmail=" + memberEmail + ", sidoCode=" + sidoCode + ", gunguCode=" + gunguCode
				+ ", createdAt=" + createdAt + ", memberRoleCd=" + memberRoleCd + "]";
	}
	
	

}
