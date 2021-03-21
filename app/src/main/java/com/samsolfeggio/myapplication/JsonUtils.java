package com.samsolfeggio.myapplication;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import samsolfeggio.myapplication.R;


class ReadJSON {
    private static String readText(Context context, int resId) throws IOException {
        InputStream is = context.getResources().openRawResource(resId);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String s = null;
        while ((s = br.readLine()) != null) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }

    static ArrayList<GraficQuestionActivity.Grafic_Question> readJSONFileGraf(Context context) throws IOException, JSONException {
        ArrayList<GraficQuestionActivity.Grafic_Question> arrayList = new ArrayList<>();
        Log.println(Log.INFO, "msg", " Json inited correctly");
        String jsonText = readText(context, R.raw.questions);
        JSONObject jsonRoot = new JSONObject(jsonText);
        JSONArray questArray = jsonRoot.getJSONArray("questions");
        for (int h = 0; h < questArray.length(); h++) {
            JSONObject inquestArray = questArray.getJSONObject(h);
            Log.println(Log.INFO, "msg", inquestArray.toString());
            String text = inquestArray.getString("text");
            JSONArray inansArray = inquestArray.getJSONArray("answers");
            int[] arr_answers = new int[inansArray.length()];
            for (int i = 0; i < inansArray.length(); i++) {
                arr_answers[i] = inansArray.getInt(i);
            }


            Log.println(Log.INFO, "text", text);
            Log.println(Log.INFO, "ans", String.valueOf(arr_answers.toString()));
            GraficQuestionActivity.Grafic_Question gq = new GraficQuestionActivity.Grafic_Question(text, arr_answers);
            arrayList.add(gq);
        }
        return arrayList;
    }

    /*static ArrayList<MusicQuestionActivity.Question> readJSONFileMuz(Context context) throws IOException, JSONException {
        ArrayList<MusicQuestionActivity.Question> arrayList = new ArrayList<>();
        Log.println(Log.INFO, "msg", " Json inited correctly");
        String jsonText = readText(context, R.raw.muzquestions);
        JSONObject jsonRoot = new JSONObject(jsonText);
        JSONArray questArray = jsonRoot.getJSONArray("questions");
        for (int h = 0; h < questArray.length(); h++) {
            JSONObject inquestArray = questArray.getJSONObject(h);
            //Log.println(Log.INFO, "msg", inquestArray.toString());
            int id = inquestArray.getInt("id");
            JSONArray inansArray = inquestArray.getJSONArray("answers");
            int cor_a = inquestArray.getInt("cor_a");
            String[] arr_answers = new String[inansArray.length()];
            for (int i = 0; i < inansArray.length(); i++) {
                arr_answers[i] = inansArray.getString(i);
            }
            MusicQuestionActivity.Question mq = new MusicQuestionActivity.Question(id, arr_answers, cor_a);
            arrayList.add(mq);
        }
        return arrayList;
    }*/
    static ArrayList<QuestionActivity.Question> readJSONFileSimp(Context context) throws IOException, JSONException {
        ArrayList<QuestionActivity.Question> arrayList = new ArrayList<>();
        Log.println(Log.INFO, "msg", " Json inited correctly");
        String jsonText = readText(context, R.raw.simplequestions);
        JSONObject jsonRoot = new JSONObject(jsonText);
        JSONArray questArray = jsonRoot.getJSONArray("questions");
        for (int h = 0; h < questArray.length(); h++) {
            JSONObject inquestArray = questArray.getJSONObject(h);
            Log.println(Log.INFO, "msg", inquestArray.toString());
            String text = inquestArray.getString("text");
            int cor_a = inquestArray.getInt("cor_a");
            JSONArray inansArray = inquestArray.getJSONArray("answers");
            String[] arr_answers = new String[inansArray.length()];
            for (int i = 0; i < inansArray.length(); i++) {
                arr_answers[i] = inansArray.getString(i);
            }
            //Log.println(Log.INFO, "text", text);
            //Log.println(Log.INFO, "ans", String.valueOf(arr_answers.toString()));
            QuestionActivity.Question q = new QuestionActivity.Question(text, arr_answers, cor_a);
            arrayList.add(q);
        }
        return arrayList;
    }


}