package com.forest.eventbususe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.forest.eventbususe.bus.Eventbus;
import com.forest.eventbususe.bus.Forest;
import com.forest.eventbususe.bus.Subscribe;
import com.forest.eventbususe.bus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    private static String TGA = "MainActivity";
    private Button button;
    private TextView tv_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Eventbus.getDefult().register(this);
        button = findViewById(R.id.btn_skip);
        tv_text = findViewById(R.id.tv_text);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void aiandroid(Forest forest){

        Log.e(TGA,"thread = "+Thread.currentThread().getName());
        //tv_text.setText(forest.getMoney());
        Log.e(TGA,"forest = "+forest.getName());

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void aiJava(Forest forest){

        Log.e(TGA,"thread2 = "+Thread.currentThread().getName());
        tv_text.setText(forest.getMoney()+"two");
        Log.e(TGA,"forest2 = "+forest.getName());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Eventbus.getDefult().onDestory(this);
    }
}
