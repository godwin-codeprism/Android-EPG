package com.godwinvc.GRVItem;

import android.content.Context;
import android.view.ViewGroup;

import com.godwinvc.GRVBackendScrollView.GRVBackendScrollView;

/**
 * Created by Godwin Vinny Carole K on Tue, 10 Sep 2019 at 21:34.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class GRVItem extends ViewGroup {
    private int mItemIndex;
    private boolean mItemIndexInitialized;

    public GRVItem(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    public void setItemIndex(int itemIndex) {
        if (mItemIndexInitialized  && this.mItemIndex != itemIndex){
            this.mItemIndex = itemIndex;
            if (getParent() != null) {
                ((GRVBackendScrollView.RecyclableWrapperViewGroup) getParent()).getAdapter().notifyItemChanged(mItemIndex);
                ((GRVBackendScrollView.RecyclableWrapperViewGroup) getParent()).getAdapter().notifyItemChanged(itemIndex);
            }
        } else {
            this.mItemIndex = itemIndex;
        }

        mItemIndexInitialized = true;
    }

    public int getItemIndex() {
        return mItemIndex;
    }
}
