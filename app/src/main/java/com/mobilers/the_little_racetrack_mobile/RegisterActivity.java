package com.mobilers.the_little_racetrack_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobilers.the_little_racetrack_mobile.Service.UserService;

public class RegisterActivity extends AppCompatActivity {
    private Button btnregister,btnloin;
    private GlobalData globalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnregister = (Button)  findViewById(R.id.registerButton);
        btnloin = (Button)  findViewById(R.id.back_to_login);
        btnloin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,
                        LoginActivity.class);
                startActivity(intent);
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtUsername;
                EditText txtPassword;
                EditText txtcfPassword;
                txtUsername = findViewById(R.id.username_register);
                String username = txtUsername.getText().toString();
                TextView erruser = findViewById(R.id.error_user_regis);

                txtPassword = findViewById(R.id.password_register);
                String password = txtPassword.getText().toString();
                TextView errpassword = findViewById(R.id.error_password_regis);

                txtcfPassword = findViewById(R.id.confirm_password);
                String cfpassword = txtcfPassword.getText().toString();
                TextView errcfpassword = findViewById(R.id.error_comfim_password_regis);

                UserService userService = new UserService(getApplicationContext());

                boolean conditionsMet = true;

                if (username.length() == 0) {
                    erruser.setVisibility(View.VISIBLE);
                    erruser.setText("cannot be left blank");
                    conditionsMet = false;
                }
                if (username.length() < 4 && username.length() !=0) {
                    erruser.setVisibility(View.VISIBLE);
                    erruser.setText("username must have at least 4 characters");
                    conditionsMet = false;
                }
                if (password.length() == 0) {
                    errpassword.setVisibility(View.VISIBLE);
                    errpassword.setText("cannot be left blank");
                    conditionsMet = false;
                }
                if (password.length() < 6 && password.length()!=0) {
                    errpassword.setVisibility(View.VISIBLE);
                    errpassword.setText("password must have at least 6 characters");
                    conditionsMet = false;
                }
                if (cfpassword.length() == 0) {
                    errcfpassword.setVisibility(View.VISIBLE);
                    errcfpassword.setText("cannot be left blank");
                    conditionsMet = false;
                }

                if (!password.equals(cfpassword) && password.length() > 0 && cfpassword.length() > 0) {
                    errcfpassword.setVisibility(View.VISIBLE);
                    errcfpassword.setText("passwords do not match");
                    errpassword.setVisibility(View.VISIBLE);
                    errpassword.setText("passwords do not match");
                    conditionsMet = false;
                }

                if (conditionsMet) {
                    boolean checkuserExists = userService.userExists(username);
                    if (!checkuserExists) {
                        erruser.setVisibility(View.INVISIBLE);
                        userService.register(username, password);
                        // globalData.setCurrentUser(txtUsername.getText().toString());
                        finish();
                    } else {
                        erruser.setVisibility(View.VISIBLE);
                        erruser.setText("User already exists!");
                    }
                }

            }
        });

    }
}