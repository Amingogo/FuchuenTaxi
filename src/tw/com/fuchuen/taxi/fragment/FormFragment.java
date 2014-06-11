package tw.com.fuchuen.taxi.fragment;

import tw.com.fuchuen.taxi.R;
import tw.com.fuchuen.taxi.config.TaxiLocalConfig;
import tw.com.fuchuen.utils.CustomUtils;
import tw.com.fuchuen.utils.UtilFormat;
import tw.com.fuchuen.utils.UtilsLog;
import tw.com.fuchuen.utils.UtilsStorage;
import tw.com.fuchuen.utils.UtilsToast;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;
import com.sleepbot.datetimepicker.time.TimePickerDialog.OnTimeSetListener;

public class FormFragment extends Fragment {
	
	protected Context mContext;
	
	protected View mMainView;
	
	protected TextView mUpTitleTextView;
	protected EditText mPassengerEditText;
	protected Spinner mPassengerSpinner;
	protected TextView mDateTextView;
	protected TextView mTimeTextView;
	protected EditText mStartPlaceEditText;
	protected EditText mDestinationEditText;
	
	
	public static Fragment newInstance() {
		FormFragment fragment = new FormFragment();
		return fragment;
	}
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		setupPassengerName();
		setupPassengerSpinner();
		setupDateTimePicker();
		setupSendButton();
		setupmStartPlace();
		setupDestination();
	}
	
	private void setupPassengerName() {
		mPassengerEditText = (EditText)mMainView.findViewById(R.id.passenger_name);
		mPassengerEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				UtilsLog.logDebug(FormFragment.class, "onTextChanged");
				UtilsStorage.putSharedPreferencesValue(mContext, TaxiLocalConfig.SHARED_PREFERENCE_TAXI_PASSENGER_NAME, mPassengerEditText.getText().toString());
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void afterTextChanged(Editable s) {}
		});
		mPassengerEditText.setText(UtilsStorage.getSharedPreferences(mContext).getString(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_PASSENGER_NAME, ""));
	}
	
	private void setupPassengerSpinner() {
		mPassengerSpinner = (Spinner)mMainView.findViewById(R.id.passenger_count_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
		        R.array.passenger_number_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mPassengerSpinner.setAdapter(adapter);
		mPassengerSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				UtilsLog.logDebug(FormFragment.class, "passenger count is "+(pos+1)+".");
				UtilsStorage.putSharedPreferencesValue(mContext, TaxiLocalConfig.SHARED_PREFERENCE_TAXI_PASSENGER_COUNT_POSITION, pos);
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				UtilsLog.logDebug(FormFragment.class, "nothing selected.");
			}
		});
		mPassengerSpinner.setSelection(UtilsStorage.getSharedPreferences(mContext).getInt(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_PASSENGER_COUNT_POSITION, 0), false);
	}
	
	private void setupDateTimePicker() {
		mDateTextView = (TextView)mMainView.findViewById(R.id.take_taxi_date);
		mDateTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				
				final int[] dateArray = CustomUtils.getDateArray(mContext);
				final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new OnDateSetListener() {
					@Override
					public void onDateSet(DatePickerDialog datePickerDialog, int year,
							int month, int day) {
						UtilsLog.logDebug(FormFragment.class, "year:"+year+" month:"+month+" day:"+day);
						CustomUtils.saveDateToSharedPreference(mContext, year, month, day);
						mDateTextView.setText(UtilFormat.getYearFormat(year)+"/"+UtilFormat.getTwoDigitFormat((month+1))+"/"+UtilFormat.getTwoDigitFormat(day));
					}
				}, dateArray[0], dateArray[1], dateArray[2], false);
				datePickerDialog.show(getFragmentManager(), TaxiLocalConfig.DATEPICKER_TAG);

			}
		});
		final int[] dateArray = CustomUtils.getDateArray(mContext);
		mDateTextView.setText(UtilFormat.getYearFormat(dateArray[0])+"/"+UtilFormat.getTwoDigitFormat((dateArray[1]+1))+"/"+UtilFormat.getTwoDigitFormat(dateArray[2]));
		
		mTimeTextView = (TextView)mMainView.findViewById(R.id.take_taxi_time);
		mTimeTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				
				final int[] timeArray = CustomUtils.getTimeArray(mContext);
				final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new OnTimeSetListener() {
					@Override
					public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
						UtilsLog.logDebug(FormFragment.class, "hourOfDay:"+hourOfDay+" minute:"+minute);
						CustomUtils.saveTimeToSharedPreference(mContext, hourOfDay, minute);
						mTimeTextView.setText(UtilFormat.getTwoDigitFormat(hourOfDay)+":"+UtilFormat.getTwoDigitFormat(minute));
					}
				}, timeArray[0], timeArray[1], true, false);
				timePickerDialog.show(getFragmentManager(), TaxiLocalConfig.TIMEPICKER_TAG);
				
			}
		});
		final int[] timeArray = CustomUtils.getTimeArray(mContext);
		mTimeTextView.setText(UtilFormat.getTwoDigitFormat(timeArray[0])+":"+UtilFormat.getTwoDigitFormat(timeArray[1]));
	}
	
	private void setupmStartPlace() {
		mStartPlaceEditText = (EditText)mMainView.findViewById(R.id.passenger_start);
		mStartPlaceEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				UtilsLog.logDebug(FormFragment.class, "StartPlaceEditText onTextChanged");
				UtilsStorage.putSharedPreferencesValue(mContext, TaxiLocalConfig.SHARED_PREFERENCE_TAXI_START_PLACE, mStartPlaceEditText.getText().toString());
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void afterTextChanged(Editable s) {}
		});
		mStartPlaceEditText.setText(UtilsStorage.getSharedPreferences(mContext).getString(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_START_PLACE, ""));
	}
	
	private void setupDestination() {
		mDestinationEditText = (EditText)mMainView.findViewById(R.id.passenger_destination);
		mDestinationEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				UtilsLog.logDebug(FormFragment.class, "DestinationEditText onTextChanged");
				UtilsStorage.putSharedPreferencesValue(mContext, TaxiLocalConfig.SHARED_PREFERENCE_TAXI_DESTINATION, mDestinationEditText.getText().toString());
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void afterTextChanged(Editable s) {}
		});
		mDestinationEditText.setText(UtilsStorage.getSharedPreferences(mContext).getString(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_DESTINATION, ""));
	}
	
	protected void setupSendButton() {
		mMainView.findViewById(R.id.send).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final int[] dateArray = CustomUtils.getDateArray(mContext);
				final int[] timeArray = CustomUtils.getTimeArray(mContext);
				String passengerName = UtilsStorage.getSharedPreferences(mContext).getString(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_PASSENGER_NAME, "");
				String passengerCount = (UtilsStorage.getSharedPreferences(mContext).getInt(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_PASSENGER_COUNT_POSITION, 0)+1)+mContext.getString(R.string.people);
				String date = dateArray[0]+"/"+(dateArray[1]+1)+"/"+dateArray[2];
				String time = timeArray[0]+":"+timeArray[1];
				String start = UtilsStorage.getSharedPreferences(mContext).getString(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_START_PLACE, "");
				String destination = UtilsStorage.getSharedPreferences(mContext).getString(TaxiLocalConfig.SHARED_PREFERENCE_TAXI_DESTINATION, "");
				
				if(passengerName.equals("") || start.equals("") || destination.equals("")) {
					UtilsToast.showLongToastMsg(mContext.getApplicationContext(), mContext.getString(R.string.leak_info), true);
					return;
				}
				
				Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+getString(R.string.driver_phone)));
				intent.putExtra("sms_body",
						CustomUtils.getMessageString(mContext,
						passengerName,
						passengerCount,
						date,
						time,
						start,
						destination));
				startActivity(intent);
			}
		});
	}
	
}
