package tw.com.fuchuen.taxi.email;

import java.util.Calendar;

import tw.com.fuchuen.taxi.R;
import tw.com.fuchuen.taxi.TakeTaxiNowActivity;
import tw.com.fuchuen.utils.BasicUtils;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class MainEmailActivity extends Activity{
	private static final String TAG = "MainEmailActivity";
	private static final int TURN_PEOPLE_ON_GREEN_LIGHT = 0x101;
	private static final int TURN_PEOPLE_ON_RED_LIGHT = 0x102;
	private static final int TURN_DATETIME_ON_GREEN_LIGHT = 0x103;
	private static final int TURN_FROMTO_ON_GREEN_LIGHT = 0x104;
	private static final int TURN_FROMTO_ON_RED_LIGHT = 0x105;
	
	private static final int UNLOCK_SEND_BUTTON = 0x106;
	private static final int LOCK_SEND_BUTTON = 0x107;
	
	private String[] passengerStrs = {"1", "2", "3", "4", "5", "6"};
	private String[] luggageStrs = new String[4];
	
	private Context mContext;
	private LayoutInflater mInflater;
	private SharedPreferences mSp;
	private TurnLightHandler mHandler;
	
	private AdView adView;
	private LinearLayout adLayout;
	
	private Spinner passengerSpinner;
	private Spinner luggageSpinner;
	
	private Button emailBtn;
	private Button peopleBtn;
	private Button timeBtn;
	private Button destinationBtn;
	
	private DatePicker dpResult;
	private TimePicker tpResult;
	
	private String mName;
	private String mPhone;
	private String mPeople;
	private String mLuggage;
	private String mRemark;
	private String mPeopleNumber;
	private String mLuggageNumber;
	
	private int mYear;
	private int mMonth;
	private int mDay;
	private int mHour;
	private int mMinute;
	
	private String mFromAddress;
	private String mToAddress;
	
	private boolean firstGreenLightOn = false;
	private boolean secondGreenLightOn = false;
	private boolean thirdGreenLightOn = false;
	
	public class TurnLightHandler extends Handler {
		public void handleMessage(Message msg) {
			switch (msg.what) { 
				case TURN_PEOPLE_ON_GREEN_LIGHT:
					peopleBtn.setBackgroundResource(R.drawable.icon_green_light);
					firstGreenLightOn = true;
					checkIsEnableSendButton();
					Log.d(TAG, "Turn on people green light");
					break;
				case TURN_PEOPLE_ON_RED_LIGHT:
					peopleBtn.setBackgroundResource(R.drawable.icon_red_light);
					firstGreenLightOn = false;
					checkIsEnableSendButton();
					Log.d(TAG, "Turn on people red light");
					break;
				case TURN_DATETIME_ON_GREEN_LIGHT:
					timeBtn.setBackgroundResource(R.drawable.icon_green_light);
					secondGreenLightOn = true;
					checkIsEnableSendButton();
					Log.d(TAG, "Turn on date and time green light");
					break;
				case TURN_FROMTO_ON_GREEN_LIGHT:
					destinationBtn.setBackgroundResource(R.drawable.icon_green_light);
					thirdGreenLightOn = true;
					checkIsEnableSendButton();
					Log.d(TAG, "Turn on from to green light");
					break;
				case TURN_FROMTO_ON_RED_LIGHT:
					destinationBtn.setBackgroundResource(R.drawable.icon_red_light);
					thirdGreenLightOn = false;
					checkIsEnableSendButton();
					Log.d(TAG, "Turn on from to red light");
					break;
				case UNLOCK_SEND_BUTTON:
					emailBtn.setEnabled(true);
					Log.d(TAG, "Unlock send button");
					break;
				case LOCK_SEND_BUTTON:
					emailBtn.setEnabled(false);
					Log.d(TAG, "Lock send button");
					break;
			}
			super.handleMessage(msg); 
		}
	}
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_email_layout);
        
        initBasicUtils();
        setupButtons();
        putAd();
	}
	
	private void initBasicUtils() {
    	mContext = this;
    	mInflater = LayoutInflater.from(mContext);
    	mSp = getSharedPreferences("userInfo", 0);
    	mHandler = new TurnLightHandler();
    }

	private void setupButtons() {
		//send email button
    	emailBtn = (Button)findViewById(R.id.sendEmailBtn);
    	emailBtn.setEnabled(false);
    	emailBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+getString(R.string.emailAddress)));
				intent.putExtra(Intent.EXTRA_SUBJECT, mSp.getString("name", "")+getString(R.string.main_menu_callCar_btn));
				intent.putExtra(Intent.EXTRA_TEXT, combineString());
				startActivity(intent);
				Log.d(TAG, "send email");
			}
    	});
    	
    	//first light button - people
    	peopleBtn = (Button)findViewById(R.id.infoBtn1);
    	peopleBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				RelativeLayout peopleView = (RelativeLayout)mInflater.inflate(R.layout.two_edit_btn_dialog_layout, null);
				final Dialog peopleDialog = BasicUtils.getDialog(mContext, peopleView,getString(R.string.passenger_info));
				((TextView)peopleView.findViewById(R.id.dialog_subtitle1)).setText(R.string.how_to_call_you);
				((TextView)peopleView.findViewById(R.id.dialog_subtitle2)).setText(R.string.contactNumber);
				
				final EditText nameET = (EditText)peopleView.findViewById(R.id.dialog_edittext1);
				final EditText phoneET = (EditText)peopleView.findViewById(R.id.dialog_edittext2);
				final EditText remarkET = (EditText)peopleView.findViewById(R.id.dialog_remark_edittext);
				
				initLuggageStrs();
				setupPassengerSpinner(peopleView);
		        setupLuggageSpinner(peopleView);
		        initPeopleSharedPreferences(nameET,phoneET,remarkET);
		        
				Button cancelBtn = (Button)peopleView.findViewById(R.id.cancel_btn);
				setupCancelButton(peopleDialog,cancelBtn);
				
				Button sendBtn = (Button)peopleView.findViewById(R.id.ok_btn);
				setupFirstLightOkButton(peopleDialog,sendBtn,nameET,phoneET,remarkET);

				peopleDialog.show();
				
				Log.d(TAG, "Show People Info Dialog");
			}
    	});
    	
    	//second light button - time
    	timeBtn = (Button)findViewById(R.id.infoBtn2);
    	timeBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				RelativeLayout dateTimeView = (RelativeLayout)mInflater.inflate(R.layout.pick_date_time_dialog, null);
				final Dialog dateTimeDialog = BasicUtils.getDialog(mContext, dateTimeView,getString(R.string.date_time_info));
				
				setCurrentDateOnView(dateTimeView);
				setCurrentTimeOnView(dateTimeView);
				
				Button cancelBtn = (Button)dateTimeView.findViewById(R.id.cancel_btn);
				setupCancelButton(dateTimeDialog,cancelBtn);
				
				Button sendBtn = (Button)dateTimeView.findViewById(R.id.ok_btn);
				setupSecondLightOkButton(dateTimeDialog,sendBtn);
				
				dateTimeDialog.show();
				
				Log.d(TAG, "Show Time Info Dialog");
			}
    	});
    	
    	//third light button - destination
    	destinationBtn = (Button)findViewById(R.id.infoBtn3);
    	destinationBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				RelativeLayout fromToView = (RelativeLayout)mInflater.inflate(R.layout.two_edit_dialog_layout, null);
				final Dialog fromToDialog = BasicUtils.getDialog(mContext, fromToView,getString(R.string.from_to_info));
				
				final EditText fromET = (EditText)fromToView.findViewById(R.id.from_edittext);
				final EditText toET = (EditText)fromToView.findViewById(R.id.to_edittext);
				
				initFromToSharedPreferences(fromET,toET);
				
				Button cancelBtn = (Button)fromToView.findViewById(R.id.cancel_btn);
				setupCancelButton(fromToDialog,cancelBtn);
				
				Button sendBtn = (Button)fromToView.findViewById(R.id.ok_btn);
				setupThirdLightOkButton(fromToDialog,sendBtn,fromET,toET);
				
				fromToDialog.show();
				
				Log.d(TAG, "Show Destination Info Dialog");
			}
    	});
	}
	
	private void setupCancelButton(final Dialog mDialog, Button cancel) {
		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mDialog.dismiss();
			}
		});
	}
	
	private void putAd() {
		adView = new AdView(this, AdSize.BANNER, getString(R.string.id));
		adLayout = (LinearLayout)findViewById(R.id.ad_layout);
	    adLayout.addView(adView);
	    adView.loadAd(new AdRequest());
	}
	
	private void initLuggageStrs() { 
		luggageStrs[0] = getString(R.string.luggage_number_less);
		luggageStrs[1] = getString(R.string.luggage_number_little_less);
		luggageStrs[2] = getString(R.string.luggage_number_little_many);
		luggageStrs[3] = getString(R.string.luggage_number_many);
	}
	
	private void initPeopleSharedPreferences(EditText nameET, EditText phoneET, EditText remarkET) {
		mName = mSp.getString("name", "");
		mPhone = mSp.getString("phone", "");
		mPeople = mSp.getString("people", "");
		mLuggage = mSp.getString("luggage", "");
		mRemark = mSp.getString("remark", "");
		
		nameET.setText(mName);
		phoneET.setText(mPhone);
		remarkET.setText(mRemark);
		passengerSpinner.setSelection(mSp.getInt("peopleNumber", 1));
		luggageSpinner.setSelection(mSp.getInt("luggageNumber", 1));
	}

	private void setupPassengerSpinner(RelativeLayout peopleView) {
		passengerSpinner = (Spinner)peopleView.findViewById(R.id.passenger_spinnner);
		ArrayAdapter<String> passengerAdapter = new ArrayAdapter<String>(
				mContext,android.R.layout.simple_spinner_item, passengerStrs);
		passengerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		passengerSpinner.setAdapter(passengerAdapter);
		passengerSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView, View view,
					int position, long id) {
				Log.d(TAG, "select"+adapterView.getSelectedItem().toString());
			}
			public void onNothingSelected(AdapterView<?> arg0) {
				//Do nothing
			}
		});
	}
	
	private void setupLuggageSpinner(RelativeLayout peopleView) {
		luggageSpinner = (Spinner)peopleView.findViewById(R.id.luggage_spinnner);
		ArrayAdapter<String> passengerAdapter = new ArrayAdapter<String>(
				mContext,android.R.layout.simple_spinner_item, luggageStrs);
		passengerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		luggageSpinner.setAdapter(passengerAdapter);
		luggageSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView, View view,
					int position, long id) {
				Log.d(TAG, "select"+adapterView.getSelectedItem().toString());
			}
			public void onNothingSelected(AdapterView<?> arg0) {
				//Do nothing
			}
		});
	}
	
	private void setupFirstLightOkButton(final Dialog peopleDialog, Button sendBtn, final EditText nameET, final EditText phoneET, final EditText remarkET) {
		sendBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SharedPreferences.Editor editor = mSp.edit();
			    editor.putString("name", nameET.getText().toString());
			    editor.putString("phone", phoneET.getText().toString());
			    editor.putString("people", passengerSpinner.getSelectedItem().toString());
			    editor.putString("luggage", luggageSpinner.getSelectedItem().toString());
			    editor.putInt("peopleNumber", passengerSpinner.getSelectedItemPosition());
			    editor.putInt("luggageNumber", luggageSpinner.getSelectedItemPosition());
			    editor.putString("remark", remarkET.getText().toString());
			    editor.commit();
			    
			    if(!nameET.getText().toString().isEmpty() &&
			    		!phoneET.getText().toString().isEmpty()) {
			    	Message msg = new Message();  
			    	msg.what = TURN_PEOPLE_ON_GREEN_LIGHT;
			    	mHandler.sendMessage(msg);
			    } else {
			    	Message msg = new Message();  
			    	msg.what = TURN_PEOPLE_ON_RED_LIGHT;
			    	mHandler.sendMessage(msg);
			    	Toast.makeText(mContext, R.string.write_info_hint, Toast.LENGTH_LONG).show();
			    }
				peopleDialog.dismiss();
			}
		});
	}
	
	public void setCurrentDateOnView(RelativeLayout dateTimeView) {

		dpResult = (DatePicker) dateTimeView.findViewById(R.id.dpResult);
		
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		dpResult.init(mYear, mMonth, mDay, dateChangeListener);
 
	}
	
	private DatePicker.OnDateChangedListener dateChangeListener
				= new DatePicker.OnDateChangedListener() {
		public void onDateChanged(DatePicker view, int year,
				int monthOfYear, int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			
			dpResult.init(year, mMonth, mDay, dateChangeListener);
		}
	};
	
	public void setCurrentTimeOnView(RelativeLayout dateTimeView) {

		tpResult = (TimePicker) dateTimeView.findViewById(R.id.tpResult);
		
		final Calendar c = Calendar.getInstance();
	    mHour = c.get(Calendar.HOUR_OF_DAY);
	    mMinute = c.get(Calendar.MINUTE);

	    tpResult.setOnTimeChangedListener(timeChangedListener);
 
	}
	
	private TimePicker.OnTimeChangedListener timeChangedListener =
		    new TimePicker.OnTimeChangedListener() {
		public void onTimeChanged(TimePicker view, int hourOfDay,
				int minute) {
			mHour = hourOfDay;
			mMinute = minute;
		}
	};
	
	private void setupSecondLightOkButton(final Dialog dateTimeDialog, Button sendBtn) {
		sendBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	    
			    Message msg = new Message();  
		    	msg.what = TURN_DATETIME_ON_GREEN_LIGHT;
		    	mHandler.sendMessage(msg);
		    	
		    	dateTimeDialog.dismiss();
			}
		});
	}
	
	private void setupThirdLightOkButton(final Dialog dateTimeDialog, Button sendBtn, final EditText fromET, final EditText toET) {
		sendBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SharedPreferences.Editor editor = mSp.edit();
			    editor.putString("from", fromET.getText().toString());
			    editor.putString("to", toET.getText().toString());
			    editor.commit();
			    
			    if(!fromET.getText().toString().isEmpty() &&
			    		!toET.getText().toString().isEmpty()) {
			    	Message msg = new Message();  
			    	msg.what = TURN_FROMTO_ON_GREEN_LIGHT;
			    	mHandler.sendMessage(msg);
			    } else {
			    	Message msg = new Message();  
			    	msg.what = TURN_FROMTO_ON_RED_LIGHT;
			    	mHandler.sendMessage(msg);
			    	Toast.makeText(mContext, R.string.write_info_hint, Toast.LENGTH_LONG).show();
			    }
		    	
		    	dateTimeDialog.dismiss();
			}
		});
	}
	
	private void initFromToSharedPreferences(EditText fromET, EditText toET) {
		mFromAddress = mSp.getString("from", "");
		mToAddress = mSp.getString("to", "");
		
		fromET.setText(mFromAddress);
		toET.setText(mToAddress);
	}
	
	private void checkIsEnableSendButton() {
		Message msg = new Message(); 
		if(firstGreenLightOn && secondGreenLightOn && thirdGreenLightOn) { 
	    	msg.what = UNLOCK_SEND_BUTTON;
	    	mHandler.sendMessage(msg);
		} else {
	    	msg.what = LOCK_SEND_BUTTON;
	    	mHandler.sendMessage(msg);
		}	
	}
	
	private String combineString() {
		String mms = "";
		mName = mSp.getString("name", "");
		mPhone = mSp.getString("phone", "");
		mPeople = mSp.getString("people", "");
		mLuggage = mSp.getString("luggage", "");
		mRemark = mSp.getString("remark", "");
		mFromAddress = mSp.getString("from", "");
		mToAddress = mSp.getString("to", "");
		
		mms += getString(R.string.mms_content_first)+mYear+getString(R.string.mms_content_year)
				+(mMonth+1)+getString(R.string.mms_content_month)
				+mDay+getString(R.string.mms_content_day)
				+mHour+getString(R.string.mms_content_hour)
				+mMinute+getString(R.string.mms_content_min)
				+",";
		
		if ((mFromAddress != null && !mFromAddress.equals(""))) {
			mms += getString(R.string.mms_content_from);
			if (mFromAddress != null && !mFromAddress.equals(""))
				mms += mFromAddress;
		}
		if ((mToAddress != null && !mToAddress.equals(""))) {
			mms += getString(R.string.mms_content_depart);
			mms += getString(R.string.mms_content_to);
			if (mToAddress != null && !mToAddress.equals(""))
				mms += mToAddress;
		}
		mms += ",";
		mms += getString(R.string.passenge)+passengerSpinner.getSelectedItem().toString()+getString(R.string.people);
		mms += ","+getString(R.string.luggage)+luggageSpinner.getSelectedItem().toString()+"¡C";
		if (mRemark != null && !mRemark.equals(""))
			mms += getString(R.string.select_others_subtitle) + mRemark+"¡C";
		mms += getString(R.string.returnBack)+"("+mName+" "+mPhone+")";
		
		return mms;
	}
}
