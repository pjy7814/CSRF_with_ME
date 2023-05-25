package com.ssafy.vue.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "SidoGugunCodeDto : 시도, 구군정보", description = "시도, 구군의 이름을 나타낸다.")
public class SidoGugunCodeDto {

	@ApiModelProperty(value = "시도코드")
	private String sidoCode;
	@ApiModelProperty(value = "시도이름")
	private String sidoName;
	@ApiModelProperty(value = "구군코드")
	private String gugunCode;
	@ApiModelProperty(value = "구군이름")
	private String gugunName;
	
	
	

	public SidoGugunCodeDto() {
		super();
	}

	public SidoGugunCodeDto(String sidoCode, String sidoName, String gugunCode, String gugunName) {
		super();
		setSidoCode(sidoCode);
		setSidoName(sidoName);
		setGugunCode(gugunCode);
		setGugunName(gugunName);
	}

	public String getSidoCode() {
		return sidoCode;
	}

	public void setSidoCode(String sidoCode) {
		if (sidoCode != null) {
			this.sidoCode = sidoCode;
		}
	}

	public String getSidoName() {
		return sidoName;
	}

	public void setSidoName(String sidoName) {
		if (sidoName != null) {
			this.sidoName = sidoName;
		}
	}

	public String getGugunCode() {
		return gugunCode;
	}

	public void setGugunCode(String gugunCode) {
		if (gugunCode != null) {
			this.gugunCode = gugunCode;
		}
	}

	public String getGugunName() {
		return gugunName;
	}

	public void setGugunName(String gugunName) {
		if(gugunName != null) {
			this.gugunName = gugunName;
		}
	}

	@Override
	public String toString() {
		return "SidoGugunCodeDto [sidoCode=" + sidoCode + ", sidoName=" + sidoName + ", gugunCode=" + gugunCode
				+ ", gugunName=" + gugunName + "]";
	}
	
	
}
