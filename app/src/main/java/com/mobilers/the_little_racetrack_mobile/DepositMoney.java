package com.mobilers.the_little_racetrack_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mobilers.the_little_racetrack_mobile.Service.DataService;
import com.mobilers.the_little_racetrack_mobile.Service.IDataService;

public class DepositMoney extends AppCompatActivity {
    Button btnAdd;
    EditText edtDepositNumnber;
    TextView textView2;
    private IDataService dataService;
    private GlobalData globalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_money);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        edtDepositNumnber = (EditText) findViewById(R.id.edtDepositNumber);
        // hiển thị số dư
        dataService = new DataService(getApplicationContext());
        textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setText((int) dataService.getBalance(globalData.getCurrentUser()));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataService = new DataService(getApplicationContext());
                globalData = GlobalData.getInstance();
                globalData.setCurrentUser("X");
                dataService.addBalance(globalData.getCurrentUser(), Long.parseLong(edtDepositNumnber.getText().toString()));
                textView2.setText((int) dataService.getBalance(globalData.getCurrentUser()));
            }
        });
    }
}