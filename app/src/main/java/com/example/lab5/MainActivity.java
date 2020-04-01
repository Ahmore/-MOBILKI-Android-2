package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static String IP="ip";
    public static String NICK="nick";

    private EditText ipEditText;
    private EditText nickEditText;
    private Button connectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ipEditText = (EditText) findViewById(R.id.ipEditText);
        nickEditText = (EditText) findViewById(R.id.nickEditText);
        connectButton = (Button) findViewById(R.id.connectBtn);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SimpleChatActivity.class);
                intent.putExtra(IP, ipEditText.getText().toString());
                intent.putExtra(NICK, nickEditText.getText().toString());
                startActivity(intent);
            }
        });
    }
}
