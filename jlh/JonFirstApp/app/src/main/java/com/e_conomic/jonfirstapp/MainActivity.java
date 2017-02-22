package com.e_conomic.jonfirstapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


public class MainActivity extends FragmentActivity implements MainFragment.MainFragmentListener {

    // Tags
    final static String DISPLAY_MESSAGE_FRAGMENT_TAG = "DisplayMessageFragment";
    final static String MAIN_ACTIVITY_TAG = "MainActivity";

    // Fragments
    private DisplayMessageFragment displayMessageFragment = null;

    // Views
    View displayMessageFragmentContainer = null;

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

        FragmentManager fragmentManager = getSupportFragmentManager();

        MainFragment mainFragment = (MainFragment) fragmentManager.findFragmentById(R.id.fragment_main);

        if (mainFragment.isDisplayMessageFragmentVisible()) {
            // Set width of fragment layout container to fill half the screen.
            changeFragmentLayout(findViewById(R.id.fragment_container_display_message),
                    ViewGroup.LayoutParams.MATCH_PARENT);
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
            return;
        }

        Log.w(MAIN_ACTIVITY_TAG, "Trying to send a message, but display_message_fragment is null.");

    }

    /** Called when the user clicks the show/hide button.
     *
     * @param showDisplayMessageFragment True if the DisplayMessageFragment should be shown on screen. */
    public void showHide(Boolean showDisplayMessageFragment) {

        // If the displaying fragment has not been initialized return.
        if (displayMessageFragment == null) {
            return;
        }

        // Setup FragmentManager and FragmentTransaction.
        if (displayMessageFragmentContainer == null) {
            displayMessageFragmentContainer =
                    findViewById(R.id.fragment_container_display_message);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);


        // Show or hide the DisplayMessageFragment.
        if (showDisplayMessageFragment) {
            ft.show(displayMessageFragment).commitNow();

            // Set size of displayMessageFragment layout container to fill half the screen.
            changeFragmentLayout(displayMessageFragmentContainer,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        } else {
            ft.hide(displayMessageFragment).commitNow();

            // Set main fragment to fill entire screen.
            changeFragmentLayout(displayMessageFragmentContainer, 0);
        }

    }

    /** Helper function to change the layout of the fragment container. */
    private void changeFragmentLayout(View fragment_layout, int newLayoutParam) {

        // Get the current Layout parameters.
        ViewGroup.LayoutParams layoutParams = fragment_layout.getLayoutParams();

        // Set the width or height of the layout that contains the fragment.
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutParams.height = newLayoutParam;
        } else {
            layoutParams.width = newLayoutParam;
        }

        // Set new Layout parameters.
        fragment_layout.setLayoutParams(layoutParams);
    }

}
