package com.grv_app.row;

import android.content.Context;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grv_app.utils.ChildFocusListener;

/**
 * Created by Godwin Vinny Carole K on Sun, 15 Sep 2019 at 22:34.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class RowGridView extends RecyclerView {

    private ChildFocusListener listener;
    private boolean didFocusChange = false;
    private int direction = 0;
    public RowGridView(@NonNull Context context, ChildFocusListener listener) {
        super(context);
        this.listener = listener;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context){
            @Override
            public boolean onRequestChildFocus(@NonNull RecyclerView parent, @NonNull State state, @NonNull View child, @Nullable View focused) {
                if(listener != null && didFocusChange && !isTheViewInBounds(child)){
                  /*  if(direction == -1){
                        listener.onChildScrolled(-(child.getMeasuredWidth()));
                    }else if(direction == 1){
                        listener.onChildScrolled((child.getMeasuredWidth()));
                    }*/

                    listener.onChildScrolled((child.getWidth()),parent);
                }
                didFocusChange = true;
                return true;
                //return  super.onRequestChildFocus(parent,state,child,focused);
            }

        };
        linearLayoutManager.setOrientation(HORIZONTAL);
        setLayoutManager(linearLayoutManager);
        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT){
                    direction = 1;
                }else if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT){
                    direction = -1;
                }
                return false;
            }
        });
    }

    private boolean isTheViewInBounds(View view){
        if(view == null){
            return false;
        }
        final Rect viewBoundsRect = new Rect();
        view.getGlobalVisibleRect(viewBoundsRect);
        final Rect screen = new Rect(0,0,1920,1080);
        return viewBoundsRect.intersect(screen);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
    }

}
