package com.reactlibrary.GuideView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.reactlibrary.R;

/**
 * Created by Godwin Vinny Carole K on Sun, 06 Oct 2019 at 20:13.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class GuideView extends FrameLayout {
    private FrameLayout BaseLayout;
    private LinearLayout TimeLineContainer;
    private LinearLayout EPGContainer;

    public GuideView(Context context) {
        super(context);
        BaseLayout = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.epg_layout, this, false);
        addView(BaseLayout);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    public void injectEPGView(View child){
        EPGContainer = BaseLayout.findViewById(R.id.EPGContainer);
        EPGContainer.addView(child);
    }

    public void injectTimelineView(View child){
        TimeLineContainer = BaseLayout.findViewById(R.id.TimelineContainer);
        TimeLineContainer.addView(child);
    }

}
