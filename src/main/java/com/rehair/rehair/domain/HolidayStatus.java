package com.rehair.rehair.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum HolidayStatus {
	HANNA("김한나"), CHUL("장철"), HANA("송하나"), BELLISSA("안벨리사");

	private final String name;
	private HolidayStatus(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}

	public static HolidayStatus nameOf(String name){
		for (HolidayStatus status : HolidayStatus.values()){
			if (status.getName().equals(name)){
				return status;
			}
		}
		return null;
	}
}
