package com.crossover.auctionapp.handler;

import android.content.Context;

import com.crossover.auctionapp.vo.UserVO;

/**
 * Created by rafaelpino on 12/13/16.
 */
public class UserHandler {
    DatabaseHandler dataHandler;

    /***
     * Method for user authentication against SQL Lite database
     * @param context
     * @param login
     * @param password
     * @return
     */
    public boolean authenticate(Context context , String login, String password){
        dataHandler = DatabaseHandler.getInstance(context);
        UserVO user = dataHandler.getUserDAO().getUserProfileByLogin(login, dataHandler.getReadableDatabase());
        boolean authenticated  = false;
        if(null != user){
            if(password.equalsIgnoreCase(user.getPassword())){
                authenticated = true;
            }
        }
        return authenticated;
    }

    /***
     * Method to query a user by login
     * @param context
     * @param login
     * @return
     */
    public UserVO getUserByLogin(Context context, String login){
        dataHandler = DatabaseHandler.getInstance(context);
        return dataHandler.getUserDAO().getUserProfileByLogin(login, dataHandler.getReadableDatabase());
    }

    public boolean insertUser(Context context, UserVO user){
        dataHandler = DatabaseHandler.getInstance(context);
        long result = dataHandler.getUserDAO().saveUserProfile(user, dataHandler.getWritableDatabase());
        if(result >=0){
            return true;
        }else{
            return false;
        }
    }
}
