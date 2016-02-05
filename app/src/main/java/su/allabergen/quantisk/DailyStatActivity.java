package su.allabergen.quantisk;

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
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class DailyStatActivity extends AppCompatActivity {

    private ListView dailyStatListView;
    private TextView date;
    private TextView article;
    private TextView dailyNameTextView;

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
        dailyNameTextView = (TextView) findViewById(R.id.dailyNameTextView);

        Intent intent = getIntent();
        dailyNameTextView.setText(intent.getStringExtra("NAME"));
        dailyStatListView.setAdapter(new DailyStatAdapter(this, intent));
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

        dates_from = dateFrom.split("/");
        dates_to = dateTo.split("/");

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
                        datesToShow.add(d + "/" + (mf) + "/" + yf);
                        articles.add((int) (Math.random() * 50));
                    }
                    for (int d = 1; d <= dt; d++) {
                        datesToShow.add(d + "/" + (mf + 1) + "/" + yf);
                        articles.add((int) (Math.random() * 50));
                    }
                } else {
                    for (int d = df; d <= dt; d++) {
                        datesToShow.add(d + "/" + (mf) + "/" + yf);
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
