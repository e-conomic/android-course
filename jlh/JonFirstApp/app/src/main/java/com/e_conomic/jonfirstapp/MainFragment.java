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

public class MainFragment extends Fragment implements View.OnClickListener {

    MainFragmentListener delegate;

    private Boolean isDisplayMessageFragmentVisible = true;

    private EditText editMessage;
    private Button showHideButton;

    public interface MainFragmentListener {
        void sendMessage(String message);
        void showHide(Boolean showDisplayMessageFragment);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            delegate = (MainFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement sendMessage and showHide methods.");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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

        // Get views used in this fragment.
        editMessage = (EditText) getView().findViewById(R.id.edit_text_message);
        showHideButton = (Button) getView().findViewById(R.id.button_showhide);
        Button sendButton = (Button) getView().findViewById(R.id.button_send);

        // Set listeners
        showHideButton.setOnClickListener(this);
        sendButton.setOnClickListener(this);

        setButtonText();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_send) {
            delegate.sendMessage(editMessage.getText().toString());
        } else {
            isDisplayMessageFragmentVisible = !isDisplayMessageFragmentVisible;
            delegate.showHide(isDisplayMessageFragmentVisible);
            setButtonText();
        }
    }

    /** Sets the text on the show/hide button. */
    private void setButtonText() {
        if (isDisplayMessageFragmentVisible) {
            showHideButton.setText(R.string.button_hidemsg);
        } else {
            showHideButton.setText(R.string.button_showmsg);
        }
    }

    /** Returns true if the fragment that displays the message is visible. */
    public Boolean isDisplayMessageFragmentVisible() {
        return isDisplayMessageFragmentVisible;
    }



}

