package su.allabergen.quantisk.activity;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import su.allabergen.quantisk.R;
import su.allabergen.quantisk.dialog.DateDialog;
import su.allabergen.quantisk.webServiceVolley.VolleyGet;

public class AdminActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner statSpinner;
    private Spinner siteSpinner;
    private Spinner nameSpinner;
    private TextView dateFrom;
    private TextView dateTo;
    private TextView dateTextView;
    private LinearLayout dateLayout;

    private String nameToPass;
    private String siteToPass;
    private boolean isDailyStat = true;

    public static List<String> _siteList = new ArrayList<>();
    public static List<String> _nameList = new ArrayList<>();
    public static ArrayAdapter<String> _nameAdapter;
    public static ArrayAdapter<String> _siteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initVariables();
        createSpinners();

        String urlName = "https://api-quantisk.rhcloud.com/v1/persons/";
        String urlSite = "https://api-quantisk.rhcloud.com/v1/sites/";

        new VolleyGet(this, urlSite, "user1", "qwerty1");
        new VolleyGet(this, urlName, "user1", "qwerty1");
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

    private void createSpinners() {
        ArrayAdapter statAdapter = ArrayAdapter.createFromResource(this, R.array.stats, android.R.layout.simple_list_item_activated_1);
        statSpinner.setAdapter(statAdapter);
        statSpinner.setOnItemSelectedListener(this);

        _siteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, _siteList);
        siteSpinner.setAdapter(_siteAdapter);
        siteSpinner.setOnItemSelectedListener(this);

        _nameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, _nameList);
        nameSpinner.setAdapter(_nameAdapter);
        nameSpinner.setOnItemSelectedListener(this);
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
            siteToPass = spinner.getSelectedItem().toString();
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
            intent.putExtra("SITE", siteToPass);
            startActivity(intent);
        } else if (dateFrom.getText().toString().substring(0, 2).equals("dd") || dateTo.getText().toString().substring(0, 2).equals("dd")) {
            showDateAlertDialog();
        } else {
            Intent intent = new Intent(this, DailyStatActivity.class);
            intent.putExtra("NAME", nameToPass);
            intent.putExtra("SITE", siteToPass);
            intent.putExtra("DATE_FROM", String.valueOf(dateFrom.getText()));
            intent.putExtra("DATE_TO", String.valueOf(dateTo.getText()));
            startActivity(intent);
        }
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
                    .setMessage("Quantisk\n\nGeeksTricks Ltd.\nCopyright 2016")
                    .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
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
