package com.mt.countdown;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * 从TextView中派生继承
 * 00:00:00
 */
public class CountDownView extends AppCompatTextView {
    public int totalTime;
    private int remainTime;
    private int duration;
    private int durationTemp;
    private sound sound = new sound();

    private OnTimerListener onTimerListener;
    private CountDownTimer countDownTimer;

    public CountDownView(Context context) {
        this(context, null);
    }

    public CountDownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void start() {
        duration = 0;
        durationTemp = 0;

        startTimer(totalTime);
    }


    public void stop() {
        if (countDownTimer == null) {
            return;
        }

        duration = 0;
        durationTemp = 0;
        countDownTimer.cancel();

        resetText(totalTime);
    }


    public void pause() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        durationTemp = duration;
    }


    public void goon() {
        startTimer(remainTime);
    }

    public void startTimer(int totalTime) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        /**
         * CountDownTimer 安卓自带倒计时控件，每隔1秒，执行以此onTick，共totalTime秒
         */
        countDownTimer = new CountDownTimer(totalTime * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                remainTime = (int) (millisUntilFinished / 1000);

                if (onTimerListener != null) {
                    if (durationTemp != 0) {
                        duration = durationTemp + totalTime - remainTime;
                    } else {
                        duration = totalTime - remainTime;
                    }
                    onTimerListener.onTick(duration);
                }

                resetText((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                Log.e("onTick", "结束");
                Toast.makeText(getContext(), "倒计时结束", Toast.LENGTH_LONG).show();
                Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(30000);//震动30秒
                sound.sound_start(getContext());//铃声开始
                new AlertDialog.Builder(getContext())
                        .setTitle("时间到了")
                        .setMessage("您设置的倒计时结束了！")
                        .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sound.sound_stop();//铃声关闭
                                vibrator.cancel();//震动关闭
                            }
                        }).create().show();
            }
        }.start();
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
        resetText(totalTime);
    }


    public void resetText(int totalSec) {
        int day = totalSec / (60 * 60 * 24);
        int hour = (totalSec - day * 60 * 60 * 24) / (60 * 60);
        int min = (totalSec - (hour * 60 * 60) - day * 60 * 60 * 24) / 60;
        int sec = totalSec % 60;

        String secStr = sec >= 10 ? String.valueOf(sec) : "0" + sec;
        String minStr = min >= 10 ? String.valueOf(min) : "0" + min;
        String hourStr = hour >= 10 ? String.valueOf(hour) : "0" + hour;
        String dayStr = day >= 10 ? String.valueOf(day) : "0" + day;
        /**
         * 显示在屏幕中间
         */
        setText(String.format("%s:%s:%s:%s", dayStr, hourStr, minStr, secStr));
    }

    public void setTimeTextView(TextView textView, int totalSec) {
        int day = totalSec / (60 * 60 * 24);
        int hour = (totalSec - day * 60 * 60 * 24) / (60 * 60);
        int min = (totalSec - (hour * 60 * 60) - day * 60 * 60 * 24) / 60;
        int sec = totalSec % 60;

        String secStr = sec >= 10 ? String.valueOf(sec) : "0" + sec;
        String minStr = min >= 10 ? String.valueOf(min) : "0" + min;
        String hourStr = hour >= 10 ? String.valueOf(hour) : "0" + hour;
        String dayStr = day >= 10 ? String.valueOf(day) : "0" + day;

        textView.setText(String.format("%s:%s:%s:%s", dayStr, hourStr, minStr, secStr));
    }

    public interface OnTimerListener {

        void onTick(int duration);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }


}
