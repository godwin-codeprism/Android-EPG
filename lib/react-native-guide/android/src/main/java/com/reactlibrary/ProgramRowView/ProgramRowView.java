package com.reactlibrary.ProgramRowView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

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
    private LayoutParams layoutParams;

    public ProgramRowView(@NonNull Context context) {
        super(context);
        this.context = context;
//        LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );
//        setLayoutParams(layoutParams);
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

    public void expand(){
        layoutParams.height = 500;
        setLayoutParams(layoutParams);
    }

}
