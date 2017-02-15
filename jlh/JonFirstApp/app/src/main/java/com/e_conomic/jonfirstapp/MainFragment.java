package com.e_conomic.jonfirstapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;

public class MainFragment extends Fragment {

    private String BUTTON_SHOWHIDE_TEXT = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    public void setShowHideText(String text) {
        BUTTON_SHOWHIDE_TEXT = text;
    }

    public void setShowHideButtonText() {
        Button button = (Button) getView().findViewById(R.id.button_showhide);
        button.setText(BUTTON_SHOWHIDE_TEXT);
    }

}

