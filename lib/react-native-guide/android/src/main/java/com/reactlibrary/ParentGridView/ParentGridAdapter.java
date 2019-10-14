package com.reactlibrary.ParentGridView;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.reactlibrary.GridItem.GridItem;
import com.reactlibrary.Utils.RecyclableWrapperViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Godwin Vinny Carole K on Mon, 07 Oct 2019 at 03:26.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class ParentGridAdapter extends RecyclerView.Adapter<ParentGridAdapter.ParentGridViewHolder> {
    private final List<GridItem> mViews = new ArrayList<>();
    private final ParentGridView mScrollView;
    private int mItemCount = 0;
    private ChildRecyclerViewScrollListener listener;

    public ParentGridAdapter(ParentGridView scrollView,ChildRecyclerViewScrollListener listener) {
        mScrollView = scrollView;
        this.listener = listener;
        //setHasStableIds(true);
    }

    public void addView(GridItem child, int index) {
        mViews.add(index, child);

        final int itemIndex = child.getItemIndex();

        notifyItemChanged(itemIndex);
    }

    public void removeViewAt(int index) {
        GridItem child = mViews.get(index);
        if (child != null) {
            mViews.remove(index);
        }
    }

    public int getViewCount() {
        return mViews.size();
    }

    @Override
    public ParentGridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup recyclableWrapperViewGroup = new RecyclableWrapperViewGroup(parent.getContext(), this);
        return new ParentGridViewHolder(recyclableWrapperViewGroup);
    }

    @Override
    public void onBindViewHolder(ParentGridViewHolder holder, int position) {
        RecyclableWrapperViewGroup vg = (RecyclableWrapperViewGroup) holder.itemView;
        vg.setChildIndex(position);
        View row = getViewByItemIndex(position);
        if (row != null && row.getParent() != vg) {
            if (row.getParent() != null) {
                ((ViewGroup) row.getParent()).removeView(row);
            }
            vg.addView(row, 0);
            Log.e("FATAL:",listener.toString());
            vg.setListener(listener);
        }
    }

    @Override
    public void onViewRecycled(ParentGridViewHolder holder) {
        super.onViewRecycled(holder);
        ((RecyclableWrapperViewGroup) holder.itemView).removeAllViews();
    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }

    public void setItemCount(int itemCount) {
        this.mItemCount = itemCount;
    }

    public View getView(int index) {
        return mViews.get(index);
    }

    public GridItem getViewByItemIndex(int position) {
        for (int i = 0; i < mViews.size(); i++) {
            if (mViews.get(i).getItemIndex() == position) {
                return mViews.get(i);
            }
        }

        return null;
    }

    public static class ParentGridViewHolder extends RecyclerView.ViewHolder {
        public ParentGridViewHolder(View itemView) {
            super(itemView);
        }
    }
}
