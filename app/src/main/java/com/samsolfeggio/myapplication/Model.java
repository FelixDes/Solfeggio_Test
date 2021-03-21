package com.samsolfeggio.myapplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Model {
    private ArrayList<Integer> notes = new ArrayList<>();
    //private Integer[] notes = new Integer[15];
    //private int[] mod = new int[7];
    private int[] mod = new int[15];
    private int change = 0;

    //boolean is_flat_on_pic = false;
    //boolean is_sharp_on_pic = false;

    public void setChange(int change) {
        this.change = change;
    }

    public int getChange() {
        return change;
    }

    private int convert(int num) {
        num = ((num - 1) % 7 + 7) % 7;
        int[] h = new int[]{0, 2, 4, 5, 7, 9, 11};
        return h[num];
    }

    public List<Integer> getAnswer() {
        ArrayList<Integer> answer = new ArrayList<>();
        for (int i = 0; i < notes.size(); i++) {
            int line_num = notes.get(i);
            int note = convert(line_num) + mod[line_num];
            answer.add((note + 12) % 12);
        }
        Collections.sort(answer);
        ArrayList<Integer> new_ans = new ArrayList<>();
        for (int i = 0; i < answer.size(); i++) {
            boolean flag = false;
            for (int j = 0; j < new_ans.size(); j++) {
                if (answer.get(i).equals(new_ans.get(j))) {
                    flag = true;
                }
            }
            if (flag == false) {
                new_ans.add(answer.get(i));
            }
        }
        return new_ans;
    }

    private int prepare_sharp_flat(int number) {
        number += change;
        //number = number % 7;
        /*if (number < 0) {
            number = number + 7;
        }*/
        return number;
    }

    void clear_all() {
        for (int i = 0; i < mod.length; i++) {
            mod[i] = 0;
        }
        notes.clear();
    }

    public void toggle_sharp(int number) {
        number = prepare_sharp_flat(number);
        /*if (number == 2 || number == 6) {
            return;
        }*/
        if (mod[number] == 1) {
            mod[number] = 0;
        } else {
            mod[number] = 1;
        }

    }

    public void toggle_flat(int number) {
        number = prepare_sharp_flat(number);
        /*if (number == 3 || number == 0) {
            return;
        }*/
        if (mod[number] == -1) {
            mod[number] = 0;
        } else {
            mod[number] = -1;
        }
    }

    public boolean is_sharp(int number) {
        number = prepare_sharp_flat(number);
        return mod[number] == 1;
    }

    public boolean is_flat(int number) {
        number = prepare_sharp_flat(number);
        return mod[number] == -1;
    }

    public void toggle_note(int number) {
        number += change;

        if (notes.contains(number)) {
            notes.remove((Integer) number);
        } else {
            notes.add(number);
        }
    }

    public boolean is_note(int number) {
        number += change;

        return notes.contains(number);

    }
}

