package su.allabergen.quantisk;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

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

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Rabat on 07.02.2016.
 */
public class PostWebService extends AsyncTask<String, Integer, Void> {

//    String urlToPost = "http://androidexample.com/media/webservice/JsonReturn.php";
//    String urlToPost2 = "/v1/dailyrank/?person_id=<person_id>&site_id=<site_id>&start_date=<start_date>&end_date=<end_date>";

    ProgressDialog progressDialog;
    Context context;
    URL url;
    HttpURLConnection connection = null;
    String content;
    String error = null;
    String data = "";
    String nameToSend;
    String username;
    String password;

    public PostWebService(Context context, String nameToSend, String username, String password) {
        this.context = context;
        this.nameToSend = nameToSend;
        progressDialog = new ProgressDialog(context);
        this.username = username;
        this.password = password;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();

        try {
            data += URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(nameToSend, "UTF-8");
            Log.i("data", data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(String... params) {
        BufferedReader buff = null;
        OutputStreamWriter outputStreamWriter = null;
        byte[] loginBytes = (username + ":" + password).getBytes();
        StringBuilder loginBuilder = new StringBuilder()
                .append("Basic ")
                .append(Base64.encodeToString(loginBytes, Base64.DEFAULT));

        try {
            url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.addRequestProperty("Authorization", loginBuilder.toString());

            Log.i("name", nameToSend);

            JSONObject uname = new JSONObject();
            uname.put("name", nameToSend);

            outputStreamWriter = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            outputStreamWriter.write(uname.toString());
            outputStreamWriter.flush();

            int responseCode = connection.getResponseCode();
            Log.i("response code", String.valueOf(responseCode));

            buff = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line = "";
                while ((line = buff.readLine()) != null) {
                    sb.append(line + "\n");
                }
                content = sb.toString();
            } else {
                Log.i("error", "Cannot connect");
            }

            Log.i("content", content);
        } catch (IOException e) {
            error = e.getMessage();
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            try {
                if (buff != null) {
                    buff.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
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
            String nameAdded = "";
            try {
                JSONObject jsonObject = new JSONObject(content);
                nameAdded = jsonObject.getString("name");
                Toast.makeText(context, "\"" + nameAdded + "\" has been added", Toast.LENGTH_LONG).show();
                output = "Name: " + nameAdded;
                if (!nameAdded.equals("")) {
                    AdminActivity.nameList.add(nameAdded);
                    AdminActivity.nameAdapter.notifyDataSetChanged();
                }

                Log.i("output", output);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
