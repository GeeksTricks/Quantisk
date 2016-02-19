package su.allabergen.quantisk;

import org.json.JSONArray;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Rabat on 19.02.2016.
 */
public class RetrofitGet {
    Retrofit adapter;

    public RetrofitGet(String url) {
        adapter = new Retrofit.Builder()
                .baseUrl(url)
                .build();
        getData();
    }

    public void getData() {
        RetrofitAPI api = adapter.create(RetrofitAPI.class);
        api.getFeed(new Callback<JSONArray>() {
            @Override
            public void onResponse(Call<JSONArray> call, Response<JSONArray> response) {

            }

            @Override
            public void onFailure(Call<JSONArray> call, Throwable t) {

            }
        });
    }
}
