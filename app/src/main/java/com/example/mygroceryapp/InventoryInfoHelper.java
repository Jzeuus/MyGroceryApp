package com.example.mygroceryapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class InventoryInfoHelper extends SQLiteOpenHelper{
    // The URI scheme used for content URIs
    public static final String SCHEME = "content";

    // The provider's authority
    public static final String AUTHORITY = "edu.ncc.introtodatabases";

    public static final Uri CONTENT_URI = Uri.parse(SCHEME + "://" + AUTHORITY);

    // table name
    public static final String TABLE_NAME = "product_inventory";

    // columns in the table
    public static final String _ID = "_id";
    public static final String ITEM_ID = "item_id";
    public static final String ITEM_NAME = "item_name";
    public static final String PANTRY_LIFE = "pantry_life";
    public static final String BARCODE_NUM = "barcode_num";

    // database version and name
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "inventoryInformation.db";

    public InventoryInfoHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + _ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + ITEM_ID +
                " TEXT, " + ITEM_NAME + " TEXT, " + PANTRY_LIFE + " TEXT, " + BARCODE_NUM + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}