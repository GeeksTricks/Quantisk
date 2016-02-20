package su.allabergen.quantisk.WebServiceVolley;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static su.allabergen.quantisk.AdminActivity.nameAdapter;
import static su.allabergen.quantisk.AdminActivity.nameList;
import static su.allabergen.quantisk.AdminActivity.siteAdapter;
import static su.allabergen.quantisk.AdminActivity.siteList;

/**
 * Created by Rabat on 19.02.2016.
 */
public class VolleyGet {
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
        final Map<Integer, String> dataFromVolley = new LinkedHashMap<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonPart = null;
                            try {
                                jsonPart = response.getJSONObject(i);
                                dataFromVolley.put(jsonPart.getInt("id"), jsonPart.getString("name"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.i("Quantisk dataMap", dataFromVolley.toString());

                        if (url.contains("persons")) {
                            for (Map.Entry<Integer, String> person : dataFromVolley.entrySet())
                                nameList.add(person.getValue());
                            nameAdapter.notifyDataSetChanged();
                            Log.i("Quantiks nameList", nameList.toString());
                        } else if (url.contains("sites")) {
                            for (Map.Entry<Integer, String> site : dataFromVolley.entrySet())
                                siteList.add(site.getValue());
                            siteAdapter.notifyDataSetChanged();
                            Log.i("Quantiks siteList", siteList.toString());
                        }
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
