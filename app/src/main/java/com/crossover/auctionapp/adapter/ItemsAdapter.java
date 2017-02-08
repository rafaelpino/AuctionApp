package com.crossover.auctionapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.widget.TextViewCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.crossover.auctionapp.R;
import com.crossover.auctionapp.vo.ItemVO;


/**
 * Created by rafaelpino on 10/11/16.
 */
public class ItemsAdapter extends ArrayAdapter{
    private final int layoutResourceId;
    private final Context context;
    private final View.OnClickListener listener;
    Object data[] = null;

    public ItemsAdapter(Context context, int layoutResourceId, Object[] data, View.OnClickListener onClickListener) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.listener = onClickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ItemHolder holder = null;

        if(row == null)
        {
            // Create view
            LayoutInflater inflater = ((Activity)super.getContext()).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ItemHolder();
            holder.itemIcon = (ImageView)row.findViewById(R.id.itemImage);
            holder.itemName = (TextView)row.findViewById(R.id.itemName);
            holder.itemValue = (TextView)row.findViewById(R.id.itemValue);
            holder.itemNextBidValue = (TextView)row.findViewById(R.id.itemNextBidValue);
            row.setTag(holder);
        }
        else
        {
            holder = (ItemHolder) row.getTag();
        }

        ItemVO item = (ItemVO) data[position];
        holder.itemName.setText("Name \n" + item.getName());
        holder.itemValue.setText("Price \n"+ String.valueOf(item.getPrice()));
        holder.itemNextBidValue.setText("Next Bid \n" +String.valueOf(item.getPrice()+ item.getBidIncrement()));

        if(item.isClosed()){
            holder.itemIcon.setImageResource(android.R.drawable.btn_star_big_off);
        }else{
            holder.itemIcon.setImageResource(android.R.drawable.btn_star_big_on);
        }

        holder.itemIcon.setOnClickListener(this.listener);
        holder.itemIcon.setTag(item);

        return row;
    }

    static class ItemHolder
    {
        ImageView itemIcon;
        TextView itemName;
        TextView itemValue;
        TextView itemNextBidValue;
    }
}
