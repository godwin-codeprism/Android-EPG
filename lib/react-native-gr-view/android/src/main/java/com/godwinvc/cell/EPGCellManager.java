package com.godwinvc.cell;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;

import javax.annotation.Nonnull;

/**
 * Created by Godwin Vinny Carole K on Wed, 18 Sep 2019 at 14:04.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class EPGCellManager extends ViewGroupManager<EPGCell> {
    public static final String REACT_CLASS = "EPGCell";

    @Nonnull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Nonnull
    @Override
    protected EPGCell createViewInstance(@Nonnull ThemedReactContext reactContext) {
        return new EPGCell(reactContext);
    }
}
