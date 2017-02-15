package com.e_conomic.jonfirstapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainFragment extends Fragment {

    // True if the display message fragment is active.
    private Boolean DISPLAYMESSAGE_STATE;

    MainActivity main_activity;

    private EditText editMessage;
    private Button showHideButton;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            main_activity = (MainActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " is not an activity");
        }

        // Set the state of the DisplayMessageFragment.
        DISPLAYMESSAGE_STATE = main_activity.isDisplayMessageFragmentVisible();
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

    // Retrieve the message from the text field.
    public String getMessage() {
        return editMessage.getText().toString();
    }

    // Changes the text on the show/hide button and sets the DISPLAYMESSAGE_STATE value.
    public void onButtonClick() {
        DISPLAYMESSAGE_STATE = !DISPLAYMESSAGE_STATE;
        setButtonText();
    }

    // Sets the text on the show/hide button.
    private void setButtonText() {
        // Set correct button text.
        if (DISPLAYMESSAGE_STATE) {
            showHideButton.setText(R.string.button_hidemsg);
        } else {
            showHideButton.setText(R.string.button_showmsg);
        }
    }



}

