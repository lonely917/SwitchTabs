package com.normal.testdemo.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by ex_chenjinghao on 2014/4/8.
 * 工具类
 */
public class Utils {

	// 这个是缓存目录，需要自行设定
	public static File cacheDir;

	// 通过Url来获得图片，如果从本地缓存中找到就不进行网络获得
	// 否则进行网络获取，并写入缓存目录
	public static Bitmap getBitmapByUrl(String url) {
		String fileName = getFileNameFromUrl(url);
		Bitmap bitmap = null;
		// 先尝试本地获取
		bitmap = getBitmapFromCache(fileName);
		// 如果为空表明本地不存在
		if (bitmap == null) {
			URL imgUrl = null;
			InputStream is = null;
			try {
				imgUrl = new URL(url);
				is = imgUrl.openStream();
				// 图片的编码
				bitmap = BitmapFactory.decodeStream(is);
				// 写下缓存目录
				saveBitmapToCache(bitmap, fileName);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				try {
					is.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bitmap;
	}

	// 从缓存目录中获取，如果不存在，返回null
	public static Bitmap getBitmapFromCache(String fileName) {
		Bitmap result = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(cacheDir, fileName));
			result = BitmapFactory.decodeStream(fis);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (fis != null) {
				try {
					fis.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	// 将图片保存到缓存目录中
	public static void saveBitmapToCache(Bitmap bitmap, String fileName) {
		if (bitmap == null) {
			return;
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(cacheDir, fileName));
			// 这个是重点方法
			bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (fos != null) {
				try {
					fos.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 从Url中解析出文件名
	public static String getFileNameFromUrl(String src) {
		int i = src.lastIndexOf("/");
		return src.substring(i + 1, src.length());
	}
}
