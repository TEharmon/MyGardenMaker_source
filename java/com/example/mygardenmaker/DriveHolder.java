package com.example.mygardenmaker;

import android.view.View;


import android.widget.TextView;

public class DriveHolder {
    public TextView titleView;
    public TextView periodView;


    public DriveHolder(View root)
    {
        titleView=root.findViewById(R.id.custom_item_title);
        periodView=root.findViewById(R.id.custom_item_period);

    }
}
