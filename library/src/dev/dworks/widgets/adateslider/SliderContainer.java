/*
 * Copyright (C) 2011 Daniel Berndt - Codeus Ltd  -  DateSlider
 * Copyright (C) 2013 Hari Krishna Dulipudi
 * 
 * This class contains all the scrolling logic of the slidable elements
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

package dev.dworks.widgets.adateslider;

import java.util.Calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import dev.dworks.widgets.adateslider.ui.LinearLayout;

/**
 * This is a container class for ScrollLayouts. It coordinates the scrolling
 * between them, so that if one is scrolled, the others are scrolled to
 * keep a consistent display of the time. It also notifies an optional
 * observer anytime the time is changed.
 */
public class SliderContainer extends LinearLayout {
    private Calendar mTime = null;
    private OnTimeChangeListener mOnTimeChangeListener;
    private int minuteInterval;
	private int mTheme = -1;

    public SliderContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }
    
    public void setTheme(int themeResId) {
        mTheme  = themeResId;
        restyleViews();
    }

    private void restyleViews() {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            if (v instanceof ScrollLayout) {
                final ScrollLayout scrollLayout = (ScrollLayout)v;
                scrollLayout.setTheme(mTheme);
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            if (v instanceof ScrollLayout) {
                final ScrollLayout scrollLayout = (ScrollLayout)v;
                scrollLayout.setTheme(mTheme);
                scrollLayout.setOnScrollListener(
                        new ScrollLayout.OnScrollListener() {
                            public void onScroll(long x) {
                                mTime.setTimeInMillis(x);
                                arrangeScrollers(scrollLayout);
                            }
                        });
            }
        }
    }

    /**
     * Set the current time and update all of the child ScrollLayouts accordingly.
     *
     * @param calendar
     */
    public void setTime(Calendar calendar) {
    	mTime = Calendar.getInstance(calendar.getTimeZone());
        mTime.setTimeInMillis(calendar.getTimeInMillis());
        arrangeScrollers(null);
    }
    
    /**
     * Get the current time
     *
     * @return The current time
     */
    public Calendar getTime() {
        return mTime;
    }    
    
    /**
     * sets the minimum date that the scroller can scroll
     * 
     * @param c the minimum date (inclusiv)
     */
    public void setMinTime(Calendar c) {
    	if (mTime==null) {
    		throw new RuntimeException("You have to call setTime before setting a MinimumTime!");
    	}
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            if (v instanceof ScrollLayout) {
                ScrollLayout scroller = (ScrollLayout)v;
                scroller.setMinTime(c.getTimeInMillis());
            }
        }
    }
    
    /**
     * sets the maximum date that the scroller can scroll
     * 
     * @param c the maximum date (inclusive)
     */
    public void setMaxTime(Calendar c) {
    	if (mTime==null) {
    		throw new RuntimeException("You have to call setTime before setting a MinimumTime!");
    	}
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            if (v instanceof ScrollLayout) {
                ScrollLayout scroller = (ScrollLayout)v;
                scroller.setMaxTime(c.getTimeInMillis());
            }
        }
    }
    
    /**
     * sets the minute interval of the scroll layouts.
     * @param minInterval
     */
    public void setMinuteInterval(int minInterval) {
    	this.minuteInterval = minInterval;
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            if (v instanceof ScrollLayout) {
                ScrollLayout scroller = (ScrollLayout)v;
                scroller.setMinuteInterval(minInterval);
            }
        }
    }

    /**
     * Sets the OnTimeChangeListener, which will be notified anytime the time is
     * set or changed.
     *
     * @param l
     */
    public void setOnTimeChangeListener(OnTimeChangeListener l) {
        mOnTimeChangeListener = l;
    }

    /**
     * Pushes our current time into all child ScrollLayouts, except the source
     * of the time change (if specified)
     *
     * @param source The ScrollLayout that generated the time change, or null if
     *               this isn't the result of a ScrollLayout-generated time change.
     */
    private void arrangeScrollers(ScrollLayout source) {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            if (v == source) {
                continue;
            }
            if (v instanceof ScrollLayout) {
                ScrollLayout scroller = (ScrollLayout)v;
                scroller.setTime(mTime.getTimeInMillis());
            }
        }

        if (mOnTimeChangeListener != null) {
        	if (minuteInterval>1) {
        		int minute = mTime.get(Calendar.MINUTE)/minuteInterval*minuteInterval;
        		
        		mTime.set(Calendar.MINUTE, minute);
        	}
            mOnTimeChangeListener.onTimeChange(mTime);
        }
    }

    public static interface OnTimeChangeListener {
        public void onTimeChange(Calendar time);
    }
}