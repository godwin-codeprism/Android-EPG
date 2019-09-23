package com.godwinvc.GRVItem;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

import javax.annotation.Nonnull;

/**
 * Created by Godwin Vinny Carole K on Tue, 10 Sep 2019 at 21:34.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class GRVItemManager extends ViewGroupManager<GRVItem> {

    @Override
    public String getName() {
        return "GRVItem";
    }

    @Nonnull
    @Override
    protected GRVItem createViewInstance(@Nonnull ThemedReactContext reactContext) {
        return new GRVItem(reactContext);
    }

    @ReactProp(name = "itemIndex")
    public void setItemIndex(GRVItem view, int itemIndex) {
        view.setItemIndex(itemIndex);
    }
}
