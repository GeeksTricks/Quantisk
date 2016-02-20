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
import android.util.Log;
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

import java.util.Map;

import su.allabergen.quantisk.R;
import su.allabergen.quantisk.WebServiceVolley.VolleyDelete;
import su.allabergen.quantisk.dialog.AddDialog;

import static su.allabergen.quantisk.WebServiceVolley.VolleyGet.personMap;
import static su.allabergen.quantisk.WebServiceVolley.VolleyGet.siteMap;

public class SettingsActivity extends AppCompatActivity {

    public static final int PERSON = 0;
    public static final int SITE = 1;
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
                    return "SITES";
                case 1:
                    return "NAMES";
                case 2:
                    return "USERS";
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
        Button addPersonBtn;
        Button removePersonBtn;
        Button editPersonBtn;
        Button addSiteBtn;
        Button removeSiteBtn;
        Button editSiteBtn;
        Button addUserBtn;
        Button removeUserBtn;
        Button editUserBtn;
        View vSite;
        View vName;
        View vUser;
        String personSelected;
        String siteSelected;
        int personId;
        int siteId;

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
//            View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                View siteView = inflater.inflate(R.layout.fragment_site, container, false);
                TextView siteTextView = (TextView) siteView.findViewById(R.id.site_label);
                Spinner siteSpinner = (Spinner) siteView.findViewById(R.id.siteSpinner);
                siteSpinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, AdminActivity.siteList));
                siteSpinner.setOnItemSelectedListener(this);
                siteTextView.setText("Modify sites");
                initSiteBtn(siteView);
                return siteView;
            }

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                View nameView = inflater.inflate(R.layout.fragment_person, container, false);
                TextView nameTextView = (TextView) nameView.findViewById(R.id.name_label);
                Spinner nameSpinner = (Spinner) nameView.findViewById(R.id.personSpinner);
                nameSpinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, AdminActivity.nameList));
                nameSpinner.setOnItemSelectedListener(this);
                nameTextView.setText("Modify names");
                initNameBtn(nameView);
                return nameView;
            }

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 3) {
                View userView = inflater.inflate(R.layout.fragment_user, container, false);
                TextView userTextView = (TextView) userView.findViewById(R.id.user_label);
                Spinner userSpinner = (Spinner) userView.findViewById(R.id.userSpinner);
                userSpinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, SignUpScreen.tempList));
                userSpinner.setOnItemSelectedListener(this);
                userTextView.setText("Modify users");
                initUserBtn(userView);
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

        private void initUserBtn(View userView) {
            vUser = userView;
            addUserBtn = (Button) userView.findViewById(R.id.addUserBtn);
            removeUserBtn = (Button) userView.findViewById(R.id.removeUserBtn);
            editUserBtn = (Button) userView.findViewById(R.id.editUserBtn);
            addUserBtn.setOnClickListener(this);
            removeUserBtn.setOnClickListener(this);
            editUserBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String currFrag = "";
            View view = null;

            switch (v.getId()) {
                case R.id.addSiteBtn :
                    currFrag = "addSiteBtn";
                    view = vSite;
                    break;
                case R.id.addPersonBtn :
                    currFrag = "addPersonBtn";
                    view = vName;
                    break;
                case R.id.addUserBtn :
                    currFrag = "addUserBtn";
                    view = vUser;
                    break;
                case R.id.removeSiteBtn :
                    currFrag = "removeSiteBtn";
                    view = vSite;
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Remove Site")
                            .setMessage("Are you sure to remove this site?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    new VolleyDeleteCustom(getActivity(), "https://api-quantisk.rhcloud.com/v1/persons/", position, "user1", "qwerty1");
//
//                                    new DeleteWebService(getActivity(), position + 1, "user1", "qwerty1")
//                                            .execute("https://api-quantisk.rhcloud.com/v1/persons/");

                                    int check = check(SITE);
                                    new VolleyDelete(getActivity(), "https://api-quantisk.rhcloud.com/v1/sites/", check, "user1", "qwerty1");

                                }
                            })
                            .setNegativeButton("No", null)
                            .create()
                            .show();
                    break;
                case R.id.removePersonBtn :
                    currFrag = "removePersonBtn";
                    view = vName;
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Remove Person")
                            .setMessage("Are you sure to remove this person?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    new VolleyDeleteCustom(getActivity(), "https://api-quantisk.rhcloud.com/v1/persons/", position, "user1", "qwerty1");

//                                    new DeleteWebService(getActivity(), position + 1, "user1", "qwerty1")
//                                            .execute("https://api-quantisk.rhcloud.com/v1/persons/");

                                    int check = check(PERSON);
                                    new VolleyDelete(getActivity(), "https://api-quantisk.rhcloud.com/v1/persons/", check, "user1", "qwerty1");
                                }
                            })
                            .setNegativeButton("No", null)
                            .create()
                            .show();
                    break;
                case R.id.removeUserBtn :
                    currFrag = "removeUserBtn";
                    view = vUser;
                    break;
                case R.id.editSiteBtn :
                    currFrag = "editSiteBtn";
                    view = vSite;
                    break;
                case R.id.editPersonBtn :
                    currFrag = "editPersonBtn";
                    view = vName;
//                    new VolleyPut(getActivity(), "https://api-quantisk.rhcloud.com/v1/persons/", -1, "user1", "qwerty1");
                    break;
                case R.id.editUserBtn :
                    currFrag = "editUserBtn";
                    view = vUser;
                    break;
            }

            AddDialog dialog = null;
            if (currFrag.equals("removeSiteBtn") || currFrag.equals("removePersonBtn") || currFrag.equals("removeUserBtn")) {
//                dialog = new AddDialog(currFrag, view, this.position);
            } else {
                dialog = new AddDialog(currFrag, view);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                dialog.show(ft, "Add Dialog");
            }
        }

        public int check(int id) {
            if (id == PERSON) {
                for (Map.Entry<Integer, String> person : personMap.entrySet()) {
                    if (person.getValue().equals(personSelected)) {
                        personId = person.getKey();
                        Log.i("Quantisk P_ID", String.valueOf(personId));
                        Log.i("Quantisk PERSON", person.getValue());
                        return personId;
                    }
                }
            } else if (id == SITE) {
                for (Map.Entry<Integer, String> site : siteMap.entrySet()) {
                    if (site.getValue().equals(siteSelected)) {
                        siteId = site.getKey();
                        Log.i("Quantisk S_ID", String.valueOf(siteId));
                        Log.i("Quantisk SITE", site.getValue());
                        return siteId;
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
                case R.id.userSpinner:
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
