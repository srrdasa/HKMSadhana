package com.hkm.hkmsadhna;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.hkm.hkmsadhna.db.MarksHelper;
import com.hkm.hkmsadhna.db.SadhnaDataSource;

public class EachDay extends AppCompatActivity {

    String DB_NAME = "new.db";

    protected SadhnaDataSource mDataSource;
    int[] fromSQL = new int[32];

    int date;
    LinearLayout ma_f,sb_f,da_f,ma_b;
    Switch ma_Switch,da_Switch,sb_Switch;
    TextView title_ma,title_sb,title_da,title_japa,just;
    CardView ma_Card,sb_Card,da_Card,japa_card;
    ImageView ma_f_full,ma_f_partial,ma_f_no,ma_b_sick,ma_b_os,ma_b_other,
            sb_f_no,sb_f_full,sb_f_partial,sb_b_os,sb_b_sick,sb_b_other,
            da_f_full,da_f_partial,da_f_no,da_b_os,da_b_sick,da_b_other,
            japa_one,japa_two,japa_three,japa_four,japa_five,
            imageView;
    static SQLiteDatabase mDatabase;
    public MarksHelper mMarksHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_day);
        Window window = this.getWindow();

        Intent mIntent = getIntent();
        date = mIntent.getIntExtra("date", 0);
        imageView = (ImageView)findViewById(R.id.isupdatedd);

        Log.v("Bundle :",date+"");



        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        print();
        printOnlyQuery();
        mDataSource = new SadhnaDataSource(EachDay.this,DB_NAME);
        ma_Card = (CardView)findViewById(R.id.ma_card_view);
        sb_Card = (CardView)findViewById(R.id.sb_card_view);
        da_Card = (CardView)findViewById(R.id.da_card_view);
        japa_card = (CardView)findViewById(R.id.japa_card_view);


        ma_Switch = (Switch) findViewById(R.id.switch1);
        da_Switch = (Switch) findViewById(R.id.switch2);
        sb_Switch = (Switch) findViewById(R.id.switch3);

        title_ma = (TextView)findViewById(R.id.title_ma);
        title_sb = (TextView)findViewById(R.id.title_sb);
        title_da = (TextView)findViewById(R.id.title_da);


        title_japa = (TextView)findViewById(R.id.title_japa);


//        Typeface tf = Typeface.createFromAsset(getAssets(), "abhi.ttf");
//        title_ma.setTypeface(tf);
//        title_sb.setTypeface(tf);
//        title_da.setTypeface(tf);
//        title_japa.setTypeface(tf);

        ma_b = (LinearLayout)findViewById(R.id.ma_b);
        ma_f = (LinearLayout)findViewById(R.id.ma_f);
        da_f = (LinearLayout)findViewById(R.id.da_f);
        sb_f = (LinearLayout)findViewById(R.id.sb_f);

        ma_f_full = (ImageView) findViewById(R.id.ma_f_full);
        ma_f_partial = (ImageView) findViewById(R.id.ma_f_partial);
        ma_f_no = (ImageView) findViewById(R.id.ma_f_no);
        ma_b_os = (ImageView) findViewById(R.id.ma_b_os);
        ma_b_sick = (ImageView) findViewById(R.id.ma_b_sick);
        ma_b_other = (ImageView) findViewById(R.id.ma_b_other);

        sb_f_no = (ImageView) findViewById(R.id.sb_f_no);
        sb_f_full = (ImageView) findViewById(R.id.sb_f_full);
        sb_f_partial = (ImageView) findViewById(R.id.sb_f_partial);
        sb_b_os = (ImageView) findViewById(R.id.sb_b_os);
        sb_b_sick = (ImageView) findViewById(R.id.sb_b_sick);
        sb_b_other = (ImageView) findViewById(R.id.sb_b_other);

        da_f_no = (ImageView) findViewById(R.id.da_f_no);
        da_f_full = (ImageView) findViewById(R.id.da_f_full);
        da_f_partial = (ImageView) findViewById(R.id.da_f_partial);
        da_b_os = (ImageView) findViewById(R.id.da_b_os);
        da_b_other = (ImageView) findViewById(R.id.da_b_other);
        da_b_sick = (ImageView) findViewById(R.id.da_b_sick);

        japa_one = (ImageView) findViewById(R.id.japa_one);
        japa_two = (ImageView) findViewById(R.id.japa_two);
        japa_three = (ImageView) findViewById(R.id.japa_three);
        japa_four = (ImageView) findViewById(R.id.japa_four);
        japa_five = (ImageView) findViewById(R.id.japa_five);


        ma_Switch.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (ma_Switch.isChecked()){
                    ma_f.setVisibility(View.GONE);
                }
                else {
                    ma_f.setVisibility(View.VISIBLE);
                }
            }
        });

        da_Switch.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (da_Switch.isChecked()){
                    da_f.setVisibility(View.GONE);
                }
                else {
                    da_f.setVisibility(View.VISIBLE);
                }
            }
        });

        sb_Switch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (sb_Switch.isChecked()) {
                    sb_f.setVisibility(View.GONE);
                }
                else {
                    sb_f.setVisibility(View.VISIBLE);
                }
            }
        });


//mangala aarti front button onCLick
        ma_f_no.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                removeCard(ma_Card);
                waitForOneSec(ma_Card);
                up_MA(0,date);print();printOnlyQuery();
//                mDataSource.open();
//                mDataSource.update_MA(3,30);
//                mDataSource.close();
//                print();printOnlyQuery();
            }
        });
        ma_f_full.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                removeCard(ma_Card);
                waitForOneSec(ma_Card);
                up_MA(100,date);print();printOnlyQuery();
            }
        });
        ma_f_partial.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                removeCard(ma_Card);
                waitForOneSec(ma_Card);
                up_MA(50,date);print();printOnlyQuery();
            }
        });

//mangala aarti back button onCLick
        ma_b_sick.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                removeCard(ma_Card);
                waitForOneSec(ma_Card);
                up_MA(8,date);print();printOnlyQuery();
            }
        });
        ma_b_os.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                removeCard(ma_Card);
                waitForOneSec(ma_Card);
                up_MA(9,date);print();printOnlyQuery();
            }
        });
        ma_b_other.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                removeCard(ma_Card);
                waitForOneSec(ma_Card);
                up_MA(10,date);print();printOnlyQuery();
            }
        });


//shreemad bhagvatam front button onCLick

        sb_f_no.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeCard(sb_Card);
                waitForOneSec(sb_Card);
                up_SB(0,date);print();printOnlyQuery();
            }
        });
        sb_f_full.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeCard(sb_Card);
                waitForOneSec(sb_Card);
                up_SB(100,date);print();printOnlyQuery();

            }
        });
        sb_f_partial.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeCard(sb_Card);
                waitForOneSec(sb_Card);
                up_SB(50,date);print();printOnlyQuery();

            }
        });

        //shreemad bhagvatam back button onCLick
        sb_b_other.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeCard(sb_Card);
                waitForOneSec(sb_Card);
                up_SB(10,date);print();printOnlyQuery();

            }
        });

        sb_b_sick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeCard(sb_Card);
                waitForOneSec(sb_Card);
                up_SB(8,date);print();printOnlyQuery();

            }
        });
        sb_b_os.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeCard(sb_Card);
                waitForOneSec(sb_Card);
                up_SB(9,date);print();printOnlyQuery();

            }
        });

        //darshna aarti front button onCLick
        da_f_no.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeCard(da_Card);
                waitForOneSec(da_Card);
                up_DA(0,date);print();printOnlyQuery();

            }
        });
        da_f_full.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeCard(da_Card);
                waitForOneSec(da_Card);
                up_DA(100,date);print();printOnlyQuery();

            }
        });
        da_f_partial.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeCard(da_Card);
                waitForOneSec(da_Card);
                up_DA(50,date);print();printOnlyQuery();

            }
        });

        //darshna aarti back button onCLick
        da_b_os.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeCard(da_Card);
                waitForOneSec(da_Card);
                up_DA(9,date);print();printOnlyQuery();

            }
        });
        da_b_other.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeCard(da_Card);
                waitForOneSec(da_Card);
                up_DA(10,date);print();printOnlyQuery();

            }
        });
        da_b_sick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeCard(da_Card);
                waitForOneSec(da_Card);
                up_DA(8,date);print();printOnlyQuery();

            }
        });

        japa_one.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeCard(japa_card);
                waitForOneSec(japa_card);
                up_JP(100,date);print();printOnlyQuery();
                printOnlyQuery();

            }
        });
        japa_two.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeCard(japa_card);
                waitForOneSec(japa_card);
                up_JP(75,date);print();printOnlyQuery();

            }
        });
        japa_three.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeCard(japa_card);
                waitForOneSec(japa_card);
                up_JP(50,date);print();printOnlyQuery();

            }
        });
        japa_four.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeCard(japa_card);
                waitForOneSec(japa_card);
                up_JP(25,date);print();printOnlyQuery();

            }
        });
        japa_five.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeCard(japa_card);
                waitForOneSec(japa_card);
                up_JP(0,date);print();
                printOnlyQuery();
            }
        });


        Toast.makeText(EachDay.this, "" + date,
                Toast.LENGTH_LONG).show();

        mDataSource = new SadhnaDataSource(EachDay.this,DB_NAME);
        mDataSource.open();
        mDataSource.insertOnlyFirstTime();
        showCards();

//        mDataSource.dropTable();
        print();
        printOnlyQuery();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            resett();
            print();
            return true;
        }else if (id == R.id.full){
            mDataSource.open();
            Cursor cursor = mDataSource.checkUpdate(date);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int i = cursor.getInt(0);
                int j = cursor.getInt(1);
                int k = cursor.getInt(2);
                int l = cursor.getInt(3);
                int m = cursor.getInt(4);
                int n = cursor.getInt(5);

                if (j == -1){
                    mDataSource.update_MA(100,date);
                }
                if (k == -1){
                    mDataSource.update_DA(100,date);
                }
                if (l == -1){
                    mDataSource.update_SB(100,date);
                }
                if (m == -1){
                    mDataSource.update_JP(100,date);
                }
                mDataSource.update_IS(1,date);
                cursor.moveToNext();
            }
            mDataSource.close();
            hideAllCards();
        }else if (id == R.id.half){
            mDataSource.open();
            Cursor cursor = mDataSource.checkUpdate(date);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int i = cursor.getInt(0);
                int j = cursor.getInt(1);
                int k = cursor.getInt(2);
                int l = cursor.getInt(3);
                int m = cursor.getInt(4);
                int n = cursor.getInt(5);

                if (j == -1){
                    mDataSource.update_MA(50,date);
                }
                if (k == -1){
                    mDataSource.update_DA(50,date);
                }
                if (l == -1){
                    mDataSource.update_SB(50,date);
                }
                if (m == -1){
                    mDataSource.update_JP(50,date);
                }
                mDataSource.update_IS(1,date);
                cursor.moveToNext();
            }
            mDataSource.close();
            hideAllCards();
        }else if (id == R.id.no){
            mDataSource.open();
            Cursor cursor = mDataSource.checkUpdate(date);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int i = cursor.getInt(0);
                int j = cursor.getInt(1);
                int k = cursor.getInt(2);
                int l = cursor.getInt(3);
                int m = cursor.getInt(4);
                int n = cursor.getInt(5);

                if (j == -1){
                    mDataSource.update_MA(0,date);
                }
                if (k == -1){
                    mDataSource.update_DA(0,date);
                }
                if (l == -1){
                    mDataSource.update_SB(0,date);
                }
                if (m == -1){
                    mDataSource.update_JP(0,date);
                }
                mDataSource.update_IS(1,date);
                cursor.moveToNext();
            }
            mDataSource.close();
            hideAllCards();
        }else if (id == R.id.sick){
            mDataSource.open();
            Cursor cursor = mDataSource.checkUpdate(date);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int i = cursor.getInt(0);
                int j = cursor.getInt(1);
                int k = cursor.getInt(2);
                int l = cursor.getInt(3);
                int m = cursor.getInt(4);
                int n = cursor.getInt(5);

                if (j == -1){
                    mDataSource.update_MA(8,date);
                }
                if (k == -1){
                    mDataSource.update_DA(8,date);
                }
                if (l == -1){
                    mDataSource.update_SB(8,date);
                }
                if (m == -1){
                    mDataSource.update_JP(8,date);
                }
                mDataSource.update_IS(1,date);
                cursor.moveToNext();
            }
            mDataSource.close();
            hideAllCards();
        }else if (id == R.id.others){
            mDataSource.open();
            Cursor cursor = mDataSource.checkUpdate(date);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int i = cursor.getInt(0);
                int j = cursor.getInt(1);
                int k = cursor.getInt(2);
                int l = cursor.getInt(3);
                int m = cursor.getInt(4);
                int n = cursor.getInt(5);

                if (j == -1){
                    mDataSource.update_MA(10,date);
                }
                if (k == -1){
                    mDataSource.update_DA(10,date);
                }
                if (l == -1){
                    mDataSource.update_SB(10,date);
                }
                if (m == -1){
                    mDataSource.update_JP(10,date);
                }
                mDataSource.update_IS(1,date);
                cursor.moveToNext();
            }
            mDataSource.close();
            hideAllCards();
        }else if (id == R.id.os){
            mDataSource.open();
            Cursor cursor = mDataSource.checkUpdate(date);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int i = cursor.getInt(0);
                int j = cursor.getInt(1);
                int k = cursor.getInt(2);
                int l = cursor.getInt(3);
                int m = cursor.getInt(4);
                int n = cursor.getInt(5);

                if (j == -1){
                    mDataSource.update_MA(9,date);
                }
                if (k == -1){
                    mDataSource.update_DA(9,date);
                }
                if (l == -1){
                    mDataSource.update_SB(9,date);
                }
                if (m == -1){
                    mDataSource.update_JP(9,date);
                }
                mDataSource.update_IS(1,date);
                cursor.moveToNext();
            }
            mDataSource.close();
            hideAllCards();
        }
        return super.onOptionsItemSelected(item);
    }

    void removeCard(CardView c){
        c.animate().scaleX(0).scaleY(0).start();

    }

    void waitForOneSec(final CardView c){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                c.setVisibility(View.GONE);
            }
        }, 400);
    }

    void print(){
        mDataSource = new SadhnaDataSource(EachDay.this,DB_NAME);
        mDataSource.open();
        Log.v("MarksFROMEachDay : "," | \t" + "Date" + " | \t" + "MA" + " | \t" + "DA" + " | \t" + "BG" + " | \t" + "JP" + " | \t" + "ISC" + " | ");
        Cursor cursor = mDataSource.readData();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int a = cursor.getColumnIndex(MarksHelper.COLUMN_DATE);
            int b = cursor.getColumnIndex(MarksHelper.COLUMN_MA);
            int c = cursor.getColumnIndex(MarksHelper.COLUMN_DA);
            int d = cursor.getColumnIndex(MarksHelper.COLUMN_BG);
            int e = cursor.getColumnIndex(MarksHelper.COLUMN_JP);
            int f = cursor.getColumnIndex(MarksHelper.COLUMN_ISCOMPLETED);

            int i = cursor.getInt(0);
            int j = cursor.getInt(1);
            int k = cursor.getInt(2);
            int l = cursor.getInt(3);
            int m = cursor.getInt(4);
            int n = cursor.getInt(5);


            Log.v("Marks : "," | \t" + i + " | \t" + j + " | \t" + k + " | \t" + l + " | \t" + m + " | \t" + n + " | ");
            cursor.moveToNext();
        }
        mDataSource.close();
    }

    void hideAllCards(){
        ma_Card.setVisibility(View.GONE);
        ma_Card.animate().scaleX(0).scaleY(0).start();
        da_Card.setVisibility(View.GONE);
        da_Card.animate().scaleX(0).scaleY(0).start();
        sb_Card.setVisibility(View.GONE);
        sb_Card.animate().scaleX(0).scaleY(0).start();
        japa_card.setVisibility(View.GONE);
        japa_card.animate().scaleX(0).scaleY(0).start();
        imageView.setVisibility(View.GONE);
    }
    public void resett(){
        mDataSource.open();
        mDataSource.update_JP(-1,date);
        mDataSource.update_MA(-1,date);
        mDataSource.update_SB(-1,date);
        mDataSource.update_DA(-1,date);
        mDataSource.update_IS(-1,date);
        mDataSource.close();

        ma_Card.setVisibility(View.VISIBLE);
        ma_Card.animate().scaleX(1).scaleY(1).start();
        da_Card.setVisibility(View.VISIBLE);
        da_Card.animate().scaleX(1).scaleY(1).start();
        sb_Card.setVisibility(View.VISIBLE);
        sb_Card.animate().scaleX(1).scaleY(1).start();
        japa_card.setVisibility(View.VISIBLE);
        japa_card.animate().scaleX(1).scaleY(1).start();
        imageView.setVisibility(View.GONE);
        print();
    }

    void printOnlyQuery(){
        mDataSource = new SadhnaDataSource(EachDay.this,DB_NAME);
        mDataSource.open();
        Log.v("MarksQuery : "," | \t" + "Date" + " | \t" + "MA" + " | \t" + "DA" + " | \t" + "BG" + " | \t" + "JP" + " | \t" + "ISC" + " | ");
        Cursor cursor = mDataSource.checkUpdate(date);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int i = cursor.getInt(0);
            int j = cursor.getInt(1);
            int k = cursor.getInt(2);
            int l = cursor.getInt(3);
            int m = cursor.getInt(4);
            int n = cursor.getInt(5);

            if (j != -1 && k != -1 && l != -1 && m != -1){
                up_IS(1,date);
                imageView.setVisibility(View.VISIBLE);
                print();
//                imageView.setVisibility(View.VISIBLE);
            }

            Log.v("Marks : "," | \t" + i + " | \t" + j + " | \t" + k + " | \t" + l + " | \t" + m + " | \t" + n + " | ");
            cursor.moveToNext();
        }
        mDataSource.close();
    }

    void showCards(){
        mDataSource = new SadhnaDataSource(EachDay.this,DB_NAME);
        mDataSource.open();
        Log.v("MarksQuery : "," | \t" + "Date" + " | \t" + "MA" + " | \t" + "DA" + " | \t" + "BG" + " | \t" + "JP" + " | \t" + "ISC" + " | ");
        Cursor cursor = mDataSource.checkUpdate(date);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int i = cursor.getInt(0);
            int j = cursor.getInt(1);
            int k = cursor.getInt(2);
            int l = cursor.getInt(3);
            int m = cursor.getInt(4);
            int n = cursor.getInt(5);

            if (j != -1 && k != -1 && l != -1 && m != -1){
                up_IS(1,date);
                print();

            }
            if (n == 1){
                imageView.setImageResource(R.drawable.isupdated);
                imageView.setVisibility(View.VISIBLE);
            }

            if (j == -1){
                ma_Card.setVisibility(View.VISIBLE);
            }

            if (k == -1){
                da_Card.setVisibility(View.VISIBLE);
            }

            if (l == -1){
                sb_Card.setVisibility(View.VISIBLE);
            }

            if (m == -1){
                japa_card.setVisibility(View.VISIBLE);
            }

            Log.v("Marks : "," | \t" + i + " | \t" + j + " | \t" + k + " | \t" + l + " | \t" + m + " | \t" + n + " | ");
            cursor.moveToNext();
        }
        mDataSource.close();
    }

    void up_MA(int value, int id){
        mDataSource = new SadhnaDataSource(EachDay.this,DB_NAME);
        mDataSource.open();
        mDataSource.update_MA(value,id);
        mDataSource.close();
    }
    void up_DA(int value, int id){
        mDataSource = new SadhnaDataSource(EachDay.this,DB_NAME);
        mDataSource.open();
        mDataSource.update_DA(value,id);
        mDataSource.close();
    }
    void up_SB(int value, int id){
        mDataSource = new SadhnaDataSource(EachDay.this,DB_NAME);
        mDataSource.open();
        mDataSource.update_SB(value,id);
        mDataSource.close();
    }
    void up_JP(int value, int id){
        mDataSource = new SadhnaDataSource(EachDay.this,DB_NAME);
        mDataSource.open();
        mDataSource.update_JP(value,id);
        mDataSource.close();
    }
    void up_IS(int value, int id){
        mDataSource = new SadhnaDataSource(EachDay.this,DB_NAME);
        mDataSource.open();
        mDataSource.update_IS(value,id);
        mDataSource.close();
    }

    public void resett(View view){
        mDataSource.open();
        mDataSource.update_JP(-1,date);
        mDataSource.update_MA(-1,date);
        mDataSource.update_SB(-1,date);
        mDataSource.update_DA(-1,date);
        mDataSource.update_IS(-1,date);
        mDataSource.close();

        ma_Card.setVisibility(View.VISIBLE);
        ma_Card.animate().scaleX(1).scaleY(1).start();
        da_Card.setVisibility(View.VISIBLE);
        da_Card.animate().scaleX(1).scaleY(1).start();
        sb_Card.setVisibility(View.VISIBLE);
        sb_Card.animate().scaleX(1).scaleY(1).start();
        japa_card.setVisibility(View.VISIBLE);
        japa_card.animate().scaleX(1).scaleY(1).start();
        imageView.setVisibility(View.GONE);

        print();
    }

    public void nav_back(View view) {
        onBackPressed();
    }
    
}
