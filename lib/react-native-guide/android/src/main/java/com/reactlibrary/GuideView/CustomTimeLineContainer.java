package com.reactlibrary.GuideView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by Godwin Vinny Carole K on Mon, 14 Oct 2019 at 16:20.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class CustomTimeLineContainer extends LinearLayout {


    public CustomTimeLineContainer(Context context) {
        super(context);
    }

    public CustomTimeLineContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTimeLineContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void scrollChildRVBy(int x, int y){
        ((RecyclerView)getChildAt(0)).scrollBy(x,y);
    }
}
