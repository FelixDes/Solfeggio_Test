package com.samsolfeggio.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import samsolfeggio.myapplication.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int QUEST = 1;
    //int[] i = new int[]{0, 1};
    //int[] i1 = new int[]{1, 0};
    TextView textsams;
    Button button1;
    Button button2;
    Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textsams = findViewById(R.id.textsams);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        /*if (i.hashCode() == i1.hashCode()) {
            Log.println(Log.INFO, "mes", "yes");
        }*/
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Class to = null;
        switch (v.getId()) {
            case R.id.button1:
                to = QuestionActivity.class;
                break;
            case R.id.button2:
                to = MusicQuestionActivity.class;
                break;
            case R.id.button3:
                to = GraficQuestionActivity.class;
                break;
        }
        if (to != null) {
            startActivityForResult(new Intent(this, to), QUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QUEST) {
            if (resultCode == RESULT_OK) {
                int r = data.getIntExtra("count", -1);
                if (r >= 0) {
                    Intent intent = new Intent(this, RsultActivity.class);
                    intent.putExtra("res_count", r);
                    startActivity(intent);
                    //Toast.makeText(this, "Колличество правильных ответов: " + r, Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}

