package com.example.administrator.demo.tagView;

import android.graphics.Color;

import com.example.administrator.demo.R;

public class Constants {
	
	
	//use dp and sp, not px
	
	//----------------- separator TagView-----------------//
	public static final int DEFAULT_LINE_MARGIN = 5;
	public static final int DEFAULT_TAG_MARGIN = 5;
	public static final int DEFAULT_TAG_TEXT_PADDING_LEFT = 8;
	public static final int DEFAULT_TAG_TEXT_PADDING_TOP = 5;
	public static final int DEFAULT_TAG_TEXT_PADDING_RIGHT = 8;
	public static final int DEFAULT_TAG_TEXT_PADDING_BOTTOM = 5;
	
	public static final int LAYOUT_WIDTH_OFFSET = 2;
	
	//----------------- separator Tag Item-----------------//
	public static final int DEFAULT_TAG_TEXT_SIZE = 7;
	public static final int DEFAULT_TAG_DELETE_INDICATOR_SIZE = 7;
	public static final int DEFAULT_TAG_RADIUS = 7;
	public static final int DEFAULT_TAG_LAYOUT_COLOR = Color.parseColor("#F8F8F8");
	public static final int DEFAULT_TAG_LAYOUT_COLOR_PRESS = Color.parseColor("#88363636");
	public static final int DEFAULT_TAG_TEXT_COLOR = Color.parseColor("#040404");
	public static final int DEFAULT_TAG_DELETE_INDICATOR_COLOR = Color.parseColor("#040404");
	public static final String DEFAULT_TAG_DELETE_ICON = "×";
	public static final boolean DEFAULT_TAG_IS_DELETABLE = true;
	public static final int DRAWABLE_BACK = R.drawable.shape_tagview;
}
