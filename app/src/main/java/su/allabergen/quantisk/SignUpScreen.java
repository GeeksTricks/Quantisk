package su.allabergen.quantisk;

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

public class SignUpScreen extends AppCompatActivity implements View.OnClickListener {

    ImageView logo;
    EditText usernameField;
    EditText passwordField;
    EditText emailField;
    Button signUpBtn;
    TextView changeModeTextView;
    RelativeLayout signUpRelativeLayout;

    boolean isSignUp = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        setLogo();
        initVariables();
        changeModeTextView.setOnClickListener(this);
        logo.setOnClickListener(this);
        signUpRelativeLayout.setOnClickListener(this);
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
        emailField = (EditText) findViewById(R.id.emailField);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);
        changeModeTextView = (TextView) findViewById(R.id.changeModeTextView);
        signUpRelativeLayout = (RelativeLayout) findViewById(R.id.signUpRelativeLayout);
    }

    public void signUpOnClick(View view) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.changeModeTextView) {
            if (isSignUp) {
                isSignUp = false;
                emailField.setVisibility(View.INVISIBLE);
                signUpBtn.setText("Log In");
                changeModeTextView.setText("Sign Up");
            } else {
                isSignUp = true;
                emailField.setVisibility(View.VISIBLE);
                signUpBtn.setText("Sign Up");
                changeModeTextView.setText("Log In");
            }
        } else if (v.getId() == R.id.logo || v.getId() == R.id.signUpRelativeLayout) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
