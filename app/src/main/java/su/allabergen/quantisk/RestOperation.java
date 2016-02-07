package su.allabergen.quantisk;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Rabat on 07.02.2016.
 */
public class RestOperation extends AsyncTask<String, Void, Void> {
    Context context;
    URL url;
    HttpURLConnection connection;
    String content;
    String error;
    String data;
    ProgressDialog progressDialog;

    public RestOperation(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog.setTitle("Please wait...");
        progressDialog.show();

        try {
            data += "&" + URLEncoder.encode("data", "UTF-8") + "=" + "hello";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(String... params) {
        BufferedReader buff = null;
        try {
            url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
            outputStreamWriter.write(data);
            outputStreamWriter.flush();

            buff = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line = null;

            while ((line = buff.readLine()) != null) {
                sb.append(line);
                sb.append(System.getProperty("line.separator"));
            }

            content = sb.toString();
        } catch (IOException e) {
            error = e.getMessage();
            e.printStackTrace();
        } finally {
            try {
                if (buff != null) {
                    buff.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        progressDialog.dismiss();

        if (error != null) {
            Log.i("error", error);
        } else {
            Log.i("server data received", content);

            String output = "";
            JSONObject jsonResponse;
            try {
                jsonResponse = new JSONObject(content);
                JSONArray jsonArray = jsonResponse.optJSONArray("Android");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject child = jsonArray.getJSONObject(i);
                    String name = child.getString("name");
                    String number = child.getString("number");
                    String time = child.getString("date_added");
                    output = "Name: " + name + "\nNumber: " + number + "\nDate added: " + time + "\n";
                }

                Log.i("output", output);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
