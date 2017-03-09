package com.e_conomic.jonfirstapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import static android.Manifest.permission.SEND_SMS;

public class SMSPermissionExplanationFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setMessage(R.string.SMSExplanation);
        dialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{SEND_SMS}, MainFragment.SEND_SMS_PERMISSION_REQUEST);
            }
        });

        return dialogBuilder.create();
    }
}
