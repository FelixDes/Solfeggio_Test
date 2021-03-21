package com.samsolfeggio.myapplication;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import samsolfeggio.myapplication.R;


public class MusicQuestionActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textquest;
    MediaPlayer mPlayer;
    Button startButton, pauseButton, stopButton;
    int currentQuestion = 0;
    Button button12;
    Button button22;
    Button button32;
    ArrayList<Question> allQ = new ArrayList<>();
    int corr_c = 0;

    static class Question {
        private int id;
        private String[] answers;
        private int correctans;

        Question(int id, String[] answers, int correctans) {
            this.id = id;
            this.answers = answers;
            this.correctans = correctans;
        }

        int getid() {
            return id;
        }

        String[] getAnswers() {
            return answers;
        }

        int getCorrectans() {
            return correctans;
        }
    }

    public void showQuestion(Question q) {
        mPlayer = MediaPlayer.create(this, q.getid());
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlay();
            }
        });
        //TextView qtv = findViewById(R.id.textquest);
        button12.setText(q.getAnswers()[0]);
        button22.setText(q.getAnswers()[1]);
        button32.setText(q.getAnswers()[2]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_question);
        textquest = findViewById(R.id.textquest);
        button12 = findViewById(R.id.button12);
        button22 = findViewById(R.id.button22);
        button32 = findViewById(R.id.button32);
        startButton = findViewById(R.id.start);
        pauseButton = findViewById(R.id.pause);
        stopButton = findViewById(R.id.stop);
        stopButton.setEnabled(false);
        pauseButton.setEnabled(false);
        allQ.add(new Question(R.raw.a_moll, new String[]{"Ля минор, тоника", "Ре минор, тоника", "До мажор, тоника"}, 0));
        allQ.add(new Question(R.raw.c_dur, new String[]{"Ре мажор, тоника", "Ре минор, тоника", "До мажор, тоника"}, 2));
        allQ.add(new Question(R.raw.d_dur, new String[]{"Ре мажор, тоника", "Ми мажор, тоника", "До мажор, тоника"}, 1));
        allQ.add(new Question(R.raw.e_moll, new String[]{"Ре минор, тоника", "Ми минор, тоника", "Ми мажор, тоника"}, 1));
        allQ.add(new Question(R.raw.des_dur, new String[]{"Ре-бемоль мажор, тоника", "Ми мажор, тоника", "Ми-бемольмажор, тоника"}, 0));

        /*try {
            runExample();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }*/
        showQuestion(allQ.get(currentQuestion));

    }
    /*public void runExample() throws IOException, JSONException {
        ArrayList<MusicQuestionActivity.Question> ans = ReadJSON.readJSONFileMuz(this);
        allQ.addAll(ans);
        Log.println(Log.INFO, "len", allQ.toString());
    }*/

    private void stopPlay() {
        mPlayer.stop();
        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);
        try {
            mPlayer.prepare();
            mPlayer.seekTo(0);
            startButton.setEnabled(true);
        } catch (Throwable t) {
            Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void play(View view) {

        mPlayer.start();
        startButton.setEnabled(false);
        pauseButton.setEnabled(true);
        stopButton.setEnabled(true);
    }

    public void pause(View view) {

        mPlayer.pause();
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(true);
    }

    public void stop(View view) {
        stopPlay();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer.isPlaying()) {
            stopPlay();
        }
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
                stopPlay();
                user_answer = 0;
                break;
            case R.id.button22:
                stopPlay();
                user_answer = 1;
                break;
            case R.id.button32:
                stopPlay();
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