package com.e_conomic.jonfirstapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainFragment extends Fragment {

    private Boolean DISPLAYMESSAGE_STATE = false;

    private EditText editMessage;
    private Button showHideButton;

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editMessage = (EditText) getView().findViewById(R.id.edit_message);
        showHideButton = (Button) getView().findViewById(R.id.button_showhide);

        setButtonText();
    }

    public String getMessage() {
        return editMessage.getText().toString();
    }

    public void onButtonClick() {
        DISPLAYMESSAGE_STATE = !DISPLAYMESSAGE_STATE;
        setButtonText();
    }


    private void setButtonText() {
        // Set correct button text.
        if (DISPLAYMESSAGE_STATE) {
            showHideButton.setText(R.string.button_hidemsg);
        } else {
            showHideButton.setText(R.string.button_showmsg);
        }
    }



}

