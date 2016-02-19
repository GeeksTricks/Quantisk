package su.allabergen.quantisk;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

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
import java.util.Map;

/**
 * Created by Rabat on 18.02.2016.
 */
public class VolleyDelete {
    RequestQueue requestQueue;
    JSONObject jsonObject;
    Context context;
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
        this.url = url;
        this.id = id + 1;
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
                        Toast.makeText(context, "DELETED Person with ID = " + id, Toast.LENGTH_SHORT).show();
                        Log.i("Quantisk response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Could not delete Person", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
//                headers.put("USERNAME", username);
//                headers.put("PASSWORD", password);
                String credentials = username + ":" + password;
                String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                headers.put("Content-Type", "application/json, charset=UTF-8");
                headers.put("Authorization", auth);
                Log.i("Quantisk header", headers.toString());
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}
