package su.allabergen.quantisk;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class AdminActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner statSpinner;
    private Spinner siteSpinner;
    private Spinner nameSpinner;
    private TextView dateFrom;
    private TextView dateTo;
    private TextView dateTextView;
    private LinearLayout dateLayout;

    private String nameToPass;
    private boolean isDailyStat = true;
    private int yearFrom;
    private int monthFrom;
    private int dayFrom;
    private int yearTo;
    private int monthTo;
    private int dayTo;

    public static List<String> siteList = new ArrayList<>();
    public static List<String> nameList = new ArrayList<>();
    private ArrayAdapter<String> nameAdapter;
    private ArrayAdapter<String> siteAdapter;
    private WebService task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initVariables();

        String urlName = "http://api-quantisk.rhcloud.com/v1/persons/";
        String urlSite = "http://api-quantisk.rhcloud.com/v1/sites/";
        task = new WebService(this);
        task.execute(urlName, urlSite);
//        task = new WebService(this);
//        task.execute(urlSite);

        spinnerLists();
        createSpinners();
    }

    private void initVariables() {
        statSpinner = (Spinner) findViewById(R.id.statSpinner);
        siteSpinner = (Spinner) findViewById(R.id.siteSpinner);
        nameSpinner = (Spinner) findViewById(R.id.nameSpinner);
        dateLayout = (LinearLayout) findViewById(R.id.dateLayout);
        dateTextView = (TextView) findViewById(R.id.dateTextView);
        dateFrom = (TextView) findViewById(R.id.dateFrom);
        dateTo = (TextView) findViewById(R.id.dateTo);
    }

    private void spinnerLists() {
//        String[] siteArray = getResources().getStringArray(R.array.sites);
//        siteList = new ArrayList<>();
//        for (int i = 0; i < siteArray.length; i++) {
//            siteList.add(siteArray[i]);
//        }

//        String[] nameArray = getResources().getStringArray(R.array.names);
//        nameList = new ArrayList<>();
//        for (int i = 0; i < nameArray.length; i++) {
//            nameList.add(nameArray[i]);
//        }
    }

    private void createSpinners() {
        ArrayAdapter statAdapter = ArrayAdapter.createFromResource(this, R.array.stats, android.R.layout.simple_list_item_activated_1);
        statSpinner.setAdapter(statAdapter);
        statSpinner.setOnItemSelectedListener(this);


        siteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, siteList);
        siteSpinner.setAdapter(siteAdapter);
        siteSpinner.setOnItemSelectedListener(this);

        nameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, nameList);
        nameSpinner.setAdapter(nameAdapter);
        nameSpinner.setOnItemSelectedListener(this);
    }

    private void updateListView() {
        try {
            nameList.addAll(task.getPersonFromWebService());
            nameAdapter.notifyDataSetChanged();
            siteList.addAll(task.getSiteFromWebService());
            siteAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class WebService extends AsyncTask<String, Void, Void> {

        private List<String> personFromWebService;
        private List<String> siteFromWebService;
        private ProgressDialog progressDialog;

        public WebService(Context context) {
            personFromWebService = new ArrayList<>();
            siteFromWebService = new ArrayList<>();
            progressDialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... urls) {
            String result = "";
            String result2 = "";
            URL url;
            URL url2;
            HttpURLConnection connection = null;
            HttpURLConnection connection2 = null;
            InputStream is = null;
            InputStream is2 = null;
            InputStreamReader reader = null;
            InputStreamReader reader2 = null;

            try {
                url = new URL(urls[0]);
                url2 = new URL(urls[1]);
                connection = (HttpURLConnection) url.openConnection();
                connection2 = (HttpURLConnection) url2.openConnection();
                connection.setRequestMethod("GET");
                connection2.setRequestMethod("GET");
                is = connection.getInputStream();
                is2 = connection2.getInputStream();
                reader = new InputStreamReader(is);
                reader2 = new InputStreamReader(is2);
                int data = reader.read();
                int data2 = reader2.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                while (data2 != -1) {
                    char current = (char) data2;
                    result2 += current;
                    data2 = reader2.read();
                }

                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonPart = jsonArray.getJSONObject(i);
                    personFromWebService.add(jsonPart.getString("name"));
                }
                JSONArray jsonArray2 = new JSONArray(result2);
                for (int i = 0; i < jsonArray2.length(); i++) {
                    JSONObject jsonPart = jsonArray2.getJSONObject(i);
                    siteFromWebService.add(jsonPart.getString("name"));
                }

                Log.i("result", result);
                Log.i("result", result2);

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (connection2 != null) {
                    connection2.disconnect();
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (is2 != null) {
                        is2.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (reader2 != null) {
                        reader2.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            updateListView();
            personFromWebService.clear();
            siteFromWebService.clear();
        }

        protected List<String> getPersonFromWebService() {
            return personFromWebService;
        }

        protected List<String> getSiteFromWebService() {
            return siteFromWebService;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;

        if (spinner.getId() == R.id.statSpinner) {
            if (position == 1) {
                dateTextView.setVisibility(View.VISIBLE);
                dateLayout.setVisibility(View.VISIBLE);
                isDailyStat = true;
            } else {
                dateTextView.setVisibility(View.INVISIBLE);
                dateLayout.setVisibility(View.INVISIBLE);
                isDailyStat = false;
            }
        }
        if (spinner.getId() == R.id.siteSpinner) {

        }
        if (spinner.getId() == R.id.nameSpinner) {
            nameToPass = spinner.getSelectedItem().toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void generateBtnClick(View view) {
        if (!isDailyStat) {
            Intent intent = new Intent(this, CommonStatActivity.class);
            intent.putExtra("NAME", nameToPass);
            startActivity(intent);
        } else if (dateFrom.getText().toString().substring(0, 2).equals("dd") || dateTo.getText().toString().substring(0, 2).equals("dd")) {
            showDateAlertDialog();
        } else {
            Intent intent = new Intent(this, DailyStatActivity.class);
            checkDate(dateFrom.getText().toString(), dateTo.getText().toString(), intent);
        }
    }

    private void checkDate(String dateFrom, String dateTo, Intent intent) {

        int[] dateDifference = getDateDifferenceInDDMMYYYY(dateFrom, dateTo);
        String[] dates_from = dateFrom.split("/");
        String[] dates_to = dateTo.split("/");
        dayFrom = Integer.parseInt(dates_from[0]);
        monthFrom = Integer.parseInt(dates_from[1]);
        yearFrom = Integer.parseInt(dates_from[2]);
        dayTo = Integer.parseInt(dates_to[0]);
        monthTo = Integer.parseInt(dates_to[1]);
        yearTo = Integer.parseInt(dates_to[2]);

        if (dateDifference[2] < 0) {
            showDateAlertDialog();
        } else {
            intent.putExtra("NAME", nameToPass);
            intent.putExtra("DATE_FROM", dateFrom);
            intent.putExtra("DATE_TO", dateTo);
            intent.putExtra("DATES", dateDifference);
            startActivity(intent);
        }
    }

    public int[] getDateDifferenceInDDMMYYYY(String dateFrom, String dateTo) {

        String[] dates_from = dateFrom.split("/");
        String[] dates_to = dateTo.split("/");
        dayFrom = Integer.parseInt(dates_from[0]);
        monthFrom = Integer.parseInt(dates_from[1]) - 1;
        yearFrom = Integer.parseInt(dates_from[2]);
        dayTo = Integer.parseInt(dates_to[0]);
        monthTo = Integer.parseInt(dates_to[1]) - 1;
        yearTo = Integer.parseInt(dates_to[2]);
        int[] monthDay = {31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int increment = 0;
        int[] ageDiffArr = new int[3];

        int year;
        int month;
        int day;

        if (dayFrom > dayTo) {
            increment = monthDay[monthFrom];
        }

        GregorianCalendar cal = new GregorianCalendar();
        boolean isLeapYear = cal.isLeapYear(yearFrom);

        if (increment == -1) {
            if (isLeapYear) {
                increment = 29;
            } else {
                increment = 28;
            }
        }

        // DAY CALCULATION
        if (increment != 0) {
            day = (dayTo + increment) - dayFrom;
            increment = 1;
        } else {
            day = dayTo - dayFrom;
        }

        // MONTH CALCULATION
        if ((monthFrom + increment) > monthTo) {
            month = (monthTo + 12) - (monthFrom + increment);
            increment = 1;
        } else {
            month = monthTo - (monthFrom + increment);
            increment = 0;
        }

        // YEAR CALCULATION
        year = yearTo - (yearFrom + increment);

        ageDiffArr[0] = day;
        ageDiffArr[1] = month;
        ageDiffArr[2] = year;

        return ageDiffArr;
    }

    private void showDateAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Ошибка")
                .setMessage("Пожалуйста, введите корректную дату")
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.create().show();
    }

    public void dateToClick(View view) {
        DateDialog dialog = new DateDialog(view);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        dialog.show(ft, "DatePicker");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.about) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("О нас")
                    .setMessage("Quantisk\n\nGeeksTricks\nCopyright 2016")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            alertDialog.create().show();
        }

        return super.onOptionsItemSelected(item);
    }
}
