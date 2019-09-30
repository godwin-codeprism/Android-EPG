package com.grv_app.cell;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.grv_app.R;


public class EPGCell extends FrameLayout {

    private Button cell;
     public int direction = 0;

    public EPGCell(@NonNull Context context) {
        this(context, null);
    }

    public EPGCell(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EPGCell(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.addView(context);
        cell = findViewById(R.id.cell);
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
       /* cell.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
               if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT){
                   direction = 1;
               }else if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT){
                   direction = -1;
               }
                return false;
            }
        });*/
    }


    private void addView (Context context){
        View.inflate(context, R.layout.epgcell, this);

    }

    private void onCellClick(View view){
        Log.i("Godwin", "Clicked: " + view.getId());
    }

    private void onCellFocus(View view){
        view.setBackgroundColor(Color.WHITE);
    }

    private void onCellBlur(View view){
        view.setBackgroundColor(Color.BLUE);
    }

    public void setShowName (String showName){
        cell.setText(showName);
    }

    public void setCustomId (Integer id){
        cell.setId(id);
    }


}
