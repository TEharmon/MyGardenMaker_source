package com.example.mygardenmaker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class DriveAdapter2 extends ArrayAdapter<DriveVO> {
    Context context;
    int resId;
    ArrayList<DriveVO> data;

    public DriveAdapter2(Context context, int resId, ArrayList<DriveVO> data){
        super(context, resId);
        this.context=context;
        this.resId=resId;
        this.data=data;
    }

    @Override
    public int getCount()
    {
        return data.size();
    }

    @Override
    public DriveVO getItem(int position)
    {
        return data.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resId, null);
            DriveHolder2 holder = new DriveHolder2(convertView); //
            convertView.setTag(holder);
        }
        DriveHolder2 holder=(DriveHolder2)convertView.getTag();
        TextView titleView=holder.titleView;
        TextView periodView=holder.periodView;

        final DriveVO vo=data.get(position);
        titleView.setText(vo.title);
        periodView.setText(vo.period);

        return convertView;
    }

}
