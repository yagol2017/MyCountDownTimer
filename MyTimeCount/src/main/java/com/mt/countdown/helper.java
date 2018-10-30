package com.mt.countdown;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class helper extends AppCompatActivity {
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper);
    }

    public void backtomain(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        helper.this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_helper, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
                helper.this.finish();
                break;
            case R.id.back_to_main:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                helper.this.finish();
                break;
            case R.id.back_to_checkall:
                Intent intent_checkall = new Intent(getApplicationContext(), Checkall.class);
                startActivity(intent_checkall);
                helper.this.finish();
                break;
        }
        return true;
    }
}
