package su.allabergen.quantisk.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import su.allabergen.quantisk.R;
import su.allabergen.quantisk.model.Dailyrank;
import su.allabergen.quantisk.model.Person;
import su.allabergen.quantisk.model.Sites;
import su.allabergen.quantisk.webServiceVolley.VolleyGet;

import static su.allabergen.quantisk.webServiceVolley.VolleyGet.dailyrankList0;
import static su.allabergen.quantisk.webServiceVolley.VolleyGet.personList0;
import static su.allabergen.quantisk.webServiceVolley.VolleyGet.siteList0;
import static su.allabergen.quantisk.webServiceVolley.VolleyGet.totalrankList0;

public class DailyStatActivity extends AppCompatActivity {

    private ListView dailyStatListView;
    private TextView date;
    private TextView article;
    private TextView dailyNameTextView;
    public static List<String> dailyList;
    public static ArrayAdapter<String> dailyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_stat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Ежедневная статистика");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dailyStatListView = (ListView) findViewById(R.id.dailyStatListView);
        date = (TextView) findViewById(R.id.date);
        article = (TextView) findViewById(R.id.article);
        dailyList = new ArrayList<>();
        dailyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dailyList);
        dailyStatListView.setAdapter(dailyAdapter);

//        https://api-quantisk.rhcloud.com/v1/dailyrank/?person_id=1&site_id=1&start_date=2016-01-09&end_date=2016-02-09

        Intent i = getIntent();
        String dateFrom = i.getStringExtra("DATE_FROM");
        String dateTo = i.getStringExtra("DATE_TO");
        String username = i.getStringExtra("NAME");
        String sitename = i.getStringExtra("SITE");
        String url = null;
        int site_id = -1;
        int person_id = -1;

        if (dailyrankList0.isEmpty()) {
            for (Person person : personList0) {
                if (person.getName().equals(username)) {
                    person_id = person.getId();
                    break;
                }
            }
            for (Sites site : siteList0) {
                if (site.getName().equals(sitename)) {
                    site_id = site.getId();
                    break;
                }
            }
            url = "https://api-quantisk.rhcloud.com/v1/dailyrank/?person_id=" + person_id + "&site_id=" + site_id + "&start_date=" + dateFrom + "&end_date=" + dateTo;
            new VolleyGet(this, url, "user1", "qwerty1");
        }

        if (!totalrankList0.isEmpty()) {
            String personName = "No Name";
            String siteName = "No Site";
            String date = "yyyy-MM-dd";
            int rate;
            for (Dailyrank dailyrank : dailyrankList0) {
                for (Person person : personList0) {
                    if (person.getId() == dailyrank.getPerson_id()) {
                        personName = person.getName();
                        break;
                    }
                }
                for (Sites site : siteList0) {
                    if (site.getId() == dailyrank.getSite_id()) {
                        siteName = site.getName();
                        break;
                    }
                }
                date = dailyrank.getDay();
                rate = dailyrank.getRank();
                dailyList.add(date + "\nИмя: " + personName + "\nСайт: " + siteName + "\nСтатистика: " + rate);
            }
            dailyAdapter.notifyDataSetChanged();
        }

//        dailyNameTextView.setText(i.getStringExtra("NAME"));
//        dailyStatListView.setAdapter(new DailyStatAdapter(this, intent));
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

class DailyStatAdapter extends BaseAdapter {
    public static final int MONTH = 1;
    public static final int YEAR = 2;
    private ArrayList<DailyStatSingleRow> dailyStatList;
    private ArrayList<String> datesToShow;
    private ArrayList<Integer> articles;
    private GregorianCalendar cal;
    private Context context;
    private String dateFrom;
    private String dateTo;
    private String[] dates_from;
    private String[] dates_to;
    private boolean isLeapYear;

    private int[] dates;
    private int yearFrom;
    private int monthFrom;
    private int dayFrom;
    private int dayTo;
    private int df;
    private int dt;
    private int[] monthDay = {31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public DailyStatAdapter(Context c, Intent i) {
        context = c;
        dailyStatList = new ArrayList<>();
        datesToShow = new ArrayList<>();
        articles = new ArrayList<>();
        cal = new GregorianCalendar();
        isLeapYear = cal.isLeapYear(yearFrom);

        dates = i.getIntArrayExtra("DATES");
        dateFrom = i.getStringExtra("DATE_FROM");
        dateTo = i.getStringExtra("DATE_TO");

        dates_from = dateFrom.split("-");
        dates_to = dateTo.split("-");

        dayFrom = Integer.parseInt(dates_from[0]);
        monthFrom = Integer.parseInt(dates_from[1]);
        yearFrom = Integer.parseInt(dates_from[2]);
        dayTo = Integer.parseInt(dates_to[0]);

        createSingleRowOfDailyStat();

        for (int t = 0; t < datesToShow.size(); t++) {
            dailyStatList.add(new DailyStatSingleRow(datesToShow.get(t), articles.get(t)));
        }
    }

    private void createSingleRowOfDailyStat() {
        for (int y = 0, yf = yearFrom; y <= dates[YEAR]; y++, yf++) {
            for (int m = 0, mf = monthFrom; m <= dates[MONTH]; m++, mf++) {
                Log.d("M", String.valueOf(m));

                if (mf % 12 == 0)
                    mf = 12;
                else mf = mf % 12;
                Log.d("MF", String.valueOf(mf));

                if (m == 0)
                    df = dayFrom;
                else df = 1;
                Log.d("DF", String.valueOf(df));

                if (m == dates[MONTH])
                    dt = dayTo;
                else dt = monthDay[mf + 1];

                if (dt == -1) {
                    if (isLeapYear)
                        dt = 29;
                    else dt = 28;
                }
                Log.d("DT", String.valueOf(dt));

                if (dates[MONTH] == 0 && df > dt) {
                    for (int d = df; d <= monthDay[mf + 1]; d++) {
                        datesToShow.add(d + "-" + (mf) + "-" + yf);
                        articles.add((int) (Math.random() * 50));
                    }
                    for (int d = 1; d <= dt; d++) {
                        datesToShow.add(d + "-" + (mf + 1) + "-" + yf);
                        articles.add((int) (Math.random() * 50));
                    }
                } else {
                    for (int d = df; d <= dt; d++) {
                        datesToShow.add(d + "-" + (mf) + "-" + yf);
                        articles.add((int) (Math.random() * 50));
                    }
                }
            }
        }
    }

    @Override
    public int getCount() {
        return dailyStatList.size();
    }

    @Override
    public Object getItem(int position) {
        return dailyStatList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.daily_stat_layout, parent, false);
        TextView date = (TextView) row.findViewById(R.id.date);
        TextView article = (TextView) row.findViewById(R.id.article);
        DailyStatSingleRow temp = dailyStatList.get(position);
        date.setText(temp.date);
        article.setText("Статьи: " + temp.article);

        return row;
    }
}

class DailyStatSingleRow {
    String date;
    int article;

    public DailyStatSingleRow(String date, int article) {
        this.date = date;
        this.article = article;
    }
}
