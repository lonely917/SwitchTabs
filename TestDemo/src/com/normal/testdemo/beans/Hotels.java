package com.normal.testdemo.beans;

import java.util.ArrayList;

/**
 * Created by ex_chenjinghao on 2014/4/8.
 * json转换之后就生成这样一个对象
 */
public class Hotels{
	private String type;
	private int page;
	// 这个是重点，保存多个hotel的信息
	private ArrayList<Hotel> hotels;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public ArrayList<Hotel> getHotels() {
		return hotels;
	}

	public void setHotels(ArrayList<Hotel> hotels) {
		this.hotels = hotels;
	}
}
