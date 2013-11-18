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

package dev.dworks.widgets.adateslider.ui;

import java.util.Calendar;

import android.content.Context;
import android.os.Bundle;
import dev.dworks.widgets.adateslider.TimeObject;
import dev.dworks.widgets.adateslider.model.Util;

/**
 * This is a subclass of the TimeLayoutView that represents a day. It uses
 * a different color to distinguish Sundays from other days.
 */
public class DayTimeLayoutView extends TimeLayoutView {

    protected boolean isSunday=false;
    protected Bundle bundle;

    /**
     * Constructor
     * @param context
     * @param isCenterView true if the element is the centered view in the ScrollLayout
     * @param topTextSize	text size of the top TextView in dps
     * @param bottomTextSize	text size of the bottom TextView in dps
     * @param lineHeight	LineHeight of the top TextView
     */
    public DayTimeLayoutView(Context context, boolean isCenterView, Bundle bundle) {
        super(context, isCenterView, bundle);
    	this.bundle = bundle;
    }
    
    @Override
    public void setupView(Context context, boolean isCenterView, Bundle bundle) {
    	super.setupView(context, isCenterView, bundle);
    	this.bundle = bundle;
    }

    @Override
    public void setVals(TimeObject to) {
        super.setVals(to);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(to.endTime);
        if (c.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY && !isSunday) {
            isSunday=true;
            colorMeSunday();
        } else if (isSunday && c.get(Calendar.DAY_OF_WEEK)!=Calendar.SUNDAY) {
            isSunday=false;
            colorMeWorkday();
        }
    }

    /**
     * this method is called when the current View takes a Sunday as time unit
     */
    protected void colorMeSunday() {
    	if (isOutOfBounds) return;
        if (isCenter) {
            bottomView.setTextColor(bundle.getInt(Util.SECONDARY_TEXT_COLOR_BOLD));
            topView.setTextColor(bundle.getInt(Util.PRIMARY_TEXT_COLOR_BOLD));
        }
        else {
            bottomView.setTextColor(bundle.getInt(Util.SECONDARY_TEXT_COLOR));
            topView.setTextColor(bundle.getInt(Util.PRIMARY_TEXT_COLOR));
        }
    }

    /**
     * this method is called when the current View takes no Sunday as time unit
     */
    protected void colorMeWorkday() {
    	if (isOutOfBounds) return;
        if (isCenter) {
            bottomView.setTextColor(bundle.getInt(Util.SECONDARY_TEXT_COLOR_BOLD));
            topView.setTextColor(bundle.getInt(Util.PRIMARY_TEXT_COLOR_BOLD));
        }
        else {
            bottomView.setTextColor(bundle.getInt(Util.SECONDARY_TEXT_COLOR));
            topView.setTextColor(bundle.getInt(Util.PRIMARY_TEXT_COLOR));
        }
    }

    @Override
    public void setVals(TimeView other) {
        super.setVals(other);
        DayTimeLayoutView otherDay = (DayTimeLayoutView) other;
        if (otherDay.isSunday && !isSunday) {
            isSunday = true;
            colorMeSunday();
        } else if (isSunday && !otherDay.isSunday) {
            isSunday = false;
            colorMeWorkday();
        }
    }
}