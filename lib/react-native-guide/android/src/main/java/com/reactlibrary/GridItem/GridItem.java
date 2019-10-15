package com.reactlibrary.GridItem;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.reactlibrary.ParentGridView.ChildRecyclerViewScrollListener;
import com.reactlibrary.ProgramRowView.ProgramRowView;
import com.reactlibrary.Utils.RecyclableWrapperViewGroup;

/**
 * Created by Godwin Vinny Carole K on Mon, 07 Oct 2019 at 03:29.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class GridItem extends FrameLayout {
    private int mItemIndex;
    private boolean mItemIndexInitialized;
    private ChildRecyclerViewScrollListener childRecyclerViewScrollListener;

    public GridItem(@NonNull Context context,ChildRecyclerViewScrollListener listener) {
        super(context);
        this.childRecyclerViewScrollListener = listener;
    }

    public void setItemIndex(int itemIndex) {
        if (mItemIndexInitialized  && this.mItemIndex != itemIndex){
            this.mItemIndex = itemIndex;
            if (getParent() != null) {
                ((RecyclableWrapperViewGroup) getParent()).getAdapter().notifyItemChanged(mItemIndex);
                ((RecyclableWrapperViewGroup) getParent()).getAdapter().notifyItemChanged(itemIndex);
            }
        } else {
            this.mItemIndex = itemIndex;
        }

        mItemIndexInitialized = true;
    }

    public int getItemIndex() {
        return mItemIndex;
    }


    public void setChildRecyclerViewScrollListener(ChildRecyclerViewScrollListener listener) {
        Log.i("Godwin","set in GridItem -> " + listener);
        this.childRecyclerViewScrollListener = listener;
    }

    public ChildRecyclerViewScrollListener getChildRecyclerViewScrollListener(){
        Log.i("Godwin","get in GridItem -> " + this.childRecyclerViewScrollListener);
        return this.childRecyclerViewScrollListener;
    }


}
