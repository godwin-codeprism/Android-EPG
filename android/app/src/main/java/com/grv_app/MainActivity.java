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
import android.view.KeyEvent;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.grv_app.utils.ChildFocusListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements ChildFocusListener {
    final Context context = this;
    LinearLayout rootView;
    GuideGridView parentRecyclerView;
    int direction = 0;
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
        parentRecyclerView = new GuideGridView(context);
        GuideGridViewAdapter programGuideGridViewAdapter = new GuideGridViewAdapter(data,this);
        parentRecyclerView.setAdapter(programGuideGridViewAdapter);
        programGuideGridViewAdapter.notifyDataSetChanged();
        rootView.addView(parentRecyclerView);
    }

    @Override
    public void onChildScrolled(int amountOfScroll,RecyclerView childRecyclerView) {
        if(parentRecyclerView != null){

            for (int i= 0; i < parentRecyclerView.getChildCount();i++){
                RecyclerView recyclerView = (RecyclerView) parentRecyclerView.getChildAt(i);
                if(direction == -1){
                    recyclerView.smoothScrollBy(-amountOfScroll,0);
                    childRecyclerView.smoothScrollBy(-amountOfScroll,0);
                    //parentRecyclerView.getChildAt(0).scrollBy(-amountOfScroll,0);
                }else if(direction == 1) {
                    recyclerView.smoothScrollBy(amountOfScroll,0);
                    childRecyclerView.smoothScrollBy(amountOfScroll,0);
                    // parentRecyclerView.getChildAt(0).scrollBy(amountOfScroll,0);
                }
            }

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case 21:
                direction = -1;
                return false;
            case 22:
                direction = 1;
                return false;
            default:
                return false;
        }
    }
}