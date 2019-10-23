package com.reactlibrary.ShowView;

import androidx.annotation.NonNull;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

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

    @ReactProp(name = "marginLeft")
    public void setMarginLeft(ShowView view, int marginLeft){
        view.setMarginLeft(marginLeft);
    }

    @ReactProp(name = "marginTop")
    public void setMarginTop(ShowView view, int marginTop){
        view.setMarginTop(marginTop);
    }

    @ReactProp(name = "marginRight")
    public void setMarginRight(ShowView view, int marginRight){
        view.setMarginRight(marginRight);
    }

    @ReactProp(name = "marginBottom")
    public void setMarginBottom(ShowView view, int marginBottom){
        view.setMarginBottom(marginBottom);
    }

    @ReactProp(name = "bgColor")
    public void setBgColor(ShowView view, int BgColor){
        view.setBgColor(BgColor);
    }

    @ReactProp(name = "activeBgColor")
    public void setActiveBgColor(ShowView view, int activeBgColor){
        view.setActiveBgColor(activeBgColor);
    }

}
