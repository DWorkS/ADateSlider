/*
 * Copyright (C) 2013 Hari Krishna Dulipudi
 *
 * This is a small demo application which demonstrates the use of the
 * dateSelector
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.dworks.widgets.adateslider.demo;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import dev.dworks.widgets.adateslider.PickerBuilder;

public class MainActivity extends FragmentActivity implements OnClickListener {

	static final int DEFAULTDATESELECTOR_ID = 0;
	static final int DEFAULTDATESELECTOR_WITHLIMIT_ID = 6;
	static final int ALTERNATIVEDATESELECTOR_ID = 1;
	static final int CUSTOMDATESELECTOR_ID = 2;
	static final int MONTHYEARDATESELECTOR_ID = 3;
	static final int TIMESELECTOR_ID = 4;
	static final int TIMESELECTOR_WITHLIMIT_ID = 7;
	static final int DATETIMESELECTOR_ID = 5;

    private static TextView dateText;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        dateText = (TextView) this.findViewById(R.id.selectedDateLabel);
        Button defaultButton = (Button) this.findViewById(R.id.defaultDateSelectButton);
        defaultButton.setOnClickListener(this);
        
        Button defaultLimitButton = (Button) this.findViewById(R.id.defaultDateLimitSelectButton);
        defaultLimitButton.setOnClickListener(this);

        Button alternativeButton = (Button) this.findViewById(R.id.alternativeDateSelectButton);
        alternativeButton.setOnClickListener(this);

        Button customButton = (Button) this.findViewById(R.id.customDateSelectButton);
        customButton.setOnClickListener(this);

        Button monthYearButton = (Button) this.findViewById(R.id.monthYearDateSelectButton);
        monthYearButton.setOnClickListener(this);

        Button timeButton = (Button) this.findViewById(R.id.timeSelectButton);
        timeButton.setOnClickListener(this);
        
        Button timeLimitButton = (Button) this.findViewById(R.id.timeLimitSelectButton);
        timeLimitButton.setOnClickListener(this);

        Button dateTimeButton = (Button) this.findViewById(R.id.dateTimeSelectButton);
        dateTimeButton.setOnClickListener(this);
    }
/*
    // define the listener which is called once a user selected the date.
    private static DateSlider.OnDateSetListener mDateSetListener =
        new DateSlider.OnDateSetListener() {
            public void onDateSet(DateSlider view, Calendar selectedDate) {
                // update the dateText view with the corresponding date
                dateText.setText(String.format("The chosen date:%n%te. %tB %tY", selectedDate, selectedDate, selectedDate));
            }
    };

    private static DateSlider.OnDateSetListener mMonthYearSetListener =
        new DateSlider.OnDateSetListener() {
            public void onDateSet(DateSlider view, Calendar selectedDate) {
                // update the dateText view with the corresponding date
                dateText.setText(String.format("The chosen date:%n%tB %tY", selectedDate, selectedDate));
            }
    };

    private static DateSlider.OnDateSetListener mTimeSetListener =
        new DateSlider.OnDateSetListener() {
            public void onDateSet(DateSlider view, Calendar selectedDate) {
                // update the dateText view with the corresponding date
                dateText.setText(String.format("The chosen time:%n%tR", selectedDate));
            }
    };

    private static DateSlider.OnDateSetListener mDateTimeSetListener =
        new DateSlider.OnDateSetListener() {
            public void onDateSet(DateSlider view, Calendar selectedDate) {
                // update the dateText view with the corresponding date
                int minute = selectedDate.get(Calendar.MINUTE) /
                        TimeLabeler.MINUTEINTERVAL*TimeLabeler.MINUTEINTERVAL;
                dateText.setText(String.format("The chosen date and time:%n%te. %tB %tY%n%tH:%02d",
                        selectedDate, selectedDate, selectedDate, selectedDate, minute));
            }
    };*/


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.defaultDateSelectButton:
			showDialogFragment(DEFAULTDATESELECTOR_ID);
			break;
		case R.id.defaultDateLimitSelectButton:
			showDialogFragment(DEFAULTDATESELECTOR_WITHLIMIT_ID);
			break;
		case R.id.alternativeDateSelectButton:
			showDialogFragment(ALTERNATIVEDATESELECTOR_ID);
			break;
		case R.id.customDateSelectButton:
			showDialogFragment(CUSTOMDATESELECTOR_ID);
			break;
		case R.id.monthYearDateSelectButton:
			showDialogFragment(MONTHYEARDATESELECTOR_ID);
			break;
		case R.id.timeSelectButton:
			showDialogFragment(TIMESELECTOR_ID);
			break;
		case R.id.timeLimitSelectButton:
			showDialogFragment(TIMESELECTOR_WITHLIMIT_ID);
			break;
		case R.id.dateTimeSelectButton:
			showDialogFragment(DATETIMESELECTOR_ID);
			break;
		default:
			break;
		}
	}
	
    public void showDialogFragment(int id) {
    	
        Calendar mInitialTime = Calendar.getInstance();
        Calendar minTime = null;
        Calendar maxTime = null;
        int minuteInterval = 1;
        int mReference = -1;
        
        switch (id) {
        case DEFAULTDATESELECTOR_ID:
        	mReference = R.layout.defaultdateslider;
        	break;
        case DEFAULTDATESELECTOR_WITHLIMIT_ID:
        	mReference = R.layout.defaultdateslider;
        	minTime = mInitialTime;
        	maxTime = mInitialTime;
        	maxTime.add(Calendar.DAY_OF_MONTH, 14);
            break;
        case ALTERNATIVEDATESELECTOR_ID:
        	mReference = R.layout.altdateslider;
        	minTime = mInitialTime;
            break;
        case CUSTOMDATESELECTOR_ID:
        	mReference = R.layout.customdateslider;
        	break;
        case MONTHYEARDATESELECTOR_ID:
        	mReference = R.layout.monthyeardateslider;
        	break;
        case TIMESELECTOR_ID:
        	mReference = R.layout.timeslider;
        	minuteInterval = 15;
        	break;
        case TIMESELECTOR_WITHLIMIT_ID:
        	mReference = R.layout.timeslider;
        	minTime = mInitialTime;
        	minTime.add(Calendar.HOUR, -2);
        	maxTime = mInitialTime;
        	minuteInterval = 5;
        	break;
        case DATETIMESELECTOR_ID:
        	mReference = R.layout.datetimeslider;
        	break;
        }
        PickerBuilder pickerBuilder = new PickerBuilder()
	        .setFragmentManager(getSupportFragmentManager())
	        .setStyleResId(R.style.PickersDialogFragment_Light)
	        .setReference(mReference)
	        .setInitialTime(mInitialTime)
	        .setMinTime(minTime)
	        .setMaxTime(maxTime)
	        .setMinuteInterval(minuteInterval);

        pickerBuilder.show();
    }
}