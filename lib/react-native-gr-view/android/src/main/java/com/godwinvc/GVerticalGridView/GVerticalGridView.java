package com.godwinvc.GVerticalGridView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.leanback.widget.VerticalGridView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.godwinvc.cell.EPGCell;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Godwin Vinny Carole K on Wed, 18 Sep 2019 at 13:46.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class GVerticalGridView extends VerticalGridView {
    private static final String TAG = "GVerticalGridView";

    private static class VerticalGridViewHolder extends ViewHolder {
        public VerticalGridViewHolder(View itemView) {
            super(itemView);
        }
    }

    public GVerticalGridView(Context context) {
        super(context);
        setHasFixedSize(true);
        ((DefaultItemAnimator)getItemAnimator()).setSupportsChangeAnimations(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        setLayoutManager(linearLayoutManager);
        setAdapter(new ReactListAdapter(this));
    }

    @SuppressLint("ViewConstructor")
    public static class VerticalGridWrapperViewGroup extends ViewGroup {

        private ReactListAdapter mAdapter;
        private int mLastMeasuredWidth;
        private int mLastMeasuredHeight;

        public VerticalGridWrapperViewGroup(Context context, ReactListAdapter adapter) {
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

        public ReactListAdapter getAdapter() {
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
    }

    /*package*/ public static class ReactListAdapter extends VerticalGridView.Adapter<VerticalGridViewHolder> {

        private final List<EPGCell> mCells = new ArrayList<>();
        private final VerticalGridView mVerticalGridView;

        public ReactListAdapter(VerticalGridView verticalGridView) {
            mVerticalGridView = verticalGridView;
            //setHasStableIds(true);
        }

        @NonNull
        @Override
        public VerticalGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ViewGroup verticalGridWrapperViewGroup = new VerticalGridWrapperViewGroup(parent.getContext(), this);
            return new VerticalGridViewHolder(verticalGridWrapperViewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull VerticalGridViewHolder holder, int position) {
            VerticalGridWrapperViewGroup vg = (VerticalGridWrapperViewGroup) holder.itemView;
        }

        @Override
        public int getItemCount() {
            return mCells.size();
        }
    }
}
