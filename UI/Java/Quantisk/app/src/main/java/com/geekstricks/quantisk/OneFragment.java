package com.geekstricks.quantisk;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;

public class OneFragment extends Fragment {

    Spinner personSpinner;
    Spinner siteSpinner;
    ListView resultsLV;

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        personSpinner = (Spinner) view.findViewById(R.id.spinner_person);
        siteSpinner = (Spinner) view.findViewById(R.id.spinner_site);
        resultsLV = (ListView) view.findViewById(R.id.results_lv);
        return view;
    }

}
