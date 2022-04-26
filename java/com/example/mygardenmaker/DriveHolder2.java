package com.example.mygardenmaker;

import android.view.View;

import android.widget.TextView;

public class DriveHolder2 {
    public TextView titleView;
    public TextView periodView;

    public DriveHolder2(View root)
    {
        titleView=root.findViewById(R.id.custom_item2_title);
        periodView=root.findViewById(R.id.custom_item2_period);

    }
}
