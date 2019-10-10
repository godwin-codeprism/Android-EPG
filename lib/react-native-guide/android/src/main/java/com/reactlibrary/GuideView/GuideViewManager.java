package com.reactlibrary.GuideView;

import android.view.View;

import androidx.annotation.NonNull;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;

/**
 * Created by Godwin Vinny Carole K on Sun, 06 Oct 2019 at 20:13.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class GuideViewManager extends ViewGroupManager<GuideView> {
    public static final String REACT_CLASS = "GuideView";
    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @NonNull
    @Override
    protected GuideView createViewInstance(@NonNull ThemedReactContext reactContext) {
        return new GuideView(reactContext);
    }

    @Override
    public void addView(GuideView parent, View child, int index) {
//        super.addView(parent, child, index);
        if(index == 0){
            parent.injectEPGView(child);
        }else{
            parent.injectTimelineView(child);
        }
    }
}
