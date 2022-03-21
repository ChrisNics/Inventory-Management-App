package com.example.inventorymanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class DBHelper extends SQLiteOpenHelper  {

//    public static final String dbName="login.db";

    public DBHelper(Context context) {
        super(context, "products.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase myDB) {
        myDB.execSQL("create Table products (name TEXT, uom TEXT, quantity TEXT, category TEXT, low TEXT, nameUOM TEXT primary key)");
        myDB.execSQL("create Table transactions (name TEXT, type TEXT, uom TEXT, quantity TEXT, date TEXT, random INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase myDB, int oldVersion, int newVersion) {
        myDB.execSQL("drop Table if exists products");
        myDB.execSQL("drop Table if exists transactions");


    }

    public ArrayList<String> getProducts () {

        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase myDb= this.getReadableDatabase();

        Cursor cursor = myDb.query("products",
                new String[]{"name", "uom", "quantity", "category", "low"},
                null,
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()){
            if (Integer.parseInt(cursor.getString(2)) <= Integer.parseInt(cursor.getString(4))) {
                list.add(cursor.getString(cursor.getColumnIndex("name")) + ' '
                        + '(' + cursor.getString(cursor.getColumnIndex("uom")) + ')'
                        + padLeft(cursor.getString(cursor.getColumnIndex("category")), 40) + "\n\n"
                        + 'x' + cursor.getString(cursor.getColumnIndex("quantity")) +  "❗");


            } else {

                list.add(cursor.getString(cursor.getColumnIndex("name")) + ' '
                        + '(' + cursor.getString(cursor.getColumnIndex("uom")) + ')'
                        + padLeft(cursor.getString(cursor.getColumnIndex("category")), 40) + "\n\n"
                        + 'x' + cursor.getString(cursor.getColumnIndex("quantity")));
            }
        }
        return list;
    }



    public String getLowFinal(int position) {
        String low;
        SQLiteDatabase myDb= this.getReadableDatabase();
        Cursor cursor = myDb.query("products",
                new String[]{"name", "uom", "quantity", "category", "low"},
                null,
                null,
                null,
                null,
                null);
        cursor.moveToPosition(position);
        low = cursor.getString(cursor.getColumnIndex("low"));
        return  low;
    }






    public ArrayList<String> getLowProducts () {

        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase myDb= this.getReadableDatabase();

        Cursor cursor = myDb.query("products",
                new String[]{"name", "uom", "quantity", "category", "low"},
                null,
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()){

            if (Integer.parseInt(cursor.getString(2)) <= Integer.parseInt(cursor.getString(4)))
            list.add(cursor.getString(cursor.getColumnIndex("name")) + ' '
                    +  "(" + cursor.getString(cursor.getColumnIndex("uom")) + ')'
                    + padLeft(cursor.getString(cursor.getColumnIndex("category")), 40) + "\n\n"
                    + "x" + cursor.getString(cursor.getColumnIndex("quantity")));
        }
        return list;
    }

    public static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    public static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }





    public boolean insertData(String name, String uom, String quantity, String category, String low){
        SQLiteDatabase myDB= this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put("name",name);
        cv.put("uom",uom);
        cv.put("quantity",quantity);
        cv.put("category",category);
        cv.put("low",low);
        cv.put("nameUOM",name + " " + "(" +uom+ ")");



        long result= myDB.insert("products",null,cv);
        if (result==-1) return false;
        else
            return true;
    }


    public boolean insertTransactions(String name, String uom, String quantity, String type, String date){
        Random r = new Random();
        String i1 = UUID.randomUUID().toString().toUpperCase();

        SQLiteDatabase myDB= this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put("name",name);
        cv.put("uom",uom);
        cv.put("quantity",quantity);
        cv.put("type",type);
        cv.put("date", date);



        long result= myDB.insert("transactions",null,cv);
        if (result==-1) return false;
        else
            return true;
    }

    int totalIn = 0, totalOut  =0;
//
    public ArrayList<String> getTransactions () {

        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase myDb= this.getReadableDatabase();
//        Cursor c = scoreDb.query(Table_Name, score, null, null, null, null, Column+" DESC");
        Cursor cursor = myDb.query("transactions",
                new String[]{"name", "type", "uom", "quantity", "date", "random"},
                null,
                null,
                null,
                null,
                "random"  + " DESC",
                null);

        while (cursor.moveToNext()){

                list.add(padRight(cursor.getString(cursor.getColumnIndex("name")), 30 )+ ' '
                        + cursor.getString(cursor.getColumnIndex("date")) + "\n\n"
                        +  "x" + cursor.getString(cursor.getColumnIndex("quantity")) + "\n\n"
                        + cursor.getString(cursor.getColumnIndex("type")));

            if(cursor.getString(cursor.getColumnIndex("type")).equals("IN")) {
                totalIn +=Integer.parseInt(cursor.getString(cursor.getColumnIndex("quantity")));
                displayTotalIn();
            } else {
                totalOut +=Integer.parseInt(cursor.getString(cursor.getColumnIndex("quantity")));
                displayTotalOut();
            }

        }


        return list;
    }

    public int displayTotalIn () {
        return  totalIn;
    }
    public int displayTotalOut () {
        return  totalOut;
    }






    public boolean updateData (String nameOnly, String name, String uom, String Low, String category){
        SQLiteDatabase myDB= this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("uom", uom);
        cv.put("low", Low);
        cv.put("category", category);
        cv.put("nameUOM",name + " " + "(" +uom+ ")");


        Cursor cursor = myDB.rawQuery("Select * from products where nameUOM=?",new String[]{nameOnly});
        if(cursor.getCount()>0) {
            long result= myDB.update("products",cv,"nameUOM=?",new String[]{nameOnly});
            if (result == -1) {
                return false;
            }else{
                return true;
            }
        }else{
            return  false;
        }
    }



    public boolean checkusernamepassword(String username,String password){
        SQLiteDatabase myDB= this.getWritableDatabase();
        Cursor cursor= myDB.rawQuery("Select * from users where username=? and password=?", new String[]{username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public boolean deleteData(String name){
        SQLiteDatabase myDB= this.getWritableDatabase();
        long result = myDB.delete("products","nameUOM = ?", new String[]{name});
        if (result==-1) return false;
        else
            return true;
    }



    public boolean addData (String mainQuantity, String nameOnly){
        SQLiteDatabase myDB= this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("quantity", mainQuantity);


        Cursor cursor = myDB.rawQuery("Select * from products where nameUOM=?",new String[]{nameOnly});
        if(cursor.getCount()>0) {
            long result= myDB.update("products",cv,"nameUOM=?",new String[]{nameOnly});
            if (result == -1) {
                return false;
            }else{
                return true;
            }
        }else{
            return  false;
        }
    }

    public void clearData(String transactions) {
        SQLiteDatabase myDB= this.getWritableDatabase();
        String clearDBQuery = "DELETE FROM "+transactions;
        myDB.execSQL(clearDBQuery);
    }

}

