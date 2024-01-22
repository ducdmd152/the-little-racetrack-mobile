package com.mobilers.the_little_racetrack_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mobilers.the_little_racetrack_mobile.Service.DataService;
import com.mobilers.the_little_racetrack_mobile.Service.IDataService;

public class DepositMoney extends AppCompatActivity {
    Button btnAdd;
    EditText edtDepositNumnber;
    private IDataService dataService;
    private GlobalData globalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_money);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        edtDepositNumnber = (EditText) findViewById(R.id.edtDepositNumber);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataService = new DataService(getApplicationContext());
                globalData = GlobalData.getInstance();
                globalData.setCurrentUser("duyduc");
                dataService.addBalance(globalData.getCurrentUser(), 100);
            }
        });
    }
}