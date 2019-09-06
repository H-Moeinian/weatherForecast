package com.example.weatherforecast;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class OpenDBHelper extends SQLiteOpenHelper {
    public  static final String DATA_BASE_NAME = "cities.db";
    String TABLE_NAME = "cities_table";
    String COL_1 = "ID";
    String COL_2 = "NAME";

    public OpenDBHelper( Context context) {
        super(context, DATA_BASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY = " CREATE TABLE " + TABLE_NAME + " (" + COL_1 +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " TEXT " + " )";
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);

    }

    public void addCity(String name) {
        if (name.matches("[a-zA-Z]+")) {

            SQLiteDatabase db = this.getWritableDatabase();
            String insertQuery = "INSERT INTO " + TABLE_NAME +
                    "(" + COL_2 + ")" +
                    "VALUES( '" + name + "')";
            db.execSQL(insertQuery);
            db.close();
        }
    }


    public List<String> getExistingCities(){
        String GET_EXISTING_CITY = "SELECT DISTINCT NAME FROM " + TABLE_NAME;

        List<String> cities = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(GET_EXISTING_CITY, null);

        while(cursor.moveToNext()){

            cities.add(cursor.getString(0));
        }
        db.close();
        return cities;
    }
}
