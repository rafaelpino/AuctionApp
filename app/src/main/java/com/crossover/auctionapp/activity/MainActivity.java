package com.crossover.auctionapp.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.crossover.auctionapp.R;
import com.crossover.auctionapp.adapter.ItemsAdapter;
import com.crossover.auctionapp.facade.PersistenceFacade;
import com.crossover.auctionapp.task.BidAgentThread;
import com.crossover.auctionapp.utils.ExtraUtils;
import com.crossover.auctionapp.vo.ItemVO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, IActivity {

    private String login;
    private PersistenceFacade facade = new PersistenceFacade();
    private boolean isShowingAvailableItems = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton addItemButton = (FloatingActionButton) findViewById(R.id.addItem);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemVO item = new ItemVO();
                showNewItemDialog("New Item", item);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        login = getIntent().getStringExtra(ExtraUtils.LOGIN_EXTRA);

        // Starts agent thread
        BidAgentThread bidAgentThread = new BidAgentThread(this);
        bidAgentThread.start();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        TextView loginText = (TextView) findViewById(R.id.main_Login);
        loginText.setText(login);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        isShowingAvailableItems = false;
        if (id == R.id.availableItems) {
            isShowingAvailableItems = true;
            getAvailableItems();
        } else if (id == R.id.itemsWon) {
            getItemsWon();
        } else if (id == R.id.editProfile) {
        }else{
            setTitle("Main Activity");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showNewItemDialog(String title, final ItemVO data){
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(MainActivity.this, R.style.AppTheme));
        LayoutInflater inflater = this.getLayoutInflater();
        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle(title);
        alertDialog.setIcon(android.R.drawable.ic_menu_zoom);
        final View dialogView = inflater.inflate(R.layout.dialog_item_create, null);
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_corners);
        alertDialog.setView(dialogView);

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String itemName = ((EditText)dialogView.findViewById(R.id.itemC_nameText)).getText().toString();
                        String itemPrice = ((EditText)dialogView.findViewById(R.id.itemC_priceText)).getText().toString();
                        String itemBidIncrement = ((EditText)dialogView.findViewById(R.id.itemC_bidText)).getText().toString();
                        String offerDuration = ((EditText)dialogView.findViewById(R.id.itemC_durationText)).getText().toString();
                        //TODO Validate form
                        data.setName(itemName);
                        data.setPrice(Long.valueOf(itemPrice));
                        data.setBidIncrement(Integer.valueOf(itemBidIncrement));
                        data.setOfferDuration(Integer.valueOf(offerDuration));
                        // Inserts new item
                        facade.insertItem(MainActivity.this, data);
                        getAvailableItems();
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


    @Override
    public void onClick(View view) {
        Object row = view.getTag();
        if(row instanceof ItemVO){
            //Update price after bid
            ItemVO item = (ItemVO) row;
            if(!item.isClosed()){
                item.setPrice(item.getPrice()+item.getBidIncrement());
                item.setLastBidUser(login);
                facade.updateItem(this, (ItemVO) row);
                getAvailableItems();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.HOUR_OF_DAY,item.getOfferDuration());
                Snackbar.make(findViewById(R.id.itemsView), "Bid for item " + item.getName() + " Offer ends at " + format.format(calendar.getTime()), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }else{
                Snackbar.make(findViewById(R.id.itemsView), "Item auction is closed", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        }
    }

    private void getAvailableItems(){
        List<ItemVO> items = facade.getAvailableItems(this);
        ItemsAdapter itemsAdapter = new ItemsAdapter(this,R.layout.auctionable_item, items.toArray(),this);
        ListView itemList = (ListView) findViewById(R.id.itemsView);
        itemList.setAdapter(itemsAdapter);
        TextView itemsAvailableLabel = (TextView) findViewById(R.id.itemsAvailableLabel);
        String label = itemsAvailableLabel.getText().toString();
        label = label.replaceFirst("\\d*",String.valueOf(items.size()));
        itemsAvailableLabel.setText(label);
        setTitle("Auction Items");
    }

    private void refreshAvailableItems(){
        List<ItemVO> items = facade.getAvailableItems(this);
        ListView itemList = (ListView) findViewById(R.id.itemsView);
        ItemsAdapter itemsAdapter = (ItemsAdapter) itemList.getAdapter();
        itemsAdapter.clear();
        itemsAdapter.addAll(items);
        itemsAdapter.notifyDataSetChanged();
        TextView itemsAvailableLabel = (TextView) findViewById(R.id.itemsAvailableLabel);
        String label = itemsAvailableLabel.getText().toString();
        label = label.replaceFirst("\\d*",String.valueOf(items.size()));
        itemsAvailableLabel.setText(label);
        setTitle("Auction Items");
    }

    private void getItemsWon(){
        List<ItemVO> items = facade.getItemsWonByLogin(this,login);
        ItemsAdapter itemsAdapter = new ItemsAdapter(this,R.layout.auctionable_item, items.toArray(),this);
        ListView itemList = (ListView) findViewById(R.id.itemsView);
        itemList.setAdapter(itemsAdapter);
        TextView itemsAvailableLabel = (TextView) findViewById(R.id.itemsAvailableLabel);
        String label = itemsAvailableLabel.getText().toString();
        label = label.replaceFirst("\\d*",String.valueOf(items.size()));
        itemsAvailableLabel.setText(label);
        setTitle("Items Won");
    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void clearTasks() {

    }

    @Override
    public void onAgentExecution() {
        if(isShowingAvailableItems){
            refreshAvailableItems();
        }
    }
}
