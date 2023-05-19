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
	private String sidoCode; 
	@ApiModelProperty(value = "군구 코드")
	private String gunguCode; 
	@ApiModelProperty(value = "회원 가입일")
	private Timestamp createdAt;
		
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberPassword() {
		return memberPassword;
	}
	public void setMemberPassword(String memberPassword) {
		this.memberPassword = memberPassword;
	}
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	public String getSidoCode() {
		return sidoCode;
	}
	public void setSidoCode(String sidoCode) {
		this.sidoCode = sidoCode;
	}
	public String getGunguCode() {
		return gunguCode;
	}
	public void setGunguCode(String gunguCode) {
		this.gunguCode = gunguCode;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	@Override
	public String toString() {
		return "MemberDto [memberId=" + memberId + ", memberName=" + memberName + ", memberPassword=" + memberPassword
				+ ", memberEmail=" + memberEmail + ", sidoCode=" + sidoCode + ", gunguCode=" + gunguCode
				+ ", createdAt=" + createdAt + "]";
	}


}
