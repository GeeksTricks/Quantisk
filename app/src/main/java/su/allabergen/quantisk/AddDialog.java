package su.allabergen.quantisk;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Rabat on 07.02.2016.
 */
public class AddDialog extends DialogFragment {
    EditText addEditText;
    String currFrag;
    View v;

    public AddDialog(String currFrag, View v) {
        this.currFrag = currFrag;
        this.v = v;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add, null);
        addEditText = (EditText) view.findViewById(R.id.addEditText);

        builder.setView(view)
                .setTitle(currFrag)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (currFrag.equals("addSiteBtn")) {
                            AdminActivity.siteList.add(addEditText.getText().toString());
                        } else if (currFrag.equals("addNameBtn")) {
//                            new PostWebService(getActivity(), addEditText.getText().toString()).execute("http://api-quantisk.rhcloud.com/v1/persons/");
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        Dialog dialog = builder.create();
        return dialog;
    }
}
