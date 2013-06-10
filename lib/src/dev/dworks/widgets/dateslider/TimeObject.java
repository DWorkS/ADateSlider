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
package dev.dworks.widgets.dateslider;

/**
 * Very simple helper class that defines a time unit with a label (text) its
 * start- and end date
 */
public class TimeObject {
	public final CharSequence text;
	public final long startTime, endTime;

	public TimeObject(final CharSequence text, final long startTime,
			final long endTime) {
		this.text = text;
		this.startTime = startTime;
		this.endTime = endTime;
	}
}