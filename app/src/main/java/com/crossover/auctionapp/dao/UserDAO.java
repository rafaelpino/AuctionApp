package com.crossover.auctionapp.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.crossover.auctionapp.schema.AppSchema;
import com.crossover.auctionapp.vo.UserVO;
import java.util.ArrayList;

/**
 * Created by rafaelpino on 12/13/16.
 */
public class UserDAO {

    /***
     * Insert method
     * @param record
     * @param sqLiteDatabase
     * @return
     */
    public long saveUserProfile(UserVO record, SQLiteDatabase sqLiteDatabase) {
        long result =-1;
        try {
            result = sqLiteDatabase.insert(
                    AppSchema.UserSchema.TABLE_NAME,
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
    public long updateUserProfile(UserVO record, SQLiteDatabase sqLiteDatabase) {
        long result =-1;
        try {
            result = sqLiteDatabase.update(AppSchema.UserSchema.TABLE_NAME,record.toContentValues(),"_id= ? ",new String[]{record.getRowId()});
        }catch (Exception e){
            Log.e("ERROR", e.getMessage());
        }
        return result;
    }

    /***
     * Full query method
     * @return
     */
    public ArrayList getUserProfiles(SQLiteDatabase sqLiteDatabase){
        Cursor cursor = sqLiteDatabase.rawQuery(" select * from " + AppSchema.UserSchema.TABLE_NAME, null);
        ArrayList<UserVO> result = new ArrayList<UserVO>();
        if(cursor!=null && cursor.getCount()>0){
                cursor.moveToFirst();
                do {
                    String rowId= cursor.getString(0);
                    String login= cursor.getString(1);
                    String usrName = cursor.getString(2);
                    String usrLastName = cursor.getString(3);
                    int usrAge = cursor.getInt(4);
                    String usrGender = cursor.getString(5);
                    int usrDocID = cursor.getInt(6);
                    String usrPass = cursor.getString(7);
                    UserVO row = new UserVO(rowId,login,usrName,usrLastName,usrAge,usrGender,usrDocID,usrPass);
                    result.add(row);
                } while(cursor.moveToNext());
        }
        return result;
    }

    /***
     * Query user by login
     * @param usrLogin
     * @param sqLiteDatabase
     * @return
     */
    public UserVO getUserProfileByLogin(String usrLogin , SQLiteDatabase sqLiteDatabase){
        Cursor cursor = sqLiteDatabase.rawQuery(" select * from " + AppSchema.UserSchema.TABLE_NAME
                + " where " + AppSchema.UserSchema.USR_LOGIN
                +" == '" + usrLogin + "'", null);
        UserVO row = null;
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                String rowId= cursor.getString(0);
                String login= cursor.getString(1);
                String usrName = cursor.getString(2);
                String usrLastName = cursor.getString(3);
                int usrAge = cursor.getInt(4);
                String usrGender = cursor.getString(5);
                int usrDocID = cursor.getInt(6);
                String usrPass = cursor.getString(7);
                row = new UserVO(rowId,login,usrName,usrLastName,usrAge,usrGender,usrDocID,usrPass);
                break;
            } while(cursor.moveToNext());
        }
        return row;
    }
}
