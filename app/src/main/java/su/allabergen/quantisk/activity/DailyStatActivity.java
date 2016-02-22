package su.allabergen.quantisk.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import su.allabergen.quantisk.R;
import su.allabergen.quantisk.model.Dailyrank;
import su.allabergen.quantisk.model.Person;
import su.allabergen.quantisk.model.Sites;
import su.allabergen.quantisk.webServiceVolley.VolleyGet;

import static su.allabergen.quantisk.webServiceVolley.VolleyGet._dailyrankList0;
import static su.allabergen.quantisk.webServiceVolley.VolleyGet._personList0;
import static su.allabergen.quantisk.webServiceVolley.VolleyGet._siteList0;
import static su.allabergen.quantisk.webServiceVolley.VolleyGet._totalrankList0;

public class DailyStatActivity extends AppCompatActivity {

    private ListView dailyStatListView;
    public static List<String> _dailyList;
    public static ArrayAdapter<String> _dailyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_stat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Ежедневная статистика");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        https://api-quantisk.rhcloud.com/v1/dailyrank/?person_id=1&site_id=1&start_date=2016-01-09&end_date=2016-02-09

        initVariables();

        Intent i = getIntent();
        getDailyRank(i);
    }

    private void initVariables() {
        dailyStatListView = (ListView) findViewById(R.id.dailyStatListView);
        _dailyList = new ArrayList<>();
        _dailyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, _dailyList);
        dailyStatListView.setAdapter(_dailyAdapter);
    }

    private void getDailyRank(Intent i) {
        String dateFrom = i.getStringExtra("DATE_FROM");
        String dateTo = i.getStringExtra("DATE_TO");
        String username = i.getStringExtra("NAME");
        String sitename = i.getStringExtra("SITE");
        String url = null;
        int site_id = -1;
        int person_id = -1;

        if (_dailyrankList0.isEmpty()) {
            for (Person person : _personList0) {
                if (person.getName().equals(username)) {
                    person_id = person.getId();
                    break;
                }
            }
            for (Sites site : _siteList0) {
                if (site.getName().equals(sitename)) {
                    site_id = site.getId();
                    break;
                }
            }
            url = "https://api-quantisk.rhcloud.com/v1/dailyrank/?person_id=" + person_id + "&site_id=" + site_id + "&start_date=" + dateFrom + "&end_date=" + dateTo;
            new VolleyGet(this, url, "user1", "qwerty1");
        }

        if (!_totalrankList0.isEmpty()) {
            String personName = "No Name";
            String siteName = "No Site";
            String date = "yyyy-MM-dd";
            int rate;
            for (Dailyrank dailyrank : _dailyrankList0) {
                for (Person person : _personList0) {
                    if (person.getId() == dailyrank.getPerson_id()) {
                        personName = person.getName();
                        break;
                    }
                }
                for (Sites site : _siteList0) {
                    if (site.getId() == dailyrank.getSite_id()) {
                        siteName = site.getName();
                        break;
                    }
                }
                date = dailyrank.getDay();
                rate = dailyrank.getRank();
                _dailyList.add(date + "\nИмя: " + personName + "\nСайт: " + siteName + "\nСтатистика: " + rate);
            }
            _dailyAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.about) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("О нас")
                    .setMessage("Quantisk\n\nGeeksTricks\nCopyright 2016")
                    .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            alertDialog.create().show();
        }
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
