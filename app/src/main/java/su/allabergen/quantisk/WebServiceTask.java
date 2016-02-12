//package su.allabergen.quantisk;
//
//import android.os.AsyncTask;
//import android.util.Log;
//import android.widget.ArrayAdapter;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
//import static su.allabergen.quantisk.AdminActivity.*;
//
///**
// * Created by Rabat on 12.02.2016.
// */
//
//public class WebServiceTask {
//
//    WebService service;
//    List<String> namesFromWS = new ArrayList<>();
//    ArrayAdapter<String> nameAdapter;
//
//    private String url;
//
//    public WebServiceTask(String url, ArrayAdapter<String> nameAdapter) {
//        this.url = url;
//        namesFromWS = getService();
//        this.nameAdapter = nameAdapter;
//    }
//
//    public List<String> getNamesFromWS() {
//        return namesFromWS;
//    }
//
//    public List<String> getService() {
//        service = new WebService();
//        service.execute(url);
//        return service.getNamesFromWebService();
//    }
//
//    public void updateListView() {
//        try {
//            nameList.addAll(getService());
//
//            nameAdapter.notifyDataSetChanged();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public class WebService extends AsyncTask<String, Void, Void> {
//
//        private List<String> namesFromWebService = new ArrayList<>();
//
//        @Override
//        protected Void doInBackground(String... urls) {
//            String result = "";
//            URL url;
//            HttpURLConnection connection = null;
//            InputStream is = null;
//            InputStreamReader reader = null;
//
//            try {
//                url = new URL(urls[0]);
//                connection = (HttpURLConnection) url.openConnection();
//                is = connection.getInputStream();
//                reader = new InputStreamReader(is);
//                int data = reader.read();
//
//                while (data != -1) {
//                    char current = (char) data;
//                    result += current;
//                    data = reader.read();
//                }
//
//                JSONArray jsonArray = new JSONArray(result);
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonPart = jsonArray.getJSONObject(i);
//                    namesFromWebService.add(jsonPart.getString("name"));
//                }
//
//                Log.i("result", result);
//
//            } catch (IOException | JSONException e) {
//                e.printStackTrace();
//            } finally {
//                if (connection != null) {
//                    connection.disconnect();
//                }
//                try {
//                    if (is != null) {
//                        is.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    if (reader != null) {
//                        reader.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            updateListView();
//        }
//
//        protected List<String> getNamesFromWebService() {
//            return namesFromWebService;
//        }
//    }
//
//}
