package su.allabergen.quantisk.webServiceVolley;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rabat on 19.02.2016.
 */
public class VolleyPutKeyword {
    private RequestQueue requestQueue;
    private JSONObject jsonObject;
    private Context context;
    private String url0;
    private String url;
    private String username;
    private String password;
    private String key1;
    private String key2;
    private int distance;
    private int id;

    public VolleyPutKeyword(Context context, String url, String key1, String key2, int distance, int id, String username, String password) {
        requestQueue = Volley.newRequestQueue(context);
        jsonObject = new JSONObject();
        this.context = context;
        this.username = username;
        this.password = password;
        this.key1 = key1;
        this.key2 = key2;
        this.distance = distance;
        this.id = id;
        this.url0 = url;
        this.url = url + this.id + "/";
        getVolley();
    }

    public void getVolley() {
        try {
            jsonObject.put("keyword1", key1);
            jsonObject.put("keyword2", key2);
            jsonObject.put("distance", distance);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Успешно изменен", Toast.LENGTH_SHORT).show();
                        Log.i("Quantisk onResponse", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Невозможно изменить", Toast.LENGTH_SHORT).show();
                        Log.i("Quantisk onErrorRes", error.toString());
                        error.printStackTrace();
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
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
