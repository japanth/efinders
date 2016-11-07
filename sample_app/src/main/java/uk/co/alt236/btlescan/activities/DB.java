package uk.co.alt236.btlescan.activities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by PC on 31/8/59.
 */
public class DB {

    public static void insertq(Context context, String address,String item_name,String distracne,String major ){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String sql = "INSERT INTO " + DBHelper.TABLE_NAMEQ + "(" + DBHelper.COL_ITEM_NAMEQ + ", " + DBHelper.COL_ADDRESSQ + ", " + DBHelper.COL_DISTRANCEQ  +  ", " + DBHelper.COL_MAJORQ  + ") VALUES ('" + address + "', '" + item_name + "', '" + distracne + "', '" + major + "')";
        db.execSQL(sql);

        db.close();
        dbHelper.close();

    }

    public static void insertime(Context context,String item_name,String time ){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String sql = "INSERT INTO " + DBHelper.TABLE_NAME_TIME + "(" + DBHelper.COL_ITEM_NAME_TIME + ", " + DBHelper.COL_TIME + ", " +  ") VALUES ('" + item_name + "', '" + time +"')";
        db.execSQL(sql);

        db.close();
        dbHelper.close();

    }


    public static void insertdistranceq(Context context, String address,String item_name,String distracne,String type,String major ){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String sql = "INSERT INTO " + DBHelper.TABLE_NAMEQ + "(" + DBHelper.COL_ITEM_NAMEQ + ", " + DBHelper.COL_ADDRESSQ + ", " + DBHelper.COL_DISTRANCEQ  +","+DBHelper.COL_TYPEQ +  ", " + DBHelper.COL_MAJORQ  + ") VALUES ('" + item_name + "', '" + address + "', '" + distracne + "', '" + type + "', '" + major + "')";
        db.execSQL(sql);

        db.close();
        dbHelper.close();

    }

    public static String[] selectallq(Context context){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor =  db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAMEQ ,null);

        cursor.moveToFirst();
        String[] res = new String[5];
        while (!cursor.isAfterLast()){
            res[0] = cursor.getString(cursor.getColumnIndex(DBHelper.COL_ITEM_NAMEQ));
            res[1] = cursor.getString(cursor.getColumnIndex(DBHelper.COL_TYPEQ));
            res[2] = cursor.getString(cursor.getColumnIndex(DBHelper.COL_ADDRESSQ));
            cursor.moveToNext();
        }
        return res;
    }





    public static String[] selectitemq(Context context){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor =  db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAMEQ ,null);

        cursor.moveToFirst();
        String[] res = new String[2];
        while (!cursor.isAfterLast()){
            res[0] = cursor.getString(cursor.getColumnIndex(DBHelper.COL_ITEM_NAMEQ));
            res[1] = cursor.getString(cursor.getColumnIndex(DBHelper.COL_DISTRANCEQ));
            cursor.moveToNext();
        }
        return res;
    }


    public static String[] selectuserq(Context context){

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor =  db.rawQuery("SELECT "+DBHelper.COL_ITEM_NAMEQ +" FROM " + DBHelper.TABLE_NAMEQ + " WHERE "+ DBHelper.COL_TYPEQ ,null);

        cursor.moveToFirst();
        String[] res = new String[2];
        while (!cursor.isAfterLast()){
            res[0] = cursor.getString(cursor.getColumnIndex(DBHelper.COL_ITEM_NAMEQ));

            cursor.moveToNext();
        }
        return res;


    }



    public static String[] selectroomq(Context context){

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor =  db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAMEQ + " WHERE "+ DBHelper.COL_TYPEQ + "= ?", new String[] {"Room"});

        cursor.moveToFirst();
        String[] res = new String[3];
        while (!cursor.isAfterLast()){
            res[0] = cursor.getString(cursor.getColumnIndex(DBHelper.COL_ITEM_NAMEQ));
            res[1] = cursor.getString(cursor.getColumnIndex(DBHelper.COL_ADDRESSQ));
            res[2] = cursor.getString(cursor.getColumnIndex(DBHelper.COL_DISTRANCEQ));

            cursor.moveToNext();
        }
        return res;
    }

    public static String[] selectbeaconq(Context context,String address){

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor =  db.rawQuery("SELECT "+DBHelper.COL_ITEM_NAMEQ +" FROM " + DBHelper.TABLE_NAMEQ + " WHERE "+ DBHelper.COL_TYPEQ + "= ?", new String[] {address});

        cursor.moveToFirst();
        String[] res = new String[2];
        while (!cursor.isAfterLast()){
            res[0] = cursor.getString(cursor.getColumnIndex(DBHelper.COL_ITEM_NAMEQ));

            cursor.moveToNext();
        }
        return res;
    }




    public static String[] checkq(Context context){

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor =  db.rawQuery("SELECT "+DBHelper.COL_ITEM_NAMEQ +" FROM " + DBHelper.TABLE_NAMEQ + " WHERE "+ DBHelper.COL_TYPEQ + "= ?", new String[] {"Objects"});

        cursor.moveToFirst();
        String[] res = new String[2];
        while (!cursor.isAfterLast()){
            res[0] = cursor.getString(cursor.getColumnIndex(DBHelper.COL_ITEM_NAMEQ));

            cursor.moveToNext();
        }
        return res;


    }

    public static String[] selectListnameq(Context context){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT "+DBHelper.COL_ITEM_NAMEQ +" FROM " + DBHelper.TABLE_NAMEQ,null );

        cursor.moveToFirst();

        String[] name = new String[1];
        while (!cursor.isAfterLast()){
            name[0]= cursor.getString(cursor.getColumnIndex(DBHelper.COL_ITEM_NAMEQ));

        }
        return  name;

    }

    public static void deletebeaconq(Context context, String name){

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


    /*sqLiteDatabase.delete(Friend.TABLE, Friend.Column.ID + " = ? ",
            new String[] { String.valueOf(friend.getId()) });*/
        db.delete(DBHelper.TABLE_NAMEQ, DBHelper.COL_ITEM_NAMEQ + " = '" + name+"'", null);

        db.close();
    }











    public static void insert(Context context, String address,String item_name,String distracne,String type,String major ){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String sql = "INSERT INTO " + DBHelper.TABLE_NAME + "(" + DBHelper.COL_ITEM_NAME + ", " + DBHelper.COL_ADDRESS + ", " + DBHelper.COL_DISTRANCE  +","+DBHelper.COL_TYPE +  ", " + DBHelper.COL_MAJOR  + ") VALUES ('" + item_name + "', '" + address + "', '" + distracne + "', '" + type + "', '" + major + "')";
        db.execSQL(sql);

        db.close();
        dbHelper.close();

    }


    public static void insertdistrance(Context context, String address,String item_name,String distracne,String type,String major ){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String sql = "INSERT INTO " + DBHelper.TABLE_NAME + "(" + DBHelper.COL_ITEM_NAME + ", " + DBHelper.COL_ADDRESSQ + ", " + DBHelper.COL_DISTRANCE  +","+DBHelper.COL_TYPE +  ", " + DBHelper.COL_MAJOR  + ") VALUES ('" + item_name + "', '" + address + "', '" + distracne + "', '" + type + "', '" + major + "')";
        db.execSQL(sql);

        db.close();
        dbHelper.close();

    }

    public static String[] selectall(Context context,String name){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor =  db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME + " WHERE "+ DBHelper.COL_ITEM_NAME + "= ? ", new String[]{name});

        cursor.moveToFirst();
        String[] res = new String[5];
        while (!cursor.isAfterLast()){
            res[0] = cursor.getString(cursor.getColumnIndex(DBHelper.COL_ITEM_NAME));
            res[1] = cursor.getString(cursor.getColumnIndex(DBHelper.COL_TYPE));
            res[2] = cursor.getString(cursor.getColumnIndex(DBHelper.COL_ADDRESS));
            cursor.moveToNext();
        }
        return res;
    }





    public static String[] selectitem(Context context,String name){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor =  db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME + " WHERE "+ DBHelper.COL_ITEM_NAME + "= ? ", new String[]{name});

        cursor.moveToFirst();
        String[] res = new String[2];
        while (!cursor.isAfterLast()){
            res[0] = cursor.getString(cursor.getColumnIndex(DBHelper.COL_ITEM_NAME));
            res[1] = cursor.getString(cursor.getColumnIndex(DBHelper.COL_DISTRANCE));
            cursor.moveToNext();
        }
        return res;
    }


    public static String[] selectuser(Context context){

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor =  db.rawQuery("SELECT "+DBHelper.COL_ADDRESS +" FROM " + DBHelper.TABLE_NAME  ,null);

        cursor.moveToFirst();
        String[] res = new String[2];
        while (!cursor.isAfterLast()){
            res[0] = cursor.getString(cursor.getColumnIndex(DBHelper.COL_ADDRESS));

            cursor.moveToNext();
        }
        return res;


    }



    public static String[] selectroom(Context context){

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor =  db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME + " WHERE "+ DBHelper.COL_TYPE + "= ?", new String[] {"Room"});

        cursor.moveToFirst();
        String[] res = new String[3];
        while (!cursor.isAfterLast()){
            res[0] = cursor.getString(cursor.getColumnIndex(DBHelper.COL_ITEM_NAME));
            res[1] = cursor.getString(cursor.getColumnIndex(DBHelper.COL_ADDRESS));
            res[2] = cursor.getString(cursor.getColumnIndex(DBHelper.COL_DISTRANCE));

            cursor.moveToNext();
        }
        return res;
    }

    public static String[] selectbeacon(Context context,String address){

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor =  db.rawQuery("SELECT "+DBHelper.COL_ITEM_NAME +" FROM " + DBHelper.TABLE_NAME + " WHERE "+ DBHelper.COL_TYPE + "= ?", new String[] {address});

        cursor.moveToFirst();
        String[] res = new String[2];
        while (!cursor.isAfterLast()){
            res[0] = cursor.getString(cursor.getColumnIndex(DBHelper.COL_ITEM_NAME));

            cursor.moveToNext();
        }
        return res;
    }




    public static String[] check(Context context){

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor =  db.rawQuery("SELECT "+DBHelper.COL_ITEM_NAME +" FROM " + DBHelper.TABLE_NAME + " WHERE "+ DBHelper.COL_TYPE + "= ?", new String[] {"Objects"});

        cursor.moveToFirst();
        String[] res = new String[2];
        while (!cursor.isAfterLast()){
            res[0] = cursor.getString(cursor.getColumnIndex(DBHelper.COL_ITEM_NAME));

            cursor.moveToNext();
        }
        return res;


    }

    public static String[] selectListname(Context context){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT "+DBHelper.COL_ITEM_NAME +" FROM " + DBHelper.TABLE_NAME,null );

        cursor.moveToFirst();

        String[] name = new String[1];
        while (!cursor.isAfterLast()){
            name[0]= cursor.getString(cursor.getColumnIndex(DBHelper.COL_ITEM_NAME));

        }
        return  name;

    }

    public static void deletebeacon(Context context, String name){

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


    /*sqLiteDatabase.delete(Friend.TABLE, Friend.Column.ID + " = ? ",
            new String[] { String.valueOf(friend.getId()) });*/
        db.delete(DBHelper.TABLE_NAME, DBHelper.COL_ITEM_NAME + " = '" + name+"'", null);

        db.close();
    }

}
