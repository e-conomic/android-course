package com.e_conomic.jonfirstapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends FragmentActivity {

    // Values to be saved
    private Boolean isDisplayMessageFragmentVisible;

    // Keys.
    final static String EXTRA_MESSAGE = "MESSAGE";
    final static String IS_MESSAGE_FRAGMENT_VISIBLE = "IS_MSG_VISIBLE";

    // Fragments
    private DisplayMessageFragment display_message_fragment = null;
    private MainFragment main_fragment = null;

    // Message string
    private String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the correct state of the DisplayMessageFragment.
        if (savedInstanceState != null) {
            isDisplayMessageFragmentVisible =
                    savedInstanceState.getBoolean(IS_MESSAGE_FRAGMENT_VISIBLE);
        } else {
            isDisplayMessageFragmentVisible = false;
        }

        setContentView(R.layout.fragment_layout);

        FragmentManager fragmentManager = getSupportFragmentManager();
        main_fragment = (MainFragment) fragmentManager.findFragmentById(R.id.main_fragment);


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the state of the DisplayMessageFragment.
        savedInstanceState.putBoolean(IS_MESSAGE_FRAGMENT_VISIBLE, isDisplayMessageFragmentVisible);
        super.onSaveInstanceState(savedInstanceState);
    }

    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {

        // Retrieve message entered in the text field.
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
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

        // Set button text and internal values in the main fragment.
        main_fragment.onButtonClick();

        // If the displaying fragment has not been initialized.
        if (display_message_fragment == null && fragment_container != null) {

            // Create and add Fragment
            display_message_fragment = new DisplayMessageFragment();
            ft.add(R.id.land_fragment_container, display_message_fragment).commitNow();

            // Set width of fragment layout container to fill half the screen.
            changeFragmentLayout(fragment_container, ViewGroup.LayoutParams.MATCH_PARENT);
            return;
        }

        // Hide Fragment if it is visible.
        if (display_message_fragment.isVisible()) {
            ft.hide(display_message_fragment).commitNow();

            // Set width of fragment layout container to 0.
            changeFragmentLayout(fragment_container, 0);
            return;
        }

        // Show Fragment if it is hidden.
        if (display_message_fragment.isHidden()) {
            ft.show(display_message_fragment).commitNow();

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

    public Boolean isDisplayMessageFragmentVisible() {
        return isDisplayMessageFragmentVisible;
    }
}
