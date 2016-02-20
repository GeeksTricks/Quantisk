package su.allabergen.quantisk.activity;

import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import su.allabergen.quantisk.R;

public class SignUpScreen extends AppCompatActivity implements View.OnClickListener {

    ImageView logo;
    EditText usernameField;
    EditText passwordField;
    Button signUpBtn;
    TextView changeModeTextView;
    RelativeLayout signUpRelativeLayout;

    SharedPreferences sharedPreferences;

    private static Set<String> tempUsername = new LinkedHashSet<>();
    private static Set<String> tempPassword = new LinkedHashSet<>();
    public static List<String> tempList = new ArrayList<>();
    private static List<String> tempList2 = new ArrayList<>();

    boolean isSignUp = true;

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
        sharedPreferences = this.getSharedPreferences("user_accounts", MODE_PRIVATE);
        if (tempUsername.isEmpty() && tempPassword.isEmpty()) {
            tempUsername = sharedPreferences.getStringSet("USERNAME", tempUsername);
            tempPassword = sharedPreferences.getStringSet("PASSWORD", tempPassword);
        }

        if (checkForSimilarity(uname, upassword)) {
            sharedPreferences.edit().remove("USERNAME").apply();
            sharedPreferences.edit().remove("PASSWORD").apply();
            tempUsername.add(uname);
            tempPassword.add(upassword);
            sharedPreferences.edit().putStringSet("USERNAME", tempUsername).apply();
            sharedPreferences.edit().putStringSet("PASSWORD", tempPassword).apply();

            Toast.makeText(this, "User successfully added", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkUserAccount(String uname, String upassword) {
        sharedPreferences = this.getSharedPreferences("user_accounts", MODE_PRIVATE);
        if (tempUsername.isEmpty() && tempPassword.isEmpty()) {
            tempUsername = sharedPreferences.getStringSet("USERNAME", tempUsername);
            tempPassword = sharedPreferences.getStringSet("PASSWORD", tempPassword);
        }

        tempList.clear();
        tempList2.clear();
        tempList.addAll(tempUsername);
        tempList2.addAll(tempPassword);

        boolean isOK = false;
        for (int i = 0; i < tempList.size(); i++) {
            if (tempList.get(i).equals(uname) && tempList2.get(i).equals(upassword)) {
                isOK = true;
                break;
            } else {
                isOK = false;
            }
        }

        if (isOK) {
            Intent intent = new Intent(getApplicationContext(), UserActivity.class);
            startActivity(intent);
            finish();
        } else if (uname.equals("admin") && upassword.equals("admin")) {
            Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Incorrect username or password.\nPlease, try again!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private boolean checkForSimilarity(String uname, String upassword) {
        if ((tempUsername.contains(uname) && tempPassword.contains(upassword))
                || (uname.equals("admin") && upassword.equals("admin"))) {
            Toast.makeText(this, "Account already taken", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
                signUpBtn.setText("Log In");
                changeModeTextView.setText("Sign Up");
            } else {
                isSignUp = true;
                signUpBtn.setText("Sign Up");
                changeModeTextView.setText("Log In");
            }
        } else if (v.getId() == R.id.logo || v.getId() == R.id.signUpRelativeLayout) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
