package com.reactlibrary.ShowView;

import androidx.annotation.NonNull;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.reactlibrary.GridItem.GridItem;

/**
 * Created by Godwin Vinny Carole K on Thu, 10 Oct 2019 at 20:38.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class ShowViewManager extends ViewGroupManager<ShowView> {
    private final static String REACT_CLASS = "ShowView";
    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @NonNull
    @Override
    protected ShowView createViewInstance(@NonNull ThemedReactContext reactContext) {
        return new ShowView(reactContext);
    }

    @ReactProp(name = "showName")
    public void setShowName(ShowView view, String showName){
        view.setShowName(showName);
    }

}
