package com.e_conomic.jonfirstapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;

public class DisplayMessageFragment extends Fragment {

    private String message = "123";

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
    public void updateMessage(String newMessage) {
        message = newMessage;
        TextView textView = (TextView) getView().findViewById(R.id.display_message_view);
        textView.setText(message);
    }

}