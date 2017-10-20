package com.hkm.hkmsadhna;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hkm.hkmsadhna.db.MarksHelper;
import com.hkm.hkmsadhna.db.SadhnaDataSource;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    int totalMA=0,totleJP=0,totalDA=0,totleBG=0;
    String DB_NAME = "new.db";
    protected SadhnaDataSource mDataSource;
    int[] fromSQL = new int[100];
    String maFrom,maTo,sbFrom,sbTo,daFrom,daTo,firstTimeinMainActivity;
    int date,hour,minuit,month,year;
    LinearLayout ma_f,sb_f,da_f,ma_b;
    Switch ma_Switch,da_Switch,sb_Switch;
    TextView title_ma,title_sb,title_da,title_japa,textView,dateTxt;
    CardView ma_Card,sb_Card,da_Card,japa_card;
    ImageView ma_f_full,ma_f_partial,ma_f_no,ma_b_sick,ma_b_os,ma_b_other,
            sb_f_no,sb_f_full,sb_f_partial,sb_b_os,sb_b_sick,sb_b_other,
            da_f_full,da_f_partial,da_f_no,da_b_os,da_b_sick,da_b_other,
            japa_one,japa_two,japa_three,japa_four,japa_five,
            img,isupdated,
            punch_ma,punch_da,punch_sb;
    int reportTableId;
    String convertedEmail;
    boolean b;
    ProgressDialog dialog;
    static SQLiteDatabase mDatabase;
    public MarksHelper mMarksHelper;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authlistner;

    FirebaseDatabase database;
    DatabaseReference myRef,mUpload,mcheckForExistence;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Please wait");
        String[] MonArray = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SUP", "OCT", "NOV", "DEC"};
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        dateTxt = (TextView)findViewById(R.id.dateText);

        firebaseAuth = FirebaseAuth.getInstance();
        Calendar calendar = Calendar.getInstance();
        date = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        Log.v("Month",month+"");
        Log.v("year",year+"");
        SharedPreferences settings = getSharedPreferences("Pre", MODE_PRIVATE);
        String value = settings.getString("email", "");
        convertedEmail = settings.getString("convertedEmail", "");
        firstTimeinMainActivity = settings.getString("fima", "");

        myRef = FirebaseDatabase.getInstance().getReference().child("UserData").child(year+"").child("9").child(convertedEmail);
        mcheckForExistence = FirebaseDatabase.getInstance().getReference().child("UserData").child(year+"").child(month+"");

        reportTableId = generateIdForTableReport();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        database = FirebaseDatabase.getInstance();


String s = String.valueOf(year);


        authlistner = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                if (firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(MainActivity.this,Login.class));
                }
            }
        };

        img = (ImageView)findViewById(R.id.img);
        isupdated = (ImageView)findViewById(R.id.isupdated);

        checkForAutomaticDate();


//        UploadDatatoFirebase();


        punch_da = (ImageView)findViewById(R.id.punch_da);
        punch_ma = (ImageView)findViewById(R.id.punch_ma);
        punch_sb = (ImageView)findViewById(R.id.punch_sb);

        print();
        printOnlyQuery();
        mDataSource = new SadhnaDataSource(MainActivity.this,DB_NAME);
        calendar = Calendar.getInstance();
        date = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minuit = calendar.get(Calendar.MINUTE);

        dateTxt.setText(date + " - " + MonArray[month] + " - " + year);

        Log.v("Cal : ","Hour : "+hour);
        Log.v("Cal : ","Minuit : "+minuit);

        ma_Card = (CardView)findViewById(R.id.ma_card_view);
        sb_Card = (CardView)findViewById(R.id.sb_card_view);
        da_Card = (CardView)findViewById(R.id.da_card_view);
        japa_card = (CardView)findViewById(R.id.japa_card_view);

        ma_Switch = (Switch) findViewById(R.id.switch1);
        da_Switch = (Switch) findViewById(R.id.switch2);
        sb_Switch = (Switch) findViewById(R.id.switch3);

        title_ma = (TextView)findViewById(R.id.title_ma);
        textView = (TextView)findViewById(R.id.textView);
        title_sb = (TextView)findViewById(R.id.title_sb);
        title_da = (TextView)findViewById(R.id.title_da);
        title_japa = (TextView)findViewById(R.id.title_japa);

        Log.v("Email",value);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
/*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
       TextView textVieww = (TextView)header.findViewById(R.id.textView);
        textVieww.setText(value);

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
                Log.v("clickk","mangala");
            }
        });
        ma_f_partial.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                removeCard(ma_Card);
                waitForOneSec(ma_Card);
                up_MA(50,date);
                print();
                printOnlyQuery();
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
                up_JP(75,date);
                print();
                printOnlyQuery();

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

        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, Calender.class);
                Log.v("NEW INTENT","MainActivity.this, MainActivity.class");
                startActivity(myIntent);
            }
        });

//        Toast.makeText(MainActivity.this, "" + date,
//                Toast.LENGTH_LONG).show();

        mDataSource = new SadhnaDataSource(MainActivity.this,DB_NAME);
        mDataSource.open();
        mDataSource.insertOnlyFirstTime();
        showCards();

//        mDataSource.dropTable();


        if (firstTimeinMainActivity == ""){
            Log.v("Insidee","Inside");
            checkUserDatabaseExistOrNot();
        }
        else {

        }

        print();
        printOnlyQuery();
        uncompleteDaysOfSadhna();
        
        
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        this.finishAffinity();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authlistner);
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       if (id == R.id.nav_gallery) {

            startActivity(new Intent(MainActivity.this,Calender.class));

        } else if (id == R.id.nav_slideshow) {
            Intent myIntent = new Intent(MainActivity.this, SettingsActivity.class);
            Log.v("NEW INTENT","DataUpdate.this, MainActivity.class");
            startActivity(myIntent);

        } else if (id == R.id.nav_share) {
            startActivity(new Intent(MainActivity.this,Recycler_view.class));


        }else if (id == R.id.nav_upload) {
           UploadDatatoFirebase();
       }
       else if (id == R.id.nav_summary) {
            startActivity(new Intent(MainActivity.this,Summary.class));
        }
        else if (id == R.id.nav_logout) {

           if (isNetworkConnected()){
           SharedPreferences settings = getSharedPreferences("Pre", MODE_PRIVATE);
           // Writing data to SharedPreferences
           SharedPreferences.Editor editor = settings.edit();
           editor.putString("fima", "");
           editor.putString("flag", "");
           editor.putString("email", "");
           editor.putString("convertedEmail", "");
           editor.commit();
               UploadDatatoFirebase();
            firebaseAuth.signOut();
           }
           else {
               Toast.makeText(MainActivity.this,"No internet connection",Toast.LENGTH_SHORT).show();
           }
       }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    void checkForAutomaticDate(){
        boolean s = isTimeAutomatic(MainActivity.this);
        Log.v("Setting",s+"");
        if (s == false) {
            Toast.makeText(MainActivity.this, "Select Automatic Date And Time", Toast.LENGTH_LONG).show();
            startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        checkForAutomaticDate();
        Calendar calendar = Calendar.getInstance();
        date = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minuit = calendar.get(Calendar.MINUTE);
        uncompleteDaysOfSadhna();
        showCards();
    }

    void removeCard(CardView c){
        c.animate().scaleX(0).scaleY(0).start();

    }

    void removeCard(ImageView i){
        i.animate().scaleX(0).scaleY(0).start();

    }

    void waitForOneSec(final CardView c){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                c.setVisibility(View.GONE);
            }
        }, 600);
    }

    void waitForOneSec(final ImageView i){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                i.setVisibility(View.GONE);
            }
        }, 400);
    }

    void print(){
        mDataSource = new SadhnaDataSource(MainActivity.this,DB_NAME);
        mDataSource.open();
        Log.v("Marks : "," | \t" + "Date" + " | \t" + "MA" + " | \t" + "DA" + " | \t" + "BG" + " | \t" + "JP" + " | \t" + "ISC" + " | ");
        Cursor cursor = mDataSource.readDataFromReport();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            int i = cursor.getInt(0);
            int j = cursor.getInt(1);
            int k = cursor.getInt(2);
            int l = cursor.getInt(3);
            int m = cursor.getInt(4);
            int n = cursor.getInt(5);

            Log.v("MarksFROMRDATAUPDATE : "," | \t" + i + " | \t" + j + " | \t" + k + " | \t" + l + " | \t" + m + " | \t" + n + " | ");
            cursor.moveToNext();
        }
        mDataSource.close();
    }

    void printNew(){
        mDataSource = new SadhnaDataSource(MainActivity.this,DB_NAME);
        mDataSource.open();
        Log.v("Marks : "," | \t" + "Date" + " | \t" + "MA" + " | \t" + "DA" + " | \t" + "BG" + " | \t" + "JP" + " | \t" + "ISC" + " | ");
        Cursor cursor = mDataSource.readData();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            int i = cursor.getInt(0);
            int j = cursor.getInt(1);
            int k = cursor.getInt(2);
            int l = cursor.getInt(3);
            int m = cursor.getInt(4);
            int n = cursor.getInt(5);

            Log.v("MarksFROMRDATAUPDATE : "," | \t" + i + " | \t" + j + " | \t" + k + " | \t" + l + " | \t" + m + " | \t" + n + " | ");
            cursor.moveToNext();
        }
        mDataSource.close();
    }


    void UploadDatatoFirebase(){
        dialog.show();
        //TODO : insted of abhiramani3@gmail take value of convertedEmail from sharedPreference and replace
        mUpload = FirebaseDatabase.getInstance().getReference().child("UserData").child(year+"").child(month+"").child(convertedEmail);
        mDataSource = new SadhnaDataSource(MainActivity.this,DB_NAME);
        mDataSource.open();
        Log.v("Marks : "," | \t" + "Date" + " | \t" + "MA" + " | \t" + "DA" + " | \t" + "BG" + " | \t" + "JP" + " | \t" + "ISC" + " | ");
        Cursor cursor = mDataSource.readData();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            int i = cursor.getInt(0);
            int j = cursor.getInt(1);
            int k = cursor.getInt(2);
            int l = cursor.getInt(3);
            int m = cursor.getInt(4);
            int n = cursor.getInt(5);

            mUpload.child(i+"").child("MA").setValue(j);
            mUpload.child(i+"").child("DA").setValue(k);
            mUpload.child(i+"").child("BG").setValue(l);
            mUpload.child(i+"").child("JP").setValue(m);


            Log.v("MarksFROM DATAUPDATE : "," | \t" + i + " | \t" + j + " | \t" + k + " | \t" + l + " | \t" + m + " | \t" + n + " | ");
            cursor.moveToNext();
        }

     DatabaseReference mUploadReport = FirebaseDatabase.getInstance().getReference().child("Report").child(convertedEmail);


        Cursor cursor2 = mDataSource.readDataFromReport();
        cursor2.moveToFirst();
        while (!cursor2.isAfterLast()) {

            int i = cursor2.getInt(0);
            int j = cursor2.getInt(1);
            int k = cursor2.getInt(2);
            int l = cursor2.getInt(3);
            int m = cursor2.getInt(4);
            int n = cursor2.getInt(5);

            mUploadReport.child(reportTableId+"").child("MA").setValue(j);
            mUploadReport.child(reportTableId+"").child("DA").setValue(k);
            mUploadReport.child(reportTableId+"").child("BG").setValue(l);
            mUploadReport.child(reportTableId+"").child("JP").setValue(m);
            mUploadReport.child(reportTableId+"").child("Totle").setValue(n);


            Log.v("MarksFROM DATAUPDATE : "," | \t" + i + " | \t" + j + " | \t" + k + " | \t" + l + " | \t" + m + " | \t" + n + " | ");
            cursor2.moveToNext();
        }

        mDataSource.close();
        dialog.dismiss();
    }

    void printOnlyQuery(){
        int ii = 1;
        totalMA=0;totleJP=0;totalDA=0;totleBG=0;
        mDataSource = new SadhnaDataSource(MainActivity.this,DB_NAME);
        mDataSource.open();
        Log.v("MarksQuery : "," | \t" + "Date" + " | \t" + "MA" + " | \t" + "DA" + " | \t" + "BG" + " | \t" + "JP" + " | \t" + "ISC" + " | ");
        Cursor cursor = mDataSource.readData();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int i = cursor.getInt(0);
            int j = cursor.getInt(1);
            int k = cursor.getInt(2);
            int l = cursor.getInt(3);
            int m = cursor.getInt(4);
            int n = cursor.getInt(5);

            Log.v("Test",j+"");
            Log.v("Test",m+"");
            Log.v("Test",k+"");
            Log.v("Test",l+"");
            Log.v("TblReporttt:",totalMA+"");

            totalMA = totalMA + j;
            totleJP = totleJP + m;
            totalDA = totalDA + k;
            totleBG = totleBG + l;

            if (j != -1 && k != -1 && l != -1 && m != -1){
                up_IS(1,date);
Log.v("hamkk","hmkk");
                print();
//                isupdated.setVisibility(View.VISIBLE);
//                imageView.setVisibility(View.VISIBLE);
            }

            Log.v("PrintOnly : "," | \t" + i + " | \t" + j + " | \t" + k + " | \t" + l + " | \t" + m + " | \t" + n + " | ");
            cursor.moveToNext();
            if (ii == date){
                break;
            }
            ii++;
        }
        Calendar calendar = Calendar.getInstance();
        date = calendar.get(Calendar.DATE);
        Log.v("TblReportt:",totalMA+"");

        totleBG = totleBG / date;
        totleJP = totleJP / date;
        totalMA = totalMA / date;
        totalDA = totalDA / date;
        int totle = (totleBG + totalMA + totalDA + totleJP) / 4;

        Log.v("TblReport:",reportTableId+"");
        Log.v("TblReport:",totalMA+"");
        Log.v("TblReport:",totalDA+"");
        Log.v("TblReport:",totleBG+"");
        Log.v("TblReport:",totleJP+"");
        Log.v("TblReport:",totle+"");

        updateReportTable(reportTableId,totalMA,totalDA,totleBG,totleJP,totle);
        
        Log.v("Report",totalDA+"");
        Log.v("Report",totalMA+"");
        Log.v("Report",totleBG+"");
        Log.v("Report",totleJP+"");

        mDataSource.close();
    }

    private void updateReportTable(int id,int ma,int da,int sb,int jp,int totle) {
        Log.v("idForReport",id+"");

        mDataSource = new SadhnaDataSource(MainActivity.this,DB_NAME);
        mDataSource.open();
        mDataSource.insertOnlyFirstTimeInReport(id,ma,da,sb,jp,totle);
        mDataSource.close();
    }

    private int generateIdForTableReport() {
        if (month > 9){
            year = year * 100;
        }
        else {
            year = year * 10;
        }
        int i = year + month;
        Log.v("idForReport",i+"");

        Toast.makeText(MainActivity.this, "SpecialId" + i, Toast.LENGTH_LONG).show();
        return i;
    }

    void showCards(){
        mDataSource = new SadhnaDataSource(MainActivity.this,DB_NAME);
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

            if (n == 1){
                Log.v("Hmm","Hmm");
                isupdated.setVisibility(View.VISIBLE);
            }

            if (j != -1 && k != -1 && l != -1 && m != -1){
                up_IS(1,date);
                isupdated.setVisibility(View.VISIBLE);
                print();
            }
            else {
                isupdated.setVisibility(View.GONE);
            }

            if (j == -1){
                ma_Card.setVisibility(View.VISIBLE);
            }
            else {
                ma_Card.setVisibility(View.GONE);
            }

            if (k == -1){
                da_Card.setVisibility(View.VISIBLE);
            }
            else {
                da_Card.setVisibility(View.GONE);
            }

            if (l == -1){
                sb_Card.setVisibility(View.VISIBLE);
            }
            else {
                sb_Card.setVisibility(View.GONE);
            }

            if (m == -1){
                japa_card.setVisibility(View.VISIBLE);
            }
            else {
                japa_card.setVisibility(View.GONE);
            }

            readTime();
            Log.v("between","From : "+getMA_FROM_Hour(maFrom) + " : " + getMA_FROM_Minuit(maFrom));
            Log.v("between","Now  : "+hour + " : " + minuit);
            Log.v("between","To   : "+getMA_TO_Hour(maTo) + " : " + getMA_TO_Minuit(maTo));

            if (j==-1){
                if (isInBetweenOrNot(getMA_FROM_Hour(maFrom),getMA_FROM_Minuit(maFrom),hour,minuit,getMA_TO_Hour(maTo),getMA_TO_Minuit(maTo))){
                    Log.v("between","Working");
                    punch_ma.animate().scaleX(0).scaleY(0).start();
                    punch_ma.setVisibility(View.VISIBLE);
                    punch_ma.setVisibility(View.VISIBLE);
                    punch_ma.animate().scaleX(1).scaleY(1).start();
                }
                else {
                    Log.v("between","Not Workning");
                    punch_ma.setVisibility(View.GONE);
                }
            }


            if (k==-1) {
                Log.v("betweenDA","From : "+getDA_FROM_Hour(daFrom) + " : " + getDA_FROM_Minuit(daFrom));
                Log.v("betweenDA","Now  : "+hour + " : " + minuit);
                Log.v("betweenDA","To   : "+getDA_TO_Hour(daTo) + " : " + getDA_TO_Minuit(daTo));
                Log.v("betweenDAInside", "Working");

                if (isInBetweenOrNot(getDA_FROM_Hour(daFrom), getDA_FROM_Minuit(daFrom), hour, minuit, getDA_TO_Hour(daTo), getDA_TO_Minuit(daTo))) {
                    Log.v("betweenDA", "Working");
                    punch_da.animate().scaleX(0).scaleY(0).start();
                    punch_da.setVisibility(View.VISIBLE);
                    punch_da.setVisibility(View.VISIBLE);
                    punch_da.animate().scaleX(1).scaleY(1).start();
                } else {
                    Log.v("betweenDA", "Not Workning");
                    punch_da.setVisibility(View.GONE);

                }
            }

            if (l==-1) {

                if (isInBetweenOrNot(getSB_FROM_Hour(sbFrom), getSB_FROM_Minuit(sbFrom), hour, minuit, getSB_TO_Hour(sbTo), getSB_TO_Minuit(sbTo))) {
                    Log.v("between", "Working");
                    punch_sb.animate().scaleX(0).scaleY(0).start();
                    punch_sb.setVisibility(View.VISIBLE);
                    punch_sb.setVisibility(View.VISIBLE);
                    punch_sb.animate().scaleX(1).scaleY(1).start();
                } else {
                    Log.v("between", "Not Workning");
                    punch_sb.setVisibility(View.GONE);
                }
            }


            Log.v("MarksFromSHowCard : "," | \t" + i + " | \t" + j + " | \t" + k + " | \t" + l + " | \t" + m + " | \t" + n + " | ");
            cursor.moveToNext();
        }
        mDataSource.close();

        readTime();
    }

    void up_MA(int value, int id){
        mDataSource = new SadhnaDataSource(MainActivity.this,DB_NAME);
        mDataSource.open();
        mDataSource.update_MA(value,id);
        mDataSource.close();

    }
    void up_DA(int value, int id){
        mDataSource = new SadhnaDataSource(MainActivity.this,DB_NAME);
        mDataSource.open();
        mDataSource.update_DA(value,id);
        mDataSource.close();
    }
    void up_SB(int value, int id){
        mDataSource = new SadhnaDataSource(MainActivity.this,DB_NAME);
        mDataSource.open();
        mDataSource.update_SB(value,id);
        mDataSource.close();
    }
    void up_JP(int value, int id){
        mDataSource = new SadhnaDataSource(MainActivity.this,DB_NAME);
        mDataSource.open();
        mDataSource.update_JP(value,id);
        mDataSource.close();
    }
    void up_IS(int value, int id){
        mDataSource = new SadhnaDataSource(MainActivity.this,DB_NAME);
        mDataSource.open();
        mDataSource.update_IS(value,id);
        mDataSource.close();
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
        isupdated.setVisibility(View.GONE);
        print();
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
        isupdated.setVisibility(View.GONE);
    }


    void uncompleteDaysOfSadhna(){
        mDataSource = new SadhnaDataSource(MainActivity.this,DB_NAME);
        mDataSource.open();
        Log.v("Marks : "," | \t" + "Date" + " | \t" + "MA" + " | \t" + "DA" + " | \t" + "BG" + " | \t" + "JP" + " | \t" + "ISC" + " | ");
        Cursor cursor = mDataSource.readData();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            int i = cursor.getInt(0);
            int j = cursor.getInt(1);
            int k = cursor.getInt(2);
            int l = cursor.getInt(3);
            int m = cursor.getInt(4);
            int n = cursor.getInt(5);

            fromSQL[i] = n;


            Log.v("Marks : "," | \t" + i + " | \t" + j + " | \t" + k + " | \t" + l + " | \t" + m + " | \t" + n + " | ");
            cursor.moveToNext();
        }
        int count = 0;
        for (int i = 1 ; i < date;i++){
            if (fromSQL[i] == -1){
                count++;
            }
        }
        Log.v("Count",count+"");
        if (count == 1){
            img.setImageResource(R.drawable.one);
        }else if (count == 2){
            img.setImageResource(R.drawable.two);
        }else if (count == 3){
            img.setImageResource(R.drawable.three);
        }else if (count == 4){
            img.setImageResource(R.drawable.four);
        }else if (count == 5){
            img.setImageResource(R.drawable.five);
        }else if (count == 6){
            img.setImageResource(R.drawable.six);
        }else if (count == 7){
            img.setImageResource(R.drawable.seven);
        }else if (count == 8){
            img.setImageResource(R.drawable.eight);
        }else if (count == 9){
            img.setImageResource(R.drawable.nine);
        }else if (count == 10){
            img.setImageResource(R.drawable.ten);
        }else if (count == 11){
            img.setImageResource(R.drawable.eleven);
        }else if (count == 12){
            img.setImageResource(R.drawable.twelve);
        }else if (count == 13){
            img.setImageResource(R.drawable.thirteen);
        }else if (count == 14){
            img.setImageResource(R.drawable.fourteen);
        }else if (count == 15){
            img.setImageResource(R.drawable.fifteen);
        }else if (count == 16){
            img.setImageResource(R.drawable.sixteen);
        }else if (count == 17){
            img.setImageResource(R.drawable.seventeen);
        }else if (count == 18){
            img.setImageResource(R.drawable.eighteen);
        }else if (count == 19){
            img.setImageResource(R.drawable.nineteen);
        }else if (count == 20){
            img.setImageResource(R.drawable.twenty);
        }else if (count == 21){
            img.setImageResource(R.drawable.twentyone);
        }else if (count == 22){
            img.setImageResource(R.drawable.ttwo);
        }else if (count == 23){
            img.setImageResource(R.drawable.tthree);
        }else if (count == 24){
            img.setImageResource(R.drawable.tfour);
        }else if (count == 25){
            img.setImageResource(R.drawable.tfive);
        }else if (count == 26){
            img.setImageResource(R.drawable.tsix);
        }else if (count == 27){
            img.setImageResource(R.drawable.tseven);
        }else if (count == 28){
            img.setImageResource(R.drawable.teight);
        }else if (count == 29){
            img.setImageResource(R.drawable.tnine);
        }else if (count == 30){
            img.setImageResource(R.drawable.thirty);
        }
        else {
            img.setImageResource(R.drawable.alll);
        }

        Log.v("COunt is ","Count is :" + count);

        mDataSource.close();
    }

    public void openSetting_Onclick(View view) {
//        Intent myIntent = new Intent(MainActivity.this, SettingsActivity.class);
//        Log.v("NEW INTENT","MainActivity.this, MainActivity.class");
//        startActivity(myIntent);
    }

    int getMA_TO_Hour(String s){
        if (s == "xxx")
        {
//            Intent myIntent = new Intent(MainActivity.this, SettingsActivity.class);
//            startActivity(myIntent);
            return 0;
        }
        else {
            String[] parts = s.split(":");
            return Integer.parseInt(parts[0]);
        }
    }

    int getMA_FROM_Hour(String s){
        String[] parts = s.split(":");
        return Integer.parseInt(parts[0]);
    }

    int getDA_TO_Hour(String s){

        String[] parts = s.split(":");
        return Integer.parseInt(parts[0]);
    }

    int getDA_FROM_Hour(String s){
        String[] parts = s.split(":");
        return Integer.parseInt(parts[0]);
    }

    int getSB_TO_Hour(String s){
        String[] parts = s.split(":");
        return Integer.parseInt(parts[0]);
    }

    int getSB_FROM_Hour(String s){
        String[] parts = s.split(":");
        return Integer.parseInt(parts[0]);
    }

    int getMA_TO_Minuit(String s){

        String[] parts = s.split(":");
        return Integer.parseInt(parts[1]);
    }

    int getMA_FROM_Minuit(String s){
        String[] parts = s.split(":");
        return Integer.parseInt(parts[1]);
    }

    int getDA_TO_Minuit(String s){
        String[] parts = s.split(":");
        return Integer.parseInt(parts[1]);
    }

    int getDA_FROM_Minuit(String s){
        String[] parts = s.split(":");
        return Integer.parseInt(parts[1]);
    }

    int getSB_TO_Minuit(String s){
        String[] parts = s.split(":");
        return Integer.parseInt(parts[1]);
    }

    int getSB_FROM_Minuit(String s){
        String[] parts = s.split(":");
        return Integer.parseInt(parts[1]);
    }


    public static boolean isTimeAutomatic(Context c) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(c.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 1;
        } else {
            return android.provider.Settings.System.getInt(c.getContentResolver(), android.provider.Settings.System.AUTO_TIME, 0) == 1;
        }
    }

    void readTime(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        maFrom = preferences.getString("Ma_From","00:00");
        maTo = preferences.getString("Ma_To","00:00");
        daFrom = preferences.getString("Da_From","00:00");
        daTo = preferences.getString("Da_To","00:00");
        sbFrom = preferences.getString("Sb_From","00:00");
        sbTo = preferences.getString("Sb_To","00:00");


        Log.v("Timings",maFrom);
        Log.v("Timings",maTo);
        Log.v("Timings",daFrom);
        Log.v("Timings",daTo);
        Log.v("Timings",sbFrom);
        Log.v("Timings",sbTo);
    }


    boolean isInBetweenOrNot(int a,int b,int c,int d,int e,int f){
        if ((c > e) || (c < a)){
            return false;
        }
        else if ((c > a) && (c <e)){
            Log.v("Function","1");

            return true;
        }
        else if (c == a){
            if ((b <= d) && (d <= f)){
                Log.v("Function","2");
                return true;
            }
            else {
                return false;
            }
        }
        else if (c == e){
            if (f >= d){
                Log.v("Function","3");
                return true;

            }
            else{
                Log.v("Function","4");

                return false;
            }
        }
        else{
            Log.v("Function","5");

            return false;
        }
    }

    public void punch_da_onclick(View view) {
        removeCard(punch_da);
        waitForOneSec(punch_da);
        removeCard(da_Card);
        waitForOneSec(da_Card);
        up_DA(3,date);//TODO : calculate point based on time for all three cards
    }

    public void punch_ma_onclick(View view) {
        removeCard(punch_ma);
        waitForOneSec(punch_ma);
        removeCard(ma_Card);
        waitForOneSec(ma_Card);
        up_MA(100,date);//TODO : calculate point based on time for all three cards
    }

    public void punch_sb_onclick(View view) {
        removeCard(punch_sb);
        waitForOneSec(punch_sb);
        removeCard(sb_Card);
        waitForOneSec(sb_Card);
        up_SB(3,date);//TODO : calculate point based on time for all three cards
    }

    public void table(View view) {
//        Intent myIntent = new Intent(MainActivity.this, RecyclarView.class);
//        startActivity(myIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        readTime();
    }

    public void getValueFromFirebase() {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.v("Datasnapshopt", dataSnapshot + "");
                int i = 1;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String bg = ds.child("BG").getValue().toString();
                    int Ibg = Integer.parseInt(bg);
                    String jp = ds.child("JP").getValue().toString();
                    int Ijp = Integer.parseInt(jp);

                    String da = ds.child("DA").getValue().toString();
                    int Ida = Integer.parseInt(da);

                    String ma = ds.child("MA").getValue().toString();
                    int Ima = Integer.parseInt(ma);

                    Log.v("Count", bg);
                    Log.v("CountValue", i + "");


                    mDataSource = new SadhnaDataSource(MainActivity.this, DB_NAME);
                    mDataSource.open();
                    mDataSource.update_SB(Ibg, i);
                    mDataSource.update_JP(Ijp, i);
                    mDataSource.update_MA(Ima, i);
                    mDataSource.update_DA(Ida, i);

                    if (Ibg != -1 && Ida != -1 && Ijp != -1 && Ima != -1) {
                        up_IS(1, i);
                        print();
                    }
                    else {
                        up_IS(-1, i);
                    }
                    mDataSource.close();
                    mDataSource = new SadhnaDataSource(MainActivity.this, DB_NAME);
                    mDataSource.open();
                    Log.v("MarksQuery : ", " | \t" + "Date" + " | \t" + "MA" + " | \t" + "DA" + " | \t" + "BG" + " | \t" + "JP" + " | \t" + "ISC" + " | ");
                    mDataSource.close();
                    i++;
                }
                uncompleteDaysOfSadhna();
                showCards();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference mUploadReport = FirebaseDatabase.getInstance().getReference().child("Report").child(convertedEmail);

        mUploadReport.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.v("DatasnapshoptR", dataSnapshot + "");
//                int i = 1;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    Log.v("dss", ds.getKey().toString() + "");

                    String ma = ds.child("MA").getValue().toString();
                    int Ima = Integer.parseInt(ma);

                    String da = ds.child("DA").getValue().toString();
                    int Ida = Integer.parseInt(da);

                    String sb = ds.child("BG").getValue().toString();
                    int Isb = Integer.parseInt(sb);

                    String jp = ds.child("JP").getValue().toString();
                    int Ijp = Integer.parseInt(jp);

                    String totle = ds.child("Totle").getValue().toString();
                    int Itotle = Integer.parseInt(totle);

                    String key = ds.getKey().toString();
                    int Ikey = Integer.parseInt(key);


                    mDataSource = new SadhnaDataSource(MainActivity.this,DB_NAME);
                    mDataSource.open();
                    mDataSource.insertOnlyFirstTimeInReport(Ikey,Ima,Ida,Isb,Ijp,Itotle);
                    mDataSource.close();
                }
//                    String bg = ds.child("BG").getValue().toString();
//                    int Ibg = Integer.parseInt(bg);
//                    String jp = ds.child("JP").getValue().toString();
//                    int Ijp = Integer.parseInt(jp);
//
//                    String da = ds.child("DA").getValue().toString();
//                    int Ida = Integer.parseInt(da);
//
//                    String ma = ds.child("MA").getValue().toString();
//                    int Ima = Integer.parseInt(ma);
//
//                    Log.v("Count", bg);
//                    Log.v("CountValue", i + "");
//
//
//                    mDataSource = new SadhnaDataSource(MainActivity.this, DB_NAME);
//                    mDataSource.open();
//                    mDataSource.update_SB(Ibg, i);
//                    mDataSource.update_JP(Ijp, i);
//                    mDataSource.update_MA(Ima, i);
//                    mDataSource.update_DA(Ida, i);
//
//
//                    Cursor cursor = mDataSource.checkUpdate(i);
//                    cursor.moveToFirst();
//                    while (!cursor.isAfterLast()) {
//                        int ii = cursor.getInt(0);
//                        int j = cursor.getInt(1);
//                        int k = cursor.getInt(2);
//                        int l = cursor.getInt(3);
//                        int m = cursor.getInt(4);
//                        int n = cursor.getInt(5);
//
//                        if (j != -1 && k != -1 && l != -1 && m != -1) {
//                            up_IS(1, i);
//                            print();
//                        }
//
//                        Log.v("Marks : ", " | \t" + i + " | \t" + j + " | \t" + k + " | \t" + l + " | \t" + m + " | \t" + n + " | ");
//                        cursor.moveToNext();
//                    }
//
//
//                    mDataSource.close();
//
//
//                    mDataSource = new SadhnaDataSource(MainActivity.this, DB_NAME);
//                    mDataSource.open();
//                    Log.v("MarksQuery : ", " | \t" + "Date" + " | \t" + "MA" + " | \t" + "DA" + " | \t" + "BG" + " | \t" + "JP" + " | \t" + "ISC" + " | ");
//
//                    mDataSource.close();
//
//
//                    i++;
//                }
//                showCards();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    void checkUserDatabaseExistOrNot(){

        dialog.show();
        mcheckForExistence.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.v("mmmm",dataSnapshot+"");
                if (dataSnapshot.hasChild(convertedEmail)){
                    getValueFromFirebase();
                    uncompleteDaysOfSadhna();
                    showCards();
                    SharedPreferences settings = getSharedPreferences("Pre", MODE_PRIVATE);
                    // Writing data to SharedPreferences
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("fima", "yes");
                    firstTimeinMainActivity = "yes";
                    editor.commit();

                }
                else {
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dialog.dismiss();
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Log.v("Testing","12");

        return cm.getActiveNetworkInfo() != null;
    }

}
