package com.e_conomic.jonfirstapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.provider.ContactsContract.CommonDataKinds.Phone;

import static android.Manifest.permission.SEND_SMS;
import static android.app.Activity.RESULT_OK;

public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    MainFragmentListener delegate;

    private LoaderManager loaderManager;

    private Boolean isDisplayMessageFragmentVisible = true;
    private Boolean isRecipientAdded = false;

    private String contactPhoneNumber = null;
    private String contactName = null;
    private String message;

    // Request codes
    final static int PICK_CONTACT_REQUEST = 1;
    public final static int SEND_SMS_PERMISSION_REQUEST = 2;

    final static String MAIN_FRAGMENT_TAG = "MainFragment";
    final static String ENTER_MESSAGE_FRAGMENT_TAG = "EnterMessageFragment";
    final static String SMS_EXPLANATION_FRAGMENT_TAG ="SMSExplanationFragment";

    // Keys
    final static String CONTACT_DETAILS = "contactDetails";

    // Layout parameters
    LinearLayout.LayoutParams recipientViewLayoutParams;

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
                    + " must implement sendMessage, showHide and showAllMessages methods.");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        loaderManager = getLoaderManager();
        setupLayoutParams();
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

            Uri contactUri = data.getData();

            Bundle cursorLoaderArgs = new Bundle();
            cursorLoaderArgs.putString(CONTACT_DETAILS, contactUri.toString());

            // Start new cursorLoader or restart an old one.
            loaderManager.restartLoader(0, cursorLoaderArgs, this);

            // Avoid creating multiple recipient views.
            if (!isRecipientAdded) {
                addRecipientView((ViewGroup) getView());
                isRecipientAdded = true;
            }
        }
    }

    /** Helper method that creates the layout parameters used in this fragment class.*/
    private void setupLayoutParams() {
        recipientViewLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /** Helper method that sets the needed button listeners and their corresponding onClick
     * method, i.e. handle the button clicks. */
    private void setButtonListeners() {

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
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
                pickContact();
            }
        });
    }

    /** Sends the user entered message if permission was granted. Requests the permission if the app
     * does not already have it. Also shows a dialog if now message was entered. */
    private void sendMessage() {

        message = editMessage.getText().toString();

        if (message.length() <= 0) {
            EnterMessageDialogFragment dialog = new EnterMessageDialogFragment();
            dialog.show(getFragmentManager(), ENTER_MESSAGE_FRAGMENT_TAG);
            return;
        }

        delegate.sendMessage(message);
        editMessage.setText("");

        if (phoneNumberHasNotBeenEntered()) {
            return;
        }

        if (ContextCompat.checkSelfPermission(getActivity(), SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // SMS permission was not granted. Show explanation or request permission.
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), SEND_SMS)) {
                // Show explanation.
                SMSPermissionExplanationFragment dialog = new SMSPermissionExplanationFragment();
                dialog.show(getFragmentManager(), SMS_EXPLANATION_FRAGMENT_TAG);
            } else {
                // Request permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{SEND_SMS}, SEND_SMS_PERMISSION_REQUEST);
            }
        } else {
            // Permission was granted. Send SMS.
            sendSMS();
        }
    }

    /** Checks if the user has added a contact with a phone number. */
    private boolean phoneNumberHasNotBeenEntered() {
        return contactPhoneNumber == null;
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
    private void addRecipientView(ViewGroup rootView) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        View recipientViewGroup =
                layoutInflater.inflate(R.layout.recipient_layout, rootView, false);

        rootView.addView(recipientViewGroup, 1, recipientViewLayoutParams);

        recipientDetailsTextView =
                (TextView) getView().findViewById(R.id.text_view_recipient_details);
    }

    /** Creates a new activity that lets the user pick a contact to send the message to. */
    private void pickContact(){
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(Phone.CONTENT_TYPE);
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    /** Sends the user entered message to the chosen contact. */
    public void sendSMS() {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(contactPhoneNumber, null, message, null, null);
    }

    /** Returns true if the fragment that displays the message is visible. */
    public Boolean isDisplayMessageFragmentVisible() {
        return isDisplayMessageFragmentVisible;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if (args == null) {
            Log.w(MAIN_FRAGMENT_TAG, "Arguments are null when trying to load");
            return null;
        }

        Uri contactUri = Uri.parse(args.getString(CONTACT_DETAILS));

        String[] contactProjection = {
                Phone.NUMBER,
                Phone.DISPLAY_NAME
        };

        return new CursorLoader(getActivity(), contactUri, contactProjection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        data.moveToFirst();

        // Get contact information.
        contactPhoneNumber = data.getString(data.getColumnIndex(Phone.NUMBER));
        contactName = data.getString(data.getColumnIndex(Phone.DISPLAY_NAME));

        setRecipientDetailsView();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Not doing anything here, but can't remove it!
    }

    /** Helper method that sets the text of the text view that displays the recipient. */
    private void setRecipientDetailsView() {

        if (recipientDetailsTextView == null) {
            Log.w(MAIN_FRAGMENT_TAG, "Trying to add recipient details to a text view that is null");
            return;
        }

        recipientDetailsTextView.setText(contactName + getString(R.string.at_no) + contactPhoneNumber);
    }
}

