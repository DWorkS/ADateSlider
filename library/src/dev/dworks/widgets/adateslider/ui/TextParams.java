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

public class TextParams {
	public int size = 25;
	public int color = 0xFF666666;
	public int colorBold = 0xFF333333;
	
	public TextParams() {
	}
	
	public TextParams(int size) {
		this.size = size;
	}
	
	public TextParams(int size, int color) {
		this.size = size;
		this.color =  color;
	}
	
	public TextParams(int size, int color, int colorBold) {
		this.size = size;
		this.color =  color;
		this.colorBold = colorBold;
	}
}
