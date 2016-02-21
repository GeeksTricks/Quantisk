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
import java.util.List;
import java.util.Map;

import su.allabergen.quantisk.model.Dailyrank;
import su.allabergen.quantisk.model.Person;
import su.allabergen.quantisk.model.Sites;
import su.allabergen.quantisk.model.Totalrank;

import static su.allabergen.quantisk.activity.AdminActivity.nameAdapter;
import static su.allabergen.quantisk.activity.AdminActivity.nameList;
import static su.allabergen.quantisk.activity.AdminActivity.siteAdapter;
import static su.allabergen.quantisk.activity.AdminActivity.siteList;
import static su.allabergen.quantisk.activity.CommonStatActivity.adapter;
import static su.allabergen.quantisk.activity.CommonStatActivity.commonList;
import static su.allabergen.quantisk.activity.DailyStatActivity.*;

/**
 * Created by Rabat on 19.02.2016.
 */
public class VolleyGet {
    public static List<Person> personList0 = new ArrayList<>();
    public static List<Sites> siteList0 = new ArrayList<>();
    public static List<Totalrank> totalrankList0 = new ArrayList<>();
    public static List<Dailyrank> dailyrankList0 = new ArrayList<>();

    ProgressDialog progressDialog;

//    public static Map<Integer, String> personMap = new LinkedHashMap<>();
//    public static Map<Integer, String> siteMap = new LinkedHashMap<>();
    RequestQueue requestQueue;
    Context context;
    String url;
    String username;
    String password;

    public VolleyGet(Context context, String url, String username, String password) {
        requestQueue = Volley.newRequestQueue(context);
        progressDialog = new ProgressDialog(context);
        this.context = context;
        this.username = username;
        this.password = password;
        this.url = url;
        getVolley();
    }

    public void getVolley() {
//        final Map<Integer, String> dataFromVolley = new LinkedHashMap<>();
        progressDialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (url.contains("persons")) {
                            personList0.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonPart = null;
                                try {
                                    jsonPart = response.getJSONObject(i);
                                    Person person = new Person();

                                    person.setId(jsonPart.getInt("id"));
                                    person.setName(jsonPart.getString("name"));

                                    personList0.add(person);

                                    progressDialog.dismiss();

//                                    dataFromVolley.put(jsonPart.getInt("id"), jsonPart.getString("name"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            nameList.clear();
                            for (Person p : personList0)
                                nameList.add(p.getName());
                            nameAdapter.notifyDataSetChanged();
                        } else if (url.contains("sites")) {
                            siteList0.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonPart = null;
                                try {
                                    jsonPart = response.getJSONObject(i);
                                    Sites site = new Sites();

                                    site.setId(jsonPart.getInt("id"));
                                    site.setName(jsonPart.getString("name"));

                                    siteList0.add(site);

                                    progressDialog.dismiss();

//                                    dataFromVolley.put(jsonPart.getInt("id"), jsonPart.getString("name"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            siteList.clear();
                            for (Sites s : siteList0) {
                                siteList.add(s.getName());
                            }
                            siteAdapter.notifyDataSetChanged();
                        } else if (url.contains("totalrank")) {
                            totalrankList0.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonPart = null;
                                try {
                                    jsonPart = response.getJSONObject(i);
                                    Totalrank totalrank = new Totalrank();

                                    totalrank.setPerson_id(jsonPart.getInt("person_id"));
                                    totalrank.setRate(jsonPart.getInt("rank"));
                                    totalrank.setSite_id(jsonPart.getInt("site_id"));

                                    totalrankList0.add(totalrank);

                                    progressDialog.dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
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
                        } else if (url.contains("dailyrank")) {
                            dailyrankList0.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonPart = null;
                                try {
                                    jsonPart = response.getJSONObject(i);
                                    Dailyrank dailyrank = new Dailyrank();

                                    dailyrank.setDay(jsonPart.getString("day"));
                                    dailyrank.setPerson_id(jsonPart.getInt("person_id"));
                                    dailyrank.setRank(jsonPart.getInt("rank"));
                                    dailyrank.setSite_id(jsonPart.getInt("site_id"));

                                    dailyrankList0.add(dailyrank);

                                    progressDialog.dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
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

//                        if (url.contains("persons")) {
//                            personMap.clear();
//                            nameList.clear();
//                            personMap.putAll(dataFromVolley);
//                            for (Map.Entry<Integer, String> person : dataFromVolley.entrySet())
//                                nameList.add(person.getValue());
//                            nameAdapter.notifyDataSetChanged();
//                        } else if (url.contains("sites")) {
//                            siteMap.clear();
//                            siteList.clear();
//                            siteMap.putAll(dataFromVolley);
//                            for (Map.Entry<Integer, String> site : dataFromVolley.entrySet())
//                                siteList.add(site.getValue());
//                            siteAdapter.notifyDataSetChanged();
//                        }
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
