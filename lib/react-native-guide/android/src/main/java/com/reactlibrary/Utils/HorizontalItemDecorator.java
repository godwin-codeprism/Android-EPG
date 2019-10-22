package com.reactlibrary.Utils;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
public class HorizontalItemDecorator extends RecyclerView.ItemDecoration {

    private final int mHorizontalSpaceWidth;

    public HorizontalItemDecorator(int horizontalSpaceHeight) {
        this.mHorizontalSpaceWidth = horizontalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.left = outRect.right = mHorizontalSpaceWidth;
    }
}
