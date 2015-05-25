package com.example.phil.labagil;

import android.app.AlertDialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Phil on 15/04/15.
 */
public class Sorting1 extends DialogFragment {

    ArrayList mSelectedItems = new ArrayList();  // Where we track the selected items
    final String choices[] = {"_id", "title", "rating"}; // VIL INTE KUNNA ÄNDRA DENNA

    int choosen = 0;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        choosen = getActivity().getPreferences(0).getInt("sorting", 0);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle("Sorting")
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setSingleChoiceItems(choices, choosen,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("manu", "Valt: " + choices[which]);
                                choosen = which;
                            }
                        })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog
                        SharedPreferences pref = getActivity().getPreferences(0);
                        pref.edit().putInt("sorting", choosen).apply();

                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        return builder.create();
    }
}
