package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SimpleChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_chat);

        String nick = getIntent().getStringExtra(MainActivity.NICK);
        String ip = getIntent().getStringExtra(MainActivity.IP);
//        nickTextView.setText(getIntent().getStringExtra(MainActivity.NICK));
    }
}
