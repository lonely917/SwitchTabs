package com.normal.testdemo.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import com.normal.testdemo.R;
import com.normal.testdemo.activitys.MainActivity;
import com.normal.testdemo.activitys.SplashActivity;
import com.normal.testdemo.adapters.HotelListAdapter;
import com.normal.testdemo.beans.Hotel;
import com.normal.testdemo.utils.Utils;
import com.normal.testdemo.view.XListView;

import java.util.List;

/**
 * Created by ex_chenjinghao on 2014/4/8.
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment implements XListView.IXListViewListener {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";
	private static int refreshCnt = 0;
	//	private ListView lv_hotels; // 要显示的ListView
	private XListView lv_hotels; // 要显示的ListView
	private List<Hotel> hotels; // 要显示在ListView的数据
	private SwipeRefreshLayout refresh;//下拉刷新的布局
	private int start = 0;
	private Handler mHandler;
	private HotelListAdapter mAdapter;

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static PlaceholderFragment newInstance(int sectionNumber) {
		PlaceholderFragment fragment = new PlaceholderFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 这一步很重要，给Utils这个类设置缓存文件夹
		Utils.cacheDir = getActivity().getCacheDir();
		// 从intent中获取数据
		Intent intent = getActivity().getIntent();
		hotels = intent.getParcelableArrayListExtra("hotels");
	}

	// 页面初始化，主要做两个方面的东西，通过id找到View、给view设置适配器，监听器
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mHandler = new Handler();
		// 这一步很重要，给Utils这个类设置缓存文件夹
		Utils.cacheDir = MainActivity.context.getCacheDir();
		// 从intent中获取数据
		Intent intent = getActivity().getIntent();
		hotels = intent.getParcelableArrayListExtra("hotels");
		Log.i("hotels", hotels + "");
		// 对页面控件进行初始化
		View rootView = inflater.inflate(R.layout.fragment_main, container,false);
		// textView显示的是页面上的内容，不是标签上的内容。
		assert rootView != null;
		TextView textView = (TextView) rootView.findViewById(R.id.section_label);
		textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
//		refresh = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh);//查找下拉刷新
//		refresh.setOnRefreshListener(this);//设置下拉刷新的监听
//		refresh.setColorScheme(R.color.red,R.color.blue,R.color.green,R.color.black);//给下拉刷新添加颜色
		lv_hotels = (XListView) rootView.findViewById(R.id.lv_hotels);
		lv_hotels.setAdapter(new HotelListAdapter(getActivity(), hotels));
		lv_hotels.setPullLoadEnable(true);
		lv_hotels.setXListViewListener(this);
		Log.i("setAdapter","初始化数据");
		// 重点是理解android的事件处理机制，观察者模式和java匿名内部类的使用方法
		lv_hotels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position <= hotels.size()) {
					Hotel h = hotels.get(position);
					// 这里有两个难点，一个是理解inflate的作用，另一个是自定义对话框的方式
					View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_view, null);
					TextView tv_hotelName = (TextView) dialogView.findViewById(R.id.tv_hotel_name);
					TextView tv_streetAddress = (TextView) dialogView.findViewById(R.id.tv_street_address);
					TextView tv_nightlyRate = (TextView) dialogView.findViewById(R.id.tv_nightly_rate);
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					// 设置按钮那里本来应该是传入一个监听器的，但因为没什么做的，所以直接用null
					builder.setTitle("Hotel info :").setView(dialogView).setPositiveButton("确定", null);
					tv_hotelName.setText(h.getName());
					tv_hotelName.setTextColor(getResources().getColor(R.color.black));
					tv_streetAddress.setText(h.getStreet_address());
					tv_streetAddress.setTextColor(getResources().getColor(R.color.black));
					tv_nightlyRate.setText(h.getNightly_rate());
					tv_nightlyRate.setTextColor(getResources().getColor(R.color.black));
					builder.show();
				}
			}
		});
		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

/*	@Override
	public void onRefresh() {
		new Handler().postDelayed(new Runnable() {
			public void run() {
				refresh.setRefreshing(false);
				Toast.makeText(getActivity(), "正在刷新",Toast.LENGTH_SHORT).show();
//				adapter.notifyDataSetChanged();
			}
		}, 1000);
	}*/

	private void geneItems() {
		for (int i = 0; i != 5; ++i) {
			hotels.add(new Hotel(null, "new " + (++start), "new address", "new nightly rate"));
		}
	}

	private void onLoad() {
		lv_hotels.stopRefresh();
		lv_hotels.stopLoadMore();
		lv_hotels.setRefreshTime("刚刚");
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
//				start = ++refreshCnt;
				new SplashActivity.GetJson().execute(SplashActivity.JSON_URL);
				hotels.clear();
				// 从intent中获取数据
				hotels = SplashActivity.hotels.getHotels();
				mAdapter = new HotelListAdapter(getActivity(), hotels);
//				mAdapter.notifyDataSetChanged();
				lv_hotels.setAdapter(mAdapter);
				onLoad();
			}
		}, 2000);
		Toast.makeText(getActivity(), "数据已刷新", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				geneItems();
				mAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
		Toast.makeText(getActivity(), "新数据已加载", Toast.LENGTH_SHORT).show();
	}
}