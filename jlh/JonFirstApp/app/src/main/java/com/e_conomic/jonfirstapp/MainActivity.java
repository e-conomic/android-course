package com.e_conomic.jonfirstapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends FragmentActivity implements MainFragment.MainFragmentListener {

    // Tags
    final static String DISPLAY_MESSAGE_FRAGMENT_TAG = "DisplayMessageFragment";
    final static String MAIN_ACTIVITY_TAG = "MainActivity";

    // Layout keys and values
    final static String SHOW_FRAGMENT_LAYOUT = "showFragment";
    final static String LANDSCAPE_HIDE_FRAGMENT_LAYOUT = "landscapeHideFragment";
    final static String PORTRAIT_HIDE_FRAGMENT_LAYOUT = "portraitHideFragment";
    final static int LANDSCAPE = Configuration.ORIENTATION_LANDSCAPE;

    // Fragments
    private DisplayMessageFragment displayMessageFragment = null;

    // Views
    private View displayMessageFragmentContainer = null;

    // Map with layout values.
    private Map<String, LinearLayout.LayoutParams> layouts = new HashMap<>();

    // Filenames
    public final static String messageFilename = "messageFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupFragments();
    }

    /** Helper method that retrieves references to the fragments used in this activity and sets
     * the layout. Also creates the fragment that displays the message if it hasn't been created
     * yet. */
    private void setupFragments() {

        // Set fragment layout values.
        layouts.put(SHOW_FRAGMENT_LAYOUT,
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT, (float) 0.5));
        layouts.put(LANDSCAPE_HIDE_FRAGMENT_LAYOUT,
                new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,
                        (float) 0.5));
        layouts.put(PORTRAIT_HIDE_FRAGMENT_LAYOUT,
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, (float) 0.5));

        FragmentManager fragmentManager = getSupportFragmentManager();

        MainFragment mainFragment = (MainFragment)
                fragmentManager.findFragmentById(R.id.fragment_main);

        // Find the container that holds the DisplayMessageFragment.
        if (displayMessageFragmentContainer == null) {
            displayMessageFragmentContainer =
                    findViewById(R.id.fragment_container_display_message);
        }

        if (mainFragment.isDisplayMessageFragmentVisible()) {
            // Set width of fragment layout container to fill half the screen.
            displayMessageFragmentContainer.setLayoutParams(
                    layouts.get(SHOW_FRAGMENT_LAYOUT));
        }

        // Find the DisplayMessageFragment on restart.
        displayMessageFragment = (DisplayMessageFragment)
                fragmentManager.findFragmentByTag(DISPLAY_MESSAGE_FRAGMENT_TAG);

        // Create DisplayMessageFragment if it hasn't been created yet. Show by default.
        if (displayMessageFragment == null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            displayMessageFragment = new DisplayMessageFragment();
            ft.add(R.id.fragment_container_display_message, displayMessageFragment,
                    DISPLAY_MESSAGE_FRAGMENT_TAG).commitNow();
        }

    }

    /** Called when the user clicks the Send button.
     *
     * @param message The message to be shown. */
    public void sendMessage(String message) {

        // If the display message fragment is visible show the message there.
        if (displayMessageFragment != null) {
            displayMessageFragment.updateMessage(message);
        } else {
            Log.w(MAIN_ACTIVITY_TAG,
                    "Trying to send message, but displayMessageFragment is null.");
        }

        writeMessageToFile(message);

    }

    /** Helper function to write the sent message to file.
     *
     * @param message The message to write. */
    private void writeMessageToFile(String message) {

        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                .format(Calendar.getInstance().getTime());
        FileOutputStream messageOutputStream;
        String finalMessage = date + ":\n" + message + "\n";

        try {
            messageOutputStream = openFileOutput(messageFilename, Context.MODE_APPEND);
            messageOutputStream.write(finalMessage.getBytes());
            messageOutputStream.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /** Called when the user clicks the show/hide button.
     *
     * @param showDisplayMessageFragment True if the fragment that shows the message should be
     *                                   visible on screen. */
    public void showHide(Boolean showDisplayMessageFragment) {

        // If the displaying fragment has not been initialized return.
        if (displayMessageFragment == null) {
            Log.w(MAIN_ACTIVITY_TAG,
                    "Pressed show/hide button, but the displayMessageFragment is null.");
            return;
        }

        if (displayMessageFragmentContainer == null) {
            Log.w(MAIN_ACTIVITY_TAG,
                    "Pressed show/hide button, but the displayMessageFragmentContainer is null.");
            return;
        }

        // Setup FragmentManager and FragmentTransaction.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

        // Show or hide the DisplayMessageFragment.
        if (showDisplayMessageFragment) {
            ft.show(displayMessageFragment).commitNow();

            // Set size of displayMessageFragment layout container to fill half the screen.
            displayMessageFragmentContainer.setLayoutParams(
                    layouts.get(SHOW_FRAGMENT_LAYOUT));

        } else {
            ft.hide(displayMessageFragment).commitNow();

            int displayOrientation = getResources().getConfiguration().orientation;

            // Set main fragment to fill entire screen.
            if (displayOrientation == LANDSCAPE) {

                displayMessageFragmentContainer.setLayoutParams(
                        layouts.get(LANDSCAPE_HIDE_FRAGMENT_LAYOUT));
            } else {
                displayMessageFragmentContainer.setLayoutParams(
                        layouts.get(PORTRAIT_HIDE_FRAGMENT_LAYOUT));
            }
        }

    }

    /** Creates a new activity that shows all the previous messages, i.e. the contents of the
     * file containing the previously written messages. */
    public void showAllMessages() {
        this.startActivity(new Intent(this, DisplayAllMessagesActivity.class));
    }
}
