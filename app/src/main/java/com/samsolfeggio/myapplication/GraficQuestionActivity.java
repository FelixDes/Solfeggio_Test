package com.samsolfeggio.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import samsolfeggio.myapplication.R;

public class GraficQuestionActivity extends AppCompatActivity {
    Button button1n, button2n, button3n, button4n, button5n, button6n, button7n, button8n, button9n, button10n, button11n, button12n, button13n, button14n, button15n, button_flat, button_sharp, button_note, button_check, button_clear;
    TextView quest_text;
    Integer is_pressed;
    DrawView dv;
    int cor_count = 0;
    int currentQuestion = 0;
    ArrayList<Grafic_Question> allQ = new ArrayList<>();

    public static class Grafic_Question {
        private int[] notes;

        public String getText() {
            return text;
        }

        public int[] getNotes() {
            return notes;
        }

        private String text;

        Grafic_Question(String text, int[] notes) {
            this.text = text;
            this.notes = notes;
        }
    }

    public static class Global_Button_Arr {
        static ArrayList<Button> button_arr = new ArrayList<>();
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafic_question);
        button_flat = findViewById(R.id.button_flat);
        button_sharp = findViewById(R.id.button_sharp);
        button_note = findViewById(R.id.button_note);
        button_check = findViewById(R.id.button_check);
        button_clear = findViewById(R.id.button_clear);
        Global_Button_Arr.button_arr.add(button1n = findViewById(R.id.button1n));
        Global_Button_Arr.button_arr.add(button2n = findViewById(R.id.button2n));
        Global_Button_Arr.button_arr.add(button3n = findViewById(R.id.button3n));
        Global_Button_Arr.button_arr.add(button4n = findViewById(R.id.button4n));
        Global_Button_Arr.button_arr.add(button5n = findViewById(R.id.button5n));
        Global_Button_Arr.button_arr.add(button6n = findViewById(R.id.button6n));
        Global_Button_Arr.button_arr.add(button7n = findViewById(R.id.button7n));
        Global_Button_Arr.button_arr.add(button8n = findViewById(R.id.button8n));
        Global_Button_Arr.button_arr.add(button9n = findViewById(R.id.button9n));
        Global_Button_Arr.button_arr.add(button10n = findViewById(R.id.button10n));
        Global_Button_Arr.button_arr.add(button11n = findViewById(R.id.button11n));
        Global_Button_Arr.button_arr.add(button12n = findViewById(R.id.button12n));
        Global_Button_Arr.button_arr.add(button13n = findViewById(R.id.button13n));
        Global_Button_Arr.button_arr.add(button14n = findViewById(R.id.button14n));
        Global_Button_Arr.button_arr.add(button15n = findViewById(R.id.button15n));
        dv = findViewById(R.id.frame);
        quest_text = findViewById(R.id.quest_text);
        for (Button button : Global_Button_Arr.button_arr) {
            button.setEnabled(false);
        }
        //allQ.add(new Grafic_Question("До Ми Соль #", new int[]{0, 4, 8}));
        try {
            runExample();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        //Log.println(Log.INFO, "msg", String.valueOf(allQ));
        showQuestion(allQ.get(currentQuestion));
    }

    public void runExample() throws IOException, JSONException {
        ArrayList<Grafic_Question> ans = ReadJSON.readJSONFileGraf(this);
        allQ.addAll(ans);
        Log.println(Log.INFO, "len", allQ.toString());
    }

    public void showQuestion(Grafic_Question q) {
        TextView qtv = findViewById(R.id.quest_text);
        qtv.setText(q.getText());
        dv.getModel().clear_all();
    }

    public void onCheckClick(View v) throws IOException, JSONException {
        List<Integer> b = dv.getModel().getAnswer();
        if (check(allQ.get(currentQuestion).notes, b)) {
            cor_count++;
        }
        currentQuestion++;
        if (currentQuestion == allQ.size()) {
            Intent intent = new Intent();
            intent.putExtra("count", cor_count);
            setResult(RESULT_OK, intent);
            finish();
            return;
        }
        //currentQuestion++;
        showQuestion(allQ.get(currentQuestion));
        Log.println(Log.INFO, "msg", String.valueOf(currentQuestion));
    }

    private boolean check(int[] q, List<Integer> w) {
        if (q.length == w.size()) {
            for (int i = 0; i < q.length; i++) {
                if (q[i] == w.get(i)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void onSetDelClick(View v) {
        if (v.getId() == R.id.button_clear) {
            is_pressed = 0;
            dv.getModel().clear_all();
            button_flat.setEnabled(true);
            button_sharp.setEnabled(true);
            button_note.setEnabled(true);
            for (Button button : Global_Button_Arr.button_arr) {
                button.setEnabled(false);
            }
        }
    }

    public void onSetClick(View v) {
        boolean to = false;
        switch (v.getId()) {
            case R.id.button_flat:
                is_pressed = -1;
                to = true;
                break;
            case R.id.button_sharp:
                is_pressed = 1;
                to = true;
                break;
            case R.id.button_note:
                is_pressed = 0;
                to = true;
                break;
        }
        if (to) {
            for (Button button : Global_Button_Arr.button_arr) {
                button.setEnabled(true);
            }
            button_flat.setEnabled(false);
            button_sharp.setEnabled(false);
            button_note.setEnabled(false);
        }
    }

    public void buttons_listener(View v) {
        int to = 0;
        switch (v.getId()) {
            case R.id.button1n:
                to = 0;
                break;
            case R.id.button2n:
                to = 2;
                break;
            case R.id.button3n:
                to = 4;
                break;
            case R.id.button4n:
                to = 6;
                break;
            case R.id.button5n:
                to = 8;
                break;
            case R.id.button6n:
                to = 10;
                break;
            case R.id.button7n:
                to = 12;
                break;
            case R.id.button8n:
                to = 14;
                break;
            case R.id.button9n:
                to = 13;
                break;
            case R.id.button10n:
                to = 11;
                break;
            case R.id.button11n:
                to = 9;
                break;
            case R.id.button12n:
                to = 7;
                break;
            case R.id.button13n:
                to = 5;
                break;
            case R.id.button14n:
                to = 3;
                break;
            case R.id.button15n:
                to = 1;
                break;
        }
        if (to >= 0) {
            //Log.println(Log.INFO, "mes", String.valueOf(to));
            if (is_pressed == 0) {
                dv.getModel().toggle_note(to);
            } else if (is_pressed == -1) {
                dv.getModel().toggle_flat(to);
            } else if (is_pressed == 1) {
                dv.getModel().toggle_sharp(to);
            }
            for (Button button : Global_Button_Arr.button_arr) {
                button.setEnabled(false);
            }
            button_flat.setEnabled(true);
            button_sharp.setEnabled(true);
            button_note.setEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }
}