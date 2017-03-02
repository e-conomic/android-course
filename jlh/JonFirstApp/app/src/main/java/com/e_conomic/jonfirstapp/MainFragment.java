package com.e_conomic.jonfirstapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class MainFragment extends Fragment {

    MainFragmentListener delegate;

    private Boolean isDisplayMessageFragmentVisible = true;
    private Boolean isRecipientAdded = false;

    // Request codes
    final static int PICK_CONTACT_REQUEST = 1;

    // Contact keys
    final static String PHONE_NUMBER = "phoneNumber";
    final static String DISPLAY_NAME = "displayName";

    // Map with contact details. TODO: make this unambiguous?
    private Map<String, String> contactDetails = new HashMap<>();

    // Views
    private EditText editMessage;
    private Button showHideButton;
    private Button sendButton;
    private Button showAllMessagesButton;
    private Button chooseRecipientButton;
    private TextView recipientDetailsTextView = null;

    public interface MainFragmentListener {
        void sendMessage(String message);
        void showHide(Boolean showDisplayMessageFragment);
        void showAllMessages();
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
        sendButton = (Button) getView().findViewById(R.id.button_send);
        showAllMessagesButton = (Button) getView().findViewById(R.id.button_show_all_messages);
        chooseRecipientButton = (Button) getView().findViewById(R.id.button_choose_recipient);

        setButtonListeners();
        setButtonText();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_CONTACT_REQUEST && resultCode == RESULT_OK) {

            if (recipientDetailsTextView == null) {
                recipientDetailsTextView =
                        (TextView) getView().findViewById(R.id.text_view_recipient_details);
            }

            Uri contactUri = data.getData();
            String[] contactProjection = {
                    contactDetails.get(PHONE_NUMBER),
                    contactDetails.get(DISPLAY_NAME)
            };

            Cursor cursor = getActivity().getContentResolver().query(
                    contactUri, contactProjection, null, null, null);

            // Move cursor to first row.
            cursor.moveToFirst();

            // Get contact information.
            int contactNameColumn = cursor.getColumnIndex(contactDetails.get(DISPLAY_NAME));
            int contactNumberColumn = cursor.getColumnIndex(contactDetails.get(PHONE_NUMBER));

            String contactName = cursor.getString(contactNameColumn);
            String contactNumber = cursor.getString(contactNumberColumn);

            cursor.close();

            recipientDetailsTextView.setText(contactName + " at " + contactNumber);

            // TODO: use cursorLoader.
        }
    }

    private void setupContactDetails() {
        contactDetails.put(PHONE_NUMBER, ContactsContract.CommonDataKinds.Phone.NUMBER);
        contactDetails.put(DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
    }

    /** Helper function that sets the needed button listeners and their corresponding onClick
     * method, i.e. handle the button clicks. */
    private void setButtonListeners() {

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delegate.sendMessage(editMessage.getText().toString());
                editMessage.setText("");
            }
        });

        showHideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isDisplayMessageFragmentVisible = !isDisplayMessageFragmentVisible;
                delegate.showHide(isDisplayMessageFragmentVisible);
                setButtonText();
            }
        });

        showAllMessagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delegate.showAllMessages();
            }
        });

        chooseRecipientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isRecipientAdded) {
                    pickContact();
                } else {
                    addRecipient();
                    pickContact();
                    isRecipientAdded = true;
                }
            }
        });
    }

    /** Sets the text on the show/hide button. */
    private void setButtonText() {
        if (isDisplayMessageFragmentVisible) {
            showHideButton.setText(R.string.button_hidemsg);
        } else {
            showHideButton.setText(R.string.button_showmsg);
        }
    }


    /** Helper method that adds a view group containing recipient information. */
    private void addRecipient() {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View recipientViewGroup =
                layoutInflater.inflate(R.layout.recipient_layout, null);

        ViewGroup fragmentRootView = (ViewGroup) getView();
        LinearLayout.LayoutParams recipientLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        fragmentRootView.addView(recipientViewGroup, 1, recipientLayoutParams);
    }

    /** Creates a new activity that lets the user pick a contact to send the message to. */
    private void pickContact(){
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    /** Returns true if the fragment that displays the message is visible. */
    public Boolean isDisplayMessageFragmentVisible() {
        return isDisplayMessageFragmentVisible;
    }



}

