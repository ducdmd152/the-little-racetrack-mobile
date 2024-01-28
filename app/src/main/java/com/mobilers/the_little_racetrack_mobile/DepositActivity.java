package com.mobilers.the_little_racetrack_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilers.the_little_racetrack_mobile.Constants.AuthenConstants;
import com.mobilers.the_little_racetrack_mobile.Constants.DepositeContants;
import com.mobilers.the_little_racetrack_mobile.Service.DataService;
import com.mobilers.the_little_racetrack_mobile.Service.IDataService;
import com.mobilers.the_little_racetrack_mobile.Service.UserService;

public class DepositActivity extends AppCompatActivity {
    Button btnAdd;
    Button btnHome;
    EditText edtDepositNumnber;
    TextView textView2;
    TextView txtUsername;
    private UserService dataService;
    private GlobalData globalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnHome = (Button) findViewById(R.id.btnHome);
        edtDepositNumnber = (EditText) findViewById(R.id.edtDepositNumber);

        // hiển thị số dư
        globalData = GlobalData.getInstance();
        dataService = new UserService(getApplicationContext());
        txtUsername = (TextView) findViewById(R.id.txtUsername);
        txtUsername.setText("@" + globalData.getCurrentUser());
        textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setText(dataService.getBalance(globalData.getCurrentUser()) + "$");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtDepositNumnber.getText().toString().isEmpty() || edtDepositNumnber.getText().toString().equals("0")) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid deposit amount", Toast.LENGTH_SHORT).show();
                } else if (Long.parseLong(edtDepositNumnber.getText().toString()) + dataService.getBalance(globalData.getCurrentUser()) > 999999) {
                    Toast.makeText(getApplicationContext(), "Balance cannot be more than 1000000", Toast.LENGTH_LONG).show();
                    edtDepositNumnber.setText("");
                } else {
                    dataService.addBalance(globalData.getCurrentUser(), Long.parseLong(edtDepositNumnber.getText().toString()));
                    textView2.setText(dataService.getBalance(globalData.getCurrentUser()) + "$");
                    Toast.makeText(getApplicationContext(), edtDepositNumnber.getText().toString() + "$ added successfully", Toast.LENGTH_LONG).show();
                    edtDepositNumnber.setText("");
                }
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("[deposite]", "::Here-Deposit::");
                Intent resultIntent = new Intent();
                setResult(DepositeContants.BACK_RESULT_CODE, resultIntent);
                finish();
            }
        });
    }
}