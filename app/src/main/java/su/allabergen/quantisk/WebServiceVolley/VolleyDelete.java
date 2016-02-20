package su.allabergen.quantisk.WebServiceVolley;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rabat on 18.02.2016.
 */
public class VolleyDelete {
    RequestQueue requestQueue;
    JSONObject jsonObject;
    Context context;
    String url0;
    String url;
    String username;
    String password;
    int id;

    public VolleyDelete(Context context, String url, int id, String username, String password) {
        requestQueue = Volley.newRequestQueue(context);
        jsonObject = new JSONObject();
        this.context = context;
        this.username = username;
        this.password = password;
        this.id = id;
        this.url0 = url;
        this.url = url + this.id + "/";
        getVolley();
    }

    public void getVolley() {
        try {
            jsonObject.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.DELETE, url, jsonObject,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                if (response.statusCode == HttpStatus.SC_NO_CONTENT)
                    Log.i("Quantisk NO CONTENT", String.valueOf(response.statusCode));
                new VolleyGet(context, url0, "user1", "qwerty1");

                return super.parseNetworkResponse(response);
            }

            @Override
            protected void deliverResponse(JSONArray response) {
                super.deliverResponse(response);
                Log.i("Quantisk deliver", response.toString());
            }

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
        requestQueue.add(jsonObjectRequest);
    }
}
