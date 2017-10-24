package com.mershellerivera_offleash_final;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Users.db";
    private static final String TABLE_NAME = "Contacts";
    private static final String COLUMN_ID = "UserID";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_USERNAME = "Username";
    private static final String COLUMN_PASSWORD = "Password";
    SQLiteDatabase db;
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + " (" +
            COLUMN_ID + " integer primary key not null, " +
            COLUMN_NAME + " text not null, " +
            COLUMN_USERNAME + " text not null, " +
            COLUMN_PASSWORD + " text not null);";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        this.db = db;
    }

    public void deleteUser(String username){
        db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " = " + username;
        db.execSQL(query);
    }

    public void insertUser(User user){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(COLUMN_ID, count);
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_PASSWORD, user.getPassword());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public String searchPassword(String username){
        db = this.getReadableDatabase();
        String query = "select username, password from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        String uname, pw;
        pw = "Not Found";

        if(cursor.moveToFirst()){
            do{
                uname = cursor.getString(0);

                if(uname.equals(username)){
                    pw = cursor.getString(1);
                    break;
                }
            }while(cursor.moveToNext());
        }
        return pw;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }
}
