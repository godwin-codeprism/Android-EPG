package com.grv_app.row;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grv_app.R;
import com.grv_app.cell.EPGCell;

import java.util.ArrayList;

/**
 * Created by Godwin Vinny Carole K on Sun, 15 Sep 2019 at 22:34.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class RowAdapter extends RecyclerView.Adapter<RowAdapter.ProgramGuideRowViewHolder> {
    private ArrayList<String> shows;
    public RowAdapter(@NonNull ArrayList<String> shows) {
        this.shows = shows;
    }

    @NonNull
    @Override
    public ProgramGuideRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EPGCell epgCell = new EPGCell(parent.getContext());
        return new ProgramGuideRowViewHolder(epgCell);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramGuideRowViewHolder holder, int position) {
        holder.setData(shows.get(position));
    }

    @Override
    public int getItemCount() {
        return shows.size();
    }

    class ProgramGuideRowViewHolder extends RecyclerView.ViewHolder{

        private Button cell;
        private ProgramGuideRowViewHolder(@NonNull View itemView) {
            super(itemView);
            cell = itemView.findViewById(R.id.cell);
        }

        private void setData(String showName){
            cell.setText(showName);
        }
    }
}
