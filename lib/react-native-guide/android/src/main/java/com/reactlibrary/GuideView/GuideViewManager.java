package com.reactlibrary.GuideView;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.facebook.yoga.YogaConstants;
import com.reactlibrary.R;
import com.reactlibrary.Utils.Helper;

/**
 * Created by Godwin Vinny Carole K on Sun, 06 Oct 2019 at 20:13.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class GuideViewManager extends ViewGroupManager<GuideView> {
    public static final String REACT_CLASS = "GuideView";
    private Context reactContext;

    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @NonNull
    @Override
    protected GuideView createViewInstance(@NonNull ThemedReactContext reactContext) {
        this.reactContext = reactContext;
        return new GuideView(reactContext);
    }

    @Override
    public void addView(GuideView parent, View child, int index) {
//        super.addView(parent, child, index);
        if (index == 0) {
            parent.injectEPGView(child);
        } else {
            parent.injectTimelineView(child);
        }
    }

    @ReactProp(name = "timelineDimensions")
    public void setTimelineDimenstions(GuideView view, ReadableMap timelineDimensions) {
        CustomTimeLineContainer customTimeLineContainer = view.findViewById(R.id.TimelineContainer);
        CustomEPGContainer customEPGContainer = view.findViewById(R.id.EPGContainer);
        FrameLayout.LayoutParams timelineLayoutParams = (FrameLayout.LayoutParams) customTimeLineContainer.getLayoutParams();
        timelineLayoutParams.width = Helper.convertDpToPixel(timelineDimensions.getInt("width"), reactContext);
        timelineLayoutParams.height = Helper.convertDpToPixel(timelineDimensions.getInt("height"), reactContext);
        customEPGContainer.setPadding(0,Helper.convertDpToPixel(timelineDimensions.getInt("height"), reactContext),0,0);
        customTimeLineContainer.setLayoutParams(timelineLayoutParams);
    }
}
