package com.grv_app;

//import com.facebook.react.ReactActivity;
//
//public class MainActivity extends ReactActivity {
//
//    /**
//     * Returns the name of the main component registered from JavaScript.
//     * This is used to schedule rendering of the component.
//     */
//    @Override
//    protected String getMainComponentName() {
//        return "grv_app";
//    }
//}

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    final Context context = this;
    LinearLayout rootView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();
    }

    public void init() {
        rootView = findViewById(R.id.rootView);
        ArrayList shows = new ArrayList<String>();
        HashMap data = new HashMap();

        for (int i = 1; i <= 100; i++) {
            shows.add("Show" + i);
        }

        for (int i = 1; i <= 100; i++) {
            data.put("Channel " + i, shows);
        }
        GuideGridView parentRecyclerView = new GuideGridView(context);
        GuideGridViewAdapter programGuideGridViewAdapter = new GuideGridViewAdapter(data);
        parentRecyclerView.setAdapter(programGuideGridViewAdapter);
        programGuideGridViewAdapter.notifyDataSetChanged();
        rootView.addView(parentRecyclerView);
    }
}