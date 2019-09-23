package com.grv_app.row;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Godwin Vinny Carole K on Sun, 15 Sep 2019 at 22:34.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class RowGridView extends RecyclerView {
    public RowGridView(@NonNull Context context) {
        super(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context){
            @Override
            public boolean onRequestChildFocus(@NonNull RecyclerView parent, @NonNull State state, @NonNull View child, @Nullable View focused) {
                return super.onRequestChildFocus(parent,state,child,focused);
//                return true;
            }
        };
        linearLayoutManager.setOrientation(HORIZONTAL);
        setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
    }
}
