package su.allabergen.quantisk.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import su.allabergen.quantisk.R;
import su.allabergen.quantisk.dialog.AddDialog;
import su.allabergen.quantisk.model.Person;
import su.allabergen.quantisk.model.Sites;
import su.allabergen.quantisk.webServiceVolley.VolleyDelete;

import static su.allabergen.quantisk.webServiceVolley.VolleyGet._personList0;
import static su.allabergen.quantisk.webServiceVolley.VolleyGet._siteList0;

public class SettingsActivity extends AppCompatActivity {

    public static final int PERSON = 0;
    public static final int SITE = 1;

    public static List<String> _keywordsList = new ArrayList<>();

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        _keywordsList.clear();
        _keywordsList.add("No Keywords");

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NavUtils.navigateUpFromSameTask(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "САЙТЫ";
                case 1:
                    return "ЛЮДИ";
                case 2:
                    return "КЛЮЧЕВЫЕ СЛОВА";
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
        private Button addPersonBtn;
        private Button removePersonBtn;
        private Button editPersonBtn;
        private Button addSiteBtn;
        private Button removeSiteBtn;
        private Button editSiteBtn;
        private Button addUserBtn;
        private Button removeUserBtn;
        private Button editUserBtn;
        private View vSite;
        private View vName;
        private View vUser;
        private String personSelected;
        private String siteSelected;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                View siteView = inflater.inflate(R.layout.fragment_site, container, false);
                TextView siteTextView = (TextView) siteView.findViewById(R.id.site_label);
                Spinner siteSpinner = (Spinner) siteView.findViewById(R.id.siteSpinner);
                siteSpinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, AdminActivity._siteList));
                siteSpinner.setOnItemSelectedListener(this);
                siteTextView.setText("Изменить сайт");
                initSiteBtn(siteView);
                return siteView;
            }

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                View nameView = inflater.inflate(R.layout.fragment_person, container, false);
                TextView nameTextView = (TextView) nameView.findViewById(R.id.name_label);
                Spinner nameSpinner = (Spinner) nameView.findViewById(R.id.personSpinner);
                nameSpinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, AdminActivity._nameList));
                nameSpinner.setOnItemSelectedListener(this);
                nameTextView.setText("Изменить людей");
                initNameBtn(nameView);
                return nameView;
            }

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 3) {
                View userView = inflater.inflate(R.layout.fragment_keywords, container, false);
                TextView keywordsTextView = (TextView) userView.findViewById(R.id.keywords_label);
                Spinner keywordsSpinner = (Spinner) userView.findViewById(R.id.keywordsSpinner);
                keywordsSpinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, _keywordsList));
                keywordsSpinner.setOnItemSelectedListener(this);
                keywordsTextView.setText("Изменить ключевых слов");
                initKeywordsBtn(userView);
                return userView;
            }

            return null;
        }

        private void initSiteBtn(View siteView) {
            vSite = siteView;
            addSiteBtn = (Button) siteView.findViewById(R.id.addSiteBtn);
            removeSiteBtn = (Button) siteView.findViewById(R.id.removeSiteBtn);
            editSiteBtn = (Button) siteView.findViewById(R.id.editSiteBtn);
            addSiteBtn.setOnClickListener(this);
            removeSiteBtn.setOnClickListener(this);
            editSiteBtn.setOnClickListener(this);
        }

        private void initNameBtn(View personView) {
            vName = personView;
            addPersonBtn = (Button) personView.findViewById(R.id.addPersonBtn);
            removePersonBtn = (Button) personView.findViewById(R.id.removePersonBtn);
            editPersonBtn = (Button) personView.findViewById(R.id.editPersonBtn);
            addPersonBtn.setOnClickListener(this);
            removePersonBtn.setOnClickListener(this);
            editPersonBtn.setOnClickListener(this);
        }

        private void initKeywordsBtn(View userView) {
            vUser = userView;
            addUserBtn = (Button) userView.findViewById(R.id.addKeywordsBtn);
            removeUserBtn = (Button) userView.findViewById(R.id.removeKeywordsBtn);
            editUserBtn = (Button) userView.findViewById(R.id.editKeywordsBtn);
            addUserBtn.setOnClickListener(this);
            removeUserBtn.setOnClickListener(this);
            editUserBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String currFrag = "";
            View view = null;

            switch (v.getId()) {
                case R.id.addSiteBtn:
                    currFrag = "addSiteBtn";
                    view = vSite;
                    break;
                case R.id.addPersonBtn:
                    currFrag = "addPersonBtn";
                    view = vName;
                    break;
                case R.id.addKeywordsBtn:
                    currFrag = "addUserBtn";
                    view = vUser;
                    break;
                case R.id.removeSiteBtn:
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Удалить сайт")
                            .setMessage("Вы уверены, что хотите удалить этот сайт?")
                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int check = check(SITE);
                                    new VolleyDelete(getActivity(), "https://api-quantisk.rhcloud.com/v1/sites/", check, "user1", "qwerty1");

                                }
                            })
                            .setNegativeButton("Нет", null)
                            .create()
                            .show();
                    break;
                case R.id.removePersonBtn:
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Удалить человека")
                            .setMessage("Вы уверены, что хотите удалить этого человека?")
                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int check = check(PERSON);
                                    new VolleyDelete(getActivity(), "https://api-quantisk.rhcloud.com/v1/persons/", check, "user1", "qwerty1");
                                }
                            })
                            .setNegativeButton("Нет", null)
                            .create()
                            .show();
                    break;
                case R.id.removeKeywordsBtn:
                    currFrag = "removeUserBtn";
                    view = vUser;
                    break;
                case R.id.editSiteBtn:
                    currFrag = "editSiteBtn";
                    view = vSite;
                    break;
                case R.id.editPersonBtn:
                    currFrag = "editPersonBtn";
                    view = vName;
                    break;
                case R.id.editKeywordsBtn:
                    currFrag = "editUserBtn";
                    view = vUser;
                    break;
            }

            AddDialog dialog = null;
            if (currFrag.equals("removeSiteBtn") || currFrag.equals("removePersonBtn") || currFrag.equals("removeUserBtn")) {
            } else if (currFrag.equals("editSiteBtn")) {
                dialog = new AddDialog(currFrag, view, siteSelected, check(SITE));
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                dialog.show(ft, "Add Dialog");
            } else if (currFrag.equals("editPersonBtn")) {
                dialog = new AddDialog(currFrag, view, personSelected, check(PERSON));
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                dialog.show(ft, "Add Dialog");
            } else {
                dialog = new AddDialog(currFrag, view);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                dialog.show(ft, "Add Dialog");
            }
        }

        public int check(int id) {
            if (id == PERSON) {
                for (Person person : _personList0) {
                    if (person.getName().equals(personSelected)) {
                        return person.getId();
                    }
                }
            } else if (id == SITE) {
                for (Sites site : _siteList0) {
                    if (site.getName().equals(siteSelected)) {
                        return site.getId();
                    }
                }
            }
            return -1;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Spinner spinner = (Spinner) parent;

            switch (spinner.getId()) {
                case R.id.personSpinner:
                    personSelected = spinner.getSelectedItem().toString();
                    break;
                case R.id.siteSpinner:
                    siteSelected = spinner.getSelectedItem().toString();
                    break;
                case R.id.keywordsSpinner:
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
