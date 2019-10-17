package com.reactlibrary.ChildGridView;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.common.SystemClock;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.NativeGestureUtil;
import com.facebook.react.views.scroll.OnScrollDispatchHelper;
import com.facebook.react.views.scroll.ScrollEvent;
import com.facebook.react.views.scroll.ScrollEventType;
import com.facebook.react.views.scroll.VelocityHelper;
import com.reactlibrary.R;
import com.reactlibrary.GridItem.GridItem;
import com.reactlibrary.Utils.GlobalScrollListener;
import com.reactlibrary.Utils.VisibleItemsChangeEvent;

/**
 * Created by Godwin Vinny Carole K on Wed, 09 Oct 2019 at 20:42.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class ChildGridView extends RecyclerView {
    private final OnScrollDispatchHelper mOnScrollDispatchHelper = new OnScrollDispatchHelper();
    private final VelocityHelper mVelocityHelper = new VelocityHelper();

    public static class ScrollOptions {
        @Nullable
        public
        Float millisecondsPerInch;
        @Nullable
        public Float viewPosition;
        @Nullable
        public Float viewOffset;
    }

    private boolean mDragging;
    private int mFirstVisibleIndex, mLastVisibleIndex;

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (mOnScrollDispatchHelper.onScrollChanged(l, t)) {
            getReactContext().getNativeModule(UIManagerModule.class).getEventDispatcher()
                    .dispatchEvent(ScrollEvent.obtain(
                            getId(),
                            ScrollEventType.SCROLL,
                            0, /* offsetX = 0, horizontal scrolling only */
                            computeVerticalScrollOffset(),
                            mOnScrollDispatchHelper.getXFlingVelocity(),
                            mOnScrollDispatchHelper.getYFlingVelocity(),
                            getWidth(),
                            computeVerticalScrollRange(),
                            getWidth(),
                            getHeight()));
        }

        final int firstIndex = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        final int lastIndex = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();

        if (firstIndex != mFirstVisibleIndex || lastIndex != mLastVisibleIndex) {
            getReactContext().getNativeModule(UIManagerModule.class).getEventDispatcher()
                    .dispatchEvent(new VisibleItemsChangeEvent(
                            getId(),
                            SystemClock.nanoTime(),
                            firstIndex,
                            lastIndex));

            mFirstVisibleIndex = firstIndex;
            mLastVisibleIndex = lastIndex;
        }
    }

    private ReactContext getReactContext() {
        return (ReactContext) ((ContextThemeWrapper) getContext()).getBaseContext();
//        getContext()
    }

    public ChildGridView(Context context) {
        super(new ContextThemeWrapper(context, R.style.ScrollbarRecyclerView));
//        super(context);
        setHasFixedSize(true);
        ((DefaultItemAnimator)getItemAnimator()).setSupportsChangeAnimations(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context){
            @Override
            public boolean onRequestChildFocus(@NonNull RecyclerView parent, @NonNull State state, @NonNull View child, @Nullable View focused) {
                if(GlobalScrollListener.listener != null){
                    GlobalScrollListener.listener.epgCellIsScrolledBy(child.getMeasuredWidth(),0);
                }
                return true;
            }
        };
        linearLayoutManager.setOrientation(HORIZONTAL);
        setLayoutManager(linearLayoutManager);
        setAdapter(new ChildGridAdapter(this));
    }

    /*package*/
    public void addViewToAdapter(GridItem child, int index) {
        ((ChildGridAdapter) getAdapter()).addView(child, index);
    }

    /*package*/
    public void removeViewFromAdapter(int index) {
        ((ChildGridAdapter) getAdapter()).removeViewAt(index);
    }

    /*package*/
    public View getChildAtFromAdapter(int index) {
        return ((ChildGridAdapter) getAdapter()).getView(index);
    }

    /*package*/
    public int getChildCountFromAdapter() {
        return ((ChildGridAdapter) getAdapter()).getViewCount();
    }

    /*package*/
    public void setItemCount(int itemCount) {
        ((ChildGridAdapter) getAdapter()).setItemCount(itemCount);
    }

    /*package*/ int getItemCount() {
        return getAdapter().getItemCount();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (super.onInterceptTouchEvent(ev)) {
            NativeGestureUtil.notifyNativeGestureStarted(this, ev);
            mDragging = true;
            getReactContext().getNativeModule(UIManagerModule.class).getEventDispatcher()
                    .dispatchEvent(ScrollEvent.obtain(
                            getId(),
                            ScrollEventType.BEGIN_DRAG,
                            0, /* offsetX = 0, horizontal scrolling only */
                            computeVerticalScrollOffset(),
                            0, // xVelocity
                            0, // yVelocity
                            getWidth(),
                            computeVerticalScrollRange(),
                            getWidth(),
                            getHeight()));
            return true;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction() & MotionEvent.ACTION_MASK;
        if (action == MotionEvent.ACTION_UP && mDragging) {
            mDragging = false;
            mVelocityHelper.calculateVelocity(ev);
            getReactContext().getNativeModule(UIManagerModule.class).getEventDispatcher()
                    .dispatchEvent(ScrollEvent.obtain(
                            getId(),
                            ScrollEventType.END_DRAG,
                            0, /* offsetX = 0, horizontal scrolling only */
                            computeVerticalScrollOffset(),
                            mVelocityHelper.getXVelocity(),
                            mVelocityHelper.getYVelocity(),
                            getWidth(),
                            computeVerticalScrollRange(),
                            getWidth(),
                            getHeight()));
        }
        return super.onTouchEvent(ev);
    }

    private boolean mRequestedLayout = false;

    @Override
    public void requestLayout() {
        super.requestLayout();

        if (!mRequestedLayout) {
            mRequestedLayout = true;
            this.post(new Runnable() {
                @Override
                public void run() {
                    mRequestedLayout = false;
                    layout(getLeft(), getTop(), getRight(), getBottom());
//                    onLayout(false, getLeft(), getTop(), getRight(), getBottom());
                }
            });
        }
    }

    @Override
    public void scrollToPosition(int position) {
        this.scrollToPosition(position, new ScrollOptions());
    }

    public void scrollToPosition(final int position, final ScrollOptions options) {
        if (options.viewPosition != null) {
            final LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
            final ChildGridAdapter adapter = (ChildGridAdapter) getAdapter();
            final View view = adapter.getViewByItemIndex(position);
            if (view != null) {
                final int viewHeight = view.getHeight();
                // In order to calculate the correct offset, we need the height of the target view.
                // If the height of the view is not available it means RN has not calculated it yet.
                // So let's listen to the layout change and we will retry scrolling.
                if (viewHeight == 0) {
                    view.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                            view.removeOnLayoutChangeListener(this);
                            scrollToPosition(position, options);
                        }
                    });
                    return;
                }

                final int boxStart = layoutManager.getPaddingTop();
                final int boxEnd = layoutManager.getHeight() - layoutManager.getPaddingBottom();
                final int boxHeight = boxEnd - boxStart;
                float viewOffset = options.viewOffset != null ? PixelUtil.toPixelFromDIP(options.viewOffset) : 0;
                int offset = (int) ((boxHeight - viewHeight) * options.viewPosition + viewOffset);
                layoutManager.scrollToPositionWithOffset(position, offset);
                return;
            }
        }

        super.scrollToPosition(position);
    }

    @Override
    public void smoothScrollToPosition(int position) {
        this.smoothScrollToPosition(position, new ScrollOptions());
    }

    public void smoothScrollToPosition(int position, final ScrollOptions options) {
        final RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(this.getContext()) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }

            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                return ((LinearLayoutManager) this.getLayoutManager()).computeScrollVectorForPosition(targetPosition);
            }

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                if (options.millisecondsPerInch != null) {
                    return options.millisecondsPerInch / displayMetrics.densityDpi;
                } else {
                    return super.calculateSpeedPerPixel(displayMetrics);
                }
            }

            @Override
            public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
                int calc = super.calculateDtToFit(viewStart, viewEnd, boxStart, boxEnd, snapPreference);
                if (options.viewPosition != null) {
                    int viewHeight = viewEnd - viewStart;
                    int boxHeight = boxEnd - boxStart;
                    float viewOffset = options.viewOffset != null ? PixelUtil.toPixelFromDIP(options.viewOffset) : 0;
                    float target = boxStart + (boxHeight - viewHeight) * options.viewPosition + viewOffset;
                    return (int) (target - viewStart);
                } else {
                    return super.calculateDtToFit(viewStart, viewEnd, boxStart, boxEnd, snapPreference);
                }
            }
        };

        smoothScroller.setTargetPosition(position);
        this.getLayoutManager().startSmoothScroll(smoothScroller);
    }

    public void setInverted(boolean inverted) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
        layoutManager.setReverseLayout(inverted);
    }

    public void setItemAnimatorEnabled(boolean enabled) {
        if (enabled) {
            DefaultItemAnimator animator = new DefaultItemAnimator();
            animator.setSupportsChangeAnimations(false);
            setItemAnimator(animator);
        } else {
            setItemAnimator(null);
        }
    }
}
