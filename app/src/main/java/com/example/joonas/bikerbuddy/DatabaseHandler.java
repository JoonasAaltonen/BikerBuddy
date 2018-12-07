package com.example.joonas.bikerbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.DatabaseUtils.InsertHelper;

// Used guidance from
// https://github.com/mitchtabian/SaveReadWriteDeleteSQLite/blob/master/SaveAndDisplaySQL/app/src/main/java/com/tabian/saveanddisplaysql/DatabaseHelper.java
// To create the databasehandler

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static String forumTableName = "forumTable";
    private static String idColumn = "id";
    private static String titleColumn = "title";
    private static String contentColumn = "postContent";

    public DatabaseHandler(Context context) {
        super(context, forumTableName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDb) {
        String createForumTable = "CREATE TABLE " + forumTableName +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                titleColumn + " TEXT," +
                contentColumn + " TEXT)";

        sqLiteDb.execSQL(createForumTable);
    }

    public void createTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String createForumTable = "CREATE TABLE " + forumTableName +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                titleColumn + " TEXT," +
                contentColumn + " TEXT)";

        db.execSQL(createForumTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDb, int i, int i1) {
        sqLiteDb.execSQL("DROP IF TABLE EXISTS " + forumTableName);
        onCreate(sqLiteDb);
    }

    public boolean addData(String title, String content) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(titleColumn, title);
        values.put(contentColumn, content);

        long insertResult = db.insertOrThrow(forumTableName, null, values);

        //if date as inserted incorrectly it will return -1
        if (insertResult == -1) {
            return false;
        } else {
            return true;
        }

       // InsertHelper ih = new InsertHelper(db, forumTableName);

    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + forumTableName;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void deleteItem(String itemName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + forumTableName + " WHERE "
                + titleColumn + " = '" + itemName + "'";
        db.execSQL(query);
    }

    public void dropTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP table " + forumTableName);
    }
}
