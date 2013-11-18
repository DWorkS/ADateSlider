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

package dev.dworks.widgets.adateslider.model;

import java.util.Calendar;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import dev.dworks.widgets.adateslider.TimeObject;
import dev.dworks.widgets.adateslider.ui.TimeTextView;
import dev.dworks.widgets.adateslider.ui.TimeView;

/**
 * A customized Labeler that displays weeks using a CustomTimeTextView
 */
public class WeekLabeler extends Labeler {
    private final String mFormatString;

    public WeekLabeler(String formatString) {
        super(120, 60);
        mFormatString = formatString;
    }

    @Override
    public TimeObject add(long time, int val) {
        return timeObjectfromCalendar(Util.addWeeks(time, val));
    }

    /**
     * We implement this as custom code rather than a method in Util because there
     * is no format string that shows the week of the year as an integer, so we just
     * format the week directly rather than extracting it from a Calendar object.
     */
    @Override
    protected TimeObject timeObjectfromCalendar(Calendar c) {
        int week = c.get(Calendar.WEEK_OF_YEAR);
        int day_of_week = c.get(Calendar.DAY_OF_WEEK)-1;
        // set calendar to first millisecond of the week
        c.add(Calendar.DAY_OF_MONTH,-day_of_week);
        c.set(Calendar.HOUR_OF_DAY,0);c.set(Calendar.MINUTE,0);c.set(Calendar.SECOND,0);
        c.set(Calendar.MILLISECOND, 0);
        long startTime = c.getTimeInMillis();
        // set calendar to last millisecond of the week
        c.add(Calendar.DAY_OF_WEEK, 6);
        c.set(Calendar.HOUR_OF_DAY,23);c.set(Calendar.MINUTE,59);c.set(Calendar.SECOND,59);
        c.set(Calendar.MILLISECOND, 999);
        long endTime = c.getTimeInMillis();
        return new TimeObject(String.format(mFormatString,week), startTime, endTime);
    }

    /**
     * create our customized TimeTextView and return it
     */
    public TimeView createView(Context context, boolean isCenterView) {
        return new CustomTimeTextView(context, isCenterView, null);
    }

    /**
     * Here we define our Custom TimeTextView which will display the fonts in its very own way.
     */
    private static class CustomTimeTextView extends TimeTextView {

        public CustomTimeTextView(Context context, boolean isCenterView, Bundle textSize) {
            super(context, isCenterView, textSize);
        }

        /**
         * Here we set up the text characteristics for the TextView, i.e. red colour,
         * serif font and semi-transparent white background for the centerView... and shadow!!!
         */
        @Override
        public void setupView(boolean isCenterView, Bundle textSize) {
            setGravity(Gravity.CENTER);
            setTextColor(0xFF883333);
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize.getInt(Util.PRIMARY_TEXT_SIZE));
            setTypeface(Typeface.SERIF);
            if (isCenterView) {
                setTypeface(Typeface.create(Typeface.SERIF, Typeface.BOLD));
                setBackgroundColor(0x55FFFFFF);
                setShadowLayer(2.5f, 3, 3, 0xFF999999);
            }
        }

    }
}