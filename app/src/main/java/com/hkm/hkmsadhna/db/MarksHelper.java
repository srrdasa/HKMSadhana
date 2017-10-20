package com.hkm.hkmsadhna.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by abhi on 24/08/17.
 */

public class MarksHelper extends SQLiteOpenHelper {

    public static final String TABLE_SADHNA = "SADHNA";
    public static final String TABLE_REPORT = "Repoart";

    //for sadhna
    public static final String COLUMN_DATE = "_DATE";
    public static final String COLUMN_MONTH = "MONTH";
    public static final String COLUMN_YEAR = "YEAR";
    public static final String COLUMN_MA = "MA";
    public static final String COLUMN_DA = "DA";
    public static final String COLUMN_BG = "BG";
    public static final String COLUMN_JP = "JP";
    public static final String COLUMN_ISCOMPLETED = "ISC";

    //for reportF
    public static final String REPORT_COLUMN_ID = "ID";
    public static final String REPORT_COLUMN_MA = "MA";
    public static final String REPORT_COLUMN_DA = "DA";
    public static final String REPORT_COLUMN_SB = "SB";
    public static final String REPORT_COLUMN_JP = "JP";
    public static final String REPORT_COLUMN_Totle = "TOTLE";


    //Declaration
    private static final String DB_NAME = "new.db";
    private static final int DB_VERSION = 1;
    private static final String DB_CREATE =
            "CREATE TABLE " + TABLE_SADHNA + " (" +
                    COLUMN_DATE + " INTEGER PRIMARY KEY," +
                    COLUMN_MONTH + " INTEGER," +
                    COLUMN_YEAR + " INTEGER," +
                    COLUMN_MA + " INTEGER," +
                    COLUMN_DA + " INTEGER," +
                    COLUMN_BG + " INTEGER," +
                    COLUMN_JP + " INTEGER," +
                    COLUMN_ISCOMPLETED + " INTEGER)";

    private static final String DB_CREATEE =
            "CREATE TABLE " + TABLE_REPORT + " (" +
                    REPORT_COLUMN_ID + " INTEGER PRIMARY KEY," +
                    REPORT_COLUMN_MA + " INTEGER," +
                    REPORT_COLUMN_DA + " INTEGER," +
                    REPORT_COLUMN_SB + " INTEGER," +
                    REPORT_COLUMN_JP + " INTEGER, " +
                    REPORT_COLUMN_Totle + " INTEGER)";

    private static final String DB_DELETE = "DROP TABLE IF EXISTS " + TABLE_SADHNA;


    public MarksHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public MarksHelper(Context context,String DB_NAME) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DB_CREATE);
        sqLiteDatabase.execSQL(DB_CREATEE);
        Log.v("Database is Created", "Successfull");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
