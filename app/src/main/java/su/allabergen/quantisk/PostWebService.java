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
public class PostWebService extends AsyncTask<String, Integer, Void> {

    String urlToPost = "http://androidexample.com/media/webservice/JsonReturn.php";
    String urlToPost2 = "/v1/dailyrank/?person_id=<person_id>&site_id=<site_id>&start_date=<start_date>&end_date=<end_date>";

    ProgressDialog progressDialog;
//    ProgressBar progressBar;
    Context context;
    URL url;
    HttpURLConnection connection = null;
    String content;
    String error;
    String data = "";
    String nameToSend;

    public PostWebService(Context context, String nameToSend) {
        this.context = context;
        this.nameToSend = nameToSend;
        progressDialog = new ProgressDialog(context);
//        progressBar = new ProgressBar(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

//        progressDialog.setTitle("Please wait...");
        progressDialog.show();
//        progressBar.setVisibility(View.VISIBLE);
//        progressBar.setIndeterminate(true);
//        progressBar.setMax(100);

        try {
//            data += "&" + URLEncoder.encode("data", "UTF-8") + "=" + "hello";

            data += "?" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(nameToSend, "UTF-8");
            Log.i("data", data);

//            data += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8");
//            data += "&" + URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(Login, "UTF-8");
//            data += "&" + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(Pass, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(String... params) {
        BufferedReader buff = null;
        OutputStreamWriter outputStreamWriter = null;
//        int count = 0;
        try {
            url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);

            outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
            outputStreamWriter.write(data);
            outputStreamWriter.flush();

            buff = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = "";

            while ((line = buff.readLine()) != null) {
                sb.append(line);
                sb.append(System.getProperty("line.separator")); // same as \n - newline

//                publishProgress((int) ((count / (line.toCharArray().length)) * 100));
//                count++;
            }

            content = sb.toString();
            Log.i("content", content);
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
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
//        progressBar.setProgress(values[0]);
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
            JSONObject jsonResponse;
            try {
                JSONArray jsonArray = new JSONArray(content);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonPart = jsonArray.getJSONObject(i);
                    nameAdded = jsonPart.getString("name");
                }
                output = "Name: " + nameAdded;
                if (!nameAdded.equals("")) {
                    AdminActivity.nameList.add(nameAdded);
                }

//                jsonResponse = new JSONObject(content);
//                JSONArray jsonArray = jsonResponse.optJSONArray("Android");
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject child = jsonArray.getJSONObject(i);
//                    String name = child.getString("name");
//                    String number = child.getString("number");
//                    String time = child.getString("date_added");
//                    output = "Name: " + name + "\nNumber: " + number + "\nDate added: " + time + "\n";
//                }

                Log.i("output", output);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
