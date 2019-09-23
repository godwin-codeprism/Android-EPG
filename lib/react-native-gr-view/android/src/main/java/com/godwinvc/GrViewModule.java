package com.godwinvc;

/**
 * Created by Godwin Vinny Carole K on Tue, 10 Sep 2019 at 21:29.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class GrViewModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public GrViewModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "GrView";
    }
}
