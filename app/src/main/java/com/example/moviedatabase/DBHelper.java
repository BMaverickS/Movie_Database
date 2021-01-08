package com.example.moviedatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    static String dbname = "movie.db";
    static int dbversion = 1;
    static String tablename = "moviedata";
    static String moviename = "title";
    static String movieyear = "year";
    static String movieid = "id";
    static String movieimage = "image";

    public DBHelper(@Nullable Context context) {
        super(context, dbname, null, dbversion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String database = "CREATE TABLE " + tablename + "(" +
                moviename + " VARCHAR(30), " +
                movieyear + " VARCHAR(10), " +
                movieid + " CHAR(15) PRIMARY KEY, " +
                movieimage + " VARCHAR(100)" + ");";

        db.execSQL(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String database = "DROP TABLE IF EXISTS " + tablename;
        db.execSQL(database);
        onCreate(db);
    }

    public Integer deletemov (String id)        //delete function
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Integer deldata = db.delete(tablename, "id = ?", new String[]{id});
        return deldata;
    }

    public boolean checksaved(String id)
    {
        SQLiteDatabase sqdb = this.getWritableDatabase();       //check saved movie or not
        Cursor cursor1 = sqdb.rawQuery("SELECT * FROM moviedata WHERE id = ?;", new String[]{id});
        if (cursor1.getCount() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
