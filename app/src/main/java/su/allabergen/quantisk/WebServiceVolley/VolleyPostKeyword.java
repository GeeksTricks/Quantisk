package su.allabergen.quantisk.webServiceVolley;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rabat on 20.02.2016.
 */
public class VolleyPostKeyword {
    private RequestQueue requestQueue;
    private JSONObject jsonObject;
    private Context context;
    private String url;
    private String username;
    private String password;
    private String key1;
    private String key2;
    private int distance;
    private int person_id;

    public VolleyPostKeyword(Context context, String url, String key1, String key2, int distance, int person_id, String username, String password) {
        requestQueue = Volley.newRequestQueue(context);
        jsonObject = new JSONObject();
        this.context = context;
        this.username = username;
        this.password = password;
        this.url = url;
        this.key1 = key1;
        this.key2 = key2;
        this.distance = distance;
        this.person_id = person_id;
        getVolley();
    }

    public void getVolley() {
        try {
            jsonObject.put("keyword1", key1);
            jsonObject.put("keyword2", key2);
            jsonObject.put("distance", distance);
            jsonObject.put("person_id", person_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Успешно добавлен", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Невозможно добавить", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                if (response.statusCode == HttpStatus.SC_OK)
                    Log.i("Quantisk CODE OK", String.valueOf(response.statusCode));

                return super.parseNetworkResponse(response);
            }

            @Override
            protected void deliverResponse(JSONObject response) {
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