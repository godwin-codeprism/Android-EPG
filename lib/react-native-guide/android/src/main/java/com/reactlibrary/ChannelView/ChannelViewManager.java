package com.reactlibrary.ChannelView;

import androidx.annotation.NonNull;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;

/**
 * Created by Godwin Vinny Carole K on Wed, 09 Oct 2019 at 21:15.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class ChannelViewManager extends ViewGroupManager<ChannelView> {
    private final static String REACT_CLASS = "ChannelView";
    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @NonNull
    @Override
    protected ChannelView createViewInstance(@NonNull ThemedReactContext reactContext) {
        return new ChannelView(reactContext);
    }
}
