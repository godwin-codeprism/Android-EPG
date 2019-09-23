package com.grv_app;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.leanback.widget.VerticalGridView;

import com.grv_app.cell.EPGCell;
import com.grv_app.utils.GuideUtils;
import com.grv_app.utils.OnRepeatedKeyInterceptListener;

import java.util.concurrent.TimeUnit;

/**
 * Created by Godwin Vinny Carole K on Fri, 13 Sep 2019 at 17:03.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class GuideGridView extends VerticalGridView {
    private static final String TAG = "GuideGridView";

    private static final int INVALID_INDEX = -1;
    private static final long FOCUS_AREA_RIGHT_MARGIN_MILLIS = TimeUnit.MINUTES.toMillis(15);
    //    private ProgramManager mProgramManager;
    private View mNextFocusByUpDown;

    // New focus will be overlapped with [mFocusRangeLeft, mFocusRangeRight].
    private int mFocusRangeLeft;
    private int mFocusRangeRight;

//    private final int mRowHeight;
//    private final int mDetailHeight;
//    private final int mSelectionRow; // Row that is focused

    private View mLastFocusedView;
    private final Rect mTempRect = new Rect();
    private int mLastUpDownDirection;

    private boolean mKeepCurrentProgramFocused;

    private ChildFocusListener mChildFocusListener;
    private final OnRepeatedKeyInterceptListener mOnRepeatedKeyInterceptListener;

    interface ChildFocusListener {
        /**
         * Is called before focus is moved. Only children to {@code ProgramGrid} will be passed. See
         * {@code ProgramGrid#setChildFocusListener(ChildFocusListener)}.
         */
        void onRequestChildFocus(View oldFocus, View newFocus);
    }

    public GuideGridView(Context context) {
        super(context);
        setItemViewCacheSize(0);
        mOnRepeatedKeyInterceptListener = new OnRepeatedKeyInterceptListener(this);
        setOnKeyInterceptListener((OnKeyInterceptListener) mOnRepeatedKeyInterceptListener);
    }

    private final ViewTreeObserver.OnGlobalFocusChangeListener mGlobalFocusChangeListener =
            new ViewTreeObserver.OnGlobalFocusChangeListener() {
                @Override
                public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                    if (newFocus != mNextFocusByUpDown) {
                        // If focus is changed by other buttons than UP/DOWN buttons,
                        // we clear the focus state.
                        clearUpDownFocusState(newFocus);
                    }
                    mNextFocusByUpDown = null;
                    if (GuideUtils.isDescendant(GuideGridView.this, newFocus)) {
                        mLastFocusedView = newFocus;
                    }
                }
            };

    @Override
    public void requestChildFocus(View child, View focused) {
        if (mChildFocusListener != null) {
            Log.i("Godwin","requestChildFocus" + getFocusedChild());
            mChildFocusListener.onRequestChildFocus(getFocusedChild(), child);
        }
        super.requestChildFocus(child, focused);
    }

    @Override
    public boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        Log.i("Godwin","onRequestFocusInDescendants");
        if (mLastFocusedView != null && mLastFocusedView.isShown()) {
            if (mLastFocusedView.requestFocus()) {
                return true;
            }
        }
        return super.onRequestFocusInDescendants(direction, previouslyFocusedRect);
    }

    private void clearUpDownFocusState(View focus) {
        mLastUpDownDirection = 0;
        mFocusRangeLeft = 0;
        mFocusRangeRight = getRightMostFocusablePosition();
        mNextFocusByUpDown = null;
        // If focus is not a program item, drop focus to the current program when back to the grid
        mKeepCurrentProgramFocused =
                !(focus instanceof EPGCell)
                        || GuideUtils.isCurrentProgram((EPGCell) focus);
    }

    private int getRightMostFocusablePosition() {
        if (!getGlobalVisibleRect(mTempRect)) {
            return Integer.MAX_VALUE;
        }
        return mTempRect.right - GuideUtils.convertMillisToPixel(FOCUS_AREA_RIGHT_MARGIN_MILLIS);
    }
}
