package com.reactlibrary.GridItem;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.reactlibrary.ParentGridView.ChildRecyclerViewScrollListener;
import com.reactlibrary.ProgramRowView.ProgramRowView;

/**
 * Created by Godwin Vinny Carole K on Mon, 07 Oct 2019 at 04:17.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class GridItemManager extends ViewGroupManager<GridItem> {

    public static ChildRecyclerViewScrollListener listener;
    private final static String REACT_CLASS = "GridItem";
    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @NonNull
    @Override
    protected GridItem createViewInstance(@NonNull ThemedReactContext reactContext) {
        Log.i("Godwin", "Creating new instance of GridItem");
        return new GridItem(reactContext,listener);
    }

    @ReactProp(name = "itemIndex")
    public void setItemIndex(GridItem view, int itemIndex) {
        view.setItemIndex(itemIndex);
    }

    @Override
    public void addView(GridItem parent, View child, int index) {
        if(child instanceof ProgramRowView){
            Log.i("Godwin","set in ProgramRowViewManager -> " + parent.getChildRecyclerViewScrollListener());
            ((ProgramRowView) child).setChildScrollListener(parent.getChildRecyclerViewScrollListener());
        }
        super.addView(parent, child, index);

    }
}
