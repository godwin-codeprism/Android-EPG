package com.reactlibrary.GuideView;

import android.os.Build;

import androidx.annotation.RequiresApi;

/**
 * Created by Godwin Vinny Carole K on Mon, 14 Oct 2019 at 17:36.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public interface focusListener {
    void scrollViewBy(int x, int y,int id, boolean shouldSmoothScroll);
}
