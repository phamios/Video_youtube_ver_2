package admega.cartoon.movies.common.utils;

import android.util.Log;
import admega.cartoon.movies.BuildConfig;

public class Logger {
	public static void v(String tag, String msg) {
		if (BuildConfig.DEBUG) {
			Log.v(tag, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (BuildConfig.DEBUG) {
			Log.d(tag, msg);
		}
	}

	public static void i(String tag, String msg) {
		if (BuildConfig.DEBUG) {
			Log.i(tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (BuildConfig.DEBUG) {
			Log.e(tag, msg);
		}
	}

	public static void out(String tag, String msg) {
		if (BuildConfig.DEBUG) {
			System.out.println(tag + "====> " + msg);
		}
	}
}
