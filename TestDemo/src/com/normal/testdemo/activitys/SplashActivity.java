package com.normal.testdemo.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import com.google.gson.Gson;
import com.normal.testdemo.R;
import com.normal.testdemo.beans.Hotels;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ex_chenjinghao on 2014/4/8.
 * 闪屏的页面
 */
public class SplashActivity extends Activity {
	public static final String JSON_URL = "https://s3-ap-northeast-1.amazonaws.com/testhotel/hotels.json";
	// json是否解释完成
	public static boolean ready = false;
	// 这是要传入到下一个Activity的列表中显示的数据
	public static Hotels hotels;
	// 闪屏时间
	public final int SPLASH_DISPLAY_LENGHT = 5000;
	// 计时器是否到时
	public boolean timeout = false;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		// 启动一个异步任务去获取json数据
		new GetJson().execute(JSON_URL);
		// 进行时间调度
		schedule(SPLASH_DISPLAY_LENGHT);
	}

	// 时间调试，delay表示过多长时间执行
	public void schedule(long delay) {
		// 如果两个条件都满足了，就启动MainActivity，同时将数据传送过去。
		if (ready && timeout) {
			Intent intent = new Intent(this, MainActivity.class);
			intent.putParcelableArrayListExtra("hotels", hotels.getHotels());
			startActivity(intent);
			this.finish();
		}
		else {
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					timeout = true;
					schedule(500);
				}
			}, delay);
		}
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		menu.add("aedf");
		return super.onMenuOpened(featureId, menu);
	}

	// 异步任务，获取json数据
	public static class GetJson extends AsyncTask<String, Integer, Hotels> {
		// 后台线程，进行网络连接
		@Override
		protected Hotels doInBackground(String... params) {
			try {
				InputStreamReader isr = new InputStreamReader(new URL(params[0]).openStream());
				Gson gson = new Gson();
				return gson.fromJson(isr, Hotels.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		// 当后台线程执行完之后会调用该方法
		@Override
		protected void onPostExecute(Hotels result) {
			if (result != null) {
				hotels = result;
				ready = true;
				Log.i("result", ready + "");
			}
		}
	}

}