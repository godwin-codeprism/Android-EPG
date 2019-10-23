package com.reactlibrary.ShowView;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.views.text.ReactTextView;

import org.w3c.dom.Text;

/**
 * Created by Godwin Vinny Carole K on Thu, 10 Oct 2019 at 20:37.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
//public class ShowView extends FrameLayout {
//    private Button cell;
//    private String showName;
//    public ShowView(Context context) {
//        super(context);
//        cell = new Button(context);
//        cell.setGravity(Gravity.CENTER);
//        cell.setAllCaps(false);
//        cell.setText("Godwin VC");
//        cell.setBackgroundColor(Color.BLUE);
//        cell.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                onCellClick(view);
//            }
//        });
//        cell.setOnFocusChangeListener(new View.OnFocusChangeListener(){
//            @Override
//            public void onFocusChange(View v, boolean hasFocus){
//                if (hasFocus) {
//                    onCellFocus(v);
//                } else {
//                    onCellBlur(v);
//                }
//            }
//        });
//        addView(cell);
//    }
//
//    private void onCellClick(View view){
//        Log.i("ShowView", showName);
//    }
//
//    private void onCellFocus(View view){
//        view.setBackgroundColor(Color.WHITE);
//    }
//
//    private void onCellBlur(View view){
//        view.setBackgroundColor(Color.BLUE);
//    }
//
//    public void setShowName (String showName){
//        this.showName = showName;
//        cell.setText(showName);
//    }
//
//    public void setCustomId (Integer id){
//        cell.setId(id);
//    }
//}

public class ShowView extends FrameLayout {

    private ReactTextView itemTitle;
    private static int sItemPadding;
    private LayoutParams titleLayoutParams;

    private int mBgColor;
    private int mActiveBgColor;

    // If set this flag disables requests to re-layout the parent view as a result of changing
    // this view, improving performance. This also prevents the parent view to lose child focus
    // as a result of the re-layout (see b/21378855).
    private boolean mPreventParentRelayout;

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

                }
            };

    private static final View.OnFocusChangeListener ON_FOCUS_CHANGED =
            new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    ShowView showView = (ShowView) view;
                    ReactTextView itemTitle = showView.getItemTitle();
                    if (hasFocus) {
                        itemTitle.setTextColor(Color.BLACK);
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

    /**
     * Update programItemView to handle alignments of text.
     */
    public void updateVisibleArea() {
        View parentView = ((View) getParent());
        if (parentView == null) {
            return;
        }
        if (getLayoutDirection() == LAYOUT_DIRECTION_LTR) {
            layoutVisibleArea(parentView.getLeft() - getLeft(), getRight() - parentView.getRight());
        } else {
            layoutVisibleArea(getRight() - parentView.getRight(), parentView.getLeft() - getLeft());

        }
    }

    /**
     * Layout title and episode according to visible area.
     *
     * <p>Here's the spec. 1. Don't show text if it's shorter than 48dp. 2. Try showing whole text
     * in visible area by placing and wrapping text, but do not wrap text less than 30min. 3.
     * Episode title is visible only if title isn't multi-line.
     *
     * @param startOffset Offset of the start position from the enclosing view's start position.
     * @param endOffset   Offset of the end position from the enclosing view's end position.
     */
    private void layoutVisibleArea(int startOffset, int endOffset) {
        int width = getWidth();
        int startPadding = Math.max(0, startOffset);
        int endPadding = Math.max(0, endOffset);
        int minWidth = Math.min(width, itemTitle.getWidth() + 2 * sItemPadding);
        if (startPadding > 0 && width - startPadding < minWidth) {
            startPadding = Math.max(0, width - minWidth);
        }
        if (endPadding > 0 && width - endPadding < minWidth) {
            endPadding = Math.max(0, width - minWidth);
        }

        if (startPadding + sItemPadding != getPaddingStart()
                || endPadding + sItemPadding != getPaddingEnd()) {
            mPreventParentRelayout = true; // The size of this view is kept, no need to tell parent.
            setPaddingRelative(startPadding + sItemPadding, 0, endPadding + sItemPadding, 0);
            mPreventParentRelayout = false;
        }
    }
}