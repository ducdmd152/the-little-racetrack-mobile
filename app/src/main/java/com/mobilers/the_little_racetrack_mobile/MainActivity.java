package com.mobilers.the_little_racetrack_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mobilers.the_little_racetrack_mobile.service.DataService;
import com.mobilers.the_little_racetrack_mobile.service.IDataService;

public class MainActivity extends AppCompatActivity {
    private IDataService dataService;
    private GlobalData globalData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataService = new DataService(getApplicationContext());
        globalData = GlobalData.getInstance();
        globalData.setCurrentUser("duyduc");

    }
}