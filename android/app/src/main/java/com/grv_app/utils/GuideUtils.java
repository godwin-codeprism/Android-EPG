package com.grv_app.utils;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.grv_app.cell.EPGCell;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by Godwin Vinny Carole K on Fri, 13 Sep 2019 at 16:23.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class GuideUtils {
    private static final int INVALID_INDEX = -1;
    private static int sWidthPerHour = 0;
    private static final long ONE_DAY_MS = TimeUnit.DAYS.toMillis(1);

    /**
     * Sets the width in pixels that corresponds to an hour in program guide. Assume that this is
     * called from main thread only, so, no synchronization.
     */
    static void setWidthPerHour(int widthPerHour) {
        sWidthPerHour = widthPerHour;
    }

    /**
     * Gets the number of pixels in program guide table that corresponds to the given milliseconds.
     */
    public static int convertMillisToPixel(long millis) {
        return (int) (millis * sWidthPerHour / TimeUnit.HOURS.toMillis(1));
    }

    /** Gets the number of pixels in program guide table that corresponds to the given range. */
    public static int convertMillisToPixel(long startMillis, long endMillis) {
        // Convert to pixels first to avoid accumulation of rounding errors.
        return GuideUtils.convertMillisToPixel(endMillis)
                - GuideUtils.convertMillisToPixel(startMillis);
    }

    /** Gets the time in millis that corresponds to the given pixels in the program guide. */
    static long convertPixelToMillis(int pixel) {
        return pixel * TimeUnit.HOURS.toMillis(1) / sWidthPerHour;
    }

    /**
     * Return the view should be focused in the given program row according to the focus range.
     *
     * @param keepCurrentProgramFocused If {@code true}, focuses on the current program if possible,
     *     else falls back the general logic.
     */
    static View findNextFocusedProgram(
            View programRow,
            int focusRangeLeft,
            int focusRangeRight,
            boolean keepCurrentProgramFocused) {
        ArrayList<View> focusables = new ArrayList<>();
        findFocusables(programRow, focusables);

        if (keepCurrentProgramFocused) {
            // Select the current program if possible.
            for (int i = 0; i < focusables.size(); ++i) {
                View focusable = focusables.get(i);
                if (focusable instanceof EPGCell
                        && isCurrentProgram((EPGCell) focusable)) {
                    return focusable;
                }
            }
        }

        // Find the largest focusable among fully overlapped focusables.
        int maxFullyOverlappedWidth = Integer.MIN_VALUE;
        int maxPartiallyOverlappedWidth = Integer.MIN_VALUE;
        int nextFocusIndex = INVALID_INDEX;
        for (int i = 0; i < focusables.size(); ++i) {
            View focusable = focusables.get(i);
            Rect focusableRect = new Rect();
            focusable.getGlobalVisibleRect(focusableRect);
            if (focusableRect.left <= focusRangeLeft && focusRangeRight <= focusableRect.right) {
                // the old focused range is fully inside the focusable, return directly.
                return focusable;
            } else if (focusRangeLeft <= focusableRect.left
                    && focusableRect.right <= focusRangeRight) {
                // the focusable is fully inside the old focused range, choose the widest one.
                int width = focusableRect.width();
                if (width > maxFullyOverlappedWidth) {
                    nextFocusIndex = i;
                    maxFullyOverlappedWidth = width;
                }
            } else if (maxFullyOverlappedWidth == Integer.MIN_VALUE) {
                int overlappedWidth =
                        (focusRangeLeft <= focusableRect.left)
                                ? focusRangeRight - focusableRect.left
                                : focusableRect.right - focusRangeLeft;
                if (overlappedWidth > maxPartiallyOverlappedWidth) {
                    nextFocusIndex = i;
                    maxPartiallyOverlappedWidth = overlappedWidth;
                }
            }
        }
        if (nextFocusIndex != INVALID_INDEX) {
            return focusables.get(nextFocusIndex);
        }
        return null;
    }

    /**
     * Returns {@code true} if the program displayed in the give {@link
     * EPGCell} is a current program.
     */
    public static boolean isCurrentProgram(EPGCell view) {
        // TODO: Must write login to check if this view has currently running program
        return false;
    }

    /** Returns {@code true} if the given view is a descendant of the give container. */
    public static boolean isDescendant(ViewGroup container, View view) {
        if (view == null) {
            return false;
        }
        for (ViewParent p = view.getParent(); p != null; p = p.getParent()) {
            if (p == container) {
                return true;
            }
        }
        return false;
    }

    private static void findFocusables(View v, ArrayList<View> outFocusable) {
        if (v.isFocusable()) {
            outFocusable.add(v);
        }
        if (v instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) v;
            for (int i = 0; i < viewGroup.getChildCount(); ++i) {
                findFocusables(viewGroup.getChildAt(i), outFocusable);
            }
        }
    }

    private GuideUtils() {}

    /**
     * Checks if two given time (in milliseconds) are in the same day with regard to the locale
     * timezone.
     */
    public static boolean isInGivenDay(long dayToMatchInMillis, long subjectTimeInMillis) {
        TimeZone timeZone = Calendar.getInstance().getTimeZone();
        long offset = timeZone.getRawOffset();
        if (timeZone.inDaylightTime(new Date(dayToMatchInMillis))) {
            offset += timeZone.getDSTSavings();
        }
        return GuideUtils.floorTime(dayToMatchInMillis + offset, ONE_DAY_MS)
                == GuideUtils.floorTime(subjectTimeInMillis + offset, ONE_DAY_MS);
    }

    /**
     * Floors time to the given {@code timeUnit}. For example, if time is 5:32:11 and timeUnit is
     * one hour (60 * 60 * 1000), then the output will be 5:00:00.
     */
    public static long floorTime(long timeMs, long timeUnit) {
        return timeMs - (timeMs % timeUnit);
    }
}
