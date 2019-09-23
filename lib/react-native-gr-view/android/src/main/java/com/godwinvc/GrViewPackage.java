package com.godwinvc;

/**
 * Created by Godwin Vinny Carole K on Tue, 10 Sep 2019 at 21:29.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.godwinvc.GRVBackendScrollView.GRVBackendScrollViewManager;
import com.godwinvc.GRVItem.GRVItemManager;
import com.godwinvc.GVerticalGridView.GVerticalGridViewManager;
import com.godwinvc.cell.EPGCellManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

public class GrViewPackage implements ReactPackage {
    @Nonnull
    @Override
    public List<NativeModule> createNativeModules(@Nonnull ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }

    @Nonnull
    @Override
    public List<ViewManager> createViewManagers(@Nonnull ReactApplicationContext reactContext) {
        return Arrays.<ViewManager>asList(
                new GRVBackendScrollViewManager(),
                new GRVItemManager(),
                new GVerticalGridViewManager(),
                new EPGCellManager()
        );
    }
}
