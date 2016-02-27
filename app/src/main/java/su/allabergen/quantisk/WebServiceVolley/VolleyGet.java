package su.allabergen.quantisk.webServiceVolley;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import su.allabergen.quantisk.model.Dailyrank;
import su.allabergen.quantisk.model.Person;
import su.allabergen.quantisk.model.Sites;
import su.allabergen.quantisk.model.Totalrank;
import su.allabergen.quantisk.model.Users;
import su.allabergen.quantisk.model.Wordpairs;

import static su.allabergen.quantisk.activity.AdminActivity._nameAdapter;
import static su.allabergen.quantisk.activity.AdminActivity._nameList;
import static su.allabergen.quantisk.activity.AdminActivity._siteAdapter;
import static su.allabergen.quantisk.activity.AdminActivity._siteList;
import static su.allabergen.quantisk.activity.CommonStatActivity._commonAdapter;
import static su.allabergen.quantisk.activity.CommonStatActivity._commonList;
import static su.allabergen.quantisk.activity.DailyStatActivity._dailyAdapter;
import static su.allabergen.quantisk.activity.DailyStatActivity._dailyList;
import static su.allabergen.quantisk.activity.SettingsActivity._keywordAdapter;
import static su.allabergen.quantisk.activity.SettingsActivity._keywordAdapterSpinner;
import static su.allabergen.quantisk.activity.SettingsActivity._keywordsListSpinner;

/**
 * Created by Rabat on 19.02.2016.
 */
public class VolleyGet {
    public static List<Person> _personList0 = new ArrayList<>();
    public static List<Sites> _siteList0 = new ArrayList<>();
    public static List<Totalrank> _totalrankList0 = new ArrayList<>();
    public static List<Dailyrank> _dailyrankList0 = new ArrayList<>();
    public static List<Wordpairs> _keywordsList0 = new ArrayList<>();
    public static List<Users> _userList0 = new ArrayList<>();
    public static volatile boolean _isDone = false;

    private RequestQueue requestQueue;
    private Context context;
    private String url;
    private String username;
    private String password;

    ProgressDialog progressDialog;

    public VolleyGet(Context context, String url, String username, String password) {
        requestQueue = Volley.newRequestQueue(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        this.context = context;
        this.username = username;
        this.password = password;
        this.url = url;
        getVolley();
    }

    public void getVolley() {
        progressDialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        // Get Person name from Web Service
                        if (url.contains("persons")) {
                            _personList0.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonPart = null;
                                try {
                                    jsonPart = response.getJSONObject(i);
                                    Person person = new Person();

                                    person.setId(jsonPart.getInt("id"));
                                    person.setName(jsonPart.getString("name"));

                                    _personList0.add(person);
                                    progressDialog.dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    progressDialog.dismiss();
                                }
                            }
                            _nameList.clear();
                            for (Person p : _personList0)
                                _nameList.add(p.getName());

                            _nameAdapter.notifyDataSetChanged();

                            // Get Site name from Web Service
                        } else if (url.contains("sites")) {
                            _siteList0.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonPart = null;
                                try {
                                    jsonPart = response.getJSONObject(i);
                                    Sites site = new Sites();

                                    site.setId(jsonPart.getInt("id"));
                                    site.setName(jsonPart.getString("name"));

                                    _siteList0.add(site);
                                    progressDialog.dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    progressDialog.dismiss();
                                }
                            }
                            _siteList.clear();
                            for (Sites s : _siteList0)
                                _siteList.add(s.getName());

                            _siteAdapter.notifyDataSetChanged();

                            // Get Common Statistics from Totalrank from Web Service
                        } else if (url.contains("totalrank")) {
                            _totalrankList0.clear();
                            if (response.length() > 0) {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonPart = null;
                                    try {
                                        jsonPart = response.getJSONObject(i);
                                        Totalrank totalrank = new Totalrank();

                                        totalrank.setPerson_id(jsonPart.getInt("person_id"));
                                        totalrank.setRate(jsonPart.getInt("rank"));
                                        totalrank.setSite_id(jsonPart.getInt("site_id"));

                                        _totalrankList0.add(totalrank);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        progressDialog.dismiss();
                                    }
                                }
                            }
                            progressDialog.dismiss();
                            String personName = "No Name";
                            int rate;
                            if (!_totalrankList0.isEmpty()) {
                                for (Totalrank totalrank : _totalrankList0) {
                                    for (Person person : _personList0) {
                                        if (person.getId() == totalrank.getPerson_id()) {
                                            personName = person.getName();
                                            break;
                                        }
                                    }
                                    rate = totalrank.getRate();
                                    _commonList.add("Имя: " + personName
                                            + "\nСтатистика: " + rate);
                                }
                            } else
                                _commonList.add("Нет данных");

                            _commonAdapter.notifyDataSetChanged();

                            // Get Daily Statistics from Dailyrank from Web Service
                        } else if (url.contains("dailyrank")) {
                            _dailyrankList0.clear();
                            if (response.length() > 0) {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonPart = null;
                                    try {
                                        jsonPart = response.getJSONObject(i);
                                        Dailyrank dailyrank = new Dailyrank();

                                        dailyrank.setDay(jsonPart.getString("day"));
                                        dailyrank.setPerson_id(jsonPart.getInt("person_id"));
                                        dailyrank.setRank(jsonPart.getInt("rank"));
                                        dailyrank.setSite_id(jsonPart.getInt("site_id"));

                                        _dailyrankList0.add(dailyrank);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        progressDialog.dismiss();
                                    }
                                }
                            }
                            progressDialog.dismiss();
                            String personName = "No Name";
                            String siteName = "No Site";
                            String date = "yyyy-MM-dd";
                            int rate;
                            if (!_dailyrankList0.isEmpty()) {
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
                                    _dailyList.add(date + "\nИмя: " + personName
                                            + "\nСайт: " + siteName
                                            + "\nСтатистика: " + rate);
                                }
                            } else
                                _dailyList.add("Нет данных");

                            _dailyAdapter.notifyDataSetChanged();

                            // Get Wordpairs from Web Service
                        } else if (url.contains("wordpairs")) {
                            _keywordsList0.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonPart = null;
                                try {
                                    jsonPart = response.getJSONObject(i);
                                    Wordpairs wordpairs = new Wordpairs();

                                    wordpairs.setDistance(jsonPart.getInt("distance"));
                                    wordpairs.setId(jsonPart.getInt("id"));
                                    wordpairs.setKeyword1(jsonPart.getString("keyword1"));
                                    wordpairs.setKeyword2(jsonPart.getString("keyword2"));
                                    wordpairs.setPerson_id(jsonPart.getInt("person_id"));

                                    _keywordsList0.add(wordpairs);
                                    progressDialog.dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    progressDialog.dismiss();
                                }
                            }

                            Set<String> keywordSet = new HashSet<>();
                            _keywordsListSpinner.clear();

                            for (Person person : _personList0)
                                keywordSet.add(person.getName());

                            for (String set : keywordSet)
                                _keywordsListSpinner.add(set);

                            _keywordAdapterSpinner.notifyDataSetChanged();
                            _keywordAdapter.notifyDataSetChanged();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        error.printStackTrace();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String credentials = username + ":" + password;
                String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                headers.put("Content-Type", "application/json, charset=UTF-8");
                headers.put("Authorization", auth);
                return headers;
            }
        };
        requestQueue.add(jsonArrayRequest);
    }
}
