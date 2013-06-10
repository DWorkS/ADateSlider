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

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

public class PickerBuilder {

    private FragmentManager manager; // Required
    private Integer styleResId = -1;
    private Fragment targetFragment;
    
    private Calendar mInitialTime; // Required
    private Calendar minTime = null;
    private Calendar maxTime = null;
    private int minuteInterval = 1;
    private int mReference = -1;

    public PickerBuilder setFragmentManager(FragmentManager manager) {
        this.manager = manager;
        return this;
    }

    public PickerBuilder setStyleResId(int styleResId) {
        this.styleResId = styleResId;
        return this;
    }

    public PickerBuilder setTargetFragment(Fragment targetFragment) {
        this.targetFragment = targetFragment;
        return this;
    }

    public PickerBuilder setReference(int reference) {
        this.mReference = reference;
        return this;
    }

    public PickerBuilder setInitialTime(Calendar initialTime) {
        this.mInitialTime = initialTime;
        return this;
    }

    public PickerBuilder setMinTime(Calendar minTime) {
        this.minTime = minTime;
        return this;
    }
    
    public PickerBuilder setMaxTime(Calendar maxTime) {
        this.maxTime = maxTime;
        return this;
    }

    public PickerBuilder setMinuteInterval(int minuteInterval) {
        this.minuteInterval = minuteInterval;
        return this;
    }

    public void show() {
        if (manager == null){
            Log.e("PickerBuilder", "setFragmentManager() and setStyleResId() must be called.");
            return;
        }
        final FragmentTransaction ft = manager.beginTransaction();
        final Fragment prev = manager.findFragmentByTag("date_dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        final PickerDialogFragment fragment = PickerDialogFragment.newInstance(mReference, styleResId, mInitialTime, minTime, maxTime, minuteInterval);
        if (targetFragment != null) {
        	fragment.setTargetFragment(targetFragment, 0);
        }
        fragment.show(ft, "date_dialog");
    }
}
