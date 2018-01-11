package russiabookmaker.perso.com.russiabookmaker.utils;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import russiabookmaker.perso.com.russiabookmaker.R;


/**
 * Created by lassayah on 11/01/2018.
 */

public class AlertDialogFragment extends DialogFragment {

    private AlertDialog.Builder builder;
    private static final String DIALOG_TITLE = "title";
    private static final String DIALOG_MESSAGE = "message";
    private String title;
    private String message;

    public AlertDialogFragment()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(DIALOG_TITLE);
            message = getArguments().getString(DIALOG_MESSAGE);
        }
    }

    public static AlertDialogFragment newInstance(String title, String message) {
        AlertDialogFragment fragment = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putString(DIALOG_TITLE, title);
        args.putString(DIALOG_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

}