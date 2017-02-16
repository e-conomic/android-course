package com.e_conomic.jonfirstapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;


public class DisplayMessageFragment extends Fragment {

    // The message to be displayed.
    private String message = "";

    // The TextView that displays the message.
    private TextView display_message;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.display_message, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get the TextView that shows the message.
        display_message = (TextView) getView().findViewById(R.id.display_message_view);
        display_message.setText(message);
    }

    /** Updates the TextView that shows the message. */
    public void updateMessage(String newMessage) {
        message = newMessage;
        if (display_message != null) {
            display_message.setText(message);
        }
    }

}