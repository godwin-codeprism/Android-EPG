package com.reactlibrary.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.leanback.widget.VerticalGridView;

/**
 * Created by Godwin Vinny Carole K on Mon, 07 Oct 2019 at 04:43.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
@SuppressLint("ViewConstructor")
public class RecyclableWrapperViewGroup extends ViewGroup {

    private VerticalGridView.Adapter mAdapter;
    private int mLastMeasuredWidth;
    private int mLastMeasuredHeight;
    private int childIndex;


    public RecyclableWrapperViewGroup(Context context, VerticalGridView.Adapter adapter) {
        super(context);
        mAdapter = adapter;
        mLastMeasuredHeight = 10;
        mLastMeasuredWidth = 10;
    }

    private OnLayoutChangeListener mChildLayoutChangeListener = new OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            int oldHeight = (oldBottom - oldTop);
            int newHeight = (bottom - top);

            if (oldHeight != newHeight) {
                if (getParent() != null) {
                    requestLayout();
                    getParent().requestLayout();
                }
            }
        }
    };

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // This view will only have one child that is managed by the `NativeViewHierarchyManager` and
        // its position and dimensions are set separately. We don't need to handle its layout here
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        child.addOnLayoutChangeListener(mChildLayoutChangeListener);
    }

    @Override
    public void onViewRemoved(View child) {
        super.onViewRemoved(child);
        child.removeOnLayoutChangeListener(mChildLayoutChangeListener);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // We override measure spec and use dimensions of the children. Children is a view added
        // from the adapter and always have a correct dimensions specified as they are calculated
        // and set with NativeViewHierarchyManager.
        // In case there is no view attached, we use the last measured dimensions.

        if (getChildCount() > 0) {
            View child = getChildAt(0);
            mLastMeasuredWidth = child.getMeasuredWidth();
            mLastMeasuredHeight = child.getMeasuredHeight();
            setMeasuredDimension(mLastMeasuredWidth, mLastMeasuredHeight);
        } else {
            setMeasuredDimension(mLastMeasuredWidth, mLastMeasuredHeight);
        }
    }

    public VerticalGridView.Adapter getAdapter() {
        return mAdapter;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Similarly to ReactViewGroup, we return true.
        // In this case it is necessary in order to force the RecyclerView to intercept the touch events,
        // in this way we can exactly know when the drag starts because "onInterceptTouchEvent"
        // of the RecyclerView will return true.
        return true;
    }

    public void setChildIndex(int childIndex) {
        this.childIndex = childIndex;
    }

    public int getChildIndex() {
        return childIndex;
    }
}
