package com.mobilers.the_little_racetrack_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mobilers.the_little_racetrack_mobile.Service.DataService;
import com.mobilers.the_little_racetrack_mobile.Service.IDataService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IDataService dataService = new DataService(getApplicationContext());
        if (dataService.userExists("duyduc")) {
            Toast.makeText(this, "duyduc already exists", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Register: " + dataService.register("duyduc", "123456"), Toast.LENGTH_LONG).show();
        }
        Toast.makeText(this, "Check login for duyduc: " + dataService.login("duyduc", "123456"), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Balance before: " + dataService.getBalance("duyduc"), Toast.LENGTH_SHORT).show();
        dataService.addBalance("duyduc", 100L);
        Toast.makeText(this, "Balance after: " + dataService.getBalance("duyduc"), Toast.LENGTH_SHORT).show();
    }
}