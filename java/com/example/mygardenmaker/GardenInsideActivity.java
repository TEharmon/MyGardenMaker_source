package com.example.mygardenmaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class GardenInsideActivity extends AppCompatActivity {
    public static Context nContext;

    // 식물 수 표시
    public void setPlantNum() {
        SharedPreferences sharedPreferences = getSharedPreferences("COUNT", MODE_PRIVATE);

        int num_a1 = sharedPreferences.getInt("count_a1", 0);
        int num_a2 = sharedPreferences.getInt("count_a2", 0);
        int num_a3 = sharedPreferences.getInt("count_a3", 0);

        int num_b1 = sharedPreferences.getInt("count_b1", 0);
        int num_b2 = sharedPreferences.getInt("count_b2", 0);
        int num_b3 = sharedPreferences.getInt("count_b3", 0);

        int num_c1 = sharedPreferences.getInt("count_c1", 0);
        int num_c2 = sharedPreferences.getInt("count_c2", 0);
        int num_c3 = sharedPreferences.getInt("count_c3", 0);

        int num_d1 = sharedPreferences.getInt("count_d1", 0);
        int num_d2 = sharedPreferences.getInt("count_d2", 0);
        int num_d3 = sharedPreferences.getInt("count_d3", 0);

        TextView bar_a1 = (TextView) findViewById(R.id.a_1_bar);
        bar_a1.setText("개수 : " + num_a1 +"개");
        TextView bar_a2 = (TextView) findViewById(R.id.a_2_bar);
        bar_a2.setText("개수 : " + num_a2 +"개");
        TextView bar_a3 = (TextView) findViewById(R.id.a_3_bar);
        bar_a3.setText("개수 : " + num_a3 +"개");

        TextView bar_b1 = (TextView) findViewById(R.id.b_1_bar);
        bar_b1.setText("개수 : " + num_b1 +"개");
        TextView bar_b2 = (TextView) findViewById(R.id.b_2_bar);
        bar_b2.setText("개수 : " + num_b2 +"개");
        TextView bar_b3 = (TextView) findViewById(R.id.b_3_bar);
        bar_b3.setText("개수 : " +num_b3 +"개");

        TextView bar_c1 = (TextView) findViewById(R.id.c_1_bar);
        bar_c1.setText("개수 : " + num_c1 +"개");
        TextView bar_c2 = (TextView) findViewById(R.id.c_2_bar);
        bar_c2.setText("개수 : " + num_c2 +"개");
        TextView bar_c3 = (TextView) findViewById(R.id.c_3_bar);
        bar_c3.setText("개수 : " + num_c3 +"개");

        TextView bar_d1 = (TextView) findViewById(R.id.d_1_bar);
        bar_d1.setText("개수 : " + num_d1 +"개");
        TextView bar_d2 = (TextView) findViewById(R.id.d_2_bar);
        bar_d2.setText("개수 : " + num_d2 +"개");
        TextView bar_d3 = (TextView) findViewById(R.id.d_3_bar);
        bar_d3.setText("개수 : " + num_d3 +"개");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garden_inside);
        setPlantNum();
        nContext = this;
    }
}