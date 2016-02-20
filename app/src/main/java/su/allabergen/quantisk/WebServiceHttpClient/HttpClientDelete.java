package su.allabergen.quantisk.WebServiceHttpClient;

import android.util.Base64;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rabat on 20.02.2016.
 */
public class HttpClientDelete {
    String url;
    int id;
    StringBuilder loginBuilder;
    public HttpClientDelete(String url, int id, String username, String password) {
        this.url = url;
        this.id = id;

        byte[] loginBytes = (username + ":" + password).getBytes();

        loginBuilder = new StringBuilder()
                .append("Basic ")
                .append(Base64.encodeToString(loginBytes, Base64.DEFAULT));
    }

    public void getData() {
        List<NameValuePair> deleteParams = new ArrayList<>();
        deleteParams.add(new BasicNameValuePair("id", "id"));
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(deleteParams);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpDelete deleteRequest = new HttpDelete(url);
        deleteRequest.addHeader("Authorization", loginBuilder.toString());
        deleteRequest.setHeader("Accept", "application/json");
        deleteRequest.setHeader("Content-type", "application/json");
        deleteRequest.setHeader("Accept-Encoding", "gzip");
    }
}
