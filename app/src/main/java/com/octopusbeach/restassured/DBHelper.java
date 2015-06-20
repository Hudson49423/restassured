package com.octopusbeach.restassured;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.octopusbeach.restassured.model.Item;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by hudson on 6/20/15.
 */
public class DBHelper extends SQLiteOpenHelper {


    private static final int VERSION = 2;
    private static final String DATABASE_NAME = "itemsdb";
    private static final String TABLE_ITEMS = "items";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DATA = "item";
    private static final String[] COLUMNS = {COLUMN_ID, COLUMN_DATA};

    private Context context;

    private static final String CREATE_DB = "create table "
            + TABLE_ITEMS + "(" + COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_DATA + " GLOB)";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        sqLiteDatabase.execSQL(CREATE_DB);
    }

    public ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        try {
            Cursor cursor = db.query(TABLE_ITEMS, COLUMNS, null, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    byte[] bytes = cursor.getBlob(1);
                    ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                    ObjectInputStream ois = new ObjectInputStream(bis);
                    Item item = (Item) ois.readObject();
                    if (item != null) {
                        item.setId(cursor.getInt(0));
                        items.add(item);
                    }
                    ois.close();
                    bis.close();
                    cursor.moveToNext();
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
        return items;
    }

    public void addItem(Item item) {
        SQLiteDatabase db = getWritableDatabase();
        if (item != null) {
            ContentValues cv = new ContentValues();
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(item);
                oos.close();
                cv.put(COLUMN_DATA, bos.toByteArray());
                db.insert(TABLE_ITEMS, null, cv);
            } catch (Exception e) {
                e.printStackTrace();
            }
            db.close();
        }
    }

    public void updateItem(Item item) {
        SQLiteDatabase db = getWritableDatabase();
        if (item != null) {
            ContentValues cv = new ContentValues();
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(item);
                oos.close();
                cv.put(COLUMN_DATA, bos.toByteArray());
                cv.put(COLUMN_ID, item.getId());
                db.update(TABLE_ITEMS, cv, COLUMN_ID + " =?",
                        new String[]{String.valueOf(item.getId())});
            } catch (Exception e) {
                e.printStackTrace();
            }
            db.close();
        }
    }

    public void deleteItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEMS, COLUMN_ID
                + " =?", new String[]{String.valueOf(item.getId())});
        db.close();
    }
}
