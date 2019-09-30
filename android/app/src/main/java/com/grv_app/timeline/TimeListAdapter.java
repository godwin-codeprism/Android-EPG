package com.grv_app.timeline;

/**
 * Created by Godwin Vinny Carole K on Wed, 25 Sep 2019 at 11:40.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */

import android.content.res.Resources;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.grv_app.R;
import com.grv_app.utils.GuideUtils;

import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


class TimeListAdapter extends RecyclerView.Adapter<TimeListAdapter.TimeViewHolder> {
    private static final long TIME_UNIT_MS = TimeUnit.MINUTES.toMillis(30);

    // Ex. 3:00 AM
    private static final String TIME_PATTERN_SAME_DAY = "h:mm a";
    // Ex. Oct 21, 3:00 AM
    private static final String TIME_PATTERN_DIFFERENT_DAY = "MMM d, h:mm a";

    private static int sRowHeaderOverlapping;

    // Nearest half hour at or before the start time.
    private long mStartUtcMs;
    private final String mTimePatternSameDay;
    private final String mTimePatternDifferentDay;

    TimeListAdapter(Resources res) {
        if (sRowHeaderOverlapping == 0) {
            sRowHeaderOverlapping =
                    Math.abs(
                            res.getDimensionPixelOffset(
                                    R.dimen.program_guide_table_header_row_overlap));
        }
        Locale locale = res.getConfiguration().locale;
        mTimePatternSameDay = DateFormat.getBestDateTimePattern(locale, TIME_PATTERN_SAME_DAY);
        mTimePatternDifferentDay =
                DateFormat.getBestDateTimePattern(locale, TIME_PATTERN_DIFFERENT_DAY);
    }

    public void update(long startTimeMs) {
        mStartUtcMs = startTimeMs;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.guide_table_header_row_item;
    }

    @Override
    public void onBindViewHolder(TimeViewHolder holder, int position) {
        long startTime = mStartUtcMs + position * TIME_UNIT_MS;
        long endTime = startTime + TIME_UNIT_MS;

        View itemView = holder.itemView;
        Date timeDate = new Date(startTime);
        String timeString;
        if (GuideUtils.isInGivenDay(System.currentTimeMillis(), startTime)) {
            timeString = DateFormat.format(mTimePatternSameDay, timeDate).toString();
        } else {
            timeString = DateFormat.format(mTimePatternDifferentDay, timeDate).toString();
        }
        ((TextView) itemView.findViewById(R.id.time)).setText(timeString);

        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        lp.width = GuideUtils.convertMillisToPixel(startTime, endTime);
        if (position == 0) {
            // Adjust width for the first entry to make the item starts from the fading edge.
            lp.setMarginStart(sRowHeaderOverlapping - lp.width / 2);
        } else {
            lp.setMarginStart(0);
        }
        itemView.setLayoutParams(lp);
    }

    @Override
    public TimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new TimeViewHolder(itemView);
    }

    static class TimeViewHolder extends RecyclerView.ViewHolder {
        TimeViewHolder(View itemView) {
            super(itemView);
        }
    }
}
