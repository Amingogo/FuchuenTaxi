package tw.com.fuchuen.utils;

import java.util.Calendar;

import tw.com.fuchuen.taxi.R;
import tw.com.fuchuen.taxi.config.TaxiLocalConfig;
import android.content.Context;

public class CustomUtils {
	
	public static void saveDateToSharedPreference(Context context, int year, int month, int day) {
		UtilsStorage.putSharedPreferencesValue(context, TaxiLocalConfig.SHARED_PREFERENCE_TAXI_YEAR, year);
		UtilsStorage.putSharedPreferencesValue(context, TaxiLocalConfig.SHARED_PREFERENCE_TAXI_MONTH, month);
		UtilsStorage.putSharedPreferencesValue(context, TaxiLocalConfig.SHARED_PREFERENCE_TAXI_DAY, day);
	}
	
	public static void saveTimeToSharedPreference(Context context, int hour, int minute) {
		UtilsStorage.putSharedPreferencesValue(context, TaxiLocalConfig.SHARED_PREFERENCE_TAXI_HOUR, hour);
		UtilsStorage.putSharedPreferencesValue(context, TaxiLocalConfig.SHARED_PREFERENCE_TAXI_MINUTE, minute);
	}
	
	public static int[] getDateArray(Context context) {
		Calendar calendar = Calendar.getInstance();
		int year = UtilsStorage.getSharedPreferences(context).getInt(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_YEAR, -1);
		int month = UtilsStorage.getSharedPreferences(context).getInt(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_MONTH, -1);
		int day = UtilsStorage.getSharedPreferences(context).getInt(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_DAY, -1);
		if(year == -1) year = calendar.get(Calendar.YEAR);
		if(month == -1) month = calendar.get(Calendar.MONTH);
		if(day == -1) day = calendar.get(Calendar.DAY_OF_MONTH);
		int[] dateArray = { year, month, day };
		return dateArray;
	}
	
	public static int[] getTimeArray(Context context) {
		Calendar calendar = Calendar.getInstance();
		int hour = UtilsStorage.getSharedPreferences(context).getInt(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_HOUR, -1);
		int minute = UtilsStorage.getSharedPreferences(context).getInt(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_MINUTE, -1);
		if(hour == -1) hour = calendar.get(Calendar.HOUR_OF_DAY);
		if(minute == -1) minute = calendar.get(Calendar.MINUTE);
		int[] timeArray = { hour, minute};
		return timeArray;
	}
	
	public static String getMessageString(Context context, String name, String count, String date, String time, String start, String destination) {
		return String.format(context.getString(R.string.message), name, count, date+" "+time, start, destination);
	}
	
}
