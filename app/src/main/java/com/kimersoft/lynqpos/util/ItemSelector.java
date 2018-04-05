package com.kimersoft.lynqpos.util;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;

import com.kimersoft.lynqpos.R;

/**
 * Created by Gadour on 07/12/2017.
 */

public class ItemSelector {

    public static final String WAITING_ITEM = "waitingItem";
    public static final String STARTED_ITEM = "startedItem";
    public static final String DELIVER_ITEM = "deliverItem";
    public static final String PICKUP_ITEM = "pickupItem";
    public static final String ALL_ITEM = "allItem";
    public static final String CLOSED_ITEM = "closedItem";


    private int allBg, waitingBgd, startedBgd, deliverBgd, pickupBgd, closedBgd, waitingHeader, startedHeader, deliverHeader,
                pickupHeader, closedHeader, allHeader;
    private View header, background, tableText;
    private Button btnGreen, btnBlue, btnYellow, btnRed;
    private Activity activity;


    public ItemSelector(Activity activity, View header, View background, View tableText, Button btnGreen, Button btnBlue, Button btnYellow, Button btnRed) {

        this.header = header;
        this.background = background;
        this.tableText = tableText;
        this.btnGreen = btnGreen;
        this.btnBlue = btnBlue;
        this.btnYellow = btnYellow;
        this.btnRed = btnRed;
        this.activity = activity;

        //Initializing colors
        waitingBgd = ContextCompat.getColor(activity, R.color.colorWaitingRecyclerBgd);
        startedBgd = ContextCompat.getColor(activity, R.color.colorStartedRecyclerBgd);
        deliverBgd = ContextCompat.getColor(activity, R.color.colorDeliverRecyclerBgd);
        pickupBgd = ContextCompat.getColor(activity, R.color.colorPickupRecyclerBgd);
        waitingHeader = ContextCompat.getColor(activity, R.color.colorRed);
        startedHeader = ContextCompat.getColor(activity, R.color.colorGreen);
        deliverHeader = ContextCompat.getColor(activity, R.color.colorOrange);
        pickupHeader = ContextCompat.getColor(activity, R.color.colorBlue);

    }

    public void switchItem(String item) {

        if (item == WAITING_ITEM) {
            changeColors(header, background, item);
            tableText.setVisibility(View.GONE);
            btnBlue.setVisibility(View.INVISIBLE);
            btnYellow.setVisibility(View.GONE);
            btnRed.setText("decline");
            btnGreen.setText("accept");


        }
        if (item == STARTED_ITEM) {
            changeColors(header, background, item);
            tableText.setVisibility(View.VISIBLE);
            btnBlue.setVisibility(View.INVISIBLE);
            btnYellow.setVisibility(View.GONE);
            btnRed.setText("cancel");
            btnGreen.setText("finish");
        }
        if (item == DELIVER_ITEM) {
            changeColors(header, background, item);
            tableText.setVisibility(View.VISIBLE);
            btnBlue.setVisibility(View.VISIBLE);
            btnYellow.setVisibility(View.GONE);
            btnRed.setText("dispute");
            btnGreen.setText("complete");
        }
        if (item == PICKUP_ITEM) {
            changeColors(header, background, item);
            tableText.setVisibility(View.GONE);
            btnBlue.setVisibility(View.GONE);
            btnYellow.setVisibility(View.VISIBLE);
            btnRed.setText("Dispute");
            btnGreen.setText("complete");

        }
        if (item == ALL_ITEM) {

        }
        if (item == CLOSED_ITEM) {

        }

    }


    private void changeColors(View header, View background, String item) {

        if (item == WAITING_ITEM) {
            header.setBackgroundColor(waitingHeader);
            background.setBackgroundColor(waitingBgd);
        }
        if (item == STARTED_ITEM) {
            header.setBackgroundColor(startedHeader);
            background.setBackgroundColor(startedBgd);

        }
        if (item == DELIVER_ITEM) {
            header.setBackgroundColor(deliverHeader);
            background.setBackgroundColor(deliverBgd);
        }
        if (item == PICKUP_ITEM) {
            header.setBackgroundColor(pickupHeader);
            background.setBackgroundColor(pickupBgd);
        }
        if (item == ALL_ITEM) {
            header.setBackgroundColor(allHeader);
            background.setBackgroundColor(allBg);
        }
        if (item == CLOSED_ITEM) {
            header.setBackgroundColor(closedHeader);
            background.setBackgroundColor(closedBgd);
        }


    }


}