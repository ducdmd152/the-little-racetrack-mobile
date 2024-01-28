package com.mobilers.the_little_racetrack_mobile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobilers.the_little_racetrack_mobile.Constants.AuthenConstants;
import com.mobilers.the_little_racetrack_mobile.Model.User;
import com.mobilers.the_little_racetrack_mobile.Service.DataService;
import com.mobilers.the_little_racetrack_mobile.Service.IDataService;
import com.mobilers.the_little_racetrack_mobile.Service.UserService;

public class LoginActivity extends AppCompatActivity {
    private Button btnregister, btnlogin;
    private TextView textResult;
    private GlobalData globalData;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userService = new UserService(getApplicationContext());
        globalData = GlobalData.getInstance();

        btnregister = (Button) findViewById(R.id.CreateAccountButton);
        btnlogin = (Button) findViewById(R.id.loginButton);
        textResult = findViewById(R.id.error_login);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textResult.setVisibility(View.VISIBLE);
                textResult.setText("!! Username or password invallid");
                EditText txtUsername;
                EditText txtPassword;
                txtUsername = findViewById(R.id.username);
                txtPassword = findViewById(R.id.password);
                boolean checkLogin = userService.login(txtUsername.getText().toString()
                        , txtPassword.getText().toString());
                if (checkLogin) {
                    textResult.setVisibility(View.INVISIBLE);
                    String username = txtUsername.getText().toString();
                    globalData.setCurrentUser(username);
                    Log.i("login", "user: " + globalData.getCurrentUser());
                    Intent resultIntent = new Intent();
                    setResult(AuthenConstants.LOGIN_RESULT_CODE, resultIntent);
                    finish();
                }

            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,
                        RegisterActivity.class);
                startActivity(intent);
            }

        });

    }
}