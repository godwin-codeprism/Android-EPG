package com.grv_app;

import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grv_app.row.RowAdapter;
import com.grv_app.row.RowGridView;
import com.grv_app.utils.ChildFocusListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Godwin Vinny Carole K on Sun, 15 Sep 2019 at 20:55.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class GuideGridViewAdapter extends RecyclerView.Adapter<GuideGridViewAdapter.ProgramGuideGridViewHolder>  {
    private HashMap data;
    private ArrayList channels;
    private ChildFocusListener childFocusListener;
    private int minTag = 0;
    private int maxTag= 0;
    private int scrollingNow = 0;

    public GuideGridViewAdapter(HashMap data,ChildFocusListener childFocusListener) {
        this.data = data;
        this.channels = new ArrayList(data.keySet());
        this.childFocusListener = childFocusListener;
    }

    @NonNull
    @Override
    public ProgramGuideGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowGridView programGuideRowGridView = new RowGridView(parent.getContext(),childFocusListener){

            @Override
            public void onScrolled(int dx, int dy) {
                super.onScrolled(dx, dy);
                if ((int)this.getTag() > maxTag) {
                    maxTag = (int)this.getTag();
                    if(maxTag > this.getChildCount() - 1) {
                        minTag += 1;
                    }
                    this.scrollBy(scrollingNow, dy);
                } else if ((int)this.getTag() < minTag && dx == 0) {
                    minTag = (int)this.getTag();
                    maxTag -= 1;
                    this.scrollBy(scrollingNow, dy);
                } else if(!((int)this.getTag() < minTag) && !((int)this.getTag() > maxTag)) {
                    scrollingNow = this.computeHorizontalScrollOffset();
                }

            }

            @Override
            public void onScrollStateChanged(int state) {
                super.onScrollStateChanged(state);
                Log.i("SCROLLING NOW---STATE", state+"");
//                if (state == 0) {
//                    scrollingNow = scrollingNow / 7;
//                    Log.i("SCROLLING_NOW_UI", scrollingNow+"");
//                }

            }

        };
        return new ProgramGuideGridViewHolder(programGuideRowGridView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramGuideGridViewHolder holder, int position) {
        holder.setAdapter((ArrayList<String>) data.get(channels.get(position)), position);
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }



    class ProgramGuideGridViewHolder extends RecyclerView.ViewHolder{
        RowGridView row;
        public ProgramGuideGridViewHolder(@NonNull RowGridView itemView) {
            super(itemView);
            row = itemView;
        }

        private void setAdapter(ArrayList<String> data, int index){
            RowAdapter programGuideRowAdapter = new RowAdapter(data);
            row.setAdapter(programGuideRowAdapter);
            row.setTag(index);
        }
    }
}
