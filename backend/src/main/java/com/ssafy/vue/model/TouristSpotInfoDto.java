package com.ssafy.vue.model;

import com.ssafy.util.MyException;

public class TouristSpotInfoDto {

	private double latitude, longitude;
	private String title, address1, address2, firstImage1, firstImage2;

	public TouristSpotInfoDto(double latitude, double longitude, String title, String address1, String address2,
							 String firstImage1, String firstImage2) throws MyException {
		setLatitude(latitude);
		setLongitude(longitude);
		setTitle(title);
		setAddress1(address1);
		setAddress2(address2);
		setFirstImage1(firstImage1);
		setFirstImage2(firstImage2);
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) throws MyException {
		if (latitude < -90 || latitude > 90) {
			throw new MyException(latitude + "는 잘못된 위도 입력입니다.");
		}
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) throws MyException {
		if (longitude < -180 || longitude > 180) {
			throw new MyException("잘못된 경도 입력입니다.");
		}
		this.longitude = longitude;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) throws MyException {
		if(title==null || title.length()==0) {
			throw new MyException("잘못된 이름을 입력하였습니다.");
		}
		this.title = title;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) throws MyException {
//		if(address1==null || address1.length()==0) {
//			throw new MyException("잘못된 주소값1을 입력하였습니다.");
//		}
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getFirstImage1() {
		return firstImage1;
	}

	public void setFirstImage1(String firstImage1) throws MyException {
		this.firstImage1 = firstImage1;
	}

	public String getFirstImage2() {
		return firstImage2;
	}

	public void setFirstImage2(String firstImage2) throws MyException {
		this.firstImage2 = firstImage2;
	}

	@Override
	public String toString() {
		return "TouristSpotInfoVO{" +
				"latitude=" + latitude +
				", longitude=" + longitude +
				", title='" + title + '\'' +
				", address1='" + address1 + '\'' +
				", address2='" + address2 + '\'' +
				", firstImage1='" + firstImage1 + '\'' +
				", firstImage2='" + firstImage2 + '\'' +
				'}';
	}
}
