package com.e_conomic.jonfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;


public class DisplayAllMessagesActivity extends AppCompatActivity {

    private String messageFileName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all_messages);

        Intent intent = getIntent();
        messageFileName = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_FILENAME);
    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView textView = (TextView) findViewById(R.id.text_view_show_all_messages);
        textView.setText(getAllMessages());
    }


    /** Retrieve all the previous messages written. */
    private String getAllMessages() {
        FileInputStream messageInputStream;
        int tempByte;
        StringBuffer allMessages = new StringBuffer("");

        try {
            messageInputStream = openFileInput(messageFileName);
            while ((tempByte = messageInputStream.read()) != -1) {
                allMessages.append((char) tempByte);
            }
            messageInputStream.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return allMessages.toString();
    }

}
