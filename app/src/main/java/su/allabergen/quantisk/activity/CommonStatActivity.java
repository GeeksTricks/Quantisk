package su.allabergen.quantisk.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import static su.allabergen.quantisk.webServiceVolley.VolleyGet.personList0;
import static su.allabergen.quantisk.webServiceVolley.VolleyGet.siteList0;
import static su.allabergen.quantisk.webServiceVolley.VolleyGet.totalrankList0;

public class CommonStatActivity extends AppCompatActivity {

    private TextView lastUpdateTimeTextView;
    private TextView siteTextView;
    private ListView commonStatListView;
    public static List<String> commonList;
    public static ArrayAdapter<String> adapter;

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

        Bundle extras = getIntent().getExtras();
        String name = extras.getString("SITE");
        int site_id = 1;
        String url = null;

        siteTextView.setText("Сайт: " + name);

        if (totalrankList0.isEmpty()) {
            for (Sites site : siteList0) {
                if (site.getName().equals(name)) {
                    site_id = site.getId();
                    url = "https://api-quantisk.rhcloud.com/v1/totalrank/" + site_id + "/";
                    new VolleyGet(this, url, "user1", "qwerty1");
                    break;
                }
            }
        }

        if (!totalrankList0.isEmpty()) {
            String personName = "No Name";
            int rate;
            for (Totalrank totalrank : totalrankList0) {
                for (Person person : personList0) {
                    if (person.getId() == totalrank.getPerson_id()) {
                        personName = person.getName();
                        break;
                    }
                }
                rate = totalrank.getRate();
                commonList.add("Имя: " + personName + "\nСтатистика: " + rate);
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void initVariables() {
        lastUpdateTimeTextView = (TextView) findViewById(R.id.lastUpdateTimeTextView);
        siteTextView = (TextView) findViewById(R.id.siteTextView);
        commonStatListView = (ListView) findViewById(R.id.commonStatListView);
        commonList = new ArrayList<>();
        commonList.add("Loading data...");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, commonList);
        commonStatListView.setAdapter(adapter);
        commonList.clear();
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
