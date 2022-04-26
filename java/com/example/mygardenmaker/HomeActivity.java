package com.example.mygardenmaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener, CircleProgressBar.ProgressFormatter{


    //하단 버튼 세개 홈으로 돌아오기, 정원가기, 달력보기
    
    ListView simpleview;
    public static DriveAdapter2 adapter;
    public static ArrayList<DriveVO> data;
    MediaPlayer mp;
    GridLayout ll;
    public static int percent=0;
    public static Context mContext; //(추가됨)
    int CHECK_NUM = 0; //음악 껐는지 켰는지 표시
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int count=0;
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
            Log.d("YearMonthPickerTest", "year = " + year + ", month = " + monthOfYear + ", day = " + dayOfMonth);
        }
    };
    // progress 값 표시

    @Override
    public CharSequence format(int progress, int max) {
        return String.format(DEFAULT_PATTERN, (int) ((float) progress / (float) max * 100));
    }
    //서클 퍼센티지바 추가
    CircleProgressBar circleProgressBar;
    private static final String DEFAULT_PATTERN = "%d%%";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContext=this;
        //circlebar
        circleProgressBar=findViewById(R.id.progressBar);
        SharedPreferences sh=getSharedPreferences("percentage",MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPreferences.edit();
        percent=sh.getInt("PERCENTAGE",0); //(고쳐진부분)
        circleProgressBar.setProgress(percent);

        // 배경음악 설정
        mp = MediaPlayer.create(this, R.raw.adventure);
        mp.setLooping(true); // 무한재생
        mp.start();

        ImageButton btn_menu = (ImageButton) findViewById(R.id.menu);
        //석삼메뉴 버튼 클릭 시 메뉴
        btn_menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                PopupMenu popup= new PopupMenu(getApplicationContext(), v);
                getMenuInflater().inflate(R.menu.option_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.adventure:
                                mp.stop();
                                mp.release();
                                mp = MediaPlayer.create(v.getContext(), R.raw.adventure);
                                mp.setLooping(true);
                                mp.start();
                                break;
                            case R.id.creative:
                                mp.stop();
                                mp.release();
                                mp = MediaPlayer.create(v.getContext(), R.raw.creative);
                                mp.setLooping(true);
                                mp.start();

                                break;
                            case R.id.evolution:
                                mp.stop();
                                mp.release();
                                mp = MediaPlayer.create(v.getContext(), R.raw.evolution);
                                mp.setLooping(true);
                                mp.start();
                                break;

                            case R.id.memories:
                                mp.stop();
                                mp.release();
                                mp = MediaPlayer.create(v.getContext(), R.raw.memories);
                                mp.setLooping(true);
                                mp.start();
                                break;
                            case R.id.once:
                                mp.stop();
                                mp.release();
                                mp = MediaPlayer.create(v.getContext(), R.raw.once);
                                mp.setLooping(true);
                                mp.start();
                                break;
                            // 배경 설정
                            case R.id.bg1:
                                ll = findViewById(R.id.potBody);
                                ll.setBackgroundResource(R.drawable.pot_background1);
                                break;
                            case R.id.bg2:
                                ll = findViewById(R.id.potBody);
                                ll.setBackgroundResource(R.drawable.pot_background2);
                                break;
                            case R.id.bg3:
                                ll = findViewById(R.id.potBody);
                                ll.setBackgroundResource(R.drawable.pot_background3);
                                break;
                            case R.id.bg4:
                                ll = findViewById(R.id.potBody);
                                ll.setBackgroundResource(R.drawable.pot_background4);
                                break;
                            case R.id.bg5:
                                ll = findViewById(R.id.potBody);
                                ll.setBackgroundResource(R.drawable.pot_background5);
                                break;
                            // 달성 목록 보여주기
                            case R.id.menu3:
                                //Toast.makeText(getApplication(),"메뉴3",Toast.LENGTH_SHORT).show();
                                try{
                                    Intent intent3=new Intent(getBaseContext(),CompleteTasksActivity.class);
                                    startActivityForResult(intent3,15000);
                                }catch(Exception e)
                                {
                                    Toast.makeText(getApplication(),e.toString(),Toast.LENGTH_SHORT).show();
                                }

                                break;
                            // 로그아웃
                            case R.id.menu4:
                                FirebaseAuth.getInstance().signOut();
                                finish();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                break;


                            default:
                                break;
                        }
                        return false;
                    }
                });

                popup.show();
            }
        });

        /*상단 좌측 today태양버튼 클릭 시 */
        Button btn_today = (Button) findViewById(R.id.today);
        btn_today.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),TodayActivity.class);
                startActivityForResult(intent,10101); //태양버튼 수정하기
            }
        });

        TabHost tabHost = findViewById(R.id.host);
        tabHost.setup();
        //탭화면 구성하기
        TabHost.TabSpec spec = tabHost.newTabSpec("tab1");
        spec.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.tabicon1, null));
        spec.setContent(R.id.potHome);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("tab2");
        spec.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.tabicon2, null));
        spec.setContent(R.id.gardenHome);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("tab3");
        spec.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.tabicon3, null));
        spec.setContent(R.id.calendarHome);
        tabHost.addTab(spec);

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat weekdayFormat = new SimpleDateFormat("EE", Locale.getDefault());

        String year = yearFormat.format(currentTime);
        String month = monthFormat.format(currentTime);
        String day = dayFormat.format(currentTime);
        String weekday = weekdayFormat.format(currentTime);
        //상단 오늘 날짜 나타내기
        TextView tv_year = (TextView) findViewById(R.id.today_year);
        tv_year.setText(year + "년");
        TextView tv_month = (TextView) findViewById(R.id.today_month);
        tv_month.setText(month + "월");
        TextView tv_day = (TextView) findViewById(R.id.today_day);
        tv_day.setText(day + "일");
        TextView tv_weekday = (TextView) findViewById(R.id.today_weekday);
        tv_weekday.setText(weekday+"요일");


        final ImageView imageview = (ImageView) findViewById(R.id.audio);
        final ImageButton imageBtn = (ImageButton) findViewById(R.id.sound);
        imageBtn.setOnClickListener(new View.OnClickListener() {     // 이미지 버튼 이벤트 정의
            @Override
            public void onClick(View v) {                       // 스피커 클릭했을 경우 노래꺼지거나 켜짐
                if (CHECK_NUM == 0) {
                    imageBtn.setSelected(true);
                    imageview.setImageResource(R.drawable.audio_off);
                    mp.pause();
                    CHECK_NUM = 1;
                }
                else {
                    imageBtn.setSelected(false);
                    imageview.setImageResource(R.drawable.audio_on);
                    mp.start();
                    CHECK_NUM = 0;
                }
            }
        });

        //3번째 탭 달력아래 리스트뷰 나타내기
        simpleview=findViewById(R.id.home_listview_simple);
        data = new ArrayList<>();
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        //db.execSQL("DELETE FROM tb_complete");
        Cursor cursor=db.rawQuery("select * from tb_garden",null);
        //db.execSQL("DELETE FROM tb_garden");
        while(cursor.moveToNext())
        {
            DriveVO vo=new DriveVO();
            vo.title=cursor.getString(1);
            vo.period=cursor.getString(2);
            data.add(vo);
        }
        db.close();
        adapter=new DriveAdapter2(this,R.layout.custom_item2,data);
        simpleview.setAdapter(adapter);
        simpleview.setOnItemLongClickListener(this);

        ImageButton btn_plus = (ImageButton) findViewById(R.id.plus);
        btn_plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), PlusActivity.class);
                startActivityForResult(intent,10000);
                adapter.notifyDataSetChanged();

            }
        });
        // garden에서 A 버튼 눌렀을 때
        Button btn_A = (Button) findViewById(R.id.group_A);
        btn_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), GardenInsideActivity.class);
                startActivityForResult(intent, 1111);
            }
        });

        // garden에서 B 버튼 눌렀을 때
        Button btn_B = (Button) findViewById(R.id.group_B);
        btn_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), GardenInsideActivity.class);
                startActivityForResult(intent, 2222);
            }
        });

        // garden에서 C 버튼 눌렀을 때
        Button btn_C = (Button) findViewById(R.id.group_C);
        btn_C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), GardenInsideActivity.class);
                startActivityForResult(intent, 3333);
            }
        });

        // garden에서 D 버튼 눌렀을 때
        Button btn_D = (Button) findViewById(R.id.group_D);
        btn_D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), GardenInsideActivity.class);
                startActivityForResult(intent, 4444);
            }
        });
    }

    //길게 눌러서 삭제하기
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        DriveAdapter2 getAdapter=(DriveAdapter2) parent.getAdapter();
        DriveVO getAdapterItem = getAdapter.getItem(position);
        DBHelper helper=new DBHelper(this);
        SQLiteDatabase db=helper.getWritableDatabase();
        try{
            //assert getAdapterItem != null;
            String tt=getAdapterItem.title;
            db.execSQL("DELETE FROM tb_garden WHERE title==?",new String[]{tt});
            Toast t=Toast.makeText(getApplicationContext(),"삭제됨...",Toast.LENGTH_SHORT);
            t.show();

        } catch (Exception e)
        {
            Toast t=Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT);
            t.show();
        }
        db.close();
        data.remove(position);
        adapter.notifyDataSetChanged();
        simpleview.setAdapter(adapter);

        return false;
    }

    public void renew_percent(int p) //(추가됨)퍼센트갱신함수
    {
        int randomValue;
        int randomFlower;
        if(p>=100)
        {
            p=p-100;
            percent=p;
            Random random = new Random();
            randomValue = random.nextInt(4);
            randomFlower = random.nextInt(3);
            //꽃이 정원에 추가되야함
            //            //우선 안내메시지 출력
            Toast.makeText(getApplicationContext(),"화분의 게이지가 가득찼습니다. 정원에 꽃이 생성됩니다.",Toast.LENGTH_LONG).show();
            if (randomValue == 0) {
                switch (randomFlower) {
                    case 0:
                        sharedPreferences=getSharedPreferences("COUNT", MODE_PRIVATE);
                        count = sharedPreferences.getInt("count_a1", 0);
                        count+=1;
                        editor=sharedPreferences.edit();
                        editor.putInt("count_a1", count);
                        editor.apply();
                        ((GardenInsideActivity)GardenInsideActivity.nContext).setPlantNum();
                        break;
                    case 1:
                        sharedPreferences=getSharedPreferences("COUNT", MODE_PRIVATE);
                        count = sharedPreferences.getInt("count_a2", 0);
                        count+=1;
                        editor=sharedPreferences.edit();
                        editor.putInt("count_a2", count);
                        editor.apply();
                        ((GardenInsideActivity)GardenInsideActivity.nContext).setPlantNum();
                        break;
                    case 2:
                        sharedPreferences=getSharedPreferences("COUNT", MODE_PRIVATE);
                        count = sharedPreferences.getInt("count_a3", 0);
                        count+=1;
                        editor=sharedPreferences.edit();
                        editor.putInt("count_a3", count);
                        editor.apply();
                        ((GardenInsideActivity)GardenInsideActivity.nContext).setPlantNum();
                        break;
                    default:
                        break;
                }
            }
            else if (randomValue == 1) {
                switch (randomFlower) {
                    case 0:
                        sharedPreferences=getSharedPreferences("COUNT", MODE_PRIVATE);
                        count = sharedPreferences.getInt("count_b1", 0);
                        count+=1;
                        editor=sharedPreferences.edit();
                        editor.putInt("count_b1", count);
                        editor.apply();
                        ((GardenInsideActivity)GardenInsideActivity.nContext).setPlantNum();
                        break;
                    case 1:
                        sharedPreferences=getSharedPreferences("COUNT", MODE_PRIVATE);
                        count = sharedPreferences.getInt("count_b2", 0);
                        count+=1;
                        editor=sharedPreferences.edit();
                        editor.putInt("count_b2", count);
                        editor.apply();
                        ((GardenInsideActivity)GardenInsideActivity.nContext).setPlantNum();
                        break;
                    case 2:
                        sharedPreferences=getSharedPreferences("COUNT", MODE_PRIVATE);
                        count = sharedPreferences.getInt("count_b3", 0);
                        count+=1;
                        editor=sharedPreferences.edit();
                        editor.putInt("count_b3", count);
                        editor.apply();
                        ((GardenInsideActivity)GardenInsideActivity.nContext).setPlantNum();
                        break;
                    default:
                        break;
                }
            }
            else if (randomValue == 2) {
                switch (randomFlower) {
                    case 0:
                        sharedPreferences=getSharedPreferences("COUNT", MODE_PRIVATE);
                        count = sharedPreferences.getInt("count_c1", 0);
                        count+=1;
                        editor=sharedPreferences.edit();
                        editor.putInt("count_c1", count);
                        editor.apply();
                        ((GardenInsideActivity)GardenInsideActivity.nContext).setPlantNum();
                        break;
                    case 1:
                        sharedPreferences=getSharedPreferences("COUNT", MODE_PRIVATE);
                        count = sharedPreferences.getInt("count_c2", 0);
                        count+=1;
                        editor=sharedPreferences.edit();
                        editor.putInt("count_c2", count);
                        editor.apply();
                        ((GardenInsideActivity)GardenInsideActivity.nContext).setPlantNum();
                        break;
                    case 2:
                        sharedPreferences=getSharedPreferences("COUNT", MODE_PRIVATE);
                        count = sharedPreferences.getInt("count_c3", 0);
                        count+=1;
                        editor=sharedPreferences.edit();
                        editor.putInt("count_c3", count);
                        editor.apply();
                        ((GardenInsideActivity)GardenInsideActivity.nContext).setPlantNum();
                        break;
                    default:
                        break;
                }
            }
            else if (randomValue == 3) {
                switch (randomFlower) {
                    case 0:
                        sharedPreferences=getSharedPreferences("COUNT", MODE_PRIVATE);
                        count = sharedPreferences.getInt("count_d1", 0);
                        count+=1;
                        editor=sharedPreferences.edit();
                        editor.putInt("count_d1", count);
                        editor.apply();
                        ((GardenInsideActivity)GardenInsideActivity.nContext).setPlantNum();
                        break;
                    case 1:
                        sharedPreferences=getSharedPreferences("COUNT", MODE_PRIVATE);
                        count = sharedPreferences.getInt("count_d2", 0);
                        count+=1;
                        editor=sharedPreferences.edit();
                        editor.putInt("count_d2", count);
                        editor.apply();
                        ((GardenInsideActivity)GardenInsideActivity.nContext).setPlantNum();
                        break;
                    case 2:
                        sharedPreferences=getSharedPreferences("COUNT", MODE_PRIVATE);
                        count = sharedPreferences.getInt("count_d3", 0);
                        count+=1;
                        editor=sharedPreferences.edit();
                        editor.putInt("count_d3", count);
                        editor.apply();
                        ((GardenInsideActivity)GardenInsideActivity.nContext).setPlantNum();
                        break;
                    default:
                        break;
                }
            }
            //((GardenInsideActivity)GardenInsideActivity.nContext).setPlantNum();
        }
        circleProgressBar.setProgress(p);
        sharedPreferences=getSharedPreferences("percentage",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putInt("PERCENTAGE",p);
        editor.apply();
    }

}