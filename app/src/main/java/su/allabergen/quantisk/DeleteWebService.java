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
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Rabat on 18.02.2016.
 */
public class DeleteWebService extends AsyncTask<String, Integer, Void> {

    ProgressDialog progressDialog;
    Context context;
    URL url;
    HttpURLConnection connection = null;
    String content;
    String error = null;
    int personToDelete;
    String username;
    String password;

    public DeleteWebService(Context context, int personToDelete, String username, String password) {
        progressDialog = new ProgressDialog(context);
        this.context = context;
        this.personToDelete = personToDelete;
        this.username = username;
        this.password = password;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
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
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.addRequestProperty("Authorization", loginBuilder.toString());

            JSONObject id = new JSONObject();
            id.put("id", personToDelete);

            outputStreamWriter = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            outputStreamWriter.write(id.toString());
            outputStreamWriter.flush();

            int responseCode = connection.getResponseCode();
            Log.i("Quantisk response code", String.valueOf(responseCode));

            buff = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            if (responseCode == HttpsURLConnection.HTTP_NO_CONTENT) {
                Toast.makeText(context, "Person has been deleted", Toast.LENGTH_LONG).show();
                String line = "";
                while ((line = buff.readLine()) != null) {
                    sb.append(line + "\n");
                }
                content = sb.toString();
            } else {
                Log.i("Quantisk", "Cannot delete");
            }
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
            Log.i("Quantisk error", error);
        } else {
            String output = "";
            String nameAdded = "";
            try {
                JSONObject jsonObject = new JSONObject(content);
                nameAdded = jsonObject.getString("name");
                if (!nameAdded.equals("")) {
                    AdminActivity.nameList.add(nameAdded);
                    AdminActivity.nameAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
