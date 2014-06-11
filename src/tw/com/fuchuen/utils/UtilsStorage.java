package tw.com.fuchuen.utils;

import tw.com.fuchuen.taxi.config.TaxiLocalConfig;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UtilsStorage {

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

}
