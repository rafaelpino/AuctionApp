package com.crossover.auctionapp.handler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.crossover.auctionapp.dao.ItemDAO;
import com.crossover.auctionapp.dao.UserDAO;
import com.crossover.auctionapp.schema.AppSchema;


/**
 * Created by rafaelpino on 12/13/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String  DATABASE_FILE_PATH = Environment.getExternalStorageDirectory().getPath();
    public static final String DATABASE_NAME = "AuctionApp.db";
    private UserDAO userDAO;
    private ItemDAO itemDAO;
    protected static DatabaseHandler instance;

    public static DatabaseHandler getInstance(Context context){
        if (instance == null){
            instance = new DatabaseHandler(context);
        }
        return instance;
    }

    private DatabaseHandler(Context context){
        super(context,DATABASE_FILE_PATH+"/"+DATABASE_NAME,null,DATABASE_VERSION);
        this.userDAO = new UserDAO();
        this.itemDAO = new ItemDAO();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        sqLiteDatabase.execSQL("CREATE TABLE " + AppSchema.UserSchema.TABLE_NAME + " ("
                + AppSchema.UserSchema._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + AppSchema.UserSchema.USR_LOGIN + " TEXT NOT NULL,"
                + AppSchema.UserSchema.USR_NAMES + " TEXT NOT NULL,"
                + AppSchema.UserSchema.USR_LAST_NAMES + " TEXT NOT NULL,"
                + AppSchema.UserSchema.USR_AGE + " INTEGER NOT NULL,"
                + AppSchema.UserSchema.USR_GENDER + " TEXT NOT NULL,"
                + AppSchema.UserSchema.USR_DOCID + " INTEGER NOT NULL,"
                + AppSchema.UserSchema.USR_PASSWORD + " TEXT NOT NULL"
                + ")");

        Log.d("INFO","Created: "+ AppSchema.UserSchema.TABLE_NAME);

        sqLiteDatabase.execSQL("CREATE TABLE " + AppSchema.ItemSchema.TABLE_NAME + " ("
                + AppSchema.ItemSchema._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + AppSchema.ItemSchema.ITM_NAME + " TEXT NOT NULL,"
                + AppSchema.ItemSchema.ITM_PRICE + " INTEGER NOT NULL,"
                + AppSchema.ItemSchema.ITM_BID_INCREMENT + " INTEGER NOT NULL,"
                + AppSchema.ItemSchema.ITM_DURATION + " INTEGER NOT NULL,"
                + AppSchema.ItemSchema.ITM_LAST_BID_USER + " TEXT ,"
                + AppSchema.ItemSchema.ITM_IS_CLOSED + " INTEGER NOT NULL"
                + ")");

        Log.d("INFO","Created: "+ AppSchema.ItemSchema.TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public ItemDAO getItemDAO() {
        return itemDAO;
    }

    public void setItemDAO(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }
}
