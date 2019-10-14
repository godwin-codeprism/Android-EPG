package com.reactlibrary.ProgramRowView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.facebook.react.ReactRootView;
import com.reactlibrary.ChannelView.ChannelView;
import com.reactlibrary.ChildGridView.ChildGridView;
import com.reactlibrary.ParentGridView.ChildRecyclerViewScrollListener;
import com.reactlibrary.R;

/**
 * Created by Godwin Vinny Carole K on Wed, 09 Oct 2019 at 21:28.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class ProgramRowView extends LinearLayout {
    private Context context;
    private LinearLayout programRowContainer;
    private LinearLayout channelViewContainer;
    private LinearLayout childGridContainer;

    public ChildRecyclerViewScrollListener getListener() {
        return listener;
    }

    private ChildRecyclerViewScrollListener listener;

    public ProgramRowView(@NonNull Context context) {
        super(context);
        this.context = context;
//        programRowContainer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.program_row_view, this, false);
//        addView(programRowContainer);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        // Nothing goes here. RN handles this
    }

    public void addChannelView(View channelView){
        channelViewContainer = programRowContainer.findViewById(R.id.ChannelViewContainer);
        channelViewContainer.addView(channelView);
    }

    public void addChildGridView(View childGridView){
        childGridContainer = programRowContainer.findViewById(R.id.ChildGridContainer);
        childGridContainer.addView(childGridView);
    }

    public void setChildScrollListener(ChildRecyclerViewScrollListener listener){
        this.listener = listener;
    }
}
