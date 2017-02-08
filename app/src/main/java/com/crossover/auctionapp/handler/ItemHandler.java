package com.crossover.auctionapp.handler;

import android.content.Context;

import com.crossover.auctionapp.vo.ItemVO;
import com.crossover.auctionapp.vo.UserVO;

import java.util.List;

/**
 * Created by rafaelpino on 12/13/16.
 */
public class ItemHandler {
    DatabaseHandler dataHandler;

    /***
     * Method to query all items
     * @param context
     * @return
     */
    public List<ItemVO> getAuctionableItems(Context context){
        dataHandler = DatabaseHandler.getInstance(context);
        List<ItemVO> items = dataHandler.getItemDAO().getItems(dataHandler.getReadableDatabase());
        return items;
    }

    public List<ItemVO> getItemsWonByLogin(Context context,String login){
        dataHandler = DatabaseHandler.getInstance(context);
        List<ItemVO> items = dataHandler.getItemDAO().getItemsWonByLogin(dataHandler.getReadableDatabase(),login);
        return items;
    }

    /**
     * Method to insert an item
     * @param context
     * @param item
     * @return
     */
    public boolean insertItem(Context context, ItemVO item){
        dataHandler = DatabaseHandler.getInstance(context);
        long result = dataHandler.getItemDAO().saveItem(item, dataHandler.getWritableDatabase());
        if(result >=0){
            return true;
        }else{
            return false;
        }
    }

    public boolean updateItem(Context context, ItemVO item){
        dataHandler = DatabaseHandler.getInstance(context);
        long result = dataHandler.getItemDAO().updateItem(item, dataHandler.getWritableDatabase());
        if(result >=0){
            return true;
        }else{
            return false;
        }
    }

    public ItemVO getRandomItem(Context context){
        dataHandler = DatabaseHandler.getInstance(context);
        return dataHandler.getItemDAO().getRandomItem(dataHandler.getReadableDatabase());
    }
}
