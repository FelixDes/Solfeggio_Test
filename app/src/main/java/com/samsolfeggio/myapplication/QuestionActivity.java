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

import samsolfeggio.myapplication.R;


public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textquest;
    int currentQuestion = 0;
    Button button12;
    Button button22;
    Button button32;
    ArrayList<Question> allQ = new ArrayList<>();
    int corr_c = 0;

    static class Question {
        private String text;
        private String[] answers;
        private int correctans;

        Question(String text, String[] answers, int correctans) {
            this.text = text;
            this.answers = answers;
            this.correctans = correctans;
        }

        String getText() {
            return text;
        }

        String[] getAnswers() {
            return answers;
        }

        int getCorrectans() {
            return correctans;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity);
        textquest = findViewById(R.id.textquest);
        button12 = findViewById(R.id.button12);
        button22 = findViewById(R.id.button22);
        button32 = findViewById(R.id.button32);
        /*allQ.add(new Question("Гармонический звук в миноре – это ", new String[]{"VI b  ступень", "VII b  ступень", "VII#  ступень"}, 2));
        allQ.add(new Question("В интервале «fis-e»  качественная величина равна", new String[]{" 5 тонам", "6 ступеням", "7 ступеням"}, 0));
        allQ.add(new Question("Сколько Ум 5/3 в гармоническом мажоре?", new String[]{"одно", "два", "ни одного"}, 1));
        allQ.add(new Question("В тональности H-dur аккорд  h-dis-fis нужно обозначить, как…", new String[]{"Б 5/3", "М 5/3", "Т 5/3"}, 2));
        allQ.add(new Question("Интервалы, которые могут быть построены только в гармонических ладах, называются", new String[]{"характерными", "тритонами", "диссонансами"}, 0));
        allQ.add(new Question("Сколько ступеней в пентатонике?", new String[]{"семь", "шесть", "пять"}, 2));
        allQ.add(new Question("Какой аккорд состоит из м.3+ м.3?", new String[]{"М 5/3", "D7", "все ответы неверны"}, 2));
        allQ.add(new Question("Какие аккорды строятся на II ступени?", new String[]{" ум.5,   Ум. 5/3,  D 6/5", "Ум VII 7,   M VII 7,   D7", "Ум 5/3,  D43"}, 2));
        allQ.add(new Question("Интервал a-fis определѐн как м.7.  Что неверно в определении?", new String[]{"только буква", "только цифра", "буква и цифра"}, 2));
        allQ.add(new Question("В альтерированном мажоре при движении вниз", new String[]{"повышаются II,  IV, VI  ступени", "понижаются VI , II  ступени", "понижаются VI,  IV,  II  ступени"}, 1));
        */try {
            runExample();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        showQuestion(allQ.get(currentQuestion));
    }
    public void runExample() throws IOException, JSONException {
        ArrayList<QuestionActivity.Question> ans = ReadJSON.readJSONFileSimp(this);
        allQ.addAll(ans);
        Log.println(Log.INFO, "len", allQ.toString());
    }

    public void showQuestion(Question q) {
        TextView qtv = findViewById(R.id.textquest);
        qtv.setText(q.getText());
        button12.setText(q.getAnswers()[0]);
        button22.setText(q.getAnswers()[1]);
        button32.setText(q.getAnswers()[2]);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }

    @Override
    public void onClick(View w) {
        int user_answer = -1;
        switch (w.getId()) {
            case R.id.button12:
                user_answer = 0;
                break;
            case R.id.button22:
                user_answer = 1;
                break;
            case R.id.button32:
                user_answer = 2;
                break;
        }
        if (user_answer == -1)
            return;
        if (currentQuestion < allQ.size()) {
            if (user_answer == allQ.get(currentQuestion).getCorrectans())
                corr_c++;
            currentQuestion++;
        }
        if (currentQuestion < allQ.size())
            showQuestion(allQ.get(currentQuestion));

        else {
            Intent intent = new Intent();
            intent.putExtra("count", corr_c);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}

