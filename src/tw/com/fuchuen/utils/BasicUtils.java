package tw.com.fuchuen.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class BasicUtils {
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
}
