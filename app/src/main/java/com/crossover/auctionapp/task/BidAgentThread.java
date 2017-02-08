package com.crossover.auctionapp.task;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.crossover.auctionapp.activity.IActivity;
import com.crossover.auctionapp.facade.PersistenceFacade;
import com.crossover.auctionapp.vo.ItemVO;

import java.util.Random;

/**
 * Thread that automatically closes item bids
 * Created by rafaelpino on 12/14/16.
 */
public class BidAgentThread extends Thread {
    PersistenceFacade facade = new PersistenceFacade();
    Context context;
    IActivity callBackActivity;
    public BidAgentThread(IActivity callbackActivity) {
        this.context = (AppCompatActivity)callbackActivity;
        this.callBackActivity = callbackActivity;
        this.setDaemon(true);
    }

    @Override
    public void run() {
        while(true){
            try{
                // Closes one auction item randomly
                ItemVO itemToClose = facade.getRandomItem(context);
                Log.d("AuctionApp","Closing item " + itemToClose.getName());
                itemToClose.setClosed(true);
                facade.updateItem(context,itemToClose);
                // Inserts a new item for auction
                Random random = new Random();
                int randId = 1 + random.nextInt(999);
                ItemVO itemToAuction = new ItemVO(null, "Item"+randId,Long.valueOf(500),5,5,null);
                boolean isInserted = facade.insertItem(context, itemToAuction);
                if(isInserted){
                    Log.d("AuctionApp","New Auction item inserted "+ itemToAuction.getName());
                }
                Thread.sleep(10000); //Sleep agent for 10 seconds
                callBackActivity.onAgentExecution();
            }catch ( Exception e){

            }
        }
    }
}
