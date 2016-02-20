package su.allabergen.quantisk.webServiceRetrofit;

import org.json.JSONArray;

import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by Rabat on 19.02.2016.
 */
public interface RetrofitAPI {
    @GET("/v1/persons/")
    @Headers({"Accept-Encoding: application/json", "user1:qwerty1"})
    void getFeed(Callback<JSONArray> jsonArray);
}
