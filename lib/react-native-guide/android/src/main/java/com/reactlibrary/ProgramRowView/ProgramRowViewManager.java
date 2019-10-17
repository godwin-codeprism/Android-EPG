package com.reactlibrary.ProgramRowView;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.views.view.ReactViewGroup;
import com.reactlibrary.ChildGridView.ChildGridView;

/**
 * Created by Godwin Vinny Carole K on Wed, 09 Oct 2019 at 21:28.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class ProgramRowViewManager extends ViewGroupManager<ProgramRowView> {
    private final static String REACT_CLASS = "ProgramRowView";
    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @NonNull
    @Override
    protected ProgramRowView createViewInstance(@NonNull ThemedReactContext reactContext) {
        return new ProgramRowView(reactContext);
    }

    @Override
    public void addView(ProgramRowView parent, View child, int index) {
        super.addView(parent, child, index);
    }
}
