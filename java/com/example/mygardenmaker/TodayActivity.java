package com.example.mygardenmaker;

import androidx.appcompat.app.AppCompatActivity;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;

import android.widget.ListView;

import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.util.Locale;


public class TodayActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener{
    ListView listview;
    ArrayList<DriveVO> data;
    DriveAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);
        listview=findViewById(R.id.today_listview);

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());


        String year = yearFormat.format(currentTime);
        String month = monthFormat.format(currentTime);
        String day = dayFormat.format(currentTime);

        String today=year+month+day;
        int in_today=Integer.parseInt(today);

        data=new ArrayList<>();
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select*from tb_garden",null);
        while(cursor.moveToNext())
        {
            DriveVO vo=new DriveVO();

            vo.title=cursor.getString(1);
            vo.period=cursor.getString(2);
            data.add(vo);
        }
        db.close();
        adapter=new DriveAdapter(this,R.layout.custom_item, data);
        listview.setAdapter(adapter);
        listview.setOnItemLongClickListener(this);

    }

    //롱클릭으로 완료한 일 삭제 그리고 새로운 DB에 저장
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        DriveAdapter getadapter=(DriveAdapter) parent.getAdapter();
        DriveVO getAdapterItem = getadapter.getItem(position);
        DBHelper helper=new DBHelper(this);
        SQLiteDatabase db=helper.getWritableDatabase();
        try{
            String completetitle=getAdapterItem.title;
            db.execSQL("DELETE FROM tb_garden WHERE title==?",new String[]{completetitle});
            db.execSQL("insert into tb_complete (title) values (?)",new String[]{completetitle});
            //삭제와 동시에 완료된 디비에 저장됨 새로운DB tb_complete는 DBHelper class참조
            HomeActivity.percent+=50; //퍼센트상승
            int p=HomeActivity.percent;
            ((HomeActivity)HomeActivity.mContext).renew_percent(p);//(추가됨)
            Toast t=Toast.makeText(getApplicationContext(),"완료됨...",Toast.LENGTH_SHORT);
            t.show();
        } catch(Exception e)
        {
            Toast t=Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT);
            t.show();
        }
        db.close();
        data.remove(position);
        adapter.notifyDataSetChanged();
        listview.setAdapter(adapter);
        return true;
    }


}