package com.cicioflaviu.wikicar.wikicar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;

public class AddCarDialog extends DialogFragment{
    EditText carMake;
    EditText carModel;
    EditText carDescription;

    AddCarDialogListener addListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_add_car, null))
                .setTitle("ADD CAR")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Dialog f = (Dialog) dialog;
                carMake = (EditText) f.findViewById(R.id.addCarMake);
                carModel = (EditText) f.findViewById(R.id.addCarModel);
                carDescription = (EditText) f.findViewById(R.id.addCarDescription);
                addListener.addButtonClicked(carMake.getText().toString(), carModel.getText().toString(), carDescription.getText().toString());
                dismiss();
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            addListener = (AddCarDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
