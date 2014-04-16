package com.normal.testdemo.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.normal.testdemo.R;
import com.normal.testdemo.beans.Hotel;
import com.normal.testdemo.utils.AsynImageLoader;

import java.util.List;

/**
 * Created by Normal on 2014/4/8.
 * This is the adapter of hotelListView in PlaceholderFragment.
 */
public class HotelListAdapter extends BaseAdapter {

	private List<Hotel> hotels;
	private LayoutInflater inflater;
	private AsynImageLoader loader;
	private Bitmap defaultBitmap;
	private Bitmap errorBitmap;


	public HotelListAdapter(Context context, List<Hotel> list) {
		inflater = LayoutInflater.from(context);
		this.hotels = list;
		// 获得两个图片，加载中和错误
		defaultBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_loading);
		errorBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_error);
		// 异步加载图片的类
		loader = new AsynImageLoader(new Handler(), defaultBitmap, errorBitmap);
	}

	@Override
	public int getCount() {
		// 这个值是表示列表有多少项
		if(hotels!=null){
			return hotels.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// 每一项的具体内容
		return hotels.get(position);
	}

	@Override
	public long getItemId(int position) {
		// 每一项的id，一般直接使用position作为id
		return position;
	}

	// 获得每一项的View，重点！！！！！！！
	// 先要有一个类，里面存放列表中的每一项的内容，下面的ViewHolder就是
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		// 这个几乎是通用的写法了，需要仔细琢磨
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item, null);
			holder = new ViewHolder();
			holder.img_hotelPic = (ImageView) convertView.findViewById(R.id.img_hotel_pic);
			holder.tv_hotelName = (TextView) convertView.findViewById(R.id.tv_hotel_name);
			holder.tv_streetAddress = (TextView) convertView.findViewById(R.id.tv_street_address);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_hotelName.setText(hotels.get(position).getName());
		holder.tv_streetAddress.setText(hotels.get(position).getStreet_address());
		holder.img_hotelPic.setTag(hotels.get(position).getThumbnail());
		// 进行图片的异步加载
		loader.loadBitmap(holder.img_hotelPic);
		return convertView;
	}
}

class ViewHolder {
	ImageView img_hotelPic;
	TextView tv_hotelName;
	TextView tv_streetAddress;
}