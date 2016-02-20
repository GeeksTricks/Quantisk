package su.allabergen.quantisk.webServiceVolley;

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

import su.allabergen.quantisk.model.Person;
import su.allabergen.quantisk.model.Sites;
import su.allabergen.quantisk.model.Totalrank;

import static su.allabergen.quantisk.activity.AdminActivity.nameAdapter;
import static su.allabergen.quantisk.activity.AdminActivity.nameList;
import static su.allabergen.quantisk.activity.AdminActivity.siteAdapter;
import static su.allabergen.quantisk.activity.AdminActivity.siteList;

/**
 * Created by Rabat on 19.02.2016.
 */
public class VolleyGet {
    public static List<Person> personList0 = new ArrayList<>();
    public static List<Sites> siteList0 = new ArrayList<>();
    public static List<Totalrank> totalrankList0 = new ArrayList<>();

//    public static Map<Integer, String> personMap = new LinkedHashMap<>();
//    public static Map<Integer, String> siteMap = new LinkedHashMap<>();
    RequestQueue requestQueue;
    JSONObject jsonObject;
    Context context;
    String url;
    String username;
    String password;

    public VolleyGet(Context context, String url, String username, String password) {
        requestQueue = Volley.newRequestQueue(context);
        jsonObject = new JSONObject();
        this.context = context;
        this.username = username;
        this.password = password;
        this.url = url;
        getVolley();
    }

    public void getVolley() {
//        final Map<Integer, String> dataFromVolley = new LinkedHashMap<>();
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
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
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
