package com.mobilers.the_little_racetrack_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.mobilers.the_little_racetrack_mobile.Model.Car;
import com.mobilers.the_little_racetrack_mobile.Service.DataService;
import com.mobilers.the_little_racetrack_mobile.Service.IDataService;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private IDataService dataService;
    private GlobalData globalData;
    private String username;
    private TextView txtUsername;
    private TextView txtBalance;
    private List<Car> cars;
    private Button btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataService = new DataService(getApplicationContext());
        globalData = GlobalData.getInstance();
        globalData.setCurrentUser("duyduc");

    }
}