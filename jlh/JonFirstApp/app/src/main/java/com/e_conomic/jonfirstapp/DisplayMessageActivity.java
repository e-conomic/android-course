package com.e_conomic.jonfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Grab the intent that started this activity.
        Intent intent = getIntent();
        // Get the message to be shown.
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        // Create the text view and set the message.
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        // Add the text view to the corresponding layout to show the message.
        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
        layout.addView(textView);
    }
}
