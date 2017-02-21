package com.e_conomic.jonfirstapp;

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

    MainActivity mainActivity;

    private EditText editMessage;
    private Button showHideButton;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Retrieve the main activity.
        try {
            mainActivity = (MainActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " is not a MainActivity.");
        }

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

        // Get views used in this fragment.
        editMessage = (EditText) getView().findViewById(R.id.edit_text_message);
        showHideButton = (Button) getView().findViewById(R.id.button_showhide);

        setButtonText();
    }

    /** Retrieve the message from the text field. */
    public String getMessage() {
        return editMessage.getText().toString();
    }

    /** Sets the text on the show/hide button. */
    public void setButtonText() {
        // Set correct button text.
        if (mainActivity != null && mainActivity.isDisplayMessageFragmentVisible()) {
            showHideButton.setText(R.string.button_hidemsg);
        } else {
            showHideButton.setText(R.string.button_showmsg);
        }
    }



}

