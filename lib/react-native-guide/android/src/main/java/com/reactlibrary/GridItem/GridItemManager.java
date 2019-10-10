package com.reactlibrary.GridItem;

import androidx.annotation.NonNull;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

/**
 * Created by Godwin Vinny Carole K on Mon, 07 Oct 2019 at 04:17.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class GridItemManager extends ViewGroupManager<GridItem> {
    private final static String REACT_CLASS = "GridItem";
    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @NonNull
    @Override
    protected GridItem createViewInstance(@NonNull ThemedReactContext reactContext) {
        return new GridItem(reactContext);
    }

    @ReactProp(name = "itemIndex")
    public void setItemIndex(GridItem view, int itemIndex) {
        view.setItemIndex(itemIndex);
    }
}
