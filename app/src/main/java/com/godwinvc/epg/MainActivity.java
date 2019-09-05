package com.godwinvc.epg;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.godwinvc.epg.cell.EPGCell;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        RecyclerView childScroll = findViewById(R.id.childScroll);
//        childScroll.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ArrayList<String> shows = new ArrayList<String>();
        LinearLayout channelList = findViewById(R.id.channelList);
        LinearLayout childList = findViewById(R.id.childList);
        for (int i = 1; i < 100; i++) {
            TextView channelName = new TextView(context);
            channelName.setBackgroundColor(Color.DKGRAY);
            channelName.setTextColor(Color.WHITE);
            channelName.setHeight(96);
            channelName.setWidth(270);
            channelName.setGravity(Gravity.CENTER);
            channelName.setText("Ch" + i);
            channelList.addView(channelName);
            shows.add("Ch" + i);
            LinearLayout innerRail = new LinearLayout(context);
            innerRail.setOrientation(LinearLayout.HORIZONTAL);
            for(int j=1; j <= 100; j++){
                EPGCell epgCell = new EPGCell(context);
                epgCell.setShowName("Ch" + i + " S" + j);
                innerRail.addView(epgCell);
            }
            childList.addView(innerRail);
        }
//        childScroll.setAdapter(new ChildScrollAdapter(shows));
    }

}

//class ChildScrollAdapter extends RecyclerView.Adapter<ChildScrollAdapter.ChildScrollHolder> {
//    private ArrayList<String> data;
//    private  Context parentContext;
//    public ChildScrollAdapter(ArrayList<String> data) {
//        this.data = data;
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
//    @NonNull
//    @Override
//    public ChildScrollHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        parentContext = parent.getContext();
////        LinearLayout innerRail = new LinearLayout(parent.getContext());
////        innerRail.setOrientation(LinearLayout.HORIZONTAL);
////        return new ChildScrollHolder(innerRail);
//        LayoutInflater inflater = LayoutInflater.from(parentContext);
//        View view = inflater.inflate(R.layout.inner_rail, parent, false);
//        return new ChildScrollHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ChildScrollHolder holder, int position) {
////        for (int j = 1; j <= 100; j++) {
////            EPGCell epgCell = new EPGCell(parentContext);
////            epgCell.setShowName(data.get(position) + " S" + j);
////            holder.innerRail.addView(epgCell);
////        }
//        EPGCell epgCell = new EPGCell(parentContext);
//        epgCell.setShowName(data.get(position));
//        holder.innerRail.addView(epgCell);
//    }
//
//    @Override
//    public int getItemCount() {
//        return data.size();
//    }
//
//    public class ChildScrollHolder extends RecyclerView.ViewHolder {
//        LinearLayout innerRail;
//        public ChildScrollHolder(@NonNull View itemView) {
//            super(itemView);
//            innerRail = itemView.findViewById(R.id.innerRail);
//        }
//    }
//}
