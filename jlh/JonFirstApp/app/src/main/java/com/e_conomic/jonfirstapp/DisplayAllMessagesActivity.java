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

    // TODO: which ones are really necessary?
    private String messageFilePath;
    private String messageFileName;
    private File messageFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all_messages);

        Intent intent = getIntent();
        messageFilePath = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_FILE_ABS_PATH);
        messageFileName = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_FILENAME);
        messageFile = new File(messageFilePath);
    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView textView = (TextView) findViewById(R.id.text_view_show_all_messages);
        textView.setText(getAllMessages());
    }


    private String getAllMessages() {
        FileInputStream messageInputStream;
        // TODO: make byte array of bigger size... (reads repeat some stuff??)
        byte[] tempMessages = new byte[1];
        ByteArrayOutputStream messageByteOutputStream = new ByteArrayOutputStream();

        try {
            messageInputStream = openFileInput(messageFileName);
            Log.i("AVAILABLE BYTES: ", Integer.toString(messageInputStream.available()));
            while (messageInputStream.read(tempMessages) != -1) {
                Log.i("TEMPORARY MSG: ", new String(tempMessages, Charset.defaultCharset()));
                messageByteOutputStream.write(tempMessages);
            }
            messageInputStream.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        String finalMessages = messageByteOutputStream.toString();
        Log.i("ALL THE MESSAGES:", finalMessages);
        return finalMessages;
    }

}
