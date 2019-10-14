package com.reactlibrary.GuideView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;


/**
 * Created by Godwin Vinny Carole K on Mon, 14 Oct 2019 at 16:20.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class CustomEPGContainer extends LinearLayout {

    private focusListener listener;
    public CustomEPGContainer(Context context) {
        super(context);
    }

    public CustomEPGContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomEPGContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void childScrolledBy(int x, int y){
        if(listener != null){
            listener.scrollViewBy(x,y);
        }
    }

    public void setFocusListener(focusListener listener){
        this.listener = listener;
    }

}
