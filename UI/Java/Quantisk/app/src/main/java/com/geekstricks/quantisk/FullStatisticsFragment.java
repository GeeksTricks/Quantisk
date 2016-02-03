package com.geekstricks.quantisk;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.Arrays;

public class FullStatisticsFragment extends Fragment {

    private Spinner personSpinner;
    private Spinner siteSpinner;
    private ListView resultsLV;
    private String[] persons;
    private String[] sites;

    public FullStatisticsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_full_statistics, container, false);
        initArrays();
        initItems(view);
        return view;
    }

    private void initArrays() {
        persons = new String[]{"Leonardo DiCaprio", "Quentin Tarantino", "Trent Reznor"};
        sites = new String[]{"twitter.com", "gossip.com"};
    }

    private void initItems(View view) {
        personSpinner = (Spinner) view.findViewById(R.id.spinner_person);
        siteSpinner = (Spinner) view.findViewById(R.id.spinner_site);
        resultsLV = (ListView) view.findViewById(R.id.results_lv);

        ArrayAdapter<String> personAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, persons);
        personSpinner.setAdapter(personAdapter);

        ArrayAdapter<String> siteAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, sites);
        siteSpinner.setAdapter(siteAdapter);
    }
}
