package tw.com.fuchuen.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class UtilsCommon {

	public static boolean haveNetworkConnection(Context context) {
	    boolean haveConnectedWifi = false;
	    boolean haveConnectedMobile = false;
	    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
	    for (NetworkInfo ni : netInfo) {
	        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
	            if (ni.isConnected())
	                haveConnectedWifi = true;
	        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
	            if (ni.isConnected())
	                haveConnectedMobile = true;
	    }
	    return haveConnectedWifi || haveConnectedMobile;
	}

	public static boolean appInstalled(Context context, String uri) {
		PackageManager pm = context.getPackageManager();
		try{
			pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
			return true;
		}catch (PackageManager.NameNotFoundException e){
			return false;
		}
	}

}
