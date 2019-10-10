package com.reactlibrary.ShowView;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

/**
 * Created by Godwin Vinny Carole K on Thu, 10 Oct 2019 at 20:37.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class ShowView extends FrameLayout {
    private Button cell;
    private String showName;
    public ShowView(Context context) {
        super(context);
        cell = new Button(context);
        cell.setGravity(Gravity.CENTER);
        cell.setAllCaps(false);
        cell.setText("Godwin VC");
        cell.setBackgroundColor(Color.BLUE);
        cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                onCellClick(view);
            }
        });
        cell.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if (hasFocus) {
                    onCellFocus(v);
                } else {
                    onCellBlur(v);
                }
            }
        });
        addView(cell);
    }

    private void onCellClick(View view){
        Log.i("ShowView", showName);
    }

    private void onCellFocus(View view){
        view.setBackgroundColor(Color.WHITE);
    }

    private void onCellBlur(View view){
        view.setBackgroundColor(Color.BLUE);
    }

    public void setShowName (String showName){
        this.showName = showName;
        cell.setText(showName);
    }

    public void setCustomId (Integer id){
        cell.setId(id);
    }
}
