package com.crossover.auctionapp.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.crossover.auctionapp.schema.AppSchema;
import com.crossover.auctionapp.vo.ItemVO;
import com.crossover.auctionapp.vo.UserVO;

import java.util.ArrayList;

/**
 * Created by rafaelpino on 12/13/16.
 */
public class ItemDAO {

    /***
     * Insert method
     * @param record
     * @param sqLiteDatabase
     * @return
     */
    public long saveItem(ItemVO record, SQLiteDatabase sqLiteDatabase) {
        long result =-1;
        try {
            result = sqLiteDatabase.insert(
                    AppSchema.ItemSchema.TABLE_NAME,
                    null,
                    record.toContentValues());
        }catch (Exception e){
            Log.e("ERROR", e.getMessage());
        }
        return result;
    }

    /***
     * Update method
     * @param record
     * @param sqLiteDatabase
     * @return
     */
    public long updateItem(ItemVO record, SQLiteDatabase sqLiteDatabase) {
        long result =-1;
        try {
            result = sqLiteDatabase.update(AppSchema.ItemSchema.TABLE_NAME,record.toContentValues(),"_id= ? ",new String[]{record.getRowId()});
        }catch (Exception e){
            Log.e("ERROR", e.getMessage());
        }
        return result;
    }

    /***
     * Full query method
     * @return
     */
    public ArrayList getItems(SQLiteDatabase sqLiteDatabase){
        Cursor cursor = sqLiteDatabase.rawQuery(" select * from " + AppSchema.ItemSchema.TABLE_NAME + " where "+AppSchema.ItemSchema.ITM_IS_CLOSED+ " == 0 ", null);
        ArrayList<ItemVO> result = new ArrayList<ItemVO>();
        if(cursor!=null && cursor.getCount()>0){
                cursor.moveToFirst();
                do {
                    String rowId= cursor.getString(0);
                    String name= cursor.getString(1);
                    long price = cursor.getLong(2);
                    int bidIncrement = cursor.getInt(3);
                    int duration = cursor.getInt(4);
                    String lastBidUser = cursor.getString(5);
                    int isClosed = cursor.getInt(6);
                    ItemVO row = new ItemVO(rowId,name,price,bidIncrement, duration,lastBidUser);
                    row.setClosed(isClosed == 1 ? true : false);
                    result.add(row);
                } while(cursor.moveToNext());
        }
        return result;
    }

    /**
     * Get items closed by login
     * @param sqLiteDatabase
     * @return
     */
    public ArrayList getItemsWonByLogin(SQLiteDatabase sqLiteDatabase,String login){
        Cursor cursor = sqLiteDatabase.rawQuery(" select * from " + AppSchema.ItemSchema.TABLE_NAME + " where "+AppSchema.ItemSchema.ITM_IS_CLOSED+ " == 1 and "+ AppSchema.ItemSchema.ITM_LAST_BID_USER + " == '"+ login+"'", null);
        ArrayList<ItemVO> result = new ArrayList<ItemVO>();
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                String rowId= cursor.getString(0);
                String name= cursor.getString(1);
                long price = cursor.getLong(2);
                int bidIncrement = cursor.getInt(3);
                int duration = cursor.getInt(4);
                String lastBidUser = cursor.getString(5);
                int isClosed = cursor.getInt(6);
                ItemVO row = new ItemVO(rowId,name,price,bidIncrement, duration,lastBidUser);
                row.setClosed(isClosed == 1 ? true : false);
                result.add(row);
            } while(cursor.moveToNext());
        }
        return result;
    }

    /***
     * Get random item
     * @return
     */
    public ItemVO getRandomItem(SQLiteDatabase sqLiteDatabase){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+AppSchema.ItemSchema.TABLE_NAME+ " ORDER BY RANDOM() LIMIT 1",null);
        ItemVO row = new ItemVO();
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                String rowId= cursor.getString(0);
                String name= cursor.getString(1);
                long price = cursor.getLong(2);
                int bidIncrement = cursor.getInt(3);
                int duration = cursor.getInt(4);
                String lastBidUser = cursor.getString(5);
                int isClosed = cursor.getInt(6);
                row = new ItemVO(rowId,name,price,bidIncrement, duration,lastBidUser);
                row.setClosed(isClosed == 1 ? true : false);
                break;
            } while(cursor.moveToNext());
        }
        return row;
    }

    /***
     * Query user by login
     * @param rowId
     * @param sqLiteDatabase
     * @return
     */
    public ItemVO getItemByRowId(String rowId , SQLiteDatabase sqLiteDatabase){
        Cursor cursor = sqLiteDatabase.rawQuery(" select * from " + AppSchema.ItemSchema.TABLE_NAME
                + " where " + AppSchema.ItemSchema.ID
                +" == '" + rowId + "'", null);
        ItemVO row = null;
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                rowId= cursor.getString(0);
                String name= cursor.getString(1);
                long price = cursor.getLong(2);
                int bidIncrement = cursor.getInt(3);
                int duration = cursor.getInt(4);
                String lastBidUser = cursor.getString(5);
                row = new ItemVO(rowId,name,price,bidIncrement,duration, lastBidUser);
                break;
            } while(cursor.moveToNext());
        }
        return row;
    }
}
