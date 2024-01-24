package com.mobilers.the_little_racetrack_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
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
    private IDataService dataService;
    private GlobalData globalData;
    private String username;
    private TextView txtUsername;
    private TextView txtBalance;
    private List<Car> cars;
    private Button btnStart;
    private Button btnAddMore;
    private Button btnTutorial;
    private final String REQUIRE = "Require";

    private MediaPlayer mediaPlayerWait, mediaplayerStart, mediaPlayerWin, mediaPlayerCheck, mediaPlayerLoose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sound setting
        mediaplayerStart = MediaPlayer.create(this, R.raw.racing1s);
        mediaPlayerWait = MediaPlayer.create(this, R.raw.wait);
        mediaPlayerWait.setLooping(true);
        mediaPlayerWait.start();
        mediaPlayerWin = MediaPlayer.create(this, R.raw.claps1s);
        mediaPlayerLoose = MediaPlayer.create(this, R.raw.loose);


        dataService = new DataService(getApplicationContext());
        globalData = GlobalData.getInstance();
        // tmp code // wating for authentication
        globalData.setCurrentUser("duyduc");
        if (dataService.userExists("duyduc") == false) {
            dataService.register("duyduc", "123456");
            dataService.setBalance("duyduc", 1000);
        }
        // end of ...
        username = globalData.getCurrentUser();

        // pre-checking
        checkAuthenticated();
        checkExistBalance();

        // mapping
        txtUsername = findViewById(R.id.txtUsername);
        txtBalance = findViewById(R.id.txtBalance);
        btnStart = findViewById(R.id.btnStart);
        btnAddMore = findViewById(R.id.btnAddMore);
        btnTutorial = findViewById(R.id.btnTutorial);

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
        cars.add(new Car("Car 1", option1, cbCar1, etAmountForCar1, sbCar1, null));
        cars.add(new Car("Car 2", option2, cbCar2, etAmountForCar2, sbCar2, null));
        cars.add(new Car("Car 3", option3, cbCar3, etAmountForCar3, sbCar3, null));


        // initing
        init();
        reset();
    }

    private void init() {
        // init data
        txtUsername.setText("@" + globalData.getCurrentUser());
        txtBalance.setText("Balance: " + dataService.getBalance(globalData.getCurrentUser()) + "$");

        // events
        btnAddMore.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DepositActivity.class);
            startActivity(intent);
        });

        btnTutorial.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
            startActivity(intent);
        });
//
//        btnLogOut.setOnClickListener(v -> {
//            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
//            startActivity(intent);
//        });

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
                if (isChecked) {
                    // Phát âm thanh khi ô đánh dấu được kiểm tra
                    playCheckSound();
                }
            });

        }

        btnStart.setOnClickListener(v -> {
            if (btnStart.getText().toString().toLowerCase().equals("reset")) {
                reset();
                return;
            }

            if (checkAmountOfBetting() == false) {
                return;
            }

            if (checkBalanceForBetting() == false) {
                return;
            }
            playStartSound();

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
            animateProgression(100, 1300, findViewById(rank2.getSeekBar().getId()));
            animateProgression(100, 1500, findViewById(rank3.getSeekBar().getId()));

            new Handler().postDelayed(() -> {
                Toast.makeText(this, rank1.getName() + " is the 1st racer!", Toast.LENGTH_SHORT).show();
            }, 1000);

            new Handler().postDelayed(() -> {
                Toast.makeText(this, rank2.getName() + " is the 2nd racer!", Toast.LENGTH_SHORT).show();
            }, 1200);

            new Handler().postDelayed(() -> {
                Toast.makeText(this, rank1.getName() + " is the 3rd racer!", Toast.LENGTH_SHORT).show();
            }, 1500);

            new Handler().postDelayed(() -> {
                btnStart.setText("RESET");
                btnStart.setEnabled(true);
            }, 1600);

            new Handler().postDelayed(() -> {
                int changedAmount = 0;
                boolean atLeastOneWin = false;
                if (rank1.getCheckBox().isChecked()) {
                    int betAmount = Integer.parseInt(rank1.getEtAmountForCar().getText().toString());
                    dataService.addBalance(username, betAmount);
                    changedAmount += betAmount;
                    playWinSound();
                    atLeastOneWin = true;
                }

                if (rank2.getCheckBox().isChecked()) {
                    int betAmount = Integer.parseInt(rank2.getEtAmountForCar().getText().toString());
                    dataService.minusBalance(username, betAmount);
                    changedAmount -= betAmount;
                    if (!atLeastOneWin) {
                        playLooseSound(); // Chỉ gọi playLooseSound() nếu chưa có xe thắng
                    }

                }

                if (rank3.getCheckBox().isChecked()) {
                    int betAmount = Integer.parseInt(rank3.getEtAmountForCar().getText().toString());
                    dataService.minusBalance(username, betAmount);
                    changedAmount -= betAmount;
                    if (!atLeastOneWin) {
                        playLooseSound(); // Chỉ gọi playLooseSound() nếu chưa có xe thắng
                    }

                }


                txtBalance.setText("Balance: " + dataService.getBalance(username) + "$");

                // INFORMING
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                String message = (changedAmount < 0 ? " - $" : " + $") + Math.abs(changedAmount) + "\nYour new balance is $" + dataService.getBalance(username) + ".";

                TextView titleTextView = new TextView(MainActivity.this);
                titleTextView.setText("Round done!");
                titleTextView.setTextSize(20);
                titleTextView.setGravity(Gravity.CENTER);

                TextView messageTextView = new TextView(MainActivity.this);
                messageTextView.setText(message);
                messageTextView.setGravity(Gravity.CENTER);
                messageTextView.setPadding(20, 20, 20, 20);
                builder.setCustomTitle(titleTextView);
                builder.setView(messageTextView);
                builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    checkExistBalance();
                });
                builder.setCancelable(false);
                AlertDialog alertDialog = builder.create();
                new Handler().postDelayed(() -> {
                    alertDialog.show();
                }, 700);


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

    private void checkAuthenticated() {
        if (GlobalData.getInstance().isAuthenticated() == false) {
//            Intent intent = new Intent(MainActivity.this, Login.class);
//            startActivity(intent);
        }
    }

    private void checkExistBalance() {
        if (dataService.getBalance(username) <= 0) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("You are out of money!")
                    .setMessage("Please deposit more money to continue.")
                    .setPositiveButton("Deposit", (d, w) -> {
                        Intent intent = new Intent(MainActivity.this, DepositActivity.class);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("Quit game", (d, w) -> {
                        finish();
                        System.exit(0);
                    })
                    .setCancelable(false)
                    .show();
        }
    }

    private boolean checkAmountOfBetting() {
        for (Car car : cars) {
            if (car.getCheckBox().isChecked()) {
                String amountStr = car.getEtAmountForCar().getText().toString();
                if (amountStr.isEmpty()) {
                    car.getEtAmountForCar().setError(REQUIRE);
                    return false;
                } else {
                    int amount = Integer.parseInt(amountStr);
                    if (amount < 1) {
                        Toast.makeText(this, "Please enter betting amount for " + car.getName().toLowerCase() + " equal to or greater than 1!", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            }

        }
        return true;
    }

    private boolean checkBalanceForBetting() {
        int totalBetAmount = 0;

        for (Car car : cars) {
            if (car.getCheckBox().isChecked()) {
                String amountStr = car.getEtAmountForCar().getText().toString();

                if (amountStr.isEmpty()) {
                    car.getEtAmountForCar().setError(REQUIRE);
                    return false;
                } else {
                    int amount = Integer.parseInt(amountStr);
                    totalBetAmount += amount;
                }
            }
        }

        int currentBalance = (int) dataService.getBalance(username);

        if (totalBetAmount > currentBalance) {
            Toast.makeText(this, "Not enough balance for the total bet amount!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private void playWinSound() {
        mediaPlayerWin.start();
    }

    private void playLooseSound() {
        mediaPlayerLoose.start();
    }

    private void playStartSound() {
        mediaplayerStart.start();
    }


    private void playCheckSound() {
        mediaPlayerCheck = MediaPlayer.create(this, R.raw.checkbox);
        mediaPlayerCheck.start();
        mediaPlayerCheck.setOnCompletionListener(MediaPlayer::release);


    }
}