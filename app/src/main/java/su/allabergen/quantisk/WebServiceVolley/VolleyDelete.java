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
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rabat on 18.02.2016.
 */
public class VolleyDelete {
    private RequestQueue requestQueue;
    private JSONObject jsonObject;
    private Context context;
    private String url0;
    private String url;
    private String username;
    private String password;
    private int id;

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

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Успешно удален", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Невозможно удалить", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                if (response.statusCode == HttpStatus.SC_NO_CONTENT)
                    Log.i("Quantisk CODE OK", String.valueOf(response.statusCode));

                return Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(response));
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
