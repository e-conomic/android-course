package com.e_conomic.jonfirstapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

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

    // Maps with layout values.
    private Map<String, ViewGroup.LayoutParams> fragmentLayouts = new HashMap<>();

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
        fragmentLayouts.put(SHOW_FRAGMENT_LAYOUT,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        fragmentLayouts.put(LANDSCAPE_HIDE_FRAGMENT_LAYOUT,
                new ViewGroup.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT));
        fragmentLayouts.put(PORTRAIT_HIDE_FRAGMENT_LAYOUT,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));

        FragmentManager fragmentManager = getSupportFragmentManager();

        MainFragment mainFragment = (MainFragment)
                fragmentManager.findFragmentById(R.id.fragment_main);

        if (mainFragment.isDisplayMessageFragmentVisible()) {
            // Set width of fragment layout container to fill half the screen.
            changeFragmentLayout(findViewById(R.id.fragment_container_display_message),
                    fragmentLayouts.get(SHOW_FRAGMENT_LAYOUT));
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
     * @param showDisplayMessageFragment True if the fragment that shows the message should be
     *                                   visible on screen. */
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
                    fragmentLayouts.get(SHOW_FRAGMENT_LAYOUT));
        } else {
            ft.hide(displayMessageFragment).commitNow();

            int displayOrientation = getResources().getConfiguration().orientation;

            // Set main fragment to fill entire screen.
            if (displayOrientation == LANDSCAPE) {
                changeFragmentLayout(displayMessageFragmentContainer,
                        fragmentLayouts.get(LANDSCAPE_HIDE_FRAGMENT_LAYOUT));
            } else {
                changeFragmentLayout(displayMessageFragmentContainer,
                        fragmentLayouts.get(PORTRAIT_HIDE_FRAGMENT_LAYOUT));
            }
        }

    }

    /** Helper function to change the layout of the fragment container.
     *
     * @param viewGroup The view that receives a layout change.
     * @param newLayoutParams The new layout parameters that will be added to the given view. */
    private void changeFragmentLayout(View viewGroup, ViewGroup.LayoutParams newLayoutParams) {

        // Get the current Layout parameters.
        ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();

        // Set the width and height of the layout that contains the fragment.
        layoutParams.height = newLayoutParams.height;
        layoutParams.width = newLayoutParams.width;

        // Set new Layout parameters.
        viewGroup.setLayoutParams(layoutParams);
    }

}
