package com.crossover.auctionapp.vo;

import android.content.ContentValues;

import com.crossover.auctionapp.schema.AppSchema;


/**
 * Created by rafaelpino on 12/13/16.
 */
public class ItemVO {
    private String rowId;
    private String name;
    private long price;
    private int bidIncrement;
    private int offerDuration;
    private String lastBidUser;
    private boolean isClosed;

    public ItemVO() {
    }

    public ItemVO(String rowId, String name, long price, int bidIncrement, int offerDuration, String lastBidUser) {
        this.rowId = rowId;
        this.name = name;
        this.price = price;
        this.bidIncrement = bidIncrement;
        this.offerDuration = offerDuration;
        this.lastBidUser = lastBidUser;
        this.isClosed = false;
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getBidIncrement() {
        return bidIncrement;
    }

    public void setBidIncrement(int bidIncrement) {
        this.bidIncrement = bidIncrement;
    }

    public int getOfferDuration() {
        return offerDuration;
    }

    public void setOfferDuration(int offerDuration) {
        this.offerDuration = offerDuration;
    }

    public String getLastBidUser() {
        return lastBidUser;
    }

    public void setLastBidUser(String lastBidUser) {
        this.lastBidUser = lastBidUser;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(AppSchema.ItemSchema.ITM_NAME, name);
        values.put(AppSchema.ItemSchema.ITM_PRICE, price);
        values.put(AppSchema.ItemSchema.ITM_BID_INCREMENT, bidIncrement);
        values.put(AppSchema.ItemSchema.ITM_DURATION,offerDuration);
        values.put(AppSchema.ItemSchema.ITM_LAST_BID_USER,lastBidUser);
        values.put(AppSchema.ItemSchema.ITM_IS_CLOSED,isClosed);
        return values;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" \n RowId : " + rowId);
        stringBuilder.append(" \n Name : " + name);
        stringBuilder.append(" \n Price : " + price);
        return stringBuilder.toString();
    }
}
