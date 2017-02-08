package com.crossover.auctionapp.facade;

import android.content.Context;

import com.crossover.auctionapp.handler.ItemHandler;
import com.crossover.auctionapp.handler.UserHandler;
import com.crossover.auctionapp.vo.ItemVO;
import com.crossover.auctionapp.vo.UserVO;

import java.util.List;

/**
 * Created by rafaelpino on 12/13/16.
 */
public class PersistenceFacade {
    UserHandler userHandler = new UserHandler();
    ItemHandler itemHandler = new ItemHandler();

    public boolean authenticate(Context context, String login , String password){
        return userHandler.authenticate(context,login,password);
    }

    public UserVO getUserByLogin(Context context, String login){
        return userHandler.getUserByLogin(context,login);
    }

    public boolean insertUser(Context context, UserVO user){
        return userHandler.insertUser(context,user);
    }

    public List<ItemVO> getAvailableItems(Context context){
        return itemHandler.getAuctionableItems(context);
    }

    public List<ItemVO> getItemsWonByLogin(Context context,String login){
        return itemHandler.getItemsWonByLogin(context,login);
    }

    public boolean insertItem(Context context, ItemVO item){
        return itemHandler.insertItem(context,item);
    }

    public boolean updateItem(Context context, ItemVO item){
        return itemHandler.updateItem(context,item);
    }

    public ItemVO getRandomItem(Context context){
        return itemHandler.getRandomItem(context);
    }
}
