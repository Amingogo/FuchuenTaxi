package tw.com.fuchuen.utils;

import java.util.Calendar;

import tw.com.fuchuen.taxi.R;
import tw.com.fuchuen.taxi.config.TaxiLocalConfig;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class BasicUtils {
	public static boolean appInstalled(Context context, String uri) {
		PackageManager pm = context.getPackageManager();
		try{
			pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
			return true;
		}catch (PackageManager.NameNotFoundException e){
			return false;
		}
	}
	
	public static Dialog getDialog(Context c, int res, String title) {
		Dialog mDialog = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(c);
		View dialogView = LayoutInflater.from(c).inflate(res, null);
		builder.setView(dialogView);
		mDialog = builder.create();
		
		return mDialog;
	}
	
	public static Dialog getDialog(Context c, View res, String title) {
		Dialog mDialog = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(c);
		builder.setView(res);
		mDialog = builder.create();
		mDialog.setTitle(title);
		
		return mDialog;
	}
	
	public static void showToastMsg(Context context, String msg){
		Toast toast=Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0,0);
		toast.show();
	}
	
	public static void showLongToastMsg(Context context, String msg){
		Toast toast=Toast.makeText(context, msg, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0,0);
		toast.show();
	}
	
	public static SharedPreferences getSharedPreferences(Context context) {
		return context.getSharedPreferences(TaxiLocalConfig.SHARED_PREFERENCE_TAXI, Context.MODE_PRIVATE);
	}
	
	public static Editor putSharedPreferencesValue(Context context, String key, Object value) {
		SharedPreferences mPref = getSharedPreferences(context);
		Editor editor = mPref.edit();

		if(value == null) {
			editor.putString(key, (String) value);
		}
		
		if (value instanceof Boolean) {
			editor.putBoolean(key, (Boolean) value);
		}

		else if (value instanceof Integer) {
			editor.putInt(key, (Integer) value);
		}

		else if (value instanceof Long) {
			editor.putLong(key, (Long) value);
		}

		else if (value instanceof Float) {
			editor.putFloat(key, (Float) value);
		}

		else if (value instanceof String) {
			editor.putString(key, (String) value);
		}

		editor.commit();

		return editor;
	}
	
	public static void removeSharedPreferencesSetting(Context context, String key) {
		getSharedPreferences(context).edit().remove(key);
	}
	
	public static void saveDateToSharedPreference(Context context, int year, int month, int day) {
		putSharedPreferencesValue(context, TaxiLocalConfig.SHARED_PREFERENCE_TAXI_YEAR, year);
		putSharedPreferencesValue(context, TaxiLocalConfig.SHARED_PREFERENCE_TAXI_MONTH, month);
		putSharedPreferencesValue(context, TaxiLocalConfig.SHARED_PREFERENCE_TAXI_DAY, day);
	}
	
	public static void saveTimeToSharedPreference(Context context, int hour, int minute) {
		putSharedPreferencesValue(context, TaxiLocalConfig.SHARED_PREFERENCE_TAXI_HOUR, hour);
		putSharedPreferencesValue(context, TaxiLocalConfig.SHARED_PREFERENCE_TAXI_MINUTE, minute);
	}
	
	public static FragmentTransaction switchToFragment(Context mContext, Fragment fragment, Bundle args, int fromResource, boolean isAddStack, boolean needAnimation, int[] animationResources) {
		FragmentManager fragmentManager=((FragmentActivity) mContext).getSupportFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();

		if (needAnimation) {
			ft.setCustomAnimations(animationResources[0], animationResources[1], animationResources[2], animationResources[3]);
		}

		if (isAddStack) {
			ft.addToBackStack(TaxiLocalConfig.FRAGMENT_STACK_NAME);
		}

		ft.replace(fromResource, fragment, TaxiLocalConfig.FRAGMENT_STACK_NAME_MAIN);
		
		return ft;
	}
	
	public static FragmentTransaction switchToFragment(Context mContext, Fragment fragment, Bundle args, int fromResource, boolean isAddStack, boolean needAnimation) {
		int[] defaultAnimArr = new int[] { R.anim.enter_right, R.anim.leave_left, R.anim.enter_left, R.anim.leave_right };
		return switchToFragment(mContext, fragment, args, fromResource, isAddStack, needAnimation,defaultAnimArr);
	}
	
	public static FragmentTransaction switchToFragmentImmediately(Context mContext, Fragment fragment, Bundle args, int fromResource, boolean isAddStack, boolean needAnimation, int[] animationResources) {
		FragmentManager fragmentManager=((FragmentActivity) mContext).getSupportFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();

		if (needAnimation) {
			ft.setCustomAnimations(animationResources[0], animationResources[1], animationResources[2], animationResources[3]);
		}

		if (isAddStack) {
			ft.addToBackStack(TaxiLocalConfig.FRAGMENT_STACK_NAME);
		}

		ft.replace(fromResource, fragment, TaxiLocalConfig.FRAGMENT_STACK_NAME_MAIN);
		
		ft.commit();
		fragmentManager.executePendingTransactions();

		return ft;
	}
	
	public static FragmentTransaction switchToFragmentImmediately(Context mContext, Fragment fragment, Bundle args, int fromResource, boolean isAddStack, boolean needAnimation) {
		int[] defaultAnimArr = new int[] { R.anim.enter_right, R.anim.leave_left, R.anim.enter_left, R.anim.leave_right };
		return switchToFragmentImmediately(mContext, fragment, args, fromResource, isAddStack, needAnimation,defaultAnimArr);
	}
	
	public static void popFragment(Context mContext) {
		FragmentManager fragmentManager=((FragmentActivity) mContext).getSupportFragmentManager();
		fragmentManager.popBackStack();
	}
	
	public static int[] getDateArray(Context context) {
		Calendar calendar = Calendar.getInstance();
		int year = getSharedPreferences(context).getInt(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_YEAR, -1);
		int month = getSharedPreferences(context).getInt(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_MONTH, -1);
		int day = getSharedPreferences(context).getInt(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_DAY, -1);
		if(year == -1) year = calendar.get(Calendar.YEAR);
		if(month == -1) month = calendar.get(Calendar.MONTH);
		if(day == -1) day = calendar.get(Calendar.DAY_OF_MONTH);
		int[] dateArray = { year, month, day };
		return dateArray;
	}
	
	public static int[] getTimeArray(Context context) {
		Calendar calendar = Calendar.getInstance();
		int hour = getSharedPreferences(context).getInt(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_HOUR, -1);
		int minute = getSharedPreferences(context).getInt(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_MINUTE, -1);
		if(hour == -1) hour = calendar.get(Calendar.HOUR_OF_DAY);
		if(minute == -1) minute = calendar.get(Calendar.MINUTE);
		int[] timeArray = { hour, minute};
		return timeArray;
	}
	
	public static String getMessageString(Context context, String name, String count, String date, String time, String start, String destination) {
		return String.format(context.getString(R.string.message), name, count, date+" "+time, start, destination);
	}
	
}
