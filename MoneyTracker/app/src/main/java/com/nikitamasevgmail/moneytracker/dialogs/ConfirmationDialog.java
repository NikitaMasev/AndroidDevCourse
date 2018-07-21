package com.nikitamasevgmail.moneytracker.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.nikitamasevgmail.moneytracker.R;
import com.nikitamasevgmail.moneytracker.listeners.ConfirmationDialogListener;

public class ConfirmationDialog extends DialogFragment {

    public static final String TAG_ID = "ConfirmationDialog";

    private ConfirmationDialogListener confirmationDialogListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setTitle(R.string.title_confirmation_dialog)
                .setMessage(R.string.msg_confirmation_dialog)
                .setPositiveButton(R.string.name_positive_btn_confirmation_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        confirmationDialogListener.onClickPositive();
                    }
                })
                .setNegativeButton(R.string.name_negative_btn_confirmation_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        confirmationDialogListener.onClickNegative();
                    }
                })
                .create();

        alertDialog.show();

        return alertDialog;
    }

    public void setConfirmationDialogListener(ConfirmationDialogListener confirmationDialogListener) {
        this.confirmationDialogListener = confirmationDialogListener;
    }
}
