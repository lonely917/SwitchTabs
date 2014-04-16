package com.normal.testdemo.activitys;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.normal.testdemo.R;
import com.normal.testdemo.adapters.SectionsPagerAdapter;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {
	public static Context context;
	private boolean isPressed = false; // 表示之前是否按过back键
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.i("MainActivity","MainActivity Created");
		context = getApplicationContext();
		// Set up the action bar.
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		//创建viewpager中的Fragment
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			//设置TabTitle
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// 常用的方法是获得item的id，针对不同的id来进行处理
		switch (item.getItemId()) {
			case R.id.action_clean_cache:
				cleanCache();
				Toast.makeText(this, R.string.clean_cache_success, Toast.LENGTH_SHORT).show();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
	                          FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
	                            FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
	                            FragmentTransaction fragmentTransaction) {
	}

	// 清除缓存
	private void cleanCache() {
		File file = getCacheDir();
		File[] subFiles = file.listFiles();
		for (File f : subFiles) {
			f.delete();
		}
	}

	// 当有按键按下时会触发该方法
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 如果按下的是返回键
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitByDoubleBack();
		}
		return false;
	}

	// 快速双击返回键退出
	private void exitByDoubleBack() {
		Timer time = null;
		if (isPressed == false) {
			isPressed = true;
			// 这是Toast的用法
			Toast.makeText(this, R.string.note, Toast.LENGTH_LONG).show();
			time = new Timer();
			// 如果两秒之后还没再按下返回键就初始化
			time.schedule(new TimerTask() {
				@Override
				public void run() {
					isPressed = false;
				}
			}, 2000);
		} else {
			// 这是退出应用的一种方法
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		}
	}
}
