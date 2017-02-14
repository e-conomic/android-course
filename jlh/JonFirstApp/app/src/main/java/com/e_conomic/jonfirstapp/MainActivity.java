package com.e_conomic.jonfirstapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends FragmentActivity {

    // Key for the intent extra holding the message.
    public final static String EXTRA_MESSAGE = "MESSAGE";

    // Used to hold the reference to the text field containing the message to be written.
    private EditText editText = null;

    // Fragments
    private Fragment display_message_fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_layout);
        editText = (EditText) findViewById(R.id.edit_message);

    }

    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {

        // The message entered by the user.
        String message = editText.getText().toString();

        // Create intent.
        Intent intent = new Intent(this, DisplayMessageActivity.class);

        // Add (user) entered message to the created intent.
        intent.putExtra(EXTRA_MESSAGE, message);

        // Start an instance of the DisplayMessageActivity specified by the intent.
        startActivity(intent);


    }

    /** Called when the user clicks the show/hide button */
    public void showHide(View view) {

        // Check that the correct layout is used.
        if (findViewById(R.id.land_fragment_container) != null) {
            // Create and add Fragment
            display_message_fragment = new DisplayMessageFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.land_fragment_container, display_message_fragment).commitNow();

            //Edit the text view (show written message).
            if (display_message_fragment != null) {
                TextView textView = (TextView)
                        // Returns null atm
                        display_message_fragment.getView().findViewById(R.id.display_message_view);
                textView.setText("hej2");
            }
        }

    }
}
