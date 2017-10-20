package com.hkm.hkmsadhna.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by abhi on 24/08/17.
 */


public class SadhnaDataSource {
    Calendar calendar = Calendar.getInstance();
    int month = calendar.get(Calendar.MONTH);
    int year = calendar.get(Calendar.YEAR);
    int date = calendar.get(Calendar.DATE);
    GregorianCalendar mycal = new GregorianCalendar(year,month,date);
    int totalDaysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);

    int[] factorial = {1, 1, 2, 6, 24, 120, 720, 5040};
     static SQLiteDatabase mDatabase;
    public MarksHelper mMarksHelper;
    private Context mContext;

    public SadhnaDataSource(Context context,String DB_NAME){
        mContext = context;
        mMarksHelper = new MarksHelper(mContext,DB_NAME);
    }

    public void open() throws SQLException {
        mDatabase = mMarksHelper.getWritableDatabase();
    }

    public void close() {
        mDatabase.close();
    }

    public void update() {
        for (int i = 1;i <= 30; i++){
            ContentValues values = new ContentValues();
            values.put(MarksHelper.COLUMN_DA, 0);
            String where = MarksHelper.COLUMN_DATE+"=?";
            String[] whereArgs = new String[] {String.valueOf(i)};
            mDatabase.update(MarksHelper.TABLE_SADHNA, values,where,whereArgs);
            Log.v("Number is  : ", 50 + " : Number is ");
        }
    }


    public void update_MA(int ma,int id) {
            ContentValues values = new ContentValues();
            values.put(MarksHelper.COLUMN_MA, ma);
        String where = MarksHelper.COLUMN_DATE+"=?";
        String[] whereArgs = new String[] {String.valueOf(id)};
        mDatabase.update(MarksHelper.TABLE_SADHNA, values,where,whereArgs);
    }

    public void update_DA(int da,int id) {
        ContentValues values = new ContentValues();
        values.put(MarksHelper.COLUMN_DA, da);
        String where = MarksHelper.COLUMN_DATE+"=?";
        String[] whereArgs = new String[] {String.valueOf(id)};
        mDatabase.update(MarksHelper.TABLE_SADHNA, values,where,whereArgs);
    }

    public void update_SB(int sb,int id) {
        ContentValues values = new ContentValues();
        values.put(MarksHelper.COLUMN_BG, sb);
        String where = MarksHelper.COLUMN_DATE+"=?";
        String[] whereArgs = new String[] {String.valueOf(id)};
        mDatabase.update(MarksHelper.TABLE_SADHNA, values,where,whereArgs);
    }

    public void update_JP(int jp,int id) {
        ContentValues values = new ContentValues();
        values.put(MarksHelper.COLUMN_JP, jp);
        String where = MarksHelper.COLUMN_DATE+"=?";
        String[] whereArgs = new String[] {String.valueOf(id)};
        mDatabase.update(MarksHelper.TABLE_SADHNA, values,where,whereArgs);
    }

    public void update_IS(int is,int id) {
        ContentValues values = new ContentValues();
        values.put(MarksHelper.COLUMN_ISCOMPLETED, is);
        String where = MarksHelper.COLUMN_DATE+"=?";
        String[] whereArgs = new String[] {String.valueOf(id)};
        mDatabase.update(MarksHelper.TABLE_SADHNA, values,where,whereArgs);
    }

    public void updateReport(int id,int ma,int da,int sb,int jp,int totle) {
        ContentValues values = new ContentValues();
        values.put(MarksHelper.REPORT_COLUMN_ID, id);
        values.put(MarksHelper.REPORT_COLUMN_MA, ma);
        values.put(MarksHelper.REPORT_COLUMN_DA, da);
        values.put(MarksHelper.REPORT_COLUMN_SB, sb);
        values.put(MarksHelper.REPORT_COLUMN_JP, jp);
        values.put(MarksHelper.REPORT_COLUMN_Totle, totle);

        String where = MarksHelper.COLUMN_DATE+"=?";
        String[] whereArgs = new String[] {String.valueOf(id)};
        mDatabase.update(MarksHelper.TABLE_REPORT, values,where,whereArgs);
    }


//    public void insert() {
//        String where = "id=?";
//        String[] whereArgs = new String[] {String.valueOf(i)};
//        ContentValues values = new ContentValues();
//        values.put(MarksHelper.COLUMN_BG, 100);
//        mDatabase.update(MarksHelper.TABLE_SADHNA, null, values);
//        Log.v("Number is  : ", 50 + " : Number is ");
//    }

    public void insert() {
        for (int number : factorial){
            ContentValues values = new ContentValues();
            values.put(MarksHelper.COLUMN_BG, number);
            mDatabase.insert(MarksHelper.TABLE_SADHNA, null, values);
            Log.v("Number is  : ", number + " : Number is ");
        }
    }

    public void insertOnlyFirstTime() {
        if (checkRowExistOrNot(28,MarksHelper.TABLE_SADHNA)){

        }
        else {
            for (int i = 1; i <= totalDaysInMonth ;i++){
                ContentValues values = new ContentValues();
                values.put(MarksHelper.COLUMN_MONTH, month);
                values.put(MarksHelper.COLUMN_YEAR, year);
                values.put(MarksHelper.COLUMN_MA, -1);
                values.put(MarksHelper.COLUMN_DA, -1);
                values.put(MarksHelper.COLUMN_BG, -1);
                values.put(MarksHelper.COLUMN_JP, -1);
                values.put(MarksHelper.COLUMN_ISCOMPLETED, -1);
                mDatabase.insert(MarksHelper.TABLE_SADHNA,null,values);
            }

        }
    }

    public void insertOnlyFirstTimeInReport(int id,int ma,int da,int sb,int jp,int total) {
        if (checkRowExistOrNotFromReport(id,MarksHelper.TABLE_REPORT)){
            ContentValues values = new ContentValues();
            values.put(MarksHelper.REPORT_COLUMN_MA, ma);
            values.put(MarksHelper.REPORT_COLUMN_DA, da);
            values.put(MarksHelper.REPORT_COLUMN_SB, sb);
            values.put(MarksHelper.REPORT_COLUMN_JP, jp);
            values.put(MarksHelper.REPORT_COLUMN_Totle, total);
            String where = MarksHelper.REPORT_COLUMN_ID+"=?";
            String[] whereArgs = new String[] {String.valueOf(id)};
            mDatabase.update(MarksHelper.TABLE_REPORT, values,where,whereArgs);
        }

        else {
                ContentValues values = new ContentValues();
                values.put(MarksHelper.REPORT_COLUMN_MA, ma);
                values.put(MarksHelper.REPORT_COLUMN_DA, da);
                values.put(MarksHelper.REPORT_COLUMN_SB, sb);
                values.put(MarksHelper.REPORT_COLUMN_JP, jp);
                values.put(MarksHelper.REPORT_COLUMN_ID, id);
                values.put(MarksHelper.REPORT_COLUMN_Totle, total);
                mDatabase.insert(MarksHelper.TABLE_REPORT,null,values);
        }
    }



    public boolean checkRowExistOrNot(int in,String TABLE_NAME) {
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " +TABLE_NAME+ " where "+MarksHelper.COLUMN_DATE + " = "+in+";",null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        else return true;
    }

    public boolean checkRowExistOrNotFromReport(int in,String TABLE_NAME) {
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " +TABLE_NAME+ " where "+MarksHelper.REPORT_COLUMN_ID + " = "+in+";",null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        else return true;
    }

    public Cursor readData() {
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " +MarksHelper.TABLE_SADHNA+";",null);
        return cursor;
    }


    public Cursor readDataFromReport() {
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " +MarksHelper.TABLE_REPORT+";",null);
        return cursor;
    }

    public Cursor readDataFromReport(int id) {
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " +MarksHelper.TABLE_REPORT+ " WHERE " + MarksHelper.REPORT_COLUMN_ID + " = "+ id +";",null);
        return cursor;
    }

    public void dropTable() {
        mDatabase.delete(MarksHelper.TABLE_SADHNA,null,null);
    }

    public Cursor checkUpdate(int id) {
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " +MarksHelper.TABLE_SADHNA+ " WHERE " + MarksHelper.COLUMN_DATE + " = "+ id +";",null);
        return cursor;
    }

    public void insertReport(int id, int ma, int da, int sb, int jp, int totle) {
        ContentValues values = new ContentValues();
        values.put(MarksHelper.REPORT_COLUMN_MA, id);
        values.put(MarksHelper.REPORT_COLUMN_DA, ma);
        values.put(MarksHelper.REPORT_COLUMN_SB, sb);
        values.put(MarksHelper.REPORT_COLUMN_JP, jp);
        values.put(MarksHelper.REPORT_COLUMN_Totle, totle);
        mDatabase.insert(MarksHelper.TABLE_REPORT,null,values);
    }
}
