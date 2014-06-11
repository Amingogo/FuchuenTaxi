package tw.com.fuchuen.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class UtilsToast {

	public static void showToastMsg(Context context, String msg, boolean isCenter){
		Toast toast=Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		if(isCenter) toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0,0);
		toast.show();
	}

	public static void showLongToastMsg(Context context, String msg, boolean isCenter){
		Toast toast=Toast.makeText(context, msg, Toast.LENGTH_LONG);
		if(isCenter) toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0,0);
		toast.show();
	}

}
