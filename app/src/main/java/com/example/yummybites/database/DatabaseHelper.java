package com.example.yummybites.database;

import static com.example.yummybites.utils.AppConstant.ADDRESS;
import static com.example.yummybites.utils.AppConstant.ADDRESS_USER_ID;
import static com.example.yummybites.utils.AppConstant.DATABASE_NAME;
import static com.example.yummybites.utils.AppConstant.TABLE_NAME;
import static com.example.yummybites.utils.AppConstant.TABLE_NAME_ADDRESS;
import static com.example.yummybites.utils.AppConstant.col_1;
import static com.example.yummybites.utils.AppConstant.col_2;
import static com.example.yummybites.utils.AppConstant.col_3;
import static com.example.yummybites.utils.AppConstant.col_4;
import static com.example.yummybites.utils.AppConstant.col_5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DatabaseHelper extends SQLiteOpenHelper {

    SQLiteDatabase sqLiteDatabase;


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " " +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT , NAME TEXT , EMAIL TEXT , " +
                " PASSWORD TEXT , PHONE_NO TEXT) ");
        sqLiteDatabase.execSQL(" CREATE TABLE IF NOT EXISTS " + TABLE_NAME_ADDRESS + " " +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, ADDRESS_USER_ID INTEGER, ADDRESS TEXT ) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME_ADDRESS);
        onCreate(sqLiteDatabase);
    }

    public boolean registerUser(String name, String email, String password, String phone_no) {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2, name);
        contentValues.put(col_3, email);
        contentValues.put(col_4, password);
        contentValues.put(col_5, phone_no);

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if (result == -1 || result == 0)
            return false;
        else
            return true;
    }


    public boolean checkUserValidate(String email) {
        sqLiteDatabase = this.getWritableDatabase();
        String columns[] = {
                col_1
        };
        String selection = col_3 + "=?";
        String[] selection_args = {email};
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, columns, selection,
                selection_args, null, null, null);
        int count = cursor.getCount();
//        sqLiteDatabase.close();
        cursor.close();
        if (count > 0)
            return false;
        else
            return true;
    }


    public boolean checkUser(String email, String password) {
        sqLiteDatabase = this.getWritableDatabase();
        String columns[] = {
                col_1
        };
        String selection = col_3 + "=?" + " and " + col_4 + "=?";
        String[] selection_args = {email, password};
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, columns, selection,
                selection_args, null, null, null);
        int count = cursor.getCount();
//        sqLiteDatabase.close();
        cursor.close();
        if (count > 0)
            return true;
        else
            return false;
    }

    public Cursor getNameFromDb(String email) {
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[]{
                        col_2
                }, col_3 + "=?", new String[]{email},
                null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
//        Log.d("TAG", "getNameFromDb: "+cursor.getString(0));
        return cursor;
    }

    public Cursor getNumberFromDb(String email) {
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[]{
                        col_5
                }, col_3 + "=?", new String[]{email},
                null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
//        Log.d("TAG", "getNameFromDb: "+cursor.getString(0));SELECT PHONE_NO FROM USER_DATA WHERE EMAIL=?
        return cursor;
    }

    public boolean registerAddress(String address, int i) {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ADDRESS_USER_ID, i);
        contentValues.put(ADDRESS, address);
        long result = sqLiteDatabase.insert(TABLE_NAME_ADDRESS, null, contentValues);
        if (result == -1 || result == 0)
            return false;
        else
            return true;
    }

    public boolean updateData(String id, String email, String phoneno) {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_1, id);
        contentValues.put(col_3, email);
        contentValues.put(col_5, phoneno);
        sqLiteDatabase.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    public Cursor getIdFromDb(String email) {
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[]{
                        col_1
                }, col_3 + "=?", new String[]{email},
                null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
//        Log.d("TAG", "getNameFromDb: "+cursor.getString(0));
        return cursor;
    }

    public Cursor getAddressFromDb(int id) {
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_ADDRESS, new String[]{
                        ADDRESS
                }, ADDRESS_USER_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
//        Log.d("TAG", "getNameFromDb: "+cursor.getString(0));
        return cursor;
    }

    public boolean updateAddress(String address) {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ADDRESS, address);
        sqLiteDatabase.update(TABLE_NAME_ADDRESS, contentValues, "ADDRESS = ?", new String[]{address});
        return true;
    }

    public void deleteAddress(String address) {

        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME_ADDRESS, "ADDRESS=?", new String[]{address});
//        sqLiteDatabase.close();
    }


//     public void insertEmpty()
//     {
//         sqLiteDatabase = this.getWritableDatabase();
//         ContentValues cv = new ContentValues();
//         int count = FoodRatingAdapter.count();
//         for(int x = 1;x<count;x++)
//         {
//             cv.put(TABLE2_ID,x);
//             cv.put(fav_ratingfood,"0");
//             sqLiteDatabase.insert(TABLE_NAME,null,cv);
//         }
//     }

//     public void insetIntoDb(String img , String name , String category , String price , String rating,String fav)
//     {
//         sqLiteDatabase = this.getWritableDatabase();
//         ContentValues contentValues   = new ContentValues();
//         contentValues.put(img_ratingfood,img);
//         contentValues.put(name_ratingfood,name);
//         contentValues.put(category_ratingfood,category);
//         contentValues.put(priceof_ratingfood,price);
//         contentValues.put(rating_ratingfood,rating);
//         contentValues.put(fav_ratingfood,fav);
//         Log.d("Fav_status",name+", fav_status,"+fav);
//     }
//
//     public Cursor readAllData(String id)
//     {
//         sqLiteDatabase = this.getReadableDatabase();
//         String sql = "SELECT * FROM "+TABLE2_NAME+"WHERE"+TABLE2_ID+"="+id+"";
//         return  sqLiteDatabase.rawQuery(sql,null,null);
//     }
//
//     public void removeFav(String id)
//     {
//         sqLiteDatabase = this.getWritableDatabase();
//         String sql = "UPDATE"+TABLE2_NAME + "SET" + fav_ratingfood+"='0' WHERE"+TABLE2_ID+"="+id+"";
//         sqLiteDatabase.execSQL(sql);
//         Log.d("remove",id.toString());
//     }
//
//     public Cursor select_fav_list()
//     {
//         sqLiteDatabase = this.getReadableDatabase();
//         String sql = "SELECT * FROM "+TABLE2_NAME+" WHERE "+fav_ratingfood+" = '1'";
//         return  sqLiteDatabase.rawQuery(sql,null,null);
//     }


}
