package su.allabergen.quantisk.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import su.allabergen.quantisk.R;
import su.allabergen.quantisk.model.Users;
import su.allabergen.quantisk.webServiceVolley.VolleyPostUser;

import static su.allabergen.quantisk.activity.AdminActivity._nameList;

public class SignUpScreen extends AppCompatActivity implements View.OnClickListener {

    private ImageView logo;
    private EditText usernameField;
    private EditText passwordField;
    private Button signUpBtn;
    private TextView changeModeTextView;
    private RelativeLayout signUpRelativeLayout;

    public static List<Users> _userList0 = new ArrayList<>();

    private boolean isSignUp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        setLogo();
        initVariables();
        setOnClick();
    }

    public void signUpOnClick(View view) {
        String uname = usernameField.getText().toString();
        String upassword = passwordField.getText().toString();

        if (isSignUp)
            saveUserAccount(uname, upassword);
        else
            checkUserAccount(uname, upassword);
    }

    private void saveUserAccount(String uname, String upassword) {

        if ((!uname.isEmpty() || uname.length() > 0) && (!upassword.isEmpty() || upassword.length() > 0)) {
            new VolleyPostUser(this, "https://api-quantisk.rhcloud.com/v1/users/", uname, upassword);
        } else {
            Toast.makeText(this, "Заполните поля", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkUserAccount(String uname, String upassword) {

        if ((!uname.isEmpty() || uname.length() > 0) && (!upassword.isEmpty() || upassword.length() > 0)) {
            new VolleyGetUser(this, "https://api-quantisk.rhcloud.com/v1/users/", uname, upassword);
        } else {
            Toast.makeText(this, "Заполните поля", Toast.LENGTH_SHORT).show();
        }
    }

    private void setLogo() {
        ImageView imageView = (ImageView) findViewById(R.id.logo);
        imageView.requestLayout();
        Display display = getWindowManager().getDefaultDisplay();
        android.view.ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();

        int width = display.getWidth();
        layoutParams.height = (int) (width / 2.25);
        imageView.setLayoutParams(layoutParams);
    }

    private void initVariables() {
        logo = (ImageView) findViewById(R.id.logo);
        usernameField = (EditText) findViewById(R.id.usernameField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);
        changeModeTextView = (TextView) findViewById(R.id.changeModeTextView);
        signUpRelativeLayout = (RelativeLayout) findViewById(R.id.signUpRelativeLayout);
    }

    private void setOnClick() {
        changeModeTextView.setOnClickListener(this);
        logo.setOnClickListener(this);
        signUpRelativeLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.changeModeTextView) {
            if (isSignUp) {
                isSignUp = false;
                signUpBtn.setText("Войти");
                changeModeTextView.setText("Регистрация");
            } else {
                isSignUp = true;
                signUpBtn.setText("Регистрация");
                changeModeTextView.setText("Войти");
            }
        } else if (v.getId() == R.id.logo || v.getId() == R.id.signUpRelativeLayout) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    class VolleyGetUser {

        private RequestQueue requestQueue;
        private Context context;
        private String url;
        private String username;
        private String password;

        ProgressDialog progressDialog;

        public VolleyGetUser(Context context, String url, String username, String password) {
            requestQueue = Volley.newRequestQueue(context);
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            this.context = context;
            this.username = username;
            this.password = password;
            this.url = url;
            getVolley();
        }

        public void getVolley() {
            progressDialog.show();
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            // Get Users from Web Service

                            if (url.contains("users")) {
                                _userList0.clear();
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonPart = null;
                                    try {
                                        jsonPart = response.getJSONObject(i);
                                        Users user = new Users();

                                        user.setId(jsonPart.getInt("id"));
                                        user.setLogin(jsonPart.getString("login"));

                                        _userList0.add(user);
                                        progressDialog.dismiss();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        progressDialog.dismiss();
                                    }
                                }
                                _nameList.clear();
                                for (Users u : _userList0)
                                    _nameList.add(u.getLogin());

                                if (!_nameList.isEmpty()) {
                                    for (Users u : _userList0) {
                                            if (u.getLogin().equals(username)) {
                                            Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            error.printStackTrace();
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    String credentials = username + ":" + password;
                    String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    headers.put("Content-Type", "application/json, charset=UTF-8");
                    headers.put("Authorization", auth);
                    return headers;
                }
            };
            requestQueue.add(jsonArrayRequest);
        }
    }
}
