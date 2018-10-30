package com.mt.countdown;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CountDownView countDownView;
    private Button checkall, setyoutime, helper, exit, setyoutime_picker;
    private TextView welcome1, welcome2, welcome3, nowtime, welcome4, nowtitle;
    private int CountDowntime = 0;//界面倒计时时间
    private int passtime = 0;//从Checkall传递过来的时间
    private String passtitle = "";//从Checkall传递过来的事件名称
    private String[] min_numbers = {"0", "1", "2", "3", "5", "5", "6", "7", "8", "9", "10"};
    private String[] sec_numbers = {"0", "1", "2", "3", "5", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};
    private String[] shi_numbers = {"0", "1", "2", "3", "5", "5", "6", "7", "8", "9", "10"};
    int min = 0, sec = 0, shi = 0, min_sec = 0;
    private databean temp_databean = new databean();
    private tools tools = new tools();

    public void stop(View view) {
        countDownView.stop();
    }

    public void pause(View view) {
        countDownView.pause();
    }

    /**
     * 开始按钮
     *
     * @param view
     */
    public void start(View view) {
        welcome1.setVisibility(View.GONE);
        welcome2.setVisibility(View.GONE);
        countDownView.setTotalTime(CountDowntime);
        countDownView.start();
    }

    public void goon(View view) {
        countDownView.goon();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getReady();
        if (passtime != 0) {
            CountDowntime = passtime;
            nowtitle.setText(passtitle);
        }
    }

    public void getReady() {
        countDownView = findViewById(R.id.id_count_down);
        checkall = (Button) findViewById(R.id.checkall);
        setyoutime = (Button) findViewById(R.id.setyoutime);
        setyoutime_picker = (Button) findViewById(R.id.setyoutime_picker);
        welcome1 = (TextView) findViewById(R.id.welcome1);
        welcome2 = (TextView) findViewById(R.id.welcome2);
        welcome3 = (TextView) findViewById(R.id.welcome3);
        welcome4 = (TextView) findViewById(R.id.welcome4);
        nowtitle = (TextView) findViewById(R.id.nowtitle);
        nowtime = (TextView) findViewById(R.id.nowtime);
        checkall.setOnClickListener(this);
        Intent intent_this = this.getIntent();
        passtime = intent_this.getIntExtra("time", 0);
        passtitle = intent_this.getStringExtra("title");
        countDownView.setTimeTextView(nowtime, passtime);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checkall:
                Intent intent_checkall = new Intent(getApplicationContext(), Checkall.class);
                startActivity(intent_checkall);
                MainActivity.this.finish();
                break;
            case R.id.helper:
                Intent intent_helper = new Intent(getApplicationContext(), com.mt.countdown.helper.class);
                startActivity(intent_helper);
                MainActivity.this.finish();
                break;
            case R.id.exit:
                this.finish();
                break;
        }
    }

    /**
     * 按钮的onClick，单击触发输入框
     *
     * @param view
     */
    public void alert_setyoutime(View view) {
        final EditText getTime_editText = new EditText(this);
        getTime_editText.setHint("单位：秒");
        new AlertDialog.Builder(this).setTitle("设置时间：")
                .setIcon(android.R.drawable.sym_def_app_icon)
                .setView(getTime_editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!isNumeric(getTime_editText.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "请输入数字", Toast.LENGTH_SHORT).show();
                            getTime_editText.setText("");
                        } else {
                            CountDowntime = Integer.valueOf(getTime_editText.getText().toString());
                            countDownView.setTimeTextView(nowtime, CountDowntime);
                            nowtitle.setText("自定义事件");
                        }
                    }
                }).setNegativeButton("取消", null).show();
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public void setyoutime_picker(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("设置时间：");
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.numberpicker, null);
        builder.setView(view);
        final EditText title = (EditText) view.findViewById(R.id.title);
        title.setVisibility(View.GONE);
        final NumberPicker min_picker = (NumberPicker) view.findViewById(R.id.min_picker);
        final NumberPicker sec_picker = (NumberPicker) view.findViewById(R.id.sec_picker);
        final NumberPicker shi_picker = (NumberPicker) view.findViewById(R.id.shi_picker);
        min_picker.setDisplayedValues(min_numbers);
        sec_picker.setDisplayedValues(sec_numbers);
        shi_picker.setDisplayedValues(shi_numbers);
        min_picker.setMinValue(0);
        sec_picker.setMinValue(0);
        shi_picker.setMinValue(0);
        min_picker.setMaxValue(10);
        sec_picker.setMaxValue(21);
        shi_picker.setMaxValue(10);
        min_picker.setValue(0);
        sec_picker.setValue(0);
        shi_picker.setValue(0);
        min_picker.setDescendantFocusability(min_picker.FOCUS_BLOCK_DESCENDANTS);
        sec_picker.setDescendantFocusability(sec_picker.FOCUS_BLOCK_DESCENDANTS);
        shi_picker.setDescendantFocusability(shi_picker.FOCUS_BLOCK_DESCENDANTS);
        builder.setPositiveButton("添加", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                shi = shi_picker.getValue();
                min = min_picker.getValue();
                sec = sec_picker.getValue();
                min_sec = shi * 3600 + min * 60 + sec;
                CountDowntime = min_sec;
                countDownView.setTimeTextView(nowtime, CountDowntime);
                nowtitle.setText("自定义事件");
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
                MainActivity.this.finish();
                break;
            case R.id.helper:
                Intent intent_helper = new Intent(getApplicationContext(), com.mt.countdown.helper.class);
                startActivity(intent_helper);
                MainActivity.this.finish();
                break;
        }
        return true;
    }
}
