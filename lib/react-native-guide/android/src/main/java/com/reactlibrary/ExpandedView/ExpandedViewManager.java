package com.reactlibrary.ExpandedView;

import androidx.annotation.NonNull;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;

/**
 * Created by Godwin Vinny Carole K on Wed, 23 Oct 2019 at 19:45.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class ExpandedViewManager extends ViewGroupManager<ExpandedView> {
    private final static String REACT_CLASS = "ExpandedView";
    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @NonNull
    @Override
    protected ExpandedView createViewInstance(@NonNull ThemedReactContext reactContext) {
        return new ExpandedView(reactContext);
    }
}
