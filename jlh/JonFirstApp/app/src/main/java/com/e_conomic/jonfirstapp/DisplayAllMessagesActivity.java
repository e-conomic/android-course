package com.e_conomic.jonfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class DisplayAllMessagesActivity extends AppCompatActivity {

    private String allMessages;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all_messages);

        Intent intent = getIntent();
        allMessages = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_FILENAME);
    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView textView = (TextView) findViewById(R.id.text_view_show_all_messages);
        textView.setText(allMessages);
    }

}
