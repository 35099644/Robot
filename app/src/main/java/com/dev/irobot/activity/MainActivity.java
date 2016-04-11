package com.dev.irobot.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.dev.irobot.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showToast();
    }

    public void showToast(){
        Toast.makeText(this,"hook test!", Toast.LENGTH_SHORT).show();
    }
}
