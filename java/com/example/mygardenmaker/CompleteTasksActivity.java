package com.example.mygardenmaker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class CompleteTasksActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_tasks);
        ListView complete_view=(ListView)findViewById(R.id.complete_list);//리스트뷰아이디참조
        ArrayList<HashMap<String, String>> simpleData = new ArrayList<>();
        DBHelper helper=new DBHelper(this);
        SQLiteDatabase db=helper.getWritableDatabase(); //db불러오기
        Cursor cursor=db.rawQuery("select * from tb_complete",null);
        //db.execSQL("DELETE FROM tb_complete");
        while(cursor.moveToNext())
        {
            HashMap<String, String> map = new HashMap<>();
            map.put("title",cursor.getString(1));
            simpleData.add(map);
        }
        db.close();
        SimpleAdapter adapter=new SimpleAdapter(this,
                simpleData,android.R.layout.simple_list_item_1,
                new String[]{"title"},
                new int[]{android.R.id.text1});
        complete_view.setAdapter(adapter);
    }
}