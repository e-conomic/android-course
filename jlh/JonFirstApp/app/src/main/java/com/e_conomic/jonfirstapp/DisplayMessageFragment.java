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
    private String prev_message = "";

    // The TextView that displays the message.
    private TextView display_message;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        String no_prev_message = getResources().getString(R.string.no_prev_message);

        // Get the previous message.
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        prev_message = sharedPreferences.getString(PREV_MESSAGE, no_prev_message);
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
        display_message = (TextView) getView().findViewById(R.id.text_view_display_message);
        display_message.setText(message);
        // Set the previous message.
        TextView display_prev_message = (TextView) getView().findViewById(R.id.text_view_display_prev_message);
        display_prev_message.setText(prev_message);
    }

    @Override
    public void onStop() {
        super.onStop();

        // Save the current message.
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

        if (display_message != null) {
            display_message.setText(message);
            return;
        }
        Log.w(DISPLAY_MESSAGE_FRAGMENT_TAG,
                "Trying to update message, but display_message is null.");
    }

}