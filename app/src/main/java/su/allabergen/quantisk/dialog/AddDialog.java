package su.allabergen.quantisk.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import su.allabergen.quantisk.R;
import su.allabergen.quantisk.webServiceVolley.VolleyPost;
import su.allabergen.quantisk.webServiceVolley.VolleyPut;

/**
 * Created by Rabat on 07.02.2016.
 */
public class AddDialog extends DialogFragment {
    private View v;
    private EditText addEditText;
    private String currFrag;
    private String nameEdit;
    private String title;
    private int id;

    public AddDialog(String currFrag, View v) {
        this.currFrag = currFrag;
        this.v = v;
    }

    public AddDialog(String currFrag, View v, String nameEdit, int id) {
        this(currFrag, v);
        this.nameEdit = nameEdit;
        this.id = id;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add, null);
        addEditText = (EditText) view.findViewById(R.id.addEditText);
        addEditText.setHint(nameEdit);

        builder.setView(view)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (currFrag) {
                            case "addSiteBtn":
                                title = "Добавить сайт";
                                new VolleyPost(getActivity(), "https://api-quantisk.rhcloud.com/v1/sites/",
                                        addEditText.getText().toString(), "user1", "qwerty1");
                                break;

                            case "addPersonBtn":
                                title = "Добавить человека";
                                new VolleyPost(getActivity(), "https://api-quantisk.rhcloud.com/v1/persons/",
                                        addEditText.getText().toString(), "user1", "qwerty1");
                                break;

                            case "addKeywordBtn":
                                title = "Добавить ключевого слово";
                                break;

                            case "editSiteBtn":
                                title = "Изменить сайт";
                                new VolleyPut(getActivity(), "https://api-quantisk.rhcloud.com/v1/sites/",
                                        id, addEditText.getText().toString(), "user1", "qwerty1");
                                break;

                            case "editPersonBtn":
                                title = "Изменить человека";
                                new VolleyPut(getActivity(), "https://api-quantisk.rhcloud.com/v1/persons/",
                                        id, addEditText.getText().toString(), "user1", "qwerty1");
                                break;

                            case "editKeywordBtn":
                                title = "Изменить ключевого слово";
                                break;
                        }
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setTitle(title);

        Dialog dialog = builder.create();
        return dialog;
    }
}
