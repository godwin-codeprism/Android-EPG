package com.grv_app.entity;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.grv_app.utils.GuideUtils;

import java.time.Instant;

/**
 * Created by Godwin Vinny Carole K on Mon, 23 Sep 2019 at 14:15.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class Schedule<T> {
    Long id;
    Long startsAtMillis;
    Long endsAtMillis;
    OriginalTimes originalTimes;
    Boolean isClickable;
    String displayTitle;
    T program;

    public Schedule(Long id, Long startsAtMillis, Long endsAtMillis, OriginalTimes originalTimes, Boolean isClickable, String displayTitle, T program) {
        this.id = id;
        this.startsAtMillis = startsAtMillis;
        this.endsAtMillis = endsAtMillis;
        this.originalTimes = originalTimes;
        this.isClickable = isClickable;
        this.displayTitle = displayTitle;
        this.program = program;
    }

    private static Long GAP_ID = -1L;

    static class OriginalTimes {
        Long startsAtMillis;
        Long endsAtMillis;

        public OriginalTimes(Long startsAtMillis, Long endsAtMillis) {
            this.startsAtMillis = startsAtMillis;
            this.endsAtMillis = endsAtMillis;
        }
    }

    public Schedule createGap(Long from, Long to) {
        Schedule schedule = new Schedule(
                GAP_ID,
                from,
                to,
                new OriginalTimes(from, to),
                false,
                null,
                null
        );
        return schedule;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Schedule createScheduleWithProgram(Long id, Instant startsAt, Instant endsAt, Boolean isClickable,
                                              String displayTitle, T program) {
        Schedule schedule = new Schedule(id,
                startsAt.toEpochMilli(),
                endsAt.toEpochMilli(),
                new OriginalTimes(startsAt.toEpochMilli(), endsAt.toEpochMilli()),
                isClickable,
                displayTitle,
                program
        );
        return schedule;
    }

    public Integer width(){
        return GuideUtils.convertMillisToPixel(startsAtMillis, endsAtMillis);
    }

    public Boolean isGap(){
        return  program == null;
    }

    public Boolean isCurrentProgram(){
        long current = System.currentTimeMillis();
        return startsAtMillis <= current && endsAtMillis > current;
    }


}
