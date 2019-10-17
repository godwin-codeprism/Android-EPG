package com.reactlibrary.ChildGridView;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.reactlibrary.GridItem.GridItem;
import com.reactlibrary.Utils.RecyclableWrapperViewGroupChild;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Godwin Vinny Carole K on Wed, 09 Oct 2019 at 20:43.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class ChildGridAdapter extends RecyclerView.Adapter<ChildGridAdapter.ProgramGridViewHolder> {
    private final List<GridItem> mViews = new ArrayList<>();
    private final ChildGridView mScrollView;
    private int mItemCount = 0;

    public ChildGridAdapter(ChildGridView scrollView) {
        mScrollView = scrollView;
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
    public ProgramGridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup recyclableWrapperViewGroup = new RecyclableWrapperViewGroupChild(parent.getContext(), this);
        return new ProgramGridViewHolder(recyclableWrapperViewGroup);
    }

    @Override
    public void onBindViewHolder(ProgramGridViewHolder holder, int position) {
        RecyclableWrapperViewGroupChild vg = (RecyclableWrapperViewGroupChild) holder.itemView;
        GridItem row = getViewByItemIndex(position);
        vg.setChildIndex(position);
        if (row != null && row.getParent() != vg) {
            if (row.getParent() != null) {
                ((ViewGroup) row.getParent()).removeView(row);
            }
            vg.addView(row, 0);
        }
    }

    @Override
    public void onViewRecycled(ProgramGridViewHolder holder) {
        super.onViewRecycled(holder);
        ((RecyclableWrapperViewGroupChild) holder.itemView).removeAllViews();
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

    public static class ProgramGridViewHolder extends RecyclerView.ViewHolder {
        public ProgramGridViewHolder(View itemView) {
            super(itemView);
        }
    }
}
