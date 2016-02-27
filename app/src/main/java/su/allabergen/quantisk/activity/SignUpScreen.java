package su.allabergen.quantisk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import su.allabergen.quantisk.R;
import su.allabergen.quantisk.model.Users;
import su.allabergen.quantisk.webServiceVolley.VolleyGet;
import su.allabergen.quantisk.webServiceVolley.VolleyPostUser;

import static su.allabergen.quantisk.activity.AdminActivity._nameList;
import static su.allabergen.quantisk.webServiceVolley.VolleyGet.*;

public class SignUpScreen extends AppCompatActivity implements View.OnClickListener {

    private ImageView logo;
    private EditText usernameField;
    private EditText passwordField;
    private Button signUpBtn;
    private TextView changeModeTextView;
    private RelativeLayout signUpRelativeLayout;

//    private SharedPreferences sharedPreferences;
//
//    private Set<String> tempUsername = new LinkedHashSet<>();
//    private Set<String> tempPassword = new LinkedHashSet<>();
//    private List<String> tempList2 = new ArrayList<>();
//
//    public static List<String> _tempList = new ArrayList<>();

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

//        sharedPreferences = this.getSharedPreferences("user_accounts", MODE_PRIVATE);
//        if (tempUsername.isEmpty() && tempPassword.isEmpty()) {
//            tempUsername = sharedPreferences.getStringSet("USERNAME", tempUsername);
//            tempPassword = sharedPreferences.getStringSet("PASSWORD", tempPassword);
//        }
//
//        if (checkForSimilarity(uname, upassword)) {
//            sharedPreferences.edit().remove("USERNAME").apply();
//            sharedPreferences.edit().remove("PASSWORD").apply();
//            tempUsername.add(uname);
//            tempPassword.add(upassword);
//            sharedPreferences.edit().putStringSet("USERNAME", tempUsername).apply();
//            sharedPreferences.edit().putStringSet("PASSWORD", tempPassword).apply();
//
//            Toast.makeText(this, "Пользователь успешно создан", Toast.LENGTH_SHORT).show();
//        }
    }

    private void checkUserAccount(String uname, String upassword) {

        if ((!uname.isEmpty() || uname.length() > 0) && (!upassword.isEmpty() || upassword.length() > 0)) {
            new VolleyGet(this, "https://api-quantisk.rhcloud.com/v1/users/", uname, upassword);
        } else {
            Toast.makeText(this, "Заполните поля", Toast.LENGTH_SHORT).show();
        }
        
        if (!_nameList.isEmpty()) {
            for (Users u : _userList0) {
                if (u.getLogin().equals(uname)) {
                    Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }

//        sharedPreferences = this.getSharedPreferences("user_accounts", MODE_PRIVATE);
//        if (tempUsername.isEmpty() && tempPassword.isEmpty()) {
//            tempUsername = sharedPreferences.getStringSet("USERNAME", tempUsername);
//            tempPassword = sharedPreferences.getStringSet("PASSWORD", tempPassword);
//        }
//
//        _tempList.clear();
//        tempList2.clear();
//        _tempList.addAll(tempUsername);
//        tempList2.addAll(tempPassword);
//
//        boolean isOK = false;
//        for (int i = 0; i < _tempList.size(); i++) {
//            if (_tempList.get(i).equals(uname) && tempList2.get(i).equals(upassword)) {
//                isOK = true;
//                break;
//            } else {
//                isOK = false;
//            }
//        }
//
//        if (isOK) {
//            Intent intent = new Intent(getApplicationContext(), UserActivity.class);
//            startActivity(intent);
//            finish();
//        } else if ((uname.equals("user1") && upassword.equals("qwerty1")) || (uname.equals("admin") && upassword.equals("admin"))) {
//            Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
//            startActivity(intent);
//            finish();
//        } else {
//            Toast.makeText(this, "Неправильное имя или пароль", Toast.LENGTH_SHORT).show();
//            return;
//        }
    }

//    private boolean checkForSimilarity(String uname, String upassword) {
//        if ((tempUsername.contains(uname) && tempPassword.contains(upassword))
//                || (uname.equals("admin") && upassword.equals("admin"))
//                || (uname.equals("user1") && upassword.equals("qwerty1"))) {
//            Toast.makeText(this, "Пользователь уже существует", Toast.LENGTH_SHORT).show();
//            return false;
//        } else if (tempUsername.isEmpty() || tempUsername.size() == 0 || tempPassword.isEmpty() || tempPassword.size() == 0) {
//            Toast.makeText(this, "Заполните поля", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        return true;
//    }

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
}
