package com.hkm.hkmsadhna;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hkm.hkmsadhna.db.SadhnaDataSource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Calender extends AppCompatActivity {

    String DB_NAME = "new.db";
    // hare krishna
    TextView[] dateCal = new TextView[43];
    RelativeLayout[] lotRel = new RelativeLayout[43];
    int[] fromSQL = new int[100];
    ImageView[] imgView = new ImageView[43];
    ImageView fab,fabAll,fabExcuse,img,fabCancle,fabSick,fabOS,fabOthor;
    LinearLayout lastLayout;
    int monthStartsFrom,totalDaysInMonth,month,date,day,gap,year,actualLimit;
    boolean longClickEnable;
    Spinner spinner;
    SadhnaDataSource mDataSource = new SadhnaDataSource(Calender.this,DB_NAME);
    ArrayList<Integer> arl = new ArrayList<Integer>();

    TextView mdate;
    CardView cardView;
    String[] MonArray = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SUP", "OCT", "NOV", "DEC"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        fab = (ImageView)findViewById(R.id.fab);
        Window window = this.getWindow();
//        String str = spinner.getSelectedItem().toString();
//        if (str == "Abhi")
//            Toast.makeText(getBaseContext(),str+"", Toast.LENGTH_SHORT).show();
//        else
//            Toast.makeText(getBaseContext(),str+" NOT", Toast.LENGTH_SHORT).show();

        SadhnaDataSource mDataSource = new SadhnaDataSource(Calender.this,DB_NAME);
        mDataSource.open();
        mDataSource.insertOnlyFirstTimeInTableSadhana();
        mDataSource.getDataForCurruntMonth();


        fabAll = (ImageView)findViewById(R.id.fabAll);
        fabExcuse = (ImageView)findViewById(R.id.fabExcuse);
        img = (ImageView)findViewById(R.id.img);
        fabCancle = (ImageView)findViewById(R.id.fabCancle);
        fabSick = (ImageView)findViewById(R.id.fabSick);
        fabOS = (ImageView)findViewById(R.id.fabOS);
        fabOthor = (ImageView)findViewById(R.id.fabOthor);

        lastLayout = (LinearLayout)findViewById(R.id.lastLayout);
        fab.setScaleX(0);
        fab.setScaleY(0);
        fabAll.setScaleX(0);
        fabAll.setScaleY(0);
        fabExcuse.setScaleX(0);
        fabExcuse.setScaleY(0);

        fabCancle.setScaleX(0);
        fabCancle.setScaleY(0);
        fabSick.setScaleX(0);
        fabSick.setScaleY(0);
        fabOS.setScaleX(0);
        fabOS.setScaleY(0);
        fabOthor.setScaleX(0);
        fabOthor.setScaleY(0);

        mdate = (TextView)findViewById(R.id.date);
        dateCal[1] = (TextView)findViewById(R.id.d1);
        dateCal[2] = (TextView)findViewById(R.id.d2);
        dateCal[3] = (TextView)findViewById(R.id.d3);
        dateCal[4] = (TextView)findViewById(R.id.d4);
        dateCal[5] = (TextView)findViewById(R.id.d5);
        dateCal[6] = (TextView)findViewById(R.id.d6);
        dateCal[7] = (TextView)findViewById(R.id.d7);
        dateCal[8] = (TextView)findViewById(R.id.d8);
        dateCal[9] = (TextView)findViewById(R.id.d9);
        dateCal[10] = (TextView)findViewById(R.id.d10);
        dateCal[11] = (TextView)findViewById(R.id.d11);
        dateCal[12] = (TextView)findViewById(R.id.d12);
        dateCal[13] = (TextView)findViewById(R.id.d13);
        dateCal[14] = (TextView)findViewById(R.id.d14);
        dateCal[15] = (TextView)findViewById(R.id.d15);
        dateCal[16] = (TextView)findViewById(R.id.d16);
        dateCal[17] = (TextView)findViewById(R.id.d17);
        dateCal[18] = (TextView)findViewById(R.id.d18);
        dateCal[19] = (TextView)findViewById(R.id.d19);
        dateCal[20] = (TextView)findViewById(R.id.d20);
        dateCal[21] = (TextView)findViewById(R.id.d21);
        dateCal[22] = (TextView)findViewById(R.id.d22);
        dateCal[23] = (TextView)findViewById(R.id.d23);
        dateCal[24] = (TextView)findViewById(R.id.d24);
        dateCal[25] = (TextView)findViewById(R.id.d25);
        dateCal[26] = (TextView)findViewById(R.id.d26);
        dateCal[27] = (TextView)findViewById(R.id.d27);
        dateCal[28] = (TextView)findViewById(R.id.d28);
        dateCal[29] = (TextView)findViewById(R.id.d29);
        dateCal[30] = (TextView)findViewById(R.id.d30);
        dateCal[31] = (TextView)findViewById(R.id.d31);
        dateCal[32] = (TextView)findViewById(R.id.d32);
        dateCal[33] = (TextView)findViewById(R.id.d33);
        dateCal[34] = (TextView)findViewById(R.id.d34);
        dateCal[35] = (TextView)findViewById(R.id.d35);
        dateCal[36] = (TextView)findViewById(R.id.d36);
        dateCal[37] = (TextView)findViewById(R.id.d37);
        dateCal[38] = (TextView)findViewById(R.id.d38);
        dateCal[39] = (TextView)findViewById(R.id.d39);
        dateCal[40] = (TextView)findViewById(R.id.d40);
        dateCal[41] = (TextView)findViewById(R.id.d41);
        dateCal[42] = (TextView)findViewById(R.id.d42);

        lotRel[1] = (RelativeLayout)findViewById(R.id.lot1);
        lotRel[2] = (RelativeLayout)findViewById(R.id.lot2);
        lotRel[3] = (RelativeLayout)findViewById(R.id.lot3);
        lotRel[4] = (RelativeLayout)findViewById(R.id.lot4);
        lotRel[5] = (RelativeLayout)findViewById(R.id.lot5);
        lotRel[6] = (RelativeLayout)findViewById(R.id.lot6);
        lotRel[7] = (RelativeLayout)findViewById(R.id.lot7);
        lotRel[8] = (RelativeLayout)findViewById(R.id.lot8);
        lotRel[9] = (RelativeLayout)findViewById(R.id.lot9);
        lotRel[10] = (RelativeLayout)findViewById(R.id.lot10);
        lotRel[11] = (RelativeLayout)findViewById(R.id.lot11);
        lotRel[12] = (RelativeLayout)findViewById(R.id.lot12);
        lotRel[13] = (RelativeLayout)findViewById(R.id.lot13);
        lotRel[14] = (RelativeLayout)findViewById(R.id.lot14);
        lotRel[15] = (RelativeLayout)findViewById(R.id.lot15);
        lotRel[16] = (RelativeLayout)findViewById(R.id.lot16);
        lotRel[17] = (RelativeLayout)findViewById(R.id.lot17);
        lotRel[18] = (RelativeLayout)findViewById(R.id.lot18);
        lotRel[19] = (RelativeLayout)findViewById(R.id.lot19);
        lotRel[20] = (RelativeLayout)findViewById(R.id.lot20);
        lotRel[21] = (RelativeLayout)findViewById(R.id.lot21);
        lotRel[22] = (RelativeLayout)findViewById(R.id.lot22);
        lotRel[23] = (RelativeLayout)findViewById(R.id.lot23);
        lotRel[24] = (RelativeLayout)findViewById(R.id.lot24);
        lotRel[25] = (RelativeLayout)findViewById(R.id.lot25);
        lotRel[26] = (RelativeLayout)findViewById(R.id.lot26);
        lotRel[27] = (RelativeLayout)findViewById(R.id.lot27);
        lotRel[28] = (RelativeLayout)findViewById(R.id.lot28);
        lotRel[29] = (RelativeLayout)findViewById(R.id.lot29);
        lotRel[30] = (RelativeLayout)findViewById(R.id.lot30);
        lotRel[31] = (RelativeLayout)findViewById(R.id.lot31);
        lotRel[32] = (RelativeLayout)findViewById(R.id.lot32);
        lotRel[33] = (RelativeLayout)findViewById(R.id.lot33);
        lotRel[34] = (RelativeLayout)findViewById(R.id.lot34);
        lotRel[35] = (RelativeLayout)findViewById(R.id.lot35);
        lotRel[36] = (RelativeLayout)findViewById(R.id.lot36);
        lotRel[37] = (RelativeLayout)findViewById(R.id.lot37);
        lotRel[38] = (RelativeLayout)findViewById(R.id.lot38);
        lotRel[39] = (RelativeLayout)findViewById(R.id.lot39);
        lotRel[40] = (RelativeLayout)findViewById(R.id.lot40);
        lotRel[41] = (RelativeLayout)findViewById(R.id.lot41);
        lotRel[42] = (RelativeLayout)findViewById(R.id.lot42);


        imgView[1] = (ImageView) findViewById(R.id.img1);
        imgView[2] = (ImageView) findViewById(R.id.img2);
        imgView[3] = (ImageView) findViewById(R.id.img3);
        imgView[4] = (ImageView) findViewById(R.id.img4);
        imgView[5] = (ImageView) findViewById(R.id.img5);
        imgView[6] = (ImageView) findViewById(R.id.img6);
        imgView[7] = (ImageView) findViewById(R.id.img7);
        imgView[8] = (ImageView) findViewById(R.id.img8);
        imgView[9] = (ImageView) findViewById(R.id.img9);
        imgView[10] = (ImageView) findViewById(R.id.img10);
        imgView[11] = (ImageView) findViewById(R.id.img11);
        imgView[12] = (ImageView) findViewById(R.id.img12);
        imgView[13] = (ImageView) findViewById(R.id.img13);
        imgView[14] = (ImageView) findViewById(R.id.img14);
        imgView[15] = (ImageView) findViewById(R.id.img15);
        imgView[16] = (ImageView) findViewById(R.id.img16);
        imgView[17] = (ImageView) findViewById(R.id.img17);
        imgView[18] = (ImageView) findViewById(R.id.img18);
        imgView[19] = (ImageView) findViewById(R.id.img19);
        imgView[20] = (ImageView) findViewById(R.id.img20);
        imgView[21] = (ImageView) findViewById(R.id.img21);
        imgView[22] = (ImageView) findViewById(R.id.img22);
        imgView[23] = (ImageView) findViewById(R.id.img23);
        imgView[24] = (ImageView) findViewById(R.id.img24);
        imgView[25] = (ImageView) findViewById(R.id.img25);
        imgView[26] = (ImageView) findViewById(R.id.img26);
        imgView[27] = (ImageView) findViewById(R.id.img27);
        imgView[28] = (ImageView) findViewById(R.id.img28);
        imgView[29] = (ImageView) findViewById(R.id.img29);
        imgView[30] = (ImageView) findViewById(R.id.img30);
        imgView[31] = (ImageView) findViewById(R.id.img31);
        imgView[32] = (ImageView) findViewById(R.id.img32);
        imgView[33] = (ImageView) findViewById(R.id.img33);
        imgView[34] = (ImageView) findViewById(R.id.img34);
        imgView[35] = (ImageView) findViewById(R.id.img35);
        imgView[36] = (ImageView) findViewById(R.id.img36);
        imgView[37] = (ImageView) findViewById(R.id.img37);
        imgView[38] = (ImageView) findViewById(R.id.img38);
        imgView[39] = (ImageView) findViewById(R.id.img39);
        imgView[40] = (ImageView) findViewById(R.id.img40);
        imgView[41] = (ImageView) findViewById(R.id.img41);
        imgView[42] = (ImageView) findViewById(R.id.img42);

        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        date = calendar.get(Calendar.DATE);
        day = calendar.get(Calendar.DAY_OF_WEEK);
        mdate.setText(date + " - " + MonArray[month] + " - " + year);
        GregorianCalendar mycal = new GregorianCalendar(year,month,date);
        totalDaysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
        monthStartsFrom = dayStart(date,day);
        gap = monthStartsFrom - 1;
        actualLimit = totalDaysInMonth + gap;



        Toast.makeText(getBaseContext(),"Total : " + totalDaysInMonth + "Starts From : "+ monthStartsFrom, Toast.LENGTH_SHORT).show();

        setDate(totalDaysInMonth,gap);
        setImage(totalDaysInMonth,gap,date);


        lotRel[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(1);
            }
        });
        lotRel[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(2);
            }
        });
        lotRel[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(3);
            }
        });
        lotRel[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(4);
            }
        });
        lotRel[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(5);
            }
        });
        lotRel[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(6);
            }
        });
        lotRel[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(7);
            }
        });
        lotRel[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(8);
            }
        });
        lotRel[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(9);
            }
        });
        lotRel[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(10);
            }
        });
        lotRel[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(11);
            }
        });
        lotRel[12].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(12);
            }
        });
        lotRel[13].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(13);
            }
        });
        lotRel[14].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(14);
            }
        });
        lotRel[15].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(15);
            }
        });
        lotRel[16].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(16);
            }
        });
        lotRel[17].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(17);
            }
        });
        lotRel[18].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(18);
            }
        });
        lotRel[19].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(19);
            }
        });
        lotRel[20].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(20);
            }
        });
        lotRel[21].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(21);
            }
        });
        lotRel[22].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(22);
            }
        });
        lotRel[23].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(23);
            }
        });
        lotRel[24].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(24);
            }
        });
        lotRel[25].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(25);
            }
        });
        lotRel[26].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(26);
            }
        });
        lotRel[27].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(27);
            }
        });
        lotRel[28].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(28);
            }
        });
        lotRel[29].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(29);
            }
        });
        lotRel[30].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(30);
            }
        });
        lotRel[32].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(32);
            }
        });
        lotRel[31].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(31);
            }
        });
        lotRel[33].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(33);
            }
        });
        lotRel[34].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(34);
            }
        });
        lotRel[35].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(35);
            }
        });
        lotRel[36].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(36);
            }
        });
        lotRel[37].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(37);
            }
        });
        lotRel[38].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(38);
            }
        });
        lotRel[39].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(39);
            }
        });
        lotRel[40].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(40);
            }
        });
        lotRel[41].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(41);
            }
        });
        lotRel[42].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleClick(42);
            }
        });



        fabAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int number : arl){
                    up_MA(100,number);
                    up_DA(100,number);
                    up_SB(100,number);
                    up_JP(100,number);
                    up_IS(1,number);
                }
                fabDisappear();
                setDate(totalDaysInMonth,gap);
                setImage(totalDaysInMonth,gap,date);

            }
        });

        fabSick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int number : arl){
                    up_MA(8,number);
                    up_DA(8,number);
                    up_SB(8,number);
                    up_JP(8,number);
                    up_IS(1,number);
                }
                ReasonFabDisappear();
                setDate(totalDaysInMonth,gap);
                setImage(totalDaysInMonth,gap,date);

            }
        });

        fabOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int number : arl){
                    up_MA(9,number);
                    up_DA(9,number);
                    up_SB(9,number);
                    up_JP(9,number);
                    up_IS(1,number);
                }
                ReasonFabDisappear();
                setDate(totalDaysInMonth,gap);
                setImage(totalDaysInMonth,gap,date);

            }
        });

        fabOthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int number : arl){
                    up_MA(10,number);
                    up_DA(10,number);
                    up_SB(10,number);
                    up_JP(10,number);
                    up_IS(1,number);
                }
                ReasonFabDisappear();
                setDate(totalDaysInMonth,gap);
                setImage(totalDaysInMonth,gap,date);

            }
        });

        fabExcuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("ONd","fabexcuse");
                fabDisappearForReason();
                ReasonFabAppear();
            }
        });

        fabCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(totalDaysInMonth,gap);
                setImage(totalDaysInMonth,gap,date);
                ReasonFabDisappear();
            }
        });

        Toast.makeText(Calender.this, "" + date,
                Toast.LENGTH_LONG).show();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("ONd","fab");
                setDate(totalDaysInMonth,gap);
                setImage(totalDaysInMonth,gap,date);
                fabDisappear();
            }
        });


        lotRel[1].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(1);
                return true;
            }
        });
        lotRel[2].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(2);
                return true;
            }
        });
        lotRel[3].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(3);
                return true;
            }
        });
        lotRel[4].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(4);
                return true;
            }
        });
        lotRel[5].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(5);
                return true;
            }
        });
        lotRel[6].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(6);
                return true;
            }
        });
        lotRel[7].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(7);
                return true;
            }
        });
        lotRel[8].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(8);
                return true;
            }
        });
        lotRel[9].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(9);
                return true;
            }
        });
        lotRel[10].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(10);
                return true;
            }
        });
        lotRel[11].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(11);
                return true;
            }
        });
        lotRel[12].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(12);
                return true;
            }
        });
        lotRel[13].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(13);
                return true;
            }
        });
        lotRel[14].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(14);
                return true;
            }
        });
        lotRel[15].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(15);
                return true;
            }
        });
        lotRel[16].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(16);
                return true;
            }
        });
        lotRel[17].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(17);
                return true;
            }
        });
        lotRel[18].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(18);
                return true;
            }
        });
        lotRel[19].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(19);
                return true;
            }
        });
        lotRel[20].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(20);
                return true;
            }
        });
        lotRel[21].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(21);
                return true;
            }
        });
        lotRel[22].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(22);
                return true;
            }
        });
        lotRel[23].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(23);
                return true;
            }
        });
        lotRel[24].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(24);
                return true;
            }
        });
        lotRel[25].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(25);
                return true;
            }
        });
        lotRel[26].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(26);
                return true;
            }
        });
        lotRel[27].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(27);
                return true;
            }
        });
        lotRel[28].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(28);
                return true;
            }
        });
        lotRel[29].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(29);
                return true;
            }
        });
        lotRel[30].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(30);
                return true;
            }
        });
        lotRel[31].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(31);
                return true;
            }
        });
        lotRel[32].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(32);
                return true;
            }
        });
        lotRel[33].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(33);
                return true;
            }
        });
        lotRel[34].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(34);
                return true;
            }
        });
        lotRel[35].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(35);
                return true;
            }
        });
        lotRel[36].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(36);
                return true;
            }
        });
        lotRel[37].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(37);
                return true;
            }
        });

        lotRel[38].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(38);
                return true;
            }
        });
        lotRel[39].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(39);
                return true;
            }
        });
        lotRel[40].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(40);
                return true;
            }
        });
        lotRel[41].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(41);
                return true;
            }
        });

        lotRel[42].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                doubleClick(42);
                return true;
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        setDate(totalDaysInMonth,gap);
        setImage(totalDaysInMonth,gap,date);

    }

    int dayStart(int date,int day){
        while (date > 1){
            if (day == 1){
                day = 7;
            }
            else {
                day--;
            }
            date--;
        }
        return day;
    }


    void setDate(int totalDaysInMonth,int gap){
        int i = 1;
        while (i <= totalDaysInMonth){
            dateCal[i + gap].setText(i+"");
            i++;
        }
//        Toast.makeText(getBaseContext(),"Total : " + (i + gap), Toast.LENGTH_SHORT).show();

        if ((i + gap) <= 36){
            lastLayout.getLayoutParams().height = 0;

        }
    }

    private void animation(){
        int ii = 1;
        while(ii < 42) {
            lotRel[ii].setScaleX(0);
            lotRel[ii].setScaleY(0);
            lotRel[ii].animate().scaleX(1).scaleY(1).start();
            ii++;
        }

        fab.animate().scaleY(1).scaleX(1).start();
    }


    void setImage(int totalDaysInMonth,int gap,int date){


        SadhnaDataSource mDataSource = new SadhnaDataSource(Calender.this,DB_NAME);
        mDataSource.open();
        Log.v("Marks : "," | \t" + "Date" + " | \t" + "MA" + " | \t" + "DA" + " | \t" + "BG" + " | \t" + "JP" + " | \t" + "ISC" + " | ");
        Cursor cursor = mDataSource.getDataForCurruntMonth();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            int i = cursor.getInt(0);
            int j = cursor.getInt(3);
            int k = cursor.getInt(4);
            int l = cursor.getInt(5);
            int m = cursor.getInt(6);
            int n = cursor.getInt(7);
            fromSQL[i] = n;
            Log.v("Marks : "," | \t" + i + " | \t" + j + " | \t" + k + " | \t" + l + " | \t" + m + " | \t" + n + " | ");
            cursor.moveToNext();
        }
        mDataSource.close();

        int i = 1;
        while (i <= totalDaysInMonth){
            Log.v("Inside While","i = " + i);

            if (i == date){
                makeToday(i+gap);
            }
            else {
                //TODO  check weather data has been stored or not
                if (i > date)
                {
                    imgView[i + gap].setImageResource(R.drawable.normal);
                    Log.v("Working","yes " + i);
                }
                else {
                    if (fromSQL[i] == -1){
                        imgView[i + gap].setImageResource(R.drawable.red);
                    }
                    else if (fromSQL[i] == 1){
                        imgView[i + gap].setImageResource(R.drawable.green);

                    }
                    else {
                        imgView[i + gap].setImageResource(R.drawable.normal);
                    }

                }
            }
            i++;
        }
    }

    void makeGreen(int id){
        imgView[id].setImageResource(R.drawable.green);
    }

    void makeRed(int id){
        imgView[id].setImageResource(R.drawable.red);
    }

    void makeNormal(int id){
        imgView[id].setImageResource(R.drawable.normal);
    }

    void makeToday(int id){
        imgView[id].setImageResource(R.drawable.today);
        dateCal[id].setTypeface(null, Typeface.BOLD);
        dateCal[id].setTextColor(Color.WHITE);
    }

    void singleClick(final int i){
        if (i >= monthStartsFrom && i <= date + gap) {
            if (longClickEnable == true){
                if (i == date + gap){
                    imgView[i].setImageResource(R.drawable.select);
                    dateCal[i].setTextColor(Color.BLACK);
                    addToArray(i-gap);
                    Log.v("Value is : ",(i-gap)+"");
                }
                else {
                    imgView[i].setImageResource(R.drawable.select);
                    Log.v("Value is : ",(i-gap)+"");
                    addToArray(i-gap);
                }
            }
            else{
                Intent myIntent = new Intent(Calender.this, EachDay.class);
                myIntent.putExtra("date", (i-gap));
                Log.v("NEW INTENT","((Calender.this, eachDay.class)");

                startActivity(myIntent);

            }
        }
        Log.v("Array : ", arl+"");
    }

    void doubleClick(int i){
        if (i >= monthStartsFrom && i <= date + gap) {
            if (i == date + gap){
                imgView[i].setImageResource(R.drawable.select);
                dateCal[i].setTextColor(Color.BLACK);
                longClickEnable = true;
                fabAppear();
                addToArray(i-gap);
            }
            else {
                longClickEnable = true;
                imgView[i].setImageResource(R.drawable.select);
                fabAppear();
                addToArray(i-gap);
            }
        }
        Log.v("Array : ", arl+"");
    }

    void fabAppear(){
        fab.setVisibility(View.VISIBLE);
        fabAll.setVisibility(View.VISIBLE);
        fabExcuse.setVisibility(View.VISIBLE);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(fab, "scaleX", 0, 1);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(fab, "scaleY", 0, 1);
        scaleX.setInterpolator(new AccelerateInterpolator());
        scaleY.setInterpolator(new AccelerateInterpolator());
        AnimatorSet selectFab = new AnimatorSet();
        selectFab.setDuration(100);
        selectFab.playTogether(scaleX,scaleY);

        ObjectAnimator scaleXf2 = ObjectAnimator.ofFloat(fabAll, "scaleX", 0, 1);
        ObjectAnimator scaleYf2 = ObjectAnimator.ofFloat(fabAll, "scaleY", 0, 1);
        scaleXf2.setInterpolator(new AccelerateInterpolator());
        scaleYf2.setInterpolator(new AccelerateInterpolator());
        AnimatorSet selectFab2 = new AnimatorSet();
        selectFab2.setDuration(100);
        selectFab2.playTogether(scaleXf2,scaleYf2);

        ObjectAnimator scaleXf3 = ObjectAnimator.ofFloat(fabExcuse, "scaleX", 0, 1);
        ObjectAnimator scaleYf3 = ObjectAnimator.ofFloat(fabExcuse, "scaleY", 0, 1);
        scaleXf3.setInterpolator(new DecelerateInterpolator());
        scaleYf3.setInterpolator(new DecelerateInterpolator());
        AnimatorSet selectFab3 = new AnimatorSet();
        selectFab3.setDuration(100);
        selectFab3.playTogether(scaleXf3,scaleYf3);

        AnimatorSet set = new AnimatorSet();
        set.playSequentially(selectFab,selectFab2,selectFab3);
        set.start();
    }


    void ReasonFabAppear(){
        fabCancle.setVisibility(View.VISIBLE);
        fabSick.setVisibility(View.VISIBLE);
        fabOS.setVisibility(View.VISIBLE);
        fabOthor.setVisibility(View.VISIBLE);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(fabCancle, "scaleX", 0, 1);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(fabCancle, "scaleY", 0, 1);
        scaleX.setInterpolator(new AccelerateInterpolator());
        scaleY.setInterpolator(new AccelerateInterpolator());
        AnimatorSet selectFab = new AnimatorSet();
        selectFab.setDuration(100);
        selectFab.playTogether(scaleX,scaleY);

        ObjectAnimator scaleXf2 = ObjectAnimator.ofFloat(fabSick, "scaleX", 0, 1);
        ObjectAnimator scaleYf2 = ObjectAnimator.ofFloat(fabSick, "scaleY", 0, 1);
        scaleXf2.setInterpolator(new AccelerateInterpolator());
        scaleYf2.setInterpolator(new AccelerateInterpolator());
        AnimatorSet selectFab2 = new AnimatorSet();
        selectFab2.setDuration(100);
        selectFab2.playTogether(scaleXf2,scaleYf2);

        ObjectAnimator scaleXf3 = ObjectAnimator.ofFloat(fabOS, "scaleX", 0, 1);
        ObjectAnimator scaleYf3 = ObjectAnimator.ofFloat(fabOS, "scaleY", 0, 1);
        scaleXf3.setInterpolator(new DecelerateInterpolator());
        scaleYf3.setInterpolator(new DecelerateInterpolator());
        AnimatorSet selectFab3 = new AnimatorSet();
        selectFab3.setDuration(100);
        selectFab3.playTogether(scaleXf3,scaleYf3);

        ObjectAnimator scaleXf4 = ObjectAnimator.ofFloat(fabOthor, "scaleX", 0, 1);
        ObjectAnimator scaleYf4 = ObjectAnimator.ofFloat(fabOthor, "scaleY", 0, 1);
        scaleXf4.setInterpolator(new DecelerateInterpolator());
        scaleYf4.setInterpolator(new DecelerateInterpolator());
        AnimatorSet selectFab4 = new AnimatorSet();
        selectFab4.setDuration(100);
        selectFab4.playTogether(scaleXf4,scaleYf4);

        AnimatorSet set = new AnimatorSet();
        set.playSequentially(selectFab,selectFab2,selectFab3,selectFab4);
        set.start();
    }

    void fabDisappear(){
        fab.animate().scaleX(0).scaleY(0).start();
        fabAll.animate().scaleX(0).scaleY(0).start();
        fabExcuse.animate().scaleX(0).scaleY(0).start();
        fab.setVisibility(View.GONE);
        fabAll.setVisibility(View.GONE);
        fabExcuse.setVisibility(View.GONE);
        longClickEnable = false;
        arl.clear();
    }

    void fabDisappearForReason(){
        fab.animate().scaleX(0).scaleY(0).start();
        fabAll.animate().scaleX(0).scaleY(0).start();
        fabExcuse.animate().scaleX(0).scaleY(0).start();
        fab.setVisibility(View.GONE);
        fabAll.setVisibility(View.GONE);
        fabExcuse.setVisibility(View.GONE);
    }

    void ReasonFabDisappear(){
        fabCancle.animate().scaleX(0).scaleY(0).start();
        fabSick.animate().scaleX(0).scaleY(0).start();
        fabOthor.animate().scaleX(0).scaleY(0).start();
        fabOS.animate().scaleX(0).scaleY(0).start();
        fabCancle.setVisibility(View.GONE);
        fabSick.setVisibility(View.GONE);
        fabOS.setVisibility(View.GONE);
        fabOthor.setVisibility(View.GONE);
        longClickEnable = false;
        arl.clear();
    }

    void addToArray(int i){
        if (arl.contains(i)){

        }
        else {
            arl.add(i);
        }
    }
    void up_MA(int value, int id){
        SadhnaDataSource mDataSource = new SadhnaDataSource(Calender.this,DB_NAME);
        mDataSource.open();
        mDataSource.update_MA(value,id);
        mDataSource.close();
    }
    void up_DA(int value, int id){
        SadhnaDataSource mDataSource = new SadhnaDataSource(Calender.this,DB_NAME);
        mDataSource.open();
        mDataSource.update_DA(value,id);
        mDataSource.close();
    }
    void up_SB(int value, int id){
        SadhnaDataSource mDataSource = new SadhnaDataSource(Calender.this,DB_NAME);
        mDataSource.open();
        mDataSource.update_SB(value,id);
        mDataSource.close();
    }
    void up_JP(int value, int id){
        SadhnaDataSource mDataSource = new SadhnaDataSource(Calender.this,DB_NAME);
        mDataSource.open();
        mDataSource.update_JP(value,id);
        mDataSource.close();
    }
    void up_IS(int value, int id){
        SadhnaDataSource mDataSource = new SadhnaDataSource(Calender.this,DB_NAME);
        mDataSource.open();
        mDataSource.update_IS(value,id);
        mDataSource.close();
    }

    public void nav_back(View view) {
        onBackPressed();
    }

//    public void fabExcuse(View view) {
//        Log.v("Onclickk","Onclickk");
//        fabDisappear();
//                ReasonFabAppear();
//    }
}
