package com.reactlibrary.GuideView;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.facebook.react.views.view.ReactViewGroup;
import com.reactlibrary.ChildGridView.ChildGridView;
import com.reactlibrary.GridItem.GridItem;
import com.reactlibrary.ParentGridView.ParentGridView;
import com.reactlibrary.ProgramRowView.ProgramRowView;
import com.reactlibrary.R;
import com.reactlibrary.ShowView.ShowView;
import com.reactlibrary.Utils.GlobalScrollController;
import com.reactlibrary.Utils.RecyclableWrapperViewGroup;

/**
 * Created by Godwin Vinny Carole K on Sun, 06 Oct 2019 at 20:13.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class GuideView extends FrameLayout implements focusListener {
    private FrameLayout BaseLayout;
    private CustomTimeLineContainer TimeLineContainer;
    private CustomEPGContainer EPGContainer;

    private final ViewTreeObserver.OnGlobalFocusChangeListener mGlobalFocusChangeListener =
            new ViewTreeObserver.OnGlobalFocusChangeListener() {
                @Override
                public void onGlobalFocusChanged(View oldFocus, View newFocus) {
//                    Log.i("Godwin", "old -> " + oldFocus);
//                    Log.i("Godwin", "new -> " + newFocus);
                }
            };

    public GuideView(Context context) {
        super(context);
        BaseLayout = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.epg_layout, this, false);
        setOnKeyListener(ON_KEY);
        addView(BaseLayout);
        GlobalScrollController.globalDx = 0;
        GlobalScrollController.globalDy = 0;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    public void injectEPGView(View child){
        EPGContainer = BaseLayout.findViewById(R.id.EPGContainer);
        EPGContainer.addView(child);
        EPGContainer.setFocusListener(this);

    }

    public void injectTimelineView(View child){
        TimeLineContainer = BaseLayout.findViewById(R.id.TimelineContainer);
        TimeLineContainer.addView(child);

    }

    private static View.OnKeyListener ON_KEY = new View.OnKeyListener(){

        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            Log.i("Godwin", "KeyEvent -> "+ keyEvent.getKeyCode());
            return true;
        }
    };

    @Override
    public void scrollViewBy(int x, int y) {
//        Log.i("Godwin", "scrollSyncCalled Called");
        if(TimeLineContainer.getChildAt(0) instanceof ChildGridView){
            TimeLineContainer.getChildAt(0).scrollBy(x,y);
        }
        if(EPGContainer.getChildAt(0) instanceof ParentGridView){
            ParentGridView parentGridView = (ParentGridView) EPGContainer.getChildAt(0);
            LinearLayoutManager parentLayoutManager = ((LinearLayoutManager) parentGridView.getLayoutManager());
            //final int firstIndex = parentLayoutManager.findFirstVisibleItemPosition();
           // final int lastIndex = parentLayoutManager.findLastVisibleItemPosition();
            for(int i=0; i <= parentLayoutManager.getChildCount(); i++){
                if(parentLayoutManager.getChildAt(i) != null && parentLayoutManager.getChildAt(i) instanceof  RecyclableWrapperViewGroup){
                    RecyclableWrapperViewGroup recyclableWrapperViewGroup = (RecyclableWrapperViewGroup) parentLayoutManager.getChildAt(i);
                    if(recyclableWrapperViewGroup != null && recyclableWrapperViewGroup.getChildCount() != 0 ){
                        ProgramRowView rowView = (ProgramRowView) ((GridItem)(recyclableWrapperViewGroup).getChildAt(0)).getChildAt(0);
                        ChildGridView childGridView = (ChildGridView) ((ReactViewGroup) rowView.getChildAt(1)).getChildAt(0);
                        childGridView.scrollBy(x,y);
                        childGridView.setScrollOffset(GlobalScrollController.globalDx);
                    }
                }
            }
        }

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalFocusChangeListener(mGlobalFocusChangeListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalFocusChangeListener(mGlobalFocusChangeListener);
    }

    @Override
    public View focusSearch(View focused, int direction) {
        if(focused instanceof ShowView){
//            Log.i("Godwin", "Focused -> "+focused+ " dir "+ direction);
//            if(direction == View.FOCUS_LEFT){
//                GlobalScrollController.listener.syncScrollBy( -focused.getMeasuredWidth(),0);
//            }else if(direction == View.FOCUS_RIGHT){
//                GlobalScrollController.listener.syncScrollBy( focused.getMeasuredWidth(),0);
//            }
        }
        return super.focusSearch(focused, direction);
    }
}
