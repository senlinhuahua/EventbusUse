package com.forest.eventbususe;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.forest.eventbususe.bus.Eventbus;
import com.forest.eventbususe.bus.Forest;

public class SecondActivity extends Activity {

    private static String TGA = "SecondActivity";
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        button = findViewById(R.id.btn_reskip);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.e(TGA,"thread = "+Thread.currentThread().getName());
//                        Eventbus.getDefult().post(new Forest("forester","4320987"));
//                    }
//                }).start();


                Log.e(TGA,"thread = "+Thread.currentThread().getName());
                Eventbus.getDefult().post(new Forest("forester","4320987"));

                finish();
            }
        });
    }
}
