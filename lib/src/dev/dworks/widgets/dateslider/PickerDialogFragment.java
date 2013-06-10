/*
 * Copyright (C) 2013 Hari Krishna Dulipudi
 *
 * Default DateSlider which allows for an easy selection of a date
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

package dev.dworks.widgets.dateslider;

import java.util.Calendar;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import dev.dworks.widgets.dateslider.R;
import dev.dworks.widgets.dateslider.SliderContainer.OnTimeChangeListener;

/**
 * A DialogFragment that hosts a SliderContainer and a couple of buttons,
 * displays the current time in the header, and notifies an observer
 * when the user selectes a time.
 */
public class PickerDialogFragment extends DialogFragment {

    private static final String REFERENCE_KEY = "PickerDialogFragment_ReferenceKey";
    private static final String THEME_RES_ID_KEY = "PickerDialogFragment_ThemeResIdKey";
    private static final String INITIAL_TIME_KEY = "PickerDialogFragment_InitialTimeKey";
    private static final String MIN_TIME_KEY = "PickerDialogFragment_MinTimeKey";
    private static final String MAX_TIME_KEY = "PickerDialogFragment_MaxTimeKey";
    private static final String MINUTE_INTERVAL_KEY = "PickerDialogFragment_MinuteIntervalKey";

    private Button mSet, mCancel;
    //private PickerContainer mPicker;

    private int mTheme = -1;
    private View mDivider, mDividerOne, mDividerTwo;
    private int mDividerColor;
    private ColorStateList mTextColor;
    private int mButtonBackgroundResId;
    private int mDialogBackgroundResId;
    protected TextView mTitleText;
    protected SliderContainer mContainer;
    
    protected Calendar mInitialTime;
    protected Calendar minTime;
    protected Calendar maxTime;
    protected int mLayoutID;
    protected int minuteInterval;
    
    public static PickerDialogFragment newInstance(int reference, int themeResId,
    		Calendar initialTime, Calendar minTime, Calendar maxTime, int minInterval) {
    	
        final PickerDialogFragment frag = new PickerDialogFragment();
        Bundle args = new Bundle();
        args.putInt(REFERENCE_KEY, reference);
        args.putInt(THEME_RES_ID_KEY, themeResId);
        if (initialTime != null) {
            args.putSerializable(INITIAL_TIME_KEY, initialTime);
        }
        if (minTime != null) {
        	args.putSerializable(MIN_TIME_KEY, minTime);
        }
        if (maxTime != null) {
            args.putSerializable(MAX_TIME_KEY, maxTime);
        }
        args.putInt(MINUTE_INTERVAL_KEY, minInterval);
        frag.setArguments(args);
        return frag;
    }
    
    public PickerDialogFragment() {
	}
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null && args.containsKey(REFERENCE_KEY)) {
        	mLayoutID = args.getInt(REFERENCE_KEY);
        }
        if (args != null && args.containsKey(THEME_RES_ID_KEY)) {
            mTheme = args.getInt(THEME_RES_ID_KEY);
        }
        if (args != null && args.containsKey(INITIAL_TIME_KEY)) {
            mInitialTime = (Calendar) args.getSerializable(INITIAL_TIME_KEY);
        }
        if (args != null && args.containsKey(MIN_TIME_KEY)) {
        	minTime = (Calendar) args.getSerializable(MIN_TIME_KEY);
        }
        if (args != null && args.containsKey(MAX_TIME_KEY)) {
        	maxTime = (Calendar) args.getSerializable(MAX_TIME_KEY);
        }
        if (args != null && args.containsKey(MINUTE_INTERVAL_KEY)) {
        	this.minuteInterval = args.getInt(MINUTE_INTERVAL_KEY);
        }
        
        mInitialTime = Calendar.getInstance(mInitialTime.getTimeZone());
        mInitialTime.setTimeInMillis(mInitialTime.getTimeInMillis());
        
        if (minuteInterval > 1) {
        	int minutes = mInitialTime.get(Calendar.MINUTE);
    		int diff = ((minutes+minuteInterval/2)/minuteInterval)*minuteInterval - minutes;
    		mInitialTime.add(Calendar.MINUTE, diff);
        }
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);

        // Init defaults
        mTextColor = getResources().getColorStateList(R.color.dialog_text_color_holo_dark);
        mButtonBackgroundResId = R.drawable.button_background_dark;
        mDividerColor = getResources().getColor(R.color.default_divider_color_dark);
        mDialogBackgroundResId = R.drawable.dialog_full_holo_dark;
        
        if (savedInstanceState!=null) {
            Calendar c = (Calendar)savedInstanceState.getSerializable("time");
            if (c != null) {
                mInitialTime = c;
            }
        }
        
        if (mTheme != -1) {

            TypedArray a = getActivity().getApplicationContext()
                    .obtainStyledAttributes(mTheme, R.styleable.PickersDialogFragment);

            mTextColor = a.getColorStateList(R.styleable.PickersDialogFragment_textColor);
            mButtonBackgroundResId = a.getResourceId(R.styleable.PickersDialogFragment_buttonBackground,
                    mButtonBackgroundResId);
            mDividerColor = a.getColor(R.styleable.PickersDialogFragment_dividerColor, mDividerColor);
            mDialogBackgroundResId = a
                    .getResourceId(R.styleable.PickersDialogFragment_dialogBackground, mDialogBackgroundResId);
            a.recycle();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

    	ContextThemeWrapper ctw = new ContextThemeWrapper(getActivity(), mTheme);
    	LayoutInflater localInflater = inflater.cloneInContext(ctw);
        View v = localInflater.inflate(R.layout.picker_dialog, container, false);
        FrameLayout frame = (FrameLayout) v.findViewById(R.id.container);
        localInflater.inflate(mLayoutID, frame);
        
        mDivider = v.findViewById(R.id.divider);
        mDividerOne = v.findViewById(R.id.divider_1);
        mDividerTwo = v.findViewById(R.id.divider_2);
        mDivider.setBackgroundColor(mDividerColor);
        mDividerOne.setBackgroundColor(mDividerColor);
        mDividerTwo.setBackgroundColor(mDividerColor);

        getDialog().getWindow().setBackgroundDrawableResource(mDialogBackgroundResId);
        
        mTitleText = (TextView) v.findViewById(R.id.dateSliderTitleText);
        mTitleText.setTextColor(mTextColor);
        mContainer = (SliderContainer) v.findViewById(R.id.dateSliderContainer);
        mContainer.setTheme(mTheme);
        
        mContainer.setOnTimeChangeListener(onTimeChangeListener);
        mContainer.setMinuteInterval(minuteInterval);
        mContainer.setTime(mInitialTime);
        if (minTime!=null) mContainer.setMinTime(minTime);
        if (maxTime!=null) mContainer.setMaxTime(maxTime);

        mSet = (Button) v.findViewById(R.id.set_button);
        mCancel = (Button) v.findViewById(R.id.cancel_button);
        mSet.setTextColor(mTextColor);
        mSet.setBackgroundResource(mButtonBackgroundResId);
        mSet.setOnClickListener(okButtonClickListener);
        
        mCancel.setTextColor(mTextColor);
        mCancel.setBackgroundResource(mButtonBackgroundResId);
        mCancel.setOnClickListener(cancelButtonClickListener);
        
        return v;
    }
    
    public void setTime(Calendar c) {
        mContainer.setTime(c);
    }

    private View.OnClickListener okButtonClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Activity activity = getActivity();
            final Fragment fragment = getTargetFragment();
            if (activity instanceof PickerDialogHandler) {
                final PickerDialogHandler act =
                        (PickerDialogHandler) activity;
                act.onDateSet(PickerDialogFragment.this, getTime());
            } else if (fragment instanceof PickerDialogHandler) {
                final PickerDialogHandler frag =
                        (PickerDialogHandler) fragment;
                frag.onDateSet(PickerDialogFragment.this, getTime());
            } else {
                //Log.e("Error! Activities that use PickerDialogFragment must implement "
                //        + "DatePickerDialogHandler");
            }
            dismiss();
        }
    };

    private View.OnClickListener cancelButtonClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            dismiss();
        }
    };

    private OnTimeChangeListener onTimeChangeListener = new OnTimeChangeListener() {
        public void onTimeChange(Calendar time) {
            setTitle();
        }
    };

    /**
     * @return The currently displayed time
     */
    protected Calendar getTime() {
        return mContainer.getTime();
    }

    /**
     * This method sets the title of the dialog
     */
    protected void setTitle() {
        if (mTitleText != null) {
            final Calendar c = getTime();
            mTitleText.setText(String.format(" %te %tB %tY", c, c, c));
        }
    }
    
    /**
     * Defines the interface which defines the methods of the OnDateSetListener
     */
    public interface PickerDialogHandler {
        /**
         * this method is called when a date was selected by the user
         * @param view			the caller of the method
         */
        public void onDateSet(PickerDialogFragment view, Calendar selectedDate);
    }    
}