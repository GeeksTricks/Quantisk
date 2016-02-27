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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import su.allabergen.quantisk.R;
import su.allabergen.quantisk.model.Person;
import su.allabergen.quantisk.model.Sites;
import su.allabergen.quantisk.model.Totalrank;
import su.allabergen.quantisk.webServiceVolley.VolleyGet;

import static su.allabergen.quantisk.webServiceVolley.VolleyGet._personList0;
import static su.allabergen.quantisk.webServiceVolley.VolleyGet._siteList0;
import static su.allabergen.quantisk.webServiceVolley.VolleyGet._totalrankList0;

public class CommonStatActivity extends AppCompatActivity {

    private TextView lastUpdateTimeTextView;
    private TextView siteTextView;
    private ListView commonStatListView;

    public static List<String> _commonList;
    public static ArrayAdapter<String> _commonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_stat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Общая статистика");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initVariables();

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        lastUpdateTimeTextView.setText(day + "/" + month + "/" + year);

        Intent intent = getIntent();
        getDailyRank(intent);
    }

    private void initVariables() {
        lastUpdateTimeTextView = (TextView) findViewById(R.id.lastUpdateTimeTextView);
        siteTextView = (TextView) findViewById(R.id.siteTextView);
        commonStatListView = (ListView) findViewById(R.id.commonStatListView);
        _commonList = new ArrayList<>();
        _commonList.add("Loading data...");
        _commonAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, _commonList);
        commonStatListView.setAdapter(_commonAdapter);
        _commonList.clear();
    }

    private void getDailyRank(Intent intent) {
        String name = intent.getStringExtra("SITE");
        int site_id = 1;
        String url = null;

        siteTextView.setText("Сайт: " + name);

        _totalrankList0.clear();
        for (Sites site : _siteList0) {
            if (site.getName().equals(name)) {
                site_id = site.getId();
                url = "https://api-quantisk.rhcloud.com/v1/totalrank/" + site_id + "/";
                new VolleyGet(this, url, "user1", "qwerty1");
                break;
            }
        }

        String personName = "No Name";
        int rate;
        for (Totalrank totalrank : _totalrankList0) {
            for (Person person : _personList0) {
                if (person.getId() == totalrank.getPerson_id()) {
                    if (person.getName() != null)
                        personName = person.getName();
                    break;
                }
            }
            if (totalrank.getRate() > 0)
                rate = totalrank.getRate();
            else rate = 0;
            _commonList.add("Имя: " + personName + "\nСтатистика: " + rate);
        }
        _commonAdapter.notifyDataSetChanged();
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
