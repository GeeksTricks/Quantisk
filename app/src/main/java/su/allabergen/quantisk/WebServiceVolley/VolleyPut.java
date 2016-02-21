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
public class VolleyPut {
    RequestQueue requestQueue;
    JSONObject jsonObject;
    Context context;
    String url0;
    String url;
    String username;
    String password;
    String nameToEdit;
    int id;

    public VolleyPut(Context context, String url, int id, String nameToEdit, String username, String password) {
        requestQueue = Volley.newRequestQueue(context);
        jsonObject = new JSONObject();
        this.context = context;
        this.username = username;
        this.password = password;
        this.nameToEdit = nameToEdit;
        this.id = id;
        this.url0 = url;
        this.url = url + this.id + "/";
        getVolley();
    }

    public void getVolley() {
        try {
            jsonObject.put("name", nameToEdit);
            Log.i("JSONOBJECT", jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Successfully Edited", Toast.LENGTH_SHORT).show();
//                          new VolleyGet(context, url0, "user1", "qwerty1");
                        Log.i("Quantisk onResponse", response.toString());
//                        new VolleyGet(url0, "user1", "qwerty1");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Could not edit", Toast.LENGTH_SHORT).show();
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
