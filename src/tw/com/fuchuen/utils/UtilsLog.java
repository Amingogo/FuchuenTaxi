package tw.com.fuchuen.utils;

import android.util.Log;

public class UtilsLog {
	
	public static final boolean isDebug = false;
	
	public static void logDebug(Class<?> clazz, String msg) {
		if(isDebug) Log.d(clazz.getSimpleName(), msg);
	}
	
	public static void logError(Class<?> clazz, String msg) {
		Log.e(clazz.getSimpleName(), msg);
	}
	
}
