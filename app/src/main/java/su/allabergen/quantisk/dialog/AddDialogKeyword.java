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
import su.allabergen.quantisk.webServiceVolley.VolleyPostKeyword;
import su.allabergen.quantisk.webServiceVolley.VolleyPutKeyword;

/**
 * Created by Rabat on 07.02.2016.
 */
public class AddDialogKeyword extends DialogFragment {
    private View v;
    private EditText etKey1;
    private EditText etKey2;
    private EditText etDistance;
    private String currFrag;
    private String key1 = "Keyword 1";
    private String key2 = "Keyword 2";
    private String distance = "Distance";
    private String title;
    private int person_id;

    public AddDialogKeyword(String currFrag, View v) {
        this.currFrag = currFrag;
        this.v = v;
    }

    public AddDialogKeyword(String currFrag, View v, int person_id) {
        this(currFrag, v);
        this.person_id = person_id;
    }

    public AddDialogKeyword(String currFrag, View v, String key1, String key2, String distance, int person_id) {
        this(currFrag, v, person_id);
        this.key1 = key1;
        this.key2 = key2;
        this.distance = distance;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_keyword, null);
        etKey1 = (EditText) view.findViewById(R.id.etKey1);
        etKey2 = (EditText) view.findViewById(R.id.etKey2);
        etDistance = (EditText) view.findViewById(R.id.etDistance);
        if (currFrag.equals("addKeywordBtn")) {
            etKey1.setHint(key1);
            etKey2.setHint(key2);
            etDistance.setHint(distance);
        } else {
            etKey1.setHint("Keyword 1");
            etKey2.setHint("Keyword 2");
            etDistance.setHint("Distance");
            etKey1.setText(key1);
            etKey2.setText(key2);
            etDistance.setText(distance);
        }

        builder.setView(view)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (currFrag) {
                            case "addKeywordBtn":
                                title = "Добавить ключевое слово";
                                new VolleyPostKeyword(getActivity(), "https://api-quantisk.rhcloud.com/v1/wordpairs/",
                                        etKey1.getText().toString(),
                                        etKey2.getText().toString(),
                                        Integer.parseInt(etDistance.getText().toString()),
                                        person_id,
                                        "user1", "qwerty1");
                                break;

                            case "editKeywordBtn":
                                title = "Изменить ключевое слово";
                                new VolleyPutKeyword(getActivity(), "https://api-quantisk.rhcloud.com/v1/wordpairs/",
                                        etKey1.getText().toString(),
                                        etKey2.getText().toString(),
                                        Integer.parseInt(etDistance.getText().toString()),
                                        person_id,
                                        "user1", "qwerty1");
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
