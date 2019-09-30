package com.grv_app.timeline;

/**
 * Created by Godwin Vinny Carole K on Wed, 25 Sep 2019 at 11:40.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
import android.content.Context;
import android.util.AttributeSet;

public class TimelineRow extends TimelineGridView {
    private static final float FADING_EDGE_STRENGTH_START = 1.0f;

    private int mScrollPosition;

    public TimelineRow(Context context) {
        this(context, null);
    }

    public TimelineRow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimelineRow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void resetScroll() {
        getLayoutManager().scrollToPosition(0);
    }

    /** Returns the current scroll position */
    public int getScrollOffset() {
        return Math.abs(mScrollPosition);
    }

    /** Scrolls horizontally to the given position. */
    public void scrollTo(int scrollOffset, boolean smoothScroll) {
        int dx =
                (scrollOffset - getScrollOffset())
                        * (getLayoutDirection() == LAYOUT_DIRECTION_LTR ? 1 : -1);
        if (smoothScroll) {
            smoothScrollBy(dx, 0);
        } else {
            scrollBy(dx, 0);
        }
    }

    @Override
    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        // Reset scroll
        if (isAttachedToWindow()) {
            scrollTo(getScrollOffset(), false);
        }
    }

    @Override
    public void onScrolled(int dx, int dy) {
        if (dx == 0 && dy == 0) {
            mScrollPosition = 0;
        } else {
            mScrollPosition += dx;
        }
    }

    @Override
    protected float getLeftFadingEdgeStrength() {
        return (getLayoutDirection() == LAYOUT_DIRECTION_LTR) ? FADING_EDGE_STRENGTH_START : 0;
    }

    @Override
    protected float getRightFadingEdgeStrength() {
        return (getLayoutDirection() == LAYOUT_DIRECTION_RTL) ? FADING_EDGE_STRENGTH_START : 0;
    }
}
