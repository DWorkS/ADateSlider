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

package dev.dworks.widgets.dateslider.model;

import java.util.Calendar;

import dev.dworks.widgets.dateslider.TimeObject;

/**
 * A Labeler that displays minutes
 */
public class MinuteLabeler extends Labeler {
    private final String mFormatString;

    public MinuteLabeler(String formatString) {
        super(45, 60);
        mFormatString = formatString;
    }

    @Override
    public TimeObject add(long time, int val) {
        return timeObjectfromCalendar(Util.addMinutes(time, val, minuteInterval));
    }

    @Override
    protected TimeObject timeObjectfromCalendar(Calendar c) {
    	if (minuteInterval>1) {
    		int minutes = c.get(Calendar.MINUTE);
    		c.set(Calendar.MINUTE, minutes-(minutes%minuteInterval));
    	}
        return Util.getMinute(c, mFormatString, minuteInterval);
    }

}
