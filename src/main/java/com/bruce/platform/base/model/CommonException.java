package com.bruce.platform.base.model;


public class CommonException {
	
	private ErrorENUM errorEnum;
	private String moreInfo;
	
	public ErrorENUM getErrorEnum() {
		return errorEnum;
	}
	public void setErrorEnum(ErrorENUM errorEnum) {
		this.errorEnum = errorEnum;
	}
	public String getMoreInfo() {
		return moreInfo;
	}
	public void setMoreInfo(String moreInfo) {
		this.moreInfo = moreInfo;
	}
}
