package com.normal.testdemo.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ex_chenjinghao on 2014/4/8.
 * 对应于一个酒店，重点在Parcelable这个接口的使用方法
 */
public class Hotel implements Parcelable{
	private String thumbnail;
	private String name;
	private String street_address;
	private String nightly_rate;

	public Hotel(String thumbnail, String name, String street_address, String nightly_rate) {
		this.thumbnail = thumbnail;
		this.name = name;
		this.street_address = street_address;
		this.nightly_rate = nightly_rate;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet_address() {
		return street_address;
	}

	public void setStreet_address(String street_address) {
		this.street_address = street_address;
	}

	public String getNightly_rate() {
		return nightly_rate;
	}

	public void setNightly_rate(String nightly_rate) {
		this.nightly_rate = nightly_rate;
	}

	// 这个几乎是Parcel的通用写法了~~~要理解其打包的思想
	// 几乎都是返回0
	@Override
	public int describeContents() {
		return 0;
	}

	// 打包，要注意顺序
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(thumbnail);
		dest.writeString(name);
		dest.writeString(street_address);
		dest.writeString(nightly_rate);
	}

	// 解包，写法几乎是一个的
	public static final Parcelable.Creator<Hotel> CREATOR = new Parcelable.Creator<Hotel>() {

		public Hotel createFromParcel(Parcel in) {
			return new Hotel(in.readString(), in.readString(), in.readString(), in.readString());
		}

		public Hotel[] newArray(int size) {
			return new Hotel[size];
		}
	};
}
