package com.mobilers.the_little_racetrack_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilers.the_little_racetrack_mobile.Model.Car;
import com.mobilers.the_little_racetrack_mobile.Service.DataService;
import com.mobilers.the_little_racetrack_mobile.Service.IDataService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private  IDataService dataService;
    private GlobalData globalData;
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

        // mapping
        txtUsername = findViewById(R.id.txtUsername);
        txtBalance = findViewById(R.id.txtBalance);
        btnStart = findViewById(R.id.btnStart);

        LinearLayout option1 = findViewById(R.id.option1);
        LinearLayout option2 = findViewById(R.id.option2);
        LinearLayout option3 = findViewById(R.id.option3);
        CheckBox cbCar1 = findViewById(R.id.cbCar1);
        CheckBox cbCar2 = findViewById(R.id.cbCar2);
        CheckBox cbCar3 = findViewById(R.id.cbCar3);
        EditText etAmountForCar1 = findViewById(R.id.etAmountForCar1);
        EditText etAmountForCar2 = findViewById(R.id.etAmountForCar2);
        EditText etAmountForCar3 = findViewById(R.id.etAmountForCar3);
        SeekBar sbCar1 = findViewById(R.id.sbCar1);
        SeekBar sbCar2 = findViewById(R.id.sbCar2);
        SeekBar sbCar3 = findViewById(R.id.sbCar3);
        cars = new ArrayList<>();
        cars.add(new Car(option1,  cbCar1, etAmountForCar1, sbCar1, null));
        cars.add(new Car(option2, cbCar2, etAmountForCar2, sbCar2, null));
        cars.add(new Car(option3, cbCar3, etAmountForCar3, sbCar3, null));


        // initing
        init();
        reset();
    }

    private void init() {
        // init data
        txtUsername.setText("@" + globalData.getCurrentUser());
        txtBalance.setText("Balance: " + dataService.getBalance(globalData.getCurrentUser()) + "$");

        // events
        for (Car car : cars) {
            // Make seekbars unable to be changed when touching
            car.getSeekBar().setOnTouchListener((v, event) -> true);
            CheckBox checkBox = car.getCheckBox();
            car.getCheckBoxContainer().setOnTouchListener((v, event) -> {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_UP:
                        checkBox.setChecked(!checkBox.isChecked());
                        break;
                }
                return true;
            });

            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                car.getEtAmountForCar().setEnabled(isChecked);
//            if (isChecked && etAmountForCar1.getText().length() == 0)
//                etAmountForCar1.setText("1"); // min = 1
                updateBtnStartEnabled();
            });
        }

        btnStart.setOnClickListener(v -> {
            if (btnStart.getText().toString().toLowerCase().equals("reset")) {
                reset();
                return;
            }

            btnStart.setEnabled(false);
            for (Car car : cars) {
                car.getCheckBoxContainer().setEnabled(false);
                car.getCheckBox().setEnabled(false);
                car.getEtAmountForCar().setEnabled(false);
            }

            Collections.shuffle(cars);

            Car rank1 = cars.get(0);
            Car rank2 = cars.get(1);
            Car rank3 = cars.get(2);

            animateProgression(100, 1000, findViewById(rank1.getSeekBar().getId()));
            animateProgression(100, 1200,findViewById(rank2.getSeekBar().getId()));
            animateProgression(100, 1500, findViewById(rank3.getSeekBar().getId()));

            new Handler().postDelayed(() -> {
                btnStart.setText("RESET");
                btnStart.setEnabled(true);
            }, 1600);
        });
    }

    private void reset() {
        btnStart.setText("START");
        btnStart.setEnabled(false);
        for (Car car : cars) {
            car.getCheckBox().setEnabled(true);
            car.getCheckBox().setChecked(false);
            car.getSeekBar().setProgress(0);
        }
    }

    private void updateBtnStartEnabled() {
        boolean status = false;
        for (Car car : cars) {
            status = status || car.getCheckBox().isChecked();
        }
        btnStart.setEnabled(status);
    }

    private void animateProgression(int progress, int duration, SeekBar seekBar) {
        final ObjectAnimator animation = ObjectAnimator.ofInt(seekBar, "progress", 0, progress);
        animation.setDuration(duration);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
        seekBar.clearAnimation();
    }
}