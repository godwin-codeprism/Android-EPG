package com.grv_app;

import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

/**
 * Created by Godwin Vinny Carole K on Wed, 18 Sep 2019 at 16:38.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class Guide implements GuideGridView.ChildFocusListener, GuideManager.Listener{
    private static final String TAG = "Guide";
    private final GuideManager mGuideManager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public Guide() {
        mGuideManager = new GuideManager();
    }

    @Override
    public void onRequestChildFocus(View oldFocus, View newFocus) {
        if (oldFocus != null && newFocus != null) {

        }
    }

    @Override
    public void onChannelsUpdated() {

    }

    @Override
    public void onTimeRangeUpdated() {

    }
}
