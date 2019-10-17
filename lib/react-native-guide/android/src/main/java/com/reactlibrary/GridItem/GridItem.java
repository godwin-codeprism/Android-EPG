package com.reactlibrary.GridItem;

import android.content.Context;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.reactlibrary.Utils.GlobalScrollControllerInterface;
import com.reactlibrary.Utils.RecyclableWrapperViewGroup;

/**
 * Created by Godwin Vinny Carole K on Mon, 07 Oct 2019 at 03:29.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class GridItem extends FrameLayout {
    private int mItemIndex;
    private boolean mItemIndexInitialized;

    public GridItem(@NonNull Context context) {
        super(context);
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


}
