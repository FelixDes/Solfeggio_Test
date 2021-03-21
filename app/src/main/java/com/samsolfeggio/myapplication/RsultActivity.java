package com.samsolfeggio.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import samsolfeggio.myapplication.R;

public class RsultActivity extends AppCompatActivity implements View.OnClickListener{
    TextView textView1, textView2;
    Button button_exit;
    Handler handler;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsult);
        button_exit = findViewById(R.id.button_exit);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                textView2.setText(String.valueOf(msg.what));
                textView2.invalidate();
            }
        };
        count = getIntent().getIntExtra("res_count", 0);
        Thread thread = new Thread(new AnotherRunnable());
        thread.start();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_exit) {
            finish();
        }
    }

    class AnotherRunnable implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i <= count; i++) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(i);
            }
        }
    }
}
