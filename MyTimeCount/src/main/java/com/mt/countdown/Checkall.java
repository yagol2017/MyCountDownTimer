package com.mt.countdown;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class Checkall extends AppCompatActivity {

    private ListView listView;
    private Button add, back, add_picker;
    private List<HashMap<String, Object>> data;
    private databean temp_databean = new databean();
    private final String TITLE = "title";
    private final String TIME = "time";
    private database database = new database(this);
    private ArrayList<databean> databeans = new ArrayList<>();
    private String[] min_numbers = {"0", "1", "2", "3", "5", "5", "6", "7", "8", "9", "10"};
    private String[] sec_numbers = {"0", "1", "2", "3", "5", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};
    private String[] shi_numbers = {"0", "1", "2", "3", "5", "5", "6", "7", "8", "9", "10"};
    int min = 0, sec = 0, shi = 0, min_sec = 0;
    private tools tools = new tools();
    private shi_min_sec sms = new shi_min_sec();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkall);
        getReady();
        databeans = database.getBean();
        setData();
        setAdapter();
        passData();
        deleteData();
    }

    public void getReady() {
        listView = (ListView) findViewById(R.id.listview);
        add = (Button) findViewById(R.id.add);
        add_picker = (Button) findViewById(R.id.add_picker);
    }

    public void add_picker(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Checkall.this);
        builder.setTitle("添加标题和时间：");
        View view = LayoutInflater.from(Checkall.this).inflate(R.layout.numberpicker, null);
        builder.setView(view);
        final EditText title = (EditText) view.findViewById(R.id.title);
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
        sec_picker.setMaxValue(59);
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

                temp_databean.setTitle(title.getText().toString());
                temp_databean.setTime(tools.Int2String(min_sec));
                database.addData(temp_databean);
                databeans = database.getBean();
                setData();
                setAdapter();
                Intent intent = new Intent(getApplicationContext(), Checkall.class);
                startActivity(intent);
                Checkall.this.overridePendingTransition(0, 0);
                Checkall.this.finish();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    public void add(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Checkall.this);
        builder.setTitle("添加标题和时间：");
        View view = LayoutInflater.from(Checkall.this).inflate(R.layout.add, null);
        builder.setView(view);
        final EditText title = (EditText) view.findViewById(R.id.title);
        final EditText time = (EditText) view.findViewById(R.id.time);
        builder.setPositiveButton("添加", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!isNumeric(time.getText().toString()) || time.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "请输入数字", Toast.LENGTH_SHORT).show();
                    time.setText("");
                } else {
                    temp_databean.setTitle(title.getText().toString());
                    temp_databean.setTime(time.getText().toString());
                    database.addData(temp_databean);
                    databeans = database.getBean();
                    setData();
                    setAdapter();
                    Intent intent = new Intent(getApplicationContext(), Checkall.class);
                    startActivity(intent);
                    Checkall.this.overridePendingTransition(0, 0);
                    Checkall.this.finish();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }


    public void setAdapter() {
        setData();
        SimpleAdapter simAdapter = new SimpleAdapter(
                Checkall.this, data, R.layout.simple_list,
                new String[]{TITLE, TIME}, new int[]{
                R.id.title, R.id.time});
        listView.setAdapter(simAdapter);
    }


    public void setData() {
        data = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < databeans.size(); i++) {
            HashMap<String, Object> maptemp = new HashMap<String, Object>();
            maptemp.put(TITLE, databeans.get(i).getTitle());
            if (databeans.get(i).getTitle() == "") {
                maptemp.put(TIME, "时间：" + databeans.get(i).getTime() + "s");
            } else {
                maptemp.put(TIME, "时间：" + tools.MakeTimeBetter(databeans.get(i).getTime()));
            }
            data.add(maptemp);
        }
    }

    public void back(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        Checkall.this.finish();
    }

    public void passData() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("time", Integer.valueOf(databeans.get(position).getTime()));
                intent.putExtra("title", databeans.get(position).getTitle());
                startActivity(intent);
                Checkall.this.finish();
            }
        });
    }

    public void deleteData() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                View view_LongClick = LayoutInflater.from(Checkall.this).inflate(R.layout.long_click_checkall, null);
                final Button share = (Button) view_LongClick.findViewById(R.id.share);
                final Button update = (Button) view_LongClick.findViewById(R.id.update);
                final Button delete = (Button) view_LongClick.findViewById(R.id.delete);
                AlertDialog.Builder builder_LongClick = new AlertDialog.Builder(Checkall.this);
                builder_LongClick.setView(view_LongClick);
                builder_LongClick.setTitle("选择操作");

                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, "标题：" + databeans.get(position).getTitle().toString() + "\n时间：" + databeans.get(position).getTime().toString());
                        startActivity(intent.createChooser(intent, "Share to..."));
                    }
                });

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder_update = new AlertDialog.Builder(Checkall.this);
                        builder_update.setTitle("修改时间");
                        View view_update = LayoutInflater.from(Checkall.this).inflate(R.layout.numberpicker, null);
                        builder_update.setView(view_update);
                        final EditText title = (EditText) view_update.findViewById(R.id.title);
                        final NumberPicker min_picker = (NumberPicker) view_update.findViewById(R.id.min_picker);
                        final NumberPicker sec_picker = (NumberPicker) view_update.findViewById(R.id.sec_picker);
                        final NumberPicker shi_picker = (NumberPicker) view_update.findViewById(R.id.shi_picker);
                        title.setText(databeans.get(position).getTitle());
                        title.setFocusable(false);
                        min_picker.setDisplayedValues(min_numbers);
                        sec_picker.setDisplayedValues(sec_numbers);
                        shi_picker.setDisplayedValues(shi_numbers);
                        min_picker.setMinValue(0);
                        sec_picker.setMinValue(0);
                        shi_picker.setMinValue(0);
                        min_picker.setMaxValue(10);
                        sec_picker.setMaxValue(59);
                        shi_picker.setMaxValue(10);
                        sms.setShiMinSec(databeans.get(position).getTime());
                        min_picker.setValue(sms.getMy_min());
                        sec_picker.setValue(sms.getMy_sec());
                        shi_picker.setValue(sms.getMy_shi());
                        min_picker.setDescendantFocusability(min_picker.FOCUS_BLOCK_DESCENDANTS);
                        sec_picker.setDescendantFocusability(sec_picker.FOCUS_BLOCK_DESCENDANTS);
                        shi_picker.setDescendantFocusability(shi_picker.FOCUS_BLOCK_DESCENDANTS);
                        builder_update.setPositiveButton("修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                shi = shi_picker.getValue();
                                min = min_picker.getValue();
                                sec = sec_picker.getValue();
                                min_sec = shi * 3600 + min * 60 + sec;
                                temp_databean.setTitle(databeans.get(position).getTitle());
                                temp_databean.setTime(tools.Int2String(min_sec));
                                database.updateData(temp_databean);
                                databeans = database.getBean();
                                setData();
                                setAdapter();
                                Intent intent = new Intent(getApplicationContext(), Checkall.class);
                                startActivity(intent);
                                Checkall.this.overridePendingTransition(0, 0);
                                Checkall.this.finish();
                            }
                        });
                        builder_update.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder_update.show();
                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        database.deleteData(databeans.get(position).getTitle());
                        databeans = database.getBean();
                        Intent intent = new Intent(getApplicationContext(), Checkall.class);
                        startActivity(intent);
                        Checkall.this.overridePendingTransition(0, 0);
                        Checkall.this.finish();
                    }
                });
                builder_LongClick.show();


//                new AlertDialog.Builder(Checkall.this)
//                        .setView(view_LongClick)
//                        .setTitle("选择操作")
//                        .setMessage("是否删除时间事件")
//                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        })
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                database.deleteData(databeans.get(position).getTitle());
//                                databeans = database.getBean();
//                                Intent intent = new Intent(getApplicationContext(), Checkall.class);
//                                startActivity(intent);
//                                Checkall.this.overridePendingTransition(0, 0);
//                                Checkall.this.finish();
//                            }
//                        }).create().show();
                return true;
            }
        });
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public void deleteall(View view) {
        database.deleteall();
        Intent intent = new Intent(getApplicationContext(), Checkall.class);
        startActivity(intent);
        Checkall.this.overridePendingTransition(0, 0);
        Checkall.this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_checkall, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
                Checkall.this.finish();
                break;
            case R.id.helper:
                Intent intent_helper = new Intent(getApplicationContext(), com.mt.countdown.helper.class);
                startActivity(intent_helper);
                Checkall.this.finish();
                break;
            case R.id.checkall_back_to_main:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                Checkall.this.finish();
                break;
            default:
                break;
        }
        return true;
    }

}
