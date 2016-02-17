package su.allabergen.quantisk;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static su.allabergen.quantisk.AdminActivity.nameAdapter;
import static su.allabergen.quantisk.AdminActivity.nameList;
import static su.allabergen.quantisk.AdminActivity.siteAdapter;
import static su.allabergen.quantisk.AdminActivity.siteList;

/**
 * Created by Rabat on 18.02.2016.
 */
public class WSAdmin {

    private List<String> personFromWebService;
    private List<String> siteFromWebService;
    private ProgressDialog progressDialog;
    private String username, password;

    public WSAdmin(Context context, String username, String password, String urlName, String urlSite) {
        progressDialog = new ProgressDialog(context);
        this.username = username;
        this.password = password;
        new WebService().execute(urlName, urlSite);
    }

    public class WebService extends AsyncTask<String, Void, List<String>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected List<String> doInBackground(String... urls) {
            personFromWebService = getJSON(urls[0], username, password);
            siteFromWebService = getJSON(urls[1], username, password);

            return null;
        }

        @Override
        protected void onPostExecute(List<String> list) {
            super.onPostExecute(list);
            progressDialog.dismiss();
            nameList.addAll(getPersonFromWebService());
            nameAdapter.notifyDataSetChanged();
            siteList.addAll(getSiteFromWebService());
            siteAdapter.notifyDataSetChanged();
            personFromWebService.clear();
            siteFromWebService.clear();
        }

        protected List<String> getPersonFromWebService() {
            return personFromWebService;
        }

        protected List<String> getSiteFromWebService() {
            return siteFromWebService;
        }
    }

    public List<String> getJSON(String url1, String username, String password) {
        List<String> dataFromWebService = new ArrayList<>();
        String result = "";
        URL url;
        HttpURLConnection connection = null;
        InputStream is = null;
        InputStreamReader reader = null;

        byte[] loginBytes = (username + ":" + password).getBytes();
        StringBuilder loginBuilder = new StringBuilder()
                .append("Basic ")
                .append(Base64.encodeToString(loginBytes, Base64.DEFAULT));

        try {
            url = new URL(url1);
            connection = (HttpURLConnection) url.openConnection();

            connection.addRequestProperty("Authorization", loginBuilder.toString());

            is = connection.getInputStream();
            reader = new InputStreamReader(is);
            int data = reader.read();

            while (data != -1) {
                char current = (char) data;
                result += current;
                data = reader.read();
            }

            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonPart = jsonArray.getJSONObject(i);
                dataFromWebService.add(jsonPart.getString("name"));
            }

            return dataFromWebService;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
