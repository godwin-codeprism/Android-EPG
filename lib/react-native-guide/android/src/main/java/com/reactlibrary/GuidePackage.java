package com.reactlibrary;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.reactlibrary.ChannelView.ChannelViewManager;
import com.reactlibrary.ParentGridView.ParentGridViewManager;
import com.reactlibrary.ChildGridView.ChildGridViewManager;
import com.reactlibrary.GridItem.GridItemManager;
import com.reactlibrary.GuideView.GuideViewManager;
import com.reactlibrary.ProgramRowView.ProgramRowViewManager;
import com.reactlibrary.ShowView.ShowViewManager;
import com.reactlibrary.TimelineView.TimelineViewManager;

public class GuidePackage implements ReactPackage {
    @NonNull
    @Override
    public List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }

    @NonNull
    @Override
    public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
        return Arrays.<ViewManager>asList(
                new GuideViewManager(),
                new ParentGridViewManager(),
                new ProgramRowViewManager(),
                new ChannelViewManager(),
                new ChildGridViewManager(),
                new GridItemManager(),
                new ShowViewManager(),
                new TimelineViewManager()
        );
    }
}
