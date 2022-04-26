package com.example.mygardenmaker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.util.Calendar;


public class PlusActivity extends AppCompatActivity implements View.OnClickListener {


    EditText subject;
    TextView input_startdate;
    TextView input_starttime;
    TextView input_enddate;
    TextView input_endtime;
    AlertDialog listDialog;
    ImageButton startdateBtn;
    ImageButton starttimeBtn;
    ImageButton enddateBtn;
    ImageButton endtimeBtn;
    EditText autotext;


    ImageButton saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);


        input_startdate=(TextView)findViewById(R.id.input_start_date);
        input_starttime=(TextView)findViewById(R.id.input_start_time);
        input_enddate=(TextView)findViewById(R.id.input_end_date);
        input_endtime=(TextView)findViewById(R.id.input_end_time);

        subject=(EditText)findViewById(R.id.input_subject);
        startdateBtn=findViewById(R.id.start_date_btn);
        starttimeBtn=findViewById(R.id.start_time_btn);
        enddateBtn=findViewById(R.id.end_date_btn);
        endtimeBtn=findViewById(R.id.end_time_btn);

        saveBtn=findViewById(R.id.save_btn);

        startdateBtn.setOnClickListener(this);
        starttimeBtn.setOnClickListener(this);
        enddateBtn.setOnClickListener(this);
        endtimeBtn.setOnClickListener(this);

        saveBtn.setOnClickListener(this);
    }
    private void showToast(String message){
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
    DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (dialog == listDialog) {
// 목록 dialog의 항목이 선택되었을 때 항목 문자열 획득
                String[] data = getResources().getStringArray(R.array.Patternloop);
                showToast(data[which] + " 선택하셨습니다.");
                autotext.setText(data[which]);
            }
        }
    };
    @Override
    public void onClick(View v) {

        if(v==startdateBtn)
        {
            Calendar c = Calendar.getInstance(); // java.util.Calendar
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                input_startdate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                }
            }, year, month, day);
            dateDialog.show();
        }
        else if(v==starttimeBtn)
        {
            Calendar c = Calendar.getInstance(); // java.util.Calendar
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            TimePickerDialog timeDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    input_starttime.setText(hourOfDay + ":" + minute);
                }
            }, hour, minute, false);
            timeDialog.show();
        }
        else if(v==enddateBtn)
        {
            Calendar c = Calendar.getInstance(); // java.util.Calendar
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    //showToast(year + "-" + (month + 1) + "-" + dayOfMonth);

                    input_enddate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                }
            }, year, month, day);
            dateDialog.show();
        }
        else if(v==endtimeBtn)
        {
            Calendar c = Calendar.getInstance(); // java.util.Calendar
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            TimePickerDialog timeDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    input_endtime.setText(hourOfDay + ":" + minute);
                }
            }, hour, minute, false);
            timeDialog.show();
        }

        else if(v==saveBtn)
        {
            String sub=subject.getText().toString().trim();
            String startyear, endyear;
            String starthour, endhour;
            startyear=input_startdate.getText().toString().trim();
            endyear=input_enddate.getText().toString().trim();
            starthour=input_starttime.getText().toString().trim();
            endhour=input_endtime.getText().toString().trim();
            if(startyear==null || endyear==null || starthour==null || starthour==null || endhour==null ||starthour=="시간 미설정"||endhour=="시간 미설정"||startyear=="날짜 미설정"||
            endyear=="날짜 미설정")
            {
                showToast("날짜와시간을 모두 입력하세요.");
            }
            else{
                String period=startyear+' '+starthour+'~'+endyear+' '+endhour;
                DBHelper helper=new DBHelper(this);
                SQLiteDatabase db = helper.getWritableDatabase();

                db.execSQL("insert into tb_garden (title,period,startdate,enddate) values (?,?,?,?)",new String[]{sub,period,startyear,endyear});
                db.close();

                DriveVO vo=new DriveVO();
                vo.title=sub;
                vo.period=period;
                HomeActivity.data.add(vo);
                HomeActivity.adapter.notifyDataSetChanged();
                Toast t=Toast.makeText(getApplicationContext(),"저장됨...",Toast.LENGTH_SHORT);
                t.show();

                finish();
            }
        }
    }

}