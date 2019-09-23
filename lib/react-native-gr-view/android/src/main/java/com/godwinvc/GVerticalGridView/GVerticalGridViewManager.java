package com.godwinvc.GVerticalGridView;

import android.util.Log;
import android.view.View;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;

import javax.annotation.Nonnull;

/**
 * Created by Godwin Vinny Carole K on Wed, 18 Sep 2019 at 13:46.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class GVerticalGridViewManager extends ViewGroupManager<GVerticalGridView> {
    public static final String REACT_CLASS = "GVerticalGridView";
    @Nonnull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Nonnull
    @Override
    protected GVerticalGridView createViewInstance(@Nonnull ThemedReactContext reactContext) {
        return new GVerticalGridView(reactContext);
    }

    @Override
    public void addView(GVerticalGridView parent, View child, int index) {
        super.addView(parent, child, index);
    }

    @Override
    public int getChildCount(GVerticalGridView parent) {
        Log.i("Godwin", "" + super.getChildCount(parent));
        return super.getChildCount(parent);
    }
}
