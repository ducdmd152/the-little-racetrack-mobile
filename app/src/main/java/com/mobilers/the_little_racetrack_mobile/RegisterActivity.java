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
                TextView txterruser = findViewById(R.id.error_user_regis);

                txtPassword = findViewById(R.id.password_register);
                TextView txterrpassword = findViewById(R.id.error_password_regis);

                txtcfPassword = findViewById(R.id.confirm_password);
                TextView txterrcfpassword = findViewById(R.id.error_comfim_password_regis);

                UserService userService = new UserService(getApplicationContext());

                if (!txtcfPassword.getText().toString().equals(txtPassword.getText().toString())){
                    txterrcfpassword.setVisibility(View.VISIBLE);
                    txterrcfpassword.setText("passwords do not match");

                    txterrpassword.setVisibility(View.VISIBLE);
                    txterrpassword.setText("passwords do not match");
                }
                else {
               boolean checkuserExists= userService.userExists(txtUsername.getText().toString());
                if (!checkuserExists){
                    txterruser.setVisibility(View.INVISIBLE);
                    userService.register(txtUsername.getText().toString(),
                            txtPassword.getText().toString());
//                    globalData.setCurrentUser(txtUsername.getText().toString());
                    Intent intent = new Intent(RegisterActivity.this,
                            MainActivity.class);
                    startActivity(intent);
                }else {
                    txterruser.setVisibility(View.VISIBLE);
                    txterruser.setText("User is exists!!!");
                }
                }
            }
        });

    }
}