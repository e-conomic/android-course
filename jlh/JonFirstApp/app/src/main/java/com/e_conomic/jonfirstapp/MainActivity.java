package com.e_conomic.jonfirstapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;


public class MainActivity extends FragmentActivity {

    // Tags
    final static String DISPLAY_MESSAGE_FRAGMENT_TAG = "DMF_TAG";

    // Values to be saved
    private Boolean isDisplayMessageFragmentVisible;

    // Keys.
    final static String IS_MESSAGE_FRAGMENT_VISIBLE = "IS_MSG_VISIBLE";

    // Fragments
    private DisplayMessageFragment display_message_fragment = null;
    private MainFragment main_fragment = null;

    // Views
    View fragment_container_display_message = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the correct state of the DisplayMessageFragment.
        if (savedInstanceState != null) {
            isDisplayMessageFragmentVisible =
                    savedInstanceState.getBoolean(IS_MESSAGE_FRAGMENT_VISIBLE);
        } else {
            // Show Fragment by default.
            isDisplayMessageFragmentVisible = true;
        }

        setContentView(R.layout.activity_main);

        setupFragments();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the state of the DisplayMessageFragment.
        savedInstanceState.putBoolean(IS_MESSAGE_FRAGMENT_VISIBLE, isDisplayMessageFragmentVisible);
        super.onSaveInstanceState(savedInstanceState);
    }

    /** Helper method that retrieves references to the fragments used in this activity and sets
     * the layout. Also creates the fragment that displays the message if it hasn't been created
     * yet. */
    private void setupFragments() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        // Setup main fragment
        main_fragment = (MainFragment) fragmentManager.findFragmentById(R.id.fragment_main);


        if (isDisplayMessageFragmentVisible) {
            // Set width of fragment layout container to fill half the screen.
            changeFragmentLayout(findViewById(R.id.fragment_container_display_message),
                    ViewGroup.LayoutParams.MATCH_PARENT);
        }

        // Find the DisplayMessageFragment on restart.
        display_message_fragment = (DisplayMessageFragment)
                fragmentManager.findFragmentByTag(DISPLAY_MESSAGE_FRAGMENT_TAG);

        // Create DisplayMessageFragment if it hasn't been created yet. Show by default.
        if (display_message_fragment == null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            display_message_fragment = new DisplayMessageFragment();
            ft.add(R.id.fragment_container_display_message, display_message_fragment,
                    DISPLAY_MESSAGE_FRAGMENT_TAG).commitNow();
        }

    }

    /** Called when the user clicks the Send button. */
    public void sendMessage(View view) {

        // If the display message fragment is visible show the message there.
        if (display_message_fragment != null) {
            display_message_fragment.updateMessage(main_fragment.getMessage());
        }

    }

    /** Called when the user clicks the show/hide button. */
    public void showHide(View view) {

        // If the displaying fragment has not been initialized return.
        if (display_message_fragment == null) {
            return;
        }

        // Setup FragmentManager and FragmentTransaction.
        if (fragment_container_display_message == null) {
            fragment_container_display_message = findViewById(R.id.fragment_container_display_message);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);


        // Hide Fragment if it is visible.
        if (display_message_fragment.isVisible()) {
            ft.hide(display_message_fragment).commitNow();

            isDisplayMessageFragmentVisible = false;

            // Inform the main fragment of the change
            main_fragment.setButtonText();

            // Set width of fragment layout container to 0.
            changeFragmentLayout(fragment_container_display_message, 0);
            return;
        }

        // Show Fragment if it is hidden.
        if (display_message_fragment.isHidden()) {
            ft.show(display_message_fragment).commitNow();

            isDisplayMessageFragmentVisible = true;

            // Inform the main fragment of the change
            main_fragment.setButtonText();

            // Set width of fragment layout container to fill half the screen.
            changeFragmentLayout(fragment_container_display_message, ViewGroup.LayoutParams.MATCH_PARENT);
            return;
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

    /** Check whether the fragment that displays the message is visible. */
    public Boolean isDisplayMessageFragmentVisible() {
        return isDisplayMessageFragmentVisible;
    }
}
