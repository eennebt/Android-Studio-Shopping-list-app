package com.example.elias.andorid_assighment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Created by ELIAS on 2/09/2019.
 */

public class HELPER {
    private static final String DB_TABLE = "products";
    private static final String TAG = "DBTABLE";

    private Context context;
    private SQLiteOpenHelper helper;
    public SQLiteDatabase db;
    private static final String COL1 = "id";
    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + " ( id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, quantity INTEGER,  product_name TEXT, price DOUBLE, location TEXT, photo BLOB NOT NULL );";


    public HELPER(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        db = helper.getWritableDatabase();
    }

    public HELPER openReadable() throws android.database.SQLException {
        helper = new SQLHelper(context);
        db = helper.getReadableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

    public boolean addRow(int quantity, String product_name, double price, String location, byte[] img) {
        synchronized(this.db) {
            ContentValues Student = new ContentValues();
            Student.put("quantity", quantity);
            Student.put("product_name", product_name);
            Student.put("price", price);
            Student.put("location", location);
            Student.put("photo", img);


            System.out.println(quantity + "  " + product_name + "    " + price);
            try {
                db.insertOrThrow(DB_TABLE, null, Student);
            } catch (Exception e) {
                Log.e("ERRROR IN ROW ", e.toString());
                e.printStackTrace();
                return false;
            }

            return true;
        }
    }
    public void clearRecords() {
        db = helper.getWritableDatabase();
        db.delete(DB_TABLE, null, null);
    }
    public int id;
    public ArrayList < String > retriveRows() {
        ArrayList < String > productRows = new ArrayList < > ();
        String[] columns = new String[] {
                "quantity",
                "product_name",
                "price",
                "location"
        };

        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        String trows = "";

        cursor.moveToFirst();
        id = 0;
        while (cursor.isAfterLast() == false) {
            id++;
            System.out.println("count= " + id);
            String ID = Integer.toString(id);

            productRows.add(ID + "\n" + cursor.getString(1) + "\n" + cursor.getInt(0) + "\n" + cursor.getDouble(2) + "\n" + cursor.getString(3));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return productRows;
    }

    public class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper(Context c) {
            super(c, DB_TABLE, null, 87);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("Products table", "Upgrading database i.e. dropping table and re-creating it");
            db.execSQL("DROP TABlE IF EXISTS " + DB_TABLE);
            onCreate(db);
        }
    }

    public Cursor getItemID(String name) {
        db = helper.getWritableDatabase();
        String[] columns = new String[] {
                "quantity ",
                "product_name ",
                "price",
                "location"
        };

        String query = "SELECT *" + " FROM " + DB_TABLE + " WHERE " + id + " = '" + name.charAt(0) + "'";
        //    Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null );
        Cursor data = db.rawQuery(query, null);
        return data;

    }

    public void updatename(String newname, int id, String newlocation, double newprice, int newquantity ) {
        db = helper.getWritableDatabase();

        String query = "UPDATE " + DB_TABLE + " SET " + "product_name" + " = '" + newname + "'" + ", " + "location" + " = '" + newlocation + "'" + ", " + "quantity" + " = '" + newquantity + "'" +", " + "price" + " = '" + newprice + "'" + " WHERE " + id + " = " + "id";

System.out.println("quantity: " + newquantity + " new price " + newprice);

        db.execSQL(query);
        db.close();

    }

    public void deleteName(int id) {
        db = helper.getWritableDatabase();

        String query = "DELETE FROM  " + DB_TABLE + " WHERE " + id + " = " + "id";
        db.execSQL(query);
    }


    public Bitmap showpic(int i) {
        Bitmap theImage = null;
        db = helper.getReadableDatabase();
        try {
            String[] columns = new String[] {
                    "quantity",
                    "product_name",
                    "price",
                    "location",
                    "photo"
            };

            Cursor cursor1 = db.query(DB_TABLE, columns, null, null, null, null, null);

            cursor1.move(i);

            byte[] imageByArray = cursor1.getBlob(4);
            cursor1.close();
            ByteArrayInputStream imageStream = new ByteArrayInputStream(imageByArray);
            theImage = BitmapFactory.decodeStream(imageStream);

        } catch (Exception exception) {
            Log.w(TAG, "FAILED RESOLEVE STACK " + exception);
            close();
        }
        return theImage;

    }



}