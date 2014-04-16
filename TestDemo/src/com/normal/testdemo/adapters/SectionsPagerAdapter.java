package com.normal.testdemo.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import com.normal.testdemo.R;
import com.normal.testdemo.activitys.MainActivity;
import com.normal.testdemo.fragments.PlaceholderFragment;

import java.util.Locale;

/**
 * Created by ex_chenjinghao on 2014/4/8.
 * This Adapter is the controller of PlaceholderFragment.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
	private Context context = MainActivity.context;

	public SectionsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		// getItem is called to instantiate the fragment for the given page.
		// Return a PlaceholderFragment (defined as a static inner class
		// below).
		switch (position){
			case 0:
				Log.i("1","fragment1 Created");
				return PlaceholderFragment.newInstance(position + 1);
			case 1:
				Log.i("2","fragment2 Created");
				return PlaceholderFragment.newInstance(position + 1);
			case 2:
				Log.i("3","fragment3 Created");
				return PlaceholderFragment.newInstance(position + 1);
		}
		return null;
	}

	@Override
	public int getCount() {
		// Show 3 total pages.
		// 实践证明3个tab最合适，4个就会导致tab栏过长。
		return 3;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// 显示标签上的标题
		Locale l = Locale.getDefault();
		switch (position) {
			case 0:
				return context.getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return context.getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return context.getString(R.string.title_section3).toUpperCase(l);
		}
		return null;
	}
}
