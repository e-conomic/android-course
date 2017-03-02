package com.e_conomic.jonfirstapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;


public class DisplayMessageFragment extends Fragment {

    // Tags
    final static String DISPLAY_MESSAGE_FRAGMENT_TAG = "DisplayMessageFragment";

    // Keys
    static final String PREV_MESSAGE = "PREV_MESSAGE";

    // The messages to be displayed.
    private String message = "";
    private String prevMessage = "";

    // The TextView that displays the message.
    private TextView displayMessageTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        String noPrevMessage = getResources().getString(R.string.no_prev_message);

        // Get the previous message.
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        prevMessage = sharedPreferences.getString(PREV_MESSAGE, noPrevMessage);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_message, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get the TextView that shows the message.
        displayMessageTextView = (TextView) getView().findViewById(R.id.text_view_display_message);
        displayMessageTextView.setText(message);
        // Set the previous message.
        TextView displayPrevMessage = (TextView) getView().findViewById(R.id.text_view_display_prev_message);
        displayPrevMessage.setText(prevMessage);
    }

    @Override
    public void onStop() {
        super.onStop();
        saveCurrentMessage();
    }

    /** Helper function that saves the current message in a shared preference file. */
    private void saveCurrentMessage() {
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREV_MESSAGE, message);
        editor.commit();
    }

    /** Updates the TextView that shows the message.
     *
     * @param newMessage The new message to be shown. */
    public void updateMessage(String newMessage) {
        message = newMessage;

        if (displayMessageTextView == null) {
            Log.w(DISPLAY_MESSAGE_FRAGMENT_TAG,
                    "Trying to update message, but display_message is null.");
            return;
        }

        displayMessageTextView.setText(message);

    }

}