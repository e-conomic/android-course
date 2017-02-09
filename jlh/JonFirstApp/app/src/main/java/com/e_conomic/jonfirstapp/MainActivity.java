package com.e_conomic.jonfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends AppCompatActivity {

    // Key for the intent extra holding the message.
    public final static String EXTRA_MESSAGE = "MESSAGE";

    // Used to hold the reference to the text field containing the message to be written.
    private EditText editText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_layout);
        //editText = (EditText) findViewById(R.id.edit_message);
    }

    /** Called when the user clicks the Send button
    public void sendMessage(View view) {
        // Create intent.
        Intent intent = new Intent(this, DisplayMessageActivity.class);

        // Add (user) entered message to the created intent.
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);

        // Start an instance of the DisplayMessageActivity specified by the intent.
        startActivity(intent);
    }*/
}
