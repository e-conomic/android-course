package com.e_conomic.jonfirstapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends FragmentActivity {

    // Keys.
    final static String EXTRA_MESSAGE = "MESSAGE";
    final static String BUTTON_SHOWHIDE_TEXT = "SHOWHIDE_TEXT";
    final static String IS_MESSAGE_FRAGMENT_VISIBLE = "IS_MSG_VISIBLE";

    // Used to hold the reference to the text field containing the message to be written.
    private EditText editText = null;

    // Fragments
    private DisplayMessageFragment display_message_fragment = null;
    private MainFragment main_fragment = null;

    // Message string
    private String message = "";

    // Show/hide button
    private Button button_showhide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_layout);
        editText = (EditText) findViewById(R.id.edit_message);
        button_showhide = (Button) findViewById(R.id.button_showhide);

        FragmentManager fragmentManager = getSupportFragmentManager();
        main_fragment = (MainFragment) fragmentManager.findFragmentById(R.id.main_fragment);

        //if (savedInstanceState != null) {
        //    button_showhide.setText(savedInstanceState.getCharSequence(BUTTON_SHOWHIDE_TEXT));
        //} else {
        //    button_showhide.setText(R.string.button_showmsg);
        //}

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save state of show/hide button.
        //savedInstanceState.putCharSequence(BUTTON_SHOWHIDE_TEXT, button_showhide.getText());

        // Save state of the display message fragment.
        //if (display_message_fragment != null) {
        //    savedInstanceState.putBoolean(IS_MESSAGE_FRAGMENT_VISIBLE,
        //            display_message_fragment.isVisible());
        //}

        super.onSaveInstanceState(savedInstanceState);
    }

    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {

        message = main_fragment.getMessage();

        // If the display message fragment is visible show the message there.
        if (display_message_fragment != null
                && display_message_fragment.isVisible()) {
            TextView textView = (TextView) display_message_fragment.getView()
                    .findViewById(R.id.display_message_view);
            textView.setText(message);
            return;
        }

        // Create intent.
        Intent intent = new Intent(this, DisplayMessageActivity.class);

        // Add (user) entered message to the created intent.
        intent.putExtra(EXTRA_MESSAGE, message);

        // Start an instance of the DisplayMessageActivity specified by the intent.
        startActivity(intent);


    }

    /** Called when the user clicks the show/hide button */
    public void showHide(View view) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        View fragment_container = findViewById(R.id.land_fragment_container);

        main_fragment.onButtonClick();

        // If the displaying fragment has not been initialized.
        if (display_message_fragment == null && fragment_container != null) {

            // Create and add Fragment
            display_message_fragment = new DisplayMessageFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.land_fragment_container, display_message_fragment).commitNow();

            // Set width of fragment layout container to fill half the screen.
            changeFragmentLayout(fragment_container, ViewGroup.LayoutParams.MATCH_PARENT);
            return;
        }

        // Hide Fragment if it is visible.
        if (display_message_fragment.isVisible()) {
            fragmentManager.beginTransaction().hide(display_message_fragment).commitNow();

            // Set width of fragment layout container to 0.
            changeFragmentLayout(fragment_container, 0);
            return;
        }

        // Show Fragment if it is hidden.
        if (display_message_fragment.isHidden()) {
            fragmentManager.beginTransaction().show(display_message_fragment).commitNow();

            // Set width of fragment layout container to fill half the screen.
            changeFragmentLayout(fragment_container, ViewGroup.LayoutParams.MATCH_PARENT);
            return;
        }

    }

    /** Helper function to change the layout of the fragment container */
    private void changeFragmentLayout(View fragment_layout, int newLayoutParam) {
        ViewGroup.LayoutParams layoutParams = fragment_layout.getLayoutParams();
        // Set the width or height of the layout that contains the fragment.
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutParams.height = newLayoutParam;
        } else {
            layoutParams.width = newLayoutParam;
        }
        fragment_layout.setLayoutParams(layoutParams);
    }

    /** Called when the user clicks the clear messages button */
    public void clear(View view) {

        if (display_message_fragment != null) {
            TextView textView = (TextView)
                    display_message_fragment.getView().findViewById(R.id.display_message_view);
            message = "";
            textView.setText(message);
        }

    }
}
