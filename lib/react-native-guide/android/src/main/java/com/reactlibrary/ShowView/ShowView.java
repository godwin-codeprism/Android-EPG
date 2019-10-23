package com.reactlibrary.ShowView;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.views.text.ReactTextView;
import com.facebook.react.views.view.ReactViewGroup;
import com.reactlibrary.ChildGridView.ChildGridView;
import com.reactlibrary.GridItem.GridItem;
import com.reactlibrary.ProgramRowView.ProgramRowView;
import com.reactlibrary.Utils.RecyclableWrapperViewGroupChild;

import org.w3c.dom.Text;

/**
 * Created by Godwin Vinny Carole K on Thu, 10 Oct 2019 at 20:37.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */

public class ShowView extends FrameLayout {

    private ReactTextView itemTitle;
    private LayoutParams titleLayoutParams;

    private int mBgColor;
    private int mActiveBgColor;

    public ShowView(@NonNull Context context) {
        super(context);
        setFocusable(true);
        setOnClickListener(ON_CLICKED);
        setOnFocusChangeListener(ON_FOCUS_CHANGED);
        itemTitle = new ReactTextView(context);
        titleLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        itemTitle.setGravity(Gravity.CENTER_VERTICAL);
        itemTitle.setLayoutParams(titleLayoutParams);
        itemTitle.setText("No info avaibale");
        itemTitle.setTextColor(Color.WHITE);
        itemTitle.setMaxLines(1);
        itemTitle.setEllipsize(TextUtils.TruncateAt.END);
        addView(itemTitle);
    }

    public ReactTextView getItemTitle() {
        return itemTitle;
    }

    public void setMarginLeft(int mMarginLeft) {
        titleLayoutParams.leftMargin = mMarginLeft;
    }

    public void setMarginTop(int mMarginTop) {
        titleLayoutParams.topMargin = mMarginTop;
    }

    public void setMarginRight(int mMarginRight) {
        titleLayoutParams.rightMargin = mMarginRight;
    }

    public void setMarginBottom(int mMarginBottom) {
        titleLayoutParams.bottomMargin = mMarginBottom;
    }

    public int getBgColor() {
        return mBgColor;
    }

    public void setBgColor(int mBgColor) {
        itemTitle.setBackgroundColor(mBgColor);
        this.mBgColor = mBgColor;
    }

    public int getActiveBgColor() {
        return mActiveBgColor;
    }

    public void setActiveBgColor(int mActiveBgColor) {
        this.mActiveBgColor = mActiveBgColor;
    }

    private static final View.OnClickListener ON_CLICKED =
            new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    ShowView showView = (ShowView) view;
                    ProgramRowView programRowView = (ProgramRowView) ((ReactViewGroup) (((ChildGridView) ((RecyclableWrapperViewGroupChild) ((GridItem) showView.getParent()).getParent()).getParent())).getParent()).getParent();
//                    programRowView.expand();
                }
            };

    private static final View.OnFocusChangeListener ON_FOCUS_CHANGED =
            new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    ShowView showView = (ShowView) view;
                    ReactTextView itemTitle = showView.getItemTitle();
                    if (hasFocus) {
                        itemTitle.setTextColor(Color.WHITE);
                        itemTitle.setBackgroundColor(showView.getActiveBgColor());
                    } else {
                        itemTitle.setTextColor(Color.WHITE);
                        itemTitle.setBackgroundColor(showView.getBgColor());
                    }
                }
            };

    public void setShowName(String showName) {
        itemTitle.setText(showName);
    }

    public void stickTextToLeft(int paddingLeft){
        itemTitle.setPadding(paddingLeft,0,0,0);
    }
}