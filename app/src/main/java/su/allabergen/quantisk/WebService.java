package su.allabergen.quantisk;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Rabat on 12.02.2016.
 */
public class WebService extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... urls) {
        String result = "";
        URL url;
        HttpURLConnection connection = null;
        InputStream is = null;
        InputStreamReader reader = null;

        try {
            url = new URL(urls[0]);
            connection = (HttpURLConnection) url.openConnection();
            is = connection.getInputStream();
            reader = new InputStreamReader(is);
            int data = reader.read();

            while (data != -1) {
                char current = (char) data;
                result += current;
                data = reader.read();
            }

            Log.i("result", result);
            return result;
        } catch (IOException e) {
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

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonPart = jsonArray.getJSONObject(i);
                Log.i("id", String.valueOf(jsonPart.getInt("id")));
                Log.i("name", jsonPart.getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
