package com.samsolfeggio.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

import samsolfeggio.myapplication.R;

class DrawThread extends Thread {
    private volatile GraficQuestionActivity G_Q_A;
    private volatile Model model;
    private SurfaceHolder surfaceHolder;
    private Bitmap bitmap_key, bitmap_flat, bitmap_sharp, bitmap_note;
    private volatile boolean running = true;//флаг для остановки потока
    //boolean is_flat_on_pic = false;
    //boolean is_sharp_on_pic = false;

    public DrawThread(Model model, Context context, SurfaceHolder surfaceHolder) {
        bitmap_key = BitmapFactory.decodeResource(context.getResources(), R.drawable.k);
        bitmap_flat = BitmapFactory.decodeResource(context.getResources(), R.drawable.flat);
        bitmap_sharp = BitmapFactory.decodeResource(context.getResources(), R.drawable.sharp);

        bitmap_note = BitmapFactory.decodeResource(context.getResources(), R.drawable.note);

        this.model = model;
        this.surfaceHolder = surfaceHolder;
    }

    void requestStop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                try {
                    Paint paint = new Paint();
                    paint.setStyle(Paint.Style.FILL_AND_STROKE);
                    paint.setColor(Color.parseColor("#F5F5F5"));

                    canvas.drawPaint(paint);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setColor(Color.BLACK);
                    paint.setStrokeWidth(6);
                    //canvas.drawLine(0,canvas.getHeight() / 14 + 6 * canvas.getHeight() / 7,canvas.getWidth()/3, canvas.getHeight() / 14 + 6 * canvas.getHeight() / 7, paint);
                    //canvas.drawRect(canvas.getWidth() / 10, canvas.getHeight() / 10, canvas.getWidth() / 10 * 9, canvas.getHeight() / 2, paint);
                    for (int i = 0; i <= 6; i++) {
                        if (i == 0 || i == 6) {
                            paint.setPathEffect(new DashPathEffect(new float[]{10, 20}, 0));
                            paint.setColor(Color.parseColor("#808080"));
                            paint.setStrokeWidth(4);
                        } else {
                            paint.setPathEffect(null);
                            paint.setColor(Color.BLACK);
                            paint.setStrokeWidth(6);
                        }
                        canvas.drawLine(0, canvas.getHeight() / 14 + i * canvas.getHeight() / 7, canvas.getWidth(), canvas.getHeight() / 14 + i * canvas.getHeight() / 7, paint);
                    }
                    canvas.drawBitmap(bitmap_key, new Rect(0, 0, bitmap_key.getWidth(), bitmap_key.getHeight()), new Rect(canvas.getWidth() / 20, canvas.getHeight() / 7/*+ смещение*/, canvas.getWidth() / 20 * 5, canvas.getHeight() / 8 * 7), paint);
                    //canvas.drawBitmap(bitmap_flat, new Rect(0, 0, bitmap_flat.getWidth(), bitmap_flat.getHeight()), new Rect(canvas.getWidth() / 3, canvas.getHeight() / 3, canvas.getWidth() / 3 * 2, canvas.getHeight() / 3 * 2), paint);
                    /*canvas.drawRect(canvas.getWidth() / 10, canvas.getHeight() / 7 * 4, canvas.getWidth() / 10 * 4, canvas.getHeight() / 14 * 9, paint);
                    canvas.drawRect(canvas.getWidth() / 10, canvas.getHeight() / 14 * 10, canvas.getWidth() / 10 * 4, canvas.getHeight() / 14 * 11, paint);
                    canvas.drawRect(canvas.getWidth() / 10, canvas.getHeight() / 14 * 12, canvas.getWidth() / 10 * 4, canvas.getHeight() / 14 * 13, paint);
                    canvas.drawRect(canvas.getWidth() / 10 * 6, canvas.getHeight() / 7 * 4, canvas.getWidth() / 10 * 9, canvas.getHeight() / 14 * 10, paint);
                    canvas.drawRect(canvas.getWidth() / 10 * 6, canvas.getHeight() / 14 * 11, canvas.getWidth() / 10 * 9, canvas.getHeight() / 14 * 13, paint);*/
                    //canvas.drawBitmap(bitmap_flat, new Rect(0, 0, bitmap_flat.getWidth(), bitmap_flat.getHeight()), new Rect(canvas.getWidth() / 3, canvas.getHeight() / 3, canvas.getWidth() / 3 + 200, canvas.getHeight() / 3 +200), paint);
                    int x_note = (int) (canvas.getWidth() / 6 * 4);
                    int x_s_f = (int) (0.5 * canvas.getHeight());
                    int y_niz_dob = canvas.getHeight() / 14 + 6 * canvas.getHeight() / 7;
                    int y_half_line = canvas.getHeight() / 14;
                    for (Integer i = 0; i <= 14; i++) { // 28
                        if (model.is_flat(i)) {
                            int sm = 150;
                            if (i % 2 == 0) {
                                sm = 230;
                            }
                            int x = x_s_f - sm;
                            int y = y_niz_dob - y_half_line * (i + 1);
                            canvas.drawBitmap(bitmap_flat, new Rect(0, 0, bitmap_flat.getWidth(), bitmap_flat.getHeight()),
                                    new Rect(x, y, x + 100, y + 200), paint);
                        } else if (model.is_sharp(i)) {
                            int sm = 150;
                            if (i % 2 == 0) {
                                sm = 230;
                            }
                            int x = x_s_f - sm;
                            int y = (int) (y_niz_dob - y_half_line * (i + 0.5));
                            canvas.drawBitmap(bitmap_sharp, new Rect(0, 0, bitmap_flat.getWidth(), bitmap_flat.getHeight()),
                                    new Rect(x, y, x + 100, y + 200), paint);
                        }
                        if (model.is_note(i)) {
                            int y = (int) (y_niz_dob - y_half_line * (i + 0.5));
                            int sm = 170;
                            if (i % 2 == 0) {
                                sm = 0;
                            }
                            canvas.drawBitmap(bitmap_note, new Rect(0, 0, bitmap_flat.getWidth(), bitmap_flat.getHeight()),
                                    new Rect(x_note - sm, y, x_note - sm + 400, y + 640), paint);
                        }
                    }
                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
