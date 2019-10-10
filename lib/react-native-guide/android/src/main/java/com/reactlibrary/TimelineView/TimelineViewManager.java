package com.reactlibrary.TimelineView;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.ContentSizeChangeEvent;
import com.facebook.react.views.scroll.ScrollEventType;
import com.reactlibrary.ChildGridView.ChildGridAdapter;
import com.reactlibrary.GridItem.GridItem;
import com.reactlibrary.Utils.VisibleItemsChangeEvent;

import java.util.Map;

/**
 * Created by Godwin Vinny Carole K on Thu, 10 Oct 2019 at 21:24.
 * Copyright (c) Code Prism Technologies Pvt Ltd
 */
public class TimelineViewManager extends ViewGroupManager<TimelineView> {
    private final static String REACT_CLASS = "TimelineView";
    private static final int COMMAND_NOTIFY_ITEM_RANGE_INSERTED = 1;
    private static final int COMMAND_NOTIFY_ITEM_RANGE_REMOVED = 2;
    private static final int COMMAND_NOTIFY_DATASET_CHANGED = 3;
    private static final int COMMAND_SCROLL_TO_INDEX = 4;
    private static final int COMMAND_NOTIFY_ITEM_MOVED = 5;
    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @NonNull
    @Override
    protected TimelineView createViewInstance(@NonNull ThemedReactContext reactContext) {
        return new TimelineView(reactContext);
    }

    @Override
    public void addView(TimelineView parent, View child, int index) {
//        super.addView(parent, child, index);
        GridItem item = (GridItem) child;
        parent.addViewToAdapter(item, index);
    }

    @Override
    public int getChildCount(TimelineView parent) {
        return parent.getChildCountFromAdapter();
    }

    @Override
    public View getChildAt(TimelineView parent, int index) {
        return parent.getChildAtFromAdapter(index);
    }

    @Override
    public void removeViewAt(TimelineView parent, int index) {
        parent.removeViewFromAdapter(index);
    }

    @ReactProp(name = "itemCount")
    public void setItemCount(TimelineView parent, int itemCount) {
        parent.setItemCount(itemCount);
        parent.getAdapter().notifyDataSetChanged();
    }

    @Override
    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.of(
                "notifyItemRangeInserted", COMMAND_NOTIFY_ITEM_RANGE_INSERTED,
                "notifyItemRangeRemoved", COMMAND_NOTIFY_ITEM_RANGE_REMOVED,
                "notifyItemMoved", COMMAND_NOTIFY_ITEM_MOVED,
                "notifyDataSetChanged", COMMAND_NOTIFY_DATASET_CHANGED,
                "scrollToIndex", COMMAND_SCROLL_TO_INDEX
        );
    }

    @Override
    public void receiveCommand(
            final TimelineView parent,
            int commandType,
            @Nullable ReadableArray args) {
        Assertions.assertNotNull(parent);
        Assertions.assertNotNull(args);
        switch (commandType) {
            case COMMAND_NOTIFY_ITEM_RANGE_INSERTED: {
                final int position = args.getInt(0);
                final int count = args.getInt(1);
                //Log.d(TAG, String.format("notify item range inserted: position %d, count %d", position, count));

                ChildGridAdapter adapter = (ChildGridAdapter) parent.getAdapter();
                adapter.setItemCount(adapter.getItemCount() + count);
                adapter.notifyItemRangeInserted(position, count);
                return;
            }

            case COMMAND_NOTIFY_ITEM_RANGE_REMOVED: {
                final int position = args.getInt(0);
                final int count = args.getInt(1);
                //Log.d(TAG, String.format("notify item range removed: position %d, count %d", position, count));

                ChildGridAdapter adapter = (ChildGridAdapter) parent.getAdapter();
                adapter.setItemCount(adapter.getItemCount() - count);
                adapter.notifyItemRangeRemoved(position, count);
                return;
            }


            case COMMAND_NOTIFY_ITEM_MOVED: {
                final int currentPosition = args.getInt(0);
                final int nextPosition = args.getInt(1);
                ChildGridAdapter adapter = (ChildGridAdapter) parent.getAdapter();
                adapter.notifyItemMoved(currentPosition, nextPosition);
                return;
            }

            case COMMAND_NOTIFY_DATASET_CHANGED: {
                final int itemCount = args.getInt(0);
                ChildGridAdapter adapter = (ChildGridAdapter) parent.getAdapter();
                adapter.setItemCount(itemCount);
                parent.getAdapter().notifyDataSetChanged();
                return;
            }

            case COMMAND_SCROLL_TO_INDEX: {
                boolean animated = args.getBoolean(0);
                int index = args.getInt(1);
                TimelineView.ScrollOptions options = new TimelineView.ScrollOptions();
                options.millisecondsPerInch = args.isNull(2) ? null : (float) args.getDouble(2);
                options.viewPosition = args.isNull(3) ? null : (float) args.getDouble(3);
                options.viewOffset = args.isNull(4) ? null : (float) args.getDouble(4);

                if (animated) {
                    parent.smoothScrollToPosition(index, options);
                } else {
                    parent.scrollToPosition(index, options);
                }
                return;
            }

            default:
                throw new IllegalArgumentException(String.format(
                        "Unsupported command %d received by %s.",
                        commandType,
                        getClass().getSimpleName()));
        }
    }

    @Override
    public
    @Nullable
    Map getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.builder()
                .put(ScrollEventType.getJSEventName(ScrollEventType.SCROLL), MapBuilder.of("registrationName", "onScroll"))
                .put(ContentSizeChangeEvent.EVENT_NAME, MapBuilder.of("registrationName", "onContentSizeChange"))
                .put(VisibleItemsChangeEvent.EVENT_NAME, MapBuilder.of("registrationName", "onVisibleItemsChange"))
                .build();
    }
}
