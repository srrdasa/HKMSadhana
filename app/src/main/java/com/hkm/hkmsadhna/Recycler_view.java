package com.hkm.hkmsadhna;

import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hkm.hkmsadhna.adapter.DerpAdapter;
import com.hkm.hkmsadhna.db.SadhnaDataSource;
import com.hkm.hkmsadhna.model.ListItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Recycler_view extends AppCompatActivity {


    String DB_NAME = "new.db";

    private DerpAdapter adapter;
    int date;
    protected SadhnaDataSource mDataSource;
    TextView id,maPoints,daPoints,sbPoints,jpPoints,summary;
    RecyclerView RecView;
    Typeface t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);


        Calendar calendar = Calendar.getInstance();
        date = calendar.get(Calendar.DATE);
        mDataSource = new SadhnaDataSource(Recycler_view.this,DB_NAME);

        RecView = (RecyclerView)findViewById(R.id.recycler_view);
        RecView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DerpAdapter(print(),this,t);
        RecView.setAdapter(adapter);
        id = (TextView)findViewById(R.id.id);
        id.setTypeface(t);
        maPoints = (TextView)findViewById(R.id.ma);
        maPoints.setTypeface(t);
        daPoints = (TextView)findViewById(R.id.da);
        daPoints.setTypeface(t);
        sbPoints = (TextView)findViewById(R.id.sb);
        sbPoints.setTypeface(t);
        jpPoints = (TextView)findViewById(R.id.jp);
        jpPoints.setTypeface(t);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        RecView = (RecyclerView)findViewById(R.id.recycler_view);
        RecView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DerpAdapter(print(),this,t);
        RecView.setAdapter(adapter);
    }

    public List<ListItem> print(){
        int z = 1;
        SadhnaDataSource mDataSource;
        List<ListItem> data = new ArrayList<>();
        mDataSource = new SadhnaDataSource(Recycler_view.this,DB_NAME);
        mDataSource.open();
        Log.v("Marks : "," | \t" + "Date" + " | \t" + "MA" + " | \t" + "DA" + " | \t" + "BG" + " | \t" + "JP" + " | \t" + "ISC" + " | ");
        Cursor cursor = mDataSource.getDataForCurruntMonth();
        Log.v("Cursor",cursor+"");
        cursor.moveToFirst();
        while (z <= date) {

            int i = cursor.getInt(0);
            int j = cursor.getInt(3);
            int k = cursor.getInt(4);
            int l = cursor.getInt(5);
            int m = cursor.getInt(6);
            int n = cursor.getInt(7);
            ListItem item = new ListItem();

            if (i == -1){
                item.setId("-1");
            }
            else
                item.setId(i+"");

            if (j == -1){
                item.setMaPoints("-1");
            }
            else
                item.setMaPoints(j+"");

            if (k == -1){
                item.setDaPoints("-1");
            }
            else
                item.setDaPoints(k+"");


            if (l == -1){
                item.setSbPoints("-1");
            }
            else
                item.setSbPoints(l+"");


            if (m == -1){
                item.setJpPoints("-1");
            }
            else
                item.setJpPoints(m+"");


            data.add(item);

            Log.v("Marks : "," | \t" + i + " | \t" + j + " | \t" + k + " | \t" + l + " | \t" + m + " | \t" + n + " | ");
            cursor.moveToNext();
            z++;
        }
        mDataSource.close();
        return data;
    }

    public void nav_back(View view) {
        onBackPressed();
    }
}
