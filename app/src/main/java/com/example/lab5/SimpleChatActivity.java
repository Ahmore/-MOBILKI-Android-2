package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class SimpleChatActivity extends AppCompatActivity {
    private static class MyHandler extends Handler{
        private final WeakReference<SimpleChatActivity> sActivity;
        MyHandler(SimpleChatActivity activity){
            sActivity = new WeakReference<SimpleChatActivity>(activity);
        }
        public void handleMessage(Message msg) {
            SimpleChatActivity activity = sActivity.get();
            activity.listItems.add("["+msg.getData().getString("NICK") + "]" +
                    msg.getData().getString("MSG"));
            activity.adapter.notifyDataSetChanged();
            activity.chatListView.setSelection(activity.listItems.size()-1);
        }
    }
    Handler myHandler = new MyHandler(this);

    private ListView chatListView;
    private EditText messageEditText;
    private Button sendButton;

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems=new ArrayList<String>();
    //DEFINING STRING ADAPTER WHICH WILL HANDLE DATA OF LISTVIEW
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_chat);

        chatListView = (ListView) findViewById(R.id.listView);
        messageEditText = (EditText) findViewById(R.id.messageEditText);
        sendButton = (Button) findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = myHandler.obtainMessage();
                Bundle b = new Bundle();
                b.putString("NICK", "JA");
                b.putString("MSG", messageEditText.getText().toString());
                msg.setData(b);
                myHandler.sendMessage(msg);
            }
        });

        String nick = getIntent().getStringExtra(MainActivity.NICK);
        String ip = getIntent().getStringExtra(MainActivity.IP);

        //w metodzie onCreate obslugujemy dodwanie wiadomosci do listy
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listItems);
        chatListView.setAdapter(adapter);
    }
}
