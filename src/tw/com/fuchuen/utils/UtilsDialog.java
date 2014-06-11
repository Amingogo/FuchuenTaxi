package tw.com.fuchuen.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class UtilsDialog {

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
	
	public static ProgressDialog getProgressDialog(Context context, String message) {
		ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setMessage(message);
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
		return progressDialog;
	}
	
	public static void dismissProgressDialog(ProgressDialog progressDialog) {
		if(progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
	}

}
