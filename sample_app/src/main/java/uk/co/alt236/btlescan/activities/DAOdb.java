package uk.co.alt236.btlescan.activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class DAOdb {
    private SQLiteDatabase database;
    private DBhelpers dbHelper;

    public DAOdb(Context context) {
        dbHelper = new DBhelpers(context);
        database = dbHelper.getWritableDatabase();
    }

    /**
     * close any database object
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * insert a text report item to the location database table
     *
     * @param image
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long addImage(MyImage image) {
        ContentValues cv = new ContentValues();
        cv.put(DBhelpers.COLUMN_PATH, image.getPath());
        cv.put(DBhelpers.COLUMN_TITLE, image.getTitle());
        cv.put(DBhelpers.COLUMN_DESCRIPTION, image.getDescription());
        cv.put(DBhelpers.COLUMN_DATETIME, System.currentTimeMillis());
        return database.insert(DBhelpers.TABLE_NAME, null, cv);
    }

    /**
     * delete the given image from database
     *
     * @param image
     */
    public void deleteImage(MyImage image) {
        String whereClause =
                DBhelpers.COLUMN_TITLE + "=? AND " + DBhelpers.COLUMN_DATETIME +
                        "=?";
        String[] whereArgs = new String[]{image.getTitle(),
                String.valueOf(image.getDatetimeLong())};
        database.delete(DBhelpers.TABLE_NAME, whereClause, whereArgs);
    }

    /**
     * @return all image as a List
     */
    public List<MyImage> getImages() {
        List<MyImage> MyImages = new ArrayList<>();
        Cursor cursor =
                database.query(DBhelpers.TABLE_NAME, null, null, null, null,
                        null, DBhelpers.COLUMN_DATETIME + " DESC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MyImage MyImage = cursorToMyImage(cursor);
            MyImages.add(MyImage);
            cursor.moveToNext();
        }
        cursor.close();
        return MyImages;
    }

    /**
     * read the cursor row and convert the row to a MyImage object
     *
     * @param cursor
     * @return MyImage object
     */
    private MyImage cursorToMyImage(Cursor cursor) {
        MyImage image = new MyImage();
        image.setPath(
                cursor.getString(cursor.getColumnIndex(DBhelpers.COLUMN_PATH)));
        image.setTitle(
                cursor.getString(cursor.getColumnIndex(DBhelpers.COLUMN_TITLE)));
        image.setDatetime(cursor.getLong(
                cursor.getColumnIndex(DBhelpers.COLUMN_DATETIME)));
        image.setDescription(cursor.getString(
                cursor.getColumnIndex(DBhelpers.COLUMN_DESCRIPTION)));
        return image;
    }
}