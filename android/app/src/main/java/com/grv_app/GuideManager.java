package com.grv_app;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.ArraySet;

import androidx.annotation.RequiresApi;
import androidx.tvprovider.media.tv.Channel;

import com.grv_app.entity.Schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by Godwin Vinny Carole K on Wed, 18 Sep 2019 at 16:03.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class GuideManager<T> {
    interface Listener {
        void onChannelsUpdated();
        void onTimeRangeUpdated();
    }
    /**
     * If the first entry's visible duration is shorter than this value, we clip the entry out.
     * Note: If this value is larger than 1 min, it could cause mismatches between the entry's
     * position and detailed view's time range.
     */
    static final long FIRST_ENTRY_MIN_DURATION = TimeUnit.MINUTES.toMillis(1);
    private final Set<Listener> mListeners = new ArraySet<>();

    private List<Channel> mChannels = new ArrayList<>();
    @SuppressLint("UseSparseArrays")
    private final Map<Long, List<Schedule<T>>> mChannelIdEntriesMap = new HashMap<>();

    public GuideManager() {
    }
}
