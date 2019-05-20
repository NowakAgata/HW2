package com.example.recyclerview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnDeleteDialogFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DeleteDialogFragment extends android.support.v4.app.DialogFragment {

    private OnDeleteDialogFragmentInteractionListener mListener;

    static DeleteDialogFragment newInstance() {
        return new DeleteDialogFragment() ;
    }
    public DeleteDialogFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getString(R.string.delete_question));
        builder.setPositiveButton(getString(R.string.dialog_confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onDialogPositiveClick(DeleteDialogFragment.this);
            }
        });
        builder.setNegativeButton(getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onDialogNegativeClick(DeleteDialogFragment.this);
            }
        });
        return  builder.create();
    }

    public interface OnDeleteDialogFragmentInteractionListener {
        // TODO: Update argument type and name
        void onDialogPositiveClick(DeleteDialogFragment dialog);
        void onDialogNegativeClick(DeleteDialogFragment dialog);
    }

//    public void onDialogPositiveClick(DeleteDialogFragment dialog){
//        if()
//    }


}
